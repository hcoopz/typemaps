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

import org.scalatest.Matchers
import shapeless._

sealed trait LookupAllValuesExcept[M <: TypeMap, K <: HList, E, V <: HList] {
  def apply(m: M, values: V): Unit
}

object LookupAllValuesExcept extends Matchers {
  def apply[K <: HList, E]: Fn[K, E] = new Fn

  class Fn[K <: HList, E] {
    def apply[M <: TypeMap, V <: HList](m: M, values: V)(implicit ev: LookupAllValuesExcept[M, K, E, V]): Unit = ev(m, values)
  }

  implicit def hnil[M <: TypeMap, E]: LookupAllValuesExcept[M, HNil, E, HNil] = new LookupAllValuesExcept[M, HNil, E, HNil] {
    override def apply(m: M, values: HNil): Unit = {}
  }

  implicit def hconsEq[M <: TypeMap, KT <: HList, E, VH, VT <: HList](implicit lookup: CannotLookupKey[M, E],
                                                                      tail: LookupAllValuesExcept[M, KT, E, VT]
                                                                     ): LookupAllValuesExcept[M, E :: KT, E, VH :: VT] =
    new LookupAllValuesExcept[M, E :: KT, E, VH :: VT] {
      override def apply(m: M, values: VH :: VT): Unit = {
        tail(m, values.tail)
      }
    }

  implicit def hconsNeq[M <: TypeMap, KH, KT <: HList, E, VH, VT <: HList](implicit lookup: Lookup[M, KH],
                                                                           neq: KH =:!= E,
                                                                           tail: LookupAllValuesExcept[M, KT, E, VT]
                                                                          ): LookupAllValuesExcept[M, KH :: KT, E, VH :: VT] =
    new LookupAllValuesExcept[M, KH :: KT, E, VH :: VT] {
      override def apply(m: M, values: VH :: VT): Unit = {
        lookup(m) shouldEqual values.head
        tail(m, values.tail)
      }
    }
}