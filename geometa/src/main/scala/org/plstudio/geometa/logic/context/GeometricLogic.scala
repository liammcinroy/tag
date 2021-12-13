// GeometricLogic.scala
//
// This file contains the traits used throughout Geometa (and embedded into each Toposcala) that are used
// to infer observationality and formula types specific to a particular geometric theory.
//

package org.plstudio.geometa.logic.context


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


// An uncontextualized formula for macros
//
trait ImplGTFormula[T <: GeomTheory] extends GTType[T] {
  type C <: GTBaseType[T]
}

// Each formula in a specific geometric theory uses this trait, to capture the geometric theory it belongs to so that
// valid formulae in differing theories do not confound their respective theories.
//
// We also need to track the contextual free variables' type, which is necessarily a GTBaseType[T]
//   NB: Product types of base types are considered base types for this purpose - see GeometricTuple.scala.
//
trait GTFormula[
  T <: GeomTheory,
  C <: GTBaseType[T]
] extends ImplGTFormula[T] {}

// An uncontextualized observational class for macros
//
trait ImplGTObservationalClass[T <: GeomTheory] extends ImplGTFormula[T] {}

// Observational classes of formulae are those formulae that can be constructed geometrically.
//   These also capture their owning theories and context.
//
trait GTObservationalClass[
  T <: GeomTheory,
  C <: GTBaseType[T]
] extends GTFormula[T, C] with ImplGTObservationalClass[T] {}

// Axioms are an implication of two formulae of shared context variables.
//
trait GTAxiom[
  T <: GeomTheory,
  C <: GTBaseType[T],
  P <: GTFormula[T, C],
  Q <: GTFormula[T, C]
] {}

// Each predicate in a specific geometric theory uses this trait, so that formulae using predicates shared by differing
// theories do not confound their respective theories.
//
// Predicates are also a type of formula, whereas functions themselves are not.
//
trait GTPred[
  T <: GeomTheory,
  C <: GTBaseType[T]
] extends GTFormula[T, C] {}

// Each base predicate in a specific geometric theory uses this trait, i.e. those explicitly stated in the
// specification.
//   Any GTPred not also a GTBasePred is a ``derived predicate''.
//   Any GTPred with 0 arguments is also called a ``proposition''.
// theories do not confound their respective theories.
//
trait GTBasePred[
  T <: GeomTheory,
  C <: GTBaseType[T]
] extends GTPred[T, C] {}

// Mark: - Logical Connectives
// With the base formulae (predicates and axioms) defined, we can now introduce the logical connectives that are
// geometric to compose formulae into more formulae.
//
// We also note that this is necessity of a compiler plugin - we cannot specialize a GTConnective to be a
// GTObservationalConnective implicity (as one might in swift, for example).
//
// Some connectives have an Assoc type that is intended to associate the contexts of formulas together.
//

trait GTAnd[
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

trait GTObservationalAnd[
  T <: GeomTheory,
  C1 <: GTBaseType[T],
  F1 <: GTObservationalClass[T, C1],
  C2 <: GTBaseType[T],
  F2 <: GTObservationalClass[T, C2],
  C <: GTBaseType[T],
  Assoc <: GTFunc2[
    T,
    C1,
    C2,
    C,
    Function2[C1, C2, C]
  ]
] extends GTObservationalClass[T, C] {}

trait GTOr[
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

trait GTObservationalOr[
  T <: GeomTheory,
  C1 <: GTBaseType[T],
  F1 <: GTObservationalClass[T, C1],
  C2 <: GTBaseType[T],
  F2 <: GTObservationalClass[T, C2],
  C <: GTBaseType[T],
  Assoc <: GTFunc2[
    T,
    C1,
    C2,
    C,
    Function2[C1, C2, C]
  ]
] extends GTObservationalClass[T, C] {}

trait GTEqual[
  T <: GeomTheory,
  BT <: GTBaseType[T]
] extends GTFormula[T, GTTuple2[T, BT, BT, Tuple2[BT, BT]]] {}

trait GTExists[
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

trait GTObservationalExists[
  T <: GeomTheory,
  Q <: GTBaseType[T],
  C1 <: GTBaseType[T],
  F1 <: GTObservationalClass[T, C1],
  C <: GTBaseType[T],
  Assoc <: GTFunc2[
    T,
    Q,
    C1,
    C,
    Function2[Q, C1, C]
  ]
] extends GTObservationalClass[T, C] {}

class GTIdentAssoc[
  T <: GeomTheory,
  C <: GTBaseType[T],
] extends GTFunc2[
  T,
  C,
  C,
  C,
  Function2[C, C, C]
] {
  def apply(c1: C, c2: C): C = c1
}
