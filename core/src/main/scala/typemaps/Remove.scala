/*
    Copyright (c) 2017 Harriet Cooper

    This file is part of typemaps.

    Typemaps is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Typemaps is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with typemaps.  If not, see <http://www.gnu.org/licenses/>.
 */
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
                                                                                                                    replace: UpdateWithKeyUnsafe.Aux[L, K, LA, KA, A, OL]
                                                                                                                   ): Remove.Aux[Bin[KA, A, L, Bin[RK, RA, RL, RR]], K, Bin[RK, RA, OR, OL]] =
    new Remove[Bin[KA, A, L, Bin[RK, RA, RL, RR]], K] {
      override type Out = Bin[RK, RA, OR, OL]
      override def apply(m: Bin[KA, A, L, Bin[RK, RA, RL, RR]]): Bin[RK, RA, OR, OL] = Bin(m.r.a, remove(m.r), replace.const(m.l, m.a))
    }
}