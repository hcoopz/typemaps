package typemaps

import TypeMap._

import scala.annotation.implicitNotFound

@implicitNotFound("${K} is not present in the map")
sealed trait Remove[M <: TypeMap, K] {
  type Out <: TypeMap
  def apply(m: M): Out
}

object Remove {
  type Aux[M <: TypeMap, K, O <: TypeMap] = Remove[M, K] { type Out = O }

  // size(LHS) <= size(RHS) <= size(LHS) + 1
  // This is enforced by Insert
  // This means that we never need to consider non-empty LHS and empty RHS

  // To delete from the RHS, delete from the RHS and then swap the LHS and resulting RHS
  implicit def binR[KA, A, L <: TypeMap, R <: TypeMap, K, O <: TypeMap](implicit r: Remove.Aux[R, K, O]
                                                                       ): Remove.Aux[Bin[KA, A, L, R], K, Bin[KA, A, O, L]] =
    new Remove[Bin[KA, A, L, R], K] {
      override type Out = Bin[KA, A, O, L]
      override def apply(m: Bin[KA, A, L, R]): Bin[KA, A, O, L] = Bin(m.a, r(m.r), m.l)
    }

  // To delete a node with an empty RHS, return an empty tree
  implicit def binATip[K, A]: Remove.Aux[Bin[K, A, Tip, Tip], K, Tip] =
    new Remove[Bin[K, A, Tip, Tip], K] {
      override type Out = Tip
      override def apply(m: Bin[K, A, Tip, Tip]): Tip = Tip
    }

  // To delete a node with a non-empty RHS, steal an element from the RHS, replace with it in the node, and then swap the resulting branches and swap
  implicit def binABinR[K, A, L <: TypeMap, RK, RA, RL <: TypeMap, RR <: TypeMap, O <: TypeMap](implicit remove: Remove.Aux[Bin[RK, RA, RL, RR], RK, O]
                                                                                               ): Remove.Aux[Bin[K, A, L, Bin[RK, RA, RL, RR]], K, Bin[RK, RA, O, L]] =
    new Remove[Bin[K, A, L, Bin[RK, RA, RL, RR]], K] {
      override type Out = Bin[RK, RA, O, L]
      override def apply(m: Bin[K, A, L, Bin[RK, RA, RL, RR]]): Bin[RK, RA, O, L] = Bin(m.r.a, remove(m.r), m.l)
    }

  // To delete from the LHS, steal an element from the RHS, replace with it in the LHS, and then swap the resulting branches
  implicit def binL[KA, A, L <: TypeMap, RK, RA, RL <: TypeMap, RR <: TypeMap, K, LA, OL <: TypeMap, OR <: TypeMap](implicit remove: Remove.Aux[Bin[RK, RA, RL, RR], RK, OR],
                                                                                                                    lookup: Lookup.Aux[L, K, LA],
                                                                                                                    replace: Update.Aux[L, K, LA, KA, A, OL]
                                                                                                                   ): Remove.Aux[Bin[KA, A, L, Bin[RK, RA, RL, RR]], K, Bin[RK, RA, OR, OL]] =
    new Remove[Bin[KA, A, L, Bin[RK, RA, RL, RR]], K] {
      override type Out = Bin[RK, RA, OR, OL]
      override def apply(m: Bin[KA, A, L, Bin[RK, RA, RL, RR]]): Bin[RK, RA, OR, OL] = Bin(m.r.a, remove(m.r), replace.const(m.l, m.a))
    }
}