package org.plstudio.geometa

import scala.reflect.api.Universe

import scala.tools.nsc
import nsc.Global
import nsc.Phase
import nsc.transform._
import nsc.plugins.Plugin
import nsc.plugins.PluginComponent

class Funcs
object Funcs {

  def detectFunc(a: Int): Int = a

  def replaceFunc(a: Int): Int = a + 1

}

class DummyAnalyzerPlugin(val global: Global) extends Plugin {
  import global._

  val name = "dummy analyzer"
  val description = "dummy analyzer test plugin"
  val components = List[PluginComponent](Component)

  private object Component extends PluginComponent with Transform {
    val global: DummyAnalyzerPlugin.this.global.type = DummyAnalyzerPlugin.this.global
    val runsAfter = List[String]("parser")
    val phaseName = DummyAnalyzerPlugin.this.name
    override def newTransformer(unit: CompilationUnit) = new DummyTransformer(unit)

    val FuncsSym = symbolOf[Funcs].companionModule

    class DummyTransformer(unit: CompilationUnit) extends Transformer {
      override def transform(tree: Tree): Tree = tree match {
        case q"val $x: Int = $y" => {
          println(s"changing $y to add another")
          q"val $x: Int = $y + 1"
        }
        case q"detectFunc($x)" => {
          println(s"changing detectFunc to replaceFunc")
          q"replaceFunc($x)"
        }
        case _ => super.transform(tree)
      }
    }
  }
}
