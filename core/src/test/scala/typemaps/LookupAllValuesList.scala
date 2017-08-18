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
import shapeless._

sealed trait LookupAllValuesList[M <: HList, K <: HList, V <: HList] {
  def apply(m: M, values: V): Unit
}

object LookupAllValuesList {
  def apply[K <: HList]: Fn[K] = new Fn

  class Fn[K <: HList] {
    def apply[M <: HList, V <: HList](m: M, values: V)(implicit ev: LookupAllValuesList[M, K, V]): Unit = ev(m, values)
  }

  implicit val hnil: LookupAllValuesList[HNil, HNil, HNil] = new LookupAllValuesList[HNil, HNil, HNil] {
    override def apply(m: HNil, values: HNil): Unit = {}
  }

  implicit val tip: LookupAllValuesList[Tip :: HNil, HNil, HNil] = new LookupAllValuesList[Tip :: HNil, HNil, HNil] {
    override def apply(m: Tip :: HNil, values: HNil): Unit = {}
  }

  implicit def hcons[H <: TypeMap, T <: HList, KH, KT <: HList, VH, VT <: HList](implicit head: LookupAllValues[H, KH :: KT, VH :: VT],
                                                                                 tail: LookupAllValuesList[T, KT, VT]
                                                                                ): LookupAllValuesList[H :: T, KH :: KT, VH :: VT] =
    new LookupAllValuesList[H :: T, KH :: KT, VH :: VT] {
      override def apply(m: H :: T, values: VH :: VT): Unit = {
        head(m.head, values)
        tail(m.tail, values.tail)
      }
    }
}