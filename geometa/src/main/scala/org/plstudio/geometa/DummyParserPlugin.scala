package org.plstudio.geometa

import scala.reflect.api.Universe

import scala.tools.nsc
import nsc.Global
import nsc.Phase
import nsc.transform._
import nsc.plugins.Plugin
import nsc.plugins.PluginComponent

object Traits {

  trait Super {}

  trait And[A, B] {}

}



class DummyParserPlugin(val global: Global) extends Plugin {
  import global._

  val name = "dummy parser"
  val description = "dummy parser test plugin"
  val components = List[PluginComponent](Component)

  private object Component extends PluginComponent with Transform {
    val global: DummyParserPlugin.this.global.type = DummyParserPlugin.this.global
    val runsAfter = List[String]("parser")
    val phaseName = DummyParserPlugin.this.name
    override def newTransformer(unit: CompilationUnit) = new DummyTransformer(unit)

    val TraitsSym = symbolOf[Traits.type].module

    class DummyTransformer(unit: CompilationUnit) extends Transformer {

      def unnest(seq: List[Tree], combin: Tree, built: Tree = q""): Tree = seq.size match {
        case 0 => built
        case 1 => built match {
          case q"" => q"${seq.last}"
          case _ => q"$combin [ ${seq.last}, $built ]"
        }
        case _ => built match {
          case q"" => unnest(seq.init.init, combin, q"$combin [ ${seq.init.last}, ${seq.last} ]")
          case _ => unnest(seq.init, combin, q"$combin [ ${seq.last}, $built ]")
        }
        }

      override def transform(tree: Tree): Tree = tree match {
        case q"And[..$tpes]" => {
          println(s"nesting ..$tpes")
          unnest(tpes, q"${Ident("And")}")
        }
        case _ => {
          println(s"$tree")
          super.transform(tree)
        }
      }
    }
  }
}
