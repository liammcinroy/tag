package org.plstudio.geometa

import scala.tools.nsc
import nsc.Global
import nsc.Phase
import nsc.plugins.Plugin
import nsc.plugins.PluginComponent

class DummyPlugin(val global: Global) extends Plugin {
  import global._

  val name = "dummy"
  val description = "dummy test plugin"
  val components = List[PluginComponent](Component)

  private object Component extends PluginComponent {
    val global: DummyPlugin.this.global.type = DummyPlugin.this.global
    val runsAfter = List[String]("refchecks")
    val phaseName = DummyPlugin.this.name
    def newPhase(_prev: Phase) = new DummyPluginPhase(_prev)
    class DummyPluginPhase(prev: Phase) extends StdPhase(prev) {
      override def name = DummyPlugin.this.name
      def apply(unit: CompilationUnit): Unit = {
        for ( tree @ Apply(Select(rcvr, nme.DIV), List(Literal(Constant(0)))) <- unit.body
             if rcvr.tpe <:< definitions.IntClass.tpe)
          {
            global.reporter.error(tree.pos, "definitely division by zero")
          }
      }
    }
  }
}
