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

import scala.annotation.implicitAmbiguous

sealed trait Insert[M <: TypeMap, K, A] {
  type Out <: TypeMap
  def apply(m: M, a: A): Out
}

object Insert {
  type Aux[M <: TypeMap, K, A, O <: TypeMap] = Insert[M, K, A] { type Out = O }

  implicit def tip[M <: Tip, K, A]: Insert.Aux[M, K, A, Bin[K, A, Tip, Tip]] =
    new Insert[M, K, A] {
      override type Out = Bin[K, A, Tip, Tip]
      override def apply(m: M, a: A): Bin[K, A, Tip, Tip] = Bin(a, Tip, Tip)
    }

  private[this] class Nope[M <: TypeMap, K, A] extends Insert[M, K, A] {
    override type Out = Nothing
    override def apply(m: M, a: A): Nothing = sys.error(s"Key already present in map (implementation error: this instance should never be used")
  }
  private[this] def nope[M <: TypeMap, K, A]: Insert.Aux[M, K, A, Nothing] = new Nope

  @implicitAmbiguous("${K} is already present in the map")
  implicit def binEqual1[K, A, L <: TypeMap, R <: TypeMap, B]: Insert.Aux[Bin[K, A, L, R], K, B, Nothing] = nope
  implicit def binEqual2[K, A, L <: TypeMap, R <: TypeMap, B]: Insert.Aux[Bin[K, A, L, R], K, B, Nothing] = nope

  // If KB can be inserted into both sides then it is not already present
  // Insert into the LHS and then swap so that a balanced tree will be constructed from successive inserts
  // The resulting tree will have size(LHS) <= size(RHS) <= size(LHS) + 1
  implicit def bin[KA, A, L <: TypeMap, R <: TypeMap, KB, B, O <: TypeMap](implicit l: Insert.Aux[L, KB, B, O],
                                                                           r: Insert[R, KB, B]
                                                                          ): Insert.Aux[Bin[KA, A, L, R], KB, B, Bin[KA, A, R, O]] =
    new Insert[Bin[KA, A, L, R], KB, B] {
      override type Out = Bin[KA, A, R, O]
      override def apply(m: Bin[KA, A, L, R], b: B): Bin[KA, A, R, O] = Bin(m.a, m.r, l(m.l, b))
    }

  final class InsertFn[M <: TypeMap, K](val m: M) extends AnyVal {
    @inline def apply[A, O <: TypeMap](a: A)(implicit insert: Insert.Aux[M, K, A, O]): O = insert(m, a)
  }
}