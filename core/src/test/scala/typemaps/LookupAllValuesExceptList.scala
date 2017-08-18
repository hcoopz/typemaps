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

import shapeless._

sealed trait LookupAllValuesExceptList[M <: HList, K <: HList, E <: HList, V <: HList] {
  def apply(m: M, values: V): Unit
}

object LookupAllValuesExceptList {
  def apply[K <: HList, E <: HList]: Fn[K, E] = new Fn

  class Fn[K <: HList, E <: HList] {
    def apply[M <: HList, V <: HList](m: M, values: V)(implicit ev: LookupAllValuesExceptList[M, K, E, V]): Unit = ev(m, values)
  }

  implicit def hnil[K <: HList, V <: HList]: LookupAllValuesExceptList[HNil, K, HNil, V] = new LookupAllValuesExceptList[HNil, K, HNil, V] {
    override def apply(m: HNil, values: V): Unit = {}
  }

  implicit def hcons[H <: TypeMap, T <: HList, K <: HList, EH, ET <: HList, V <: HList](implicit head: LookupAllValuesExcept[H, K, EH, V],
                                                                                        tail: LookupAllValuesExceptList[T, K, ET, V]
                                                                                       ): LookupAllValuesExceptList[H :: T, K, EH :: ET, V] =
    new LookupAllValuesExceptList[H :: T, K, EH :: ET, V] {
      override def apply(m: H :: T, values: V): Unit = {
        head(m.head, values)
        tail(m.tail, values)
      }
    }
}