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
  C <: GTBaseType[T],
  F <: GTFormula[T, C]
] extends GTFormula[T, C] {}

trait TForAll[
  T <: GeomTheory,
  Q <: GTBaseType[T],
  C1 <: GTBaseType[T],
  F1 <: GTFormula[T, C1],
  C <: GTBaseType[T],
  Assoc <: GTFunc2[
    T,
    Q,
    C1,
    C,
    Function2[Q, C1, C]
  ]
] extends GTFormula[T, C] {}

trait TImplies[
  T <: GeomTheory,
  C1 <: GTBaseType[T],
  F1 <: GTFormula[T, C1],
  C2 <: GTBaseType[T],
  F2 <: GTFormula[T, C2],
  C <: GTBaseType[T],
  Assoc <: GTFunc2[
    T,
    C1,
    C2,
    C,
    Function2[C1, C2, C]
  ]
] extends GTFormula[T, C] {}
