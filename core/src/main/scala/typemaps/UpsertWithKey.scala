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

sealed trait UpsertWithKey[M <: TypeMap, KA, A, KB, B] {
  type Out <: TypeMap
  def apply(m: M, update: A => B, insert: => B): Out
}

object UpsertWithKey {
  type Aux[M <: TypeMap, KA, A, KB, B, O <: TypeMap] = UpsertWithKey[M, KA, A, KB, B] { type Out = O }

  // A key can be inserted into a map iff it cannot be updated in the map

  implicit def insert[M <: TypeMap, KA, A, KB, B, O <: TypeMap](implicit i: Insert.Aux[M, KB, B, O]
                                                               ): UpsertWithKey.Aux[M, KA, A, KB, B, O] =
    new UpsertWithKey[M, KA, A, KB, B] {
      override type Out = O
      override def apply(m: M, update: (A) => B, insert: => B): O = i(m, insert)
    }

  implicit def update[M <: TypeMap, KA, A, KB, B, O <: TypeMap](implicit u: UpdateWithKey.Aux[M, KA, A, KB, B, O]
                                                               ): UpsertWithKey.Aux[M, KA, A, KB, B, O] =
    new UpsertWithKey[M, KA, A, KB, B] {
      override type Out = O
      override def apply(m: M, update: (A) => B, insert: => B): O = u(m, update)
    }

  final class UpsertWithKeyFn[M <: TypeMap, KA, A, KB](val m: M) extends AnyVal {
    @inline def apply[B](update: A => B, insert: => B)(implicit upsert: UpsertWithKey[M, KA, A, KB, B]): upsert.Out = upsert(m, update, insert)
    @inline def using[B](update: A => B, insert: => B)(implicit upsert: UpsertWithKey[M, KA, A, KB, B]): upsert.Out = upsert(m, update, insert)
  }

  final class UpsertWithKeySetFn[M <: TypeMap, A](val m: M) extends AnyVal {
    @inline def apply[B](update: A => B, insert: => B)(implicit upsert: UpsertWithKey[M, A, A, B, B]): upsert.Out = upsert(m, update, insert)
    @inline def using[B](update: A => B, insert: => B)(implicit upsert: UpsertWithKey[M, A, A, B, B]): upsert.Out = upsert(m, update, insert)
  }
}
