package org.plstudio.geometa.macros.dummy

import scala.language.experimental.macros
import scala.reflect.macros.whitebox.Context

trait DummyT[T] {}

trait SuperT {}
trait A extends SuperT {}
trait B extends SuperT {}

class ImplDummyFundep(val c: Context) {

  // This just extends
  //
  def dummyFundep[T : c.WeakTypeTag](foo: c.Expr[T])(implicit tt: c.WeakTypeTag[T]): c.Expr[T] = {
    import c.universe._
    c.Expr(q"$foo.asInstanceOf[$tt with DummyT[$tt]]")
  }

  // This normalizes values of T to always be T with A with B
  //
  def lessDummyFundep[T : c.WeakTypeTag](foo: c.Expr[T]): c.Expr[T] = {
    import c.universe._
    val tt = weakTypeOf[T]
    val tAB = weakTypeOf[T with A with B]
    val tA = weakTypeOf[T with A]
    val tB = weakTypeOf[T with B]
    if (tt =:= tAB) c.Expr(q"$foo.asInstanceOf[$tt]")
    else if (tt <:< tA) c.Expr(q"$foo.asInstanceOf[$tB]")
    else if (tt <:< tB) c.Expr(q"$foo.asInstanceOf[$tA]")
    else c.Expr(q"$foo.asInstanceOf[$tAB]")
  }

  // This materializes a T with the given traits as specified by tpes
  // TODO
  //
  def notDumbFundep[T : c.WeakTypeTag](tpes: c.Expr[SuperT]*)(constr: c.Expr[Unit => T]): c.Expr[T] = {
    import c.universe._
    val tt = weakTypeOf[T]
    val tAB = weakTypeOf[T with A with B]
    val tA = weakTypeOf[T with A]
    val tB = weakTypeOf[T with B]
    c.Expr(q"$constr.apply()")
  }

  // Endows the new type with whatever extensions we already know
  //
  def typeFundep1[T1 : c.WeakTypeTag](t1: c.Expr[T1]): c.Expr[SuperT] = {
    import c.universe._
    val anon = newTypeName(c.fresh())
    val supT = weakTypeOf[SuperT]
    val supTAB = weakTypeOf[A with B]
    val supTA = weakTypeOf[A]
    val supTB = weakTypeOf[B]

    val tt1 = weakTypeOf[T1]

    val (ext, ext2) = (
      tt1 <:< supTAB,
      tt1 <:< supTA,
      tt1 <:< supTB
    ) match {
      case (true, _, _) => (supTA, Some(supTB))
      case (_, true, _) => (supTA, None)
      case (_, _, true) => (supTB, None)
      case _ => (supT, None)
    }

    val anonDef =
      ext2.map { e => q"class $anon extends $ext with $e {}" }
        .getOrElse(q"class $anon extends $ext {}")
    c.Expr[SuperT](
      q"""
        {
          $anonDef
          new $anon()
        }
       """
    )
  }

  // Endows the new type with whatever extensions we already know if both arguments satisfy it
  //
  def typeFundep2[T1 : c.WeakTypeTag, T2 : c.WeakTypeTag](t1: c.Expr[T1], t2: c.Expr[T2]): c.Expr[SuperT] = {
    import c.universe._
    val anon = newTypeName(c.fresh())
    val supT = weakTypeOf[SuperT]
    val supTAB = weakTypeOf[A with B]
    val supTA = weakTypeOf[A]
    val supTB = weakTypeOf[B]

    val tt1 = weakTypeOf[T1]
    val tt2 = weakTypeOf[T2]

    val (ext, ext2) = (
      tt1 <:< supTAB && tt2 <:< supTAB,
      tt1 <:< supTA && tt2 <:< supTA,
      tt1 <:< supTB && tt2 <:< supTB
    ) match {
      case (true, _, _) => (supTA, Some(supTB))
      case (_, true, _) => (supTA, None)
      case (_, _, true) => (supTB, None)
      case _ => (supT, None)
    }

    val anonDef =
      ext2.map { e => q"class $anon extends $ext with $e {}" }
        .getOrElse(q"class $anon extends $ext {}")
    c.Expr[SuperT](
      q"""
        {
          $anonDef
          new $anon()
        }
       """
    )
  }

}

object DummyMacros {
  implicit def dummyFundep[T](foo: T): T = macro ImplDummyFundep.dummyFundep[T]
  implicit def lessDummyFundep[T](foo: T): T = macro ImplDummyFundep.lessDummyFundep[T]
  implicit def notDumbFundep[T](tpes: SuperT*)(constr: Unit => T): T = macro ImplDummyFundep.notDumbFundep[T]

  implicit def materialize[T1](t1: T1): SuperT = macro ImplDummyFundep.typeFundep1[T1]
  implicit def materialize[T1, T2](t1: T1, t2: T2): SuperT = macro ImplDummyFundep.typeFundep2[T1, T2]
}
