// GeometricMacros.scala
//
// This file contains the macros that allow Geometa to materialize types for formulas and extend them as
// observational classes or not.
//
// Note that much of this code was generated 

package org.plstudio.geometa.macros

import scala.language.experimental.macros
import scala.reflect.macros.whitebox.Context

import org.plstudio.geometa.logic._

// Mark: - Implicit definitions
//
class GeometricAndBundle(val c: Context) {

  def basic[
    T <: GeomTheory : c.WeakTypeTag,
    F <: GTFormula[T] : c.WeakTypeTag
  ](
    t : c.Expr[T],
    form: c.Expr[F]
  ) = {
    import c.universe._
    q"$t"
  }

  def observationalAnd2[
    T <: GeomTheory : c.WeakTypeTag,
    F1 <: GTFormula[T] : c.WeakTypeTag,
    F2 <: GTFormula[T] : c.WeakTypeTag
  ](
    formula1: c.Expr[F1],
    formula2: c.Expr[F2]
  ): c.Expr[GTFormula[T]] = {
    import c.universe._

    val ttObs = weakTypeOf[GTObservationalClass[T]]

    val ttF1 = weakTypeOf[F1]
    val ttF2 = weakTypeOf[F2]

    val ext =
      if (
        ttF1 <:< ttObs &&
        ttF2 <:< ttObs
      )
        ttObs
      else
        weakTypeOf[GTFormula[T]]

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
    F <: GTFormula[T]
  ](
    t: T,
    form: F
  ): T = macro GeometricAndBundle.basic[
    T,
    F
  ]

  implicit def observationalAnd[
    T <: GeomTheory,
    F1 <: GTFormula[T],
    F2 <: GTFormula[T]
  ](
    formula1: F1,
    formula2: F2
  ): GTFormula[T] = macro GeometricAndBundle.observationalAnd2[
      T,
      F1,
      F2
    ]

}

