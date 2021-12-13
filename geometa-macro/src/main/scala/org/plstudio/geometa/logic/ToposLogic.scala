// ToposLogic.scala
//
// This file contains the traits used throughout Geometa (and embedded into each Toposcala) that are used
// to infer formula types specific to a particular topos' internal language specified by a geometric theory.
//
// Note that this is distinguished from GeometricLogic.scala, as the internal language of a topos is intuitionist
// and not geometric. Hence some formula types may be non-geometric.
//

package org.plstudio.geometa.logic

// Mark: - Logical Connectives
//

trait TNeg[
  T <: GeomTheory,
  F <: GTFormula[T]
] extends GTFormula[T] {}

trait TForAll[
  T <: GeomTheory,
  Q <: GTBaseType[T],
  F1 <: GTFormula[T],
] extends GTFormula[T] {}

trait TImplies[
  T <: GeomTheory,
  F1 <: GTFormula[T],
  F2 <: GTFormula[T],
] extends GTFormula[T] {}
