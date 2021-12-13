// GeometricLogic.scala
//
// This file contains the traits used throughout Geometa (and embedded into each Toposcala) that are used
// to infer observationality and formula types specific to a particular geometric theory.
//

package org.plstudio.geometa.logic


// Mark: - Types
//

// Each geometric theory uses this trait, to dictate to which theory various vocabulary logic systems belong to.
//
// Typically, the actual type for a particular theory is empty, but by carrying the theory on formulae, we are
// allowed to compare the multitheory specific typing. It's a verbosity nightmare, but it's a DSL for that reason.
//
trait GeomTheory {}

// Each type in a specific geometric theory uses this trait, so that formulae using types shared by differing
// theories do not confound their respective theories.
//
trait GTType[T <: GeomTheory] {}

// Each base type in a specific geometric theory uses this trait, i.e. those explicitly stated in the specification.
//   Any GTType not also a GTBaseType is a ``derived type''.
//
trait GTBaseType[T <: GeomTheory] extends GTType[T] {}

class GTVacuum[T <: GeomTheory] extends GTBaseType[T] {}

// Mark: - Formulae
// With types defined, we can begin to get into formulae, which are also types, but more general than base types.
//


// Each formula in a specific geometric theory uses this trait, to capture the geometric theory it belongs to so that
// valid formulae in differing theories do not confound their respective theories.
//
trait GTFormula[T <: GeomTheory] extends GTType[T] {}

// Observational classes of formulae are those formulae that can be constructed geometrically.
//   These also capture their owning theories and context.
//
trait GTObservationalClass[T <: GeomTheory] extends GTFormula[T] {}

// Axioms are an implication of two formulae of shared context variables.
//
trait GTAxiom[
  T <: GeomTheory,
  P <: GTFormula[T],
  Q <: GTFormula[T]
] {}

// Each predicate in a specific geometric theory uses this trait, so that formulae using predicates shared by differing
// theories do not confound their respective theories.
//
// Predicates are also a type of formula, whereas functions themselves are not.
//
trait GTPred[
  T <: GeomTheory
] extends GTFormula[T] {}

// Each base predicate in a specific geometric theory uses this trait, i.e. those explicitly stated in the
// specification.
//   Any GTPred not also a GTBasePred is a ``derived predicate''.
//   Any GTPred with 0 arguments is also called a ``proposition''.
// theories do not confound their respective theories.
//
trait GTBasePred[
  T <: GeomTheory
] extends GTPred[T] {}

// Mark: - Logical Connectives
// With the base formulae (predicates and axioms) defined, we can now introduce the logical connectives that are
// geometric to compose formulae into more formulae.
//
// We also note that this is necessity of a macro - we cannot specialize a GTConnective to be a
// GTObservationalConnective implicity (as one might in swift, for example).
//

trait GTAnd[
  T <: GeomTheory,
  F1 <: GTFormula[T],
  F2 <: GTFormula[T],
] extends GTFormula[T] {}

trait GTOr[
  T <: GeomTheory,
  F1 <: GTFormula[T],
  F2 <: GTFormula[T],
] extends GTFormula[T] {}

trait GTEqual[
  T <: GeomTheory,
  BT <: GTBaseType[T]
] extends GTFormula[T] {}

trait GTExists[
  T <: GeomTheory,
  Q <: GTBaseType[T],
  F1 <: GTFormula[T],
] extends GTFormula[T] {}

trait GTObservationalExists[
  T <: GeomTheory,
  Q <: GTBaseType[T],
  F1 <: GTObservationalClass[T],
] extends GTObservationalClass[T] {}
