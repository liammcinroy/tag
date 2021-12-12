package org.plstudio.toposcala

import org.plstudio.geometa.Funcs._
import org.plstudio.geometa.Traits._

object Playground {
  def main(args: Array[String]): Unit = {
    // some dumb wrong syntax [for testing purposes]
    val a: Int = 1
    println(a)
    val b = detectFunc(a)
    println(b)

    // formula(A, B, C)

    import org.plstudio.geometa.macros.dummy.DummyMacros._
    println(s"${materialize1[Int](1)}")
  }

  // def formula(foo: Super*) = {}
}
