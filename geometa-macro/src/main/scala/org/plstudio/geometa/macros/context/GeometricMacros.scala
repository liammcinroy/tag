// GeometricMacros.scala
//
// This file contains the macros that allow Geometa to materialize types for formulas and extend them as
// observational classes or not.
//
// Note that much of this code was generated 

package org.plstudio.geometa.macros.context

import scala.language.experimental.macros
import scala.reflect.macros.whitebox.Context

import org.plstudio.geometa.logic.context._

// Mark: - Implicit definitions
//
class GeometricAndBundle(val c: Context) {

  def basic[
    T <: GeomTheory : c.WeakTypeTag,
    C <: GTBaseType[T] : c.WeakTypeTag,
    F <: GTFormula[T, C] : c.WeakTypeTag
  ](
    t : c.Expr[T],
    con: c.Expr[C],
    form: c.Expr[F]
  ) = {
    import c.universe._
    q"$t"
  }

  def observationalAnd2[
    T <: GeomTheory : c.WeakTypeTag,
    C <: GTBaseType[T] : c.WeakTypeTag,
    F1 <: GTFormula[T, C] : c.WeakTypeTag,
    F2 <: GTFormula[T, C] : c.WeakTypeTag
  ](
    formula1: c.Expr[F1],
    formula2: c.Expr[F2]
  ): c.Expr[ImplGTFormula[T]] = {
    import c.universe._

    val ttObs = weakTypeOf[ImplGTObservationalClass[T]]

    val ttF1 = weakTypeOf[F1]
    val ttF2 = weakTypeOf[F2]

    val ext = weakTypeOf[ImplGTFormula[T]]
    /*  if (
        ttF1 <:< ttObs &&
        ttF2 <:< ttObs
      )
        weakTypeOf[
          GTObservationalAnd[
            T,
            C,
            F1,
            C,
            F2,
            C,
            GTIdentAssoc[T, C]
          ]
        ]
      else
        weakTypeOf[
          GTAnd[
            T,
            C,
            F1,
            C,
            F2,
            C,
            GTIdentAssoc[T, C]
          ]
        ]
    */

    val anon = newTypeName(c.fresh())
    c.Expr(
      q"""
      {
        class $anon extends $ext {}
        new $anon()
      }
       """
    )
  }

}

// Mark: - Macro object
//

object GeometricMacros {
  // implicit def dummyFundep[T](foo: T): T = macro GeometricAndBundle.dummyFundep[T]

  implicit def basic[
    T <: GeomTheory,
    C <: GTBaseType[T],
    F <: GTFormula[T, C]
  ](
    t: T,
    con: C,
    form: F
  ): T = macro GeometricAndBundle.basic[
    T,
    C,
    F
  ]

  /*
  implicit def observationalAnd[
    T <: GeomTheory,
    C <: GTBaseType[T],
    F1 <: GTFormula[T, C],
    F2 <: GTFormula[T, C]
  ](
    formula1: F1,
    formula2: F2
  ): ImplGTFormula[T] = macro GeometricAndBundle.observationalAnd2[
      T,
      C,
      F1,
      F2
    ]
  */

}

