package org.plstudio.toposcala

import org.plstudio.geometa.Funcs._

object Playground {
  def main(args: Array[String]): Unit = {
    val a: Int = 1
    println(s"source is 1: $a")
    val b = detectFunc(a)
    println(s"and detectFunc: $b")
  }
}
