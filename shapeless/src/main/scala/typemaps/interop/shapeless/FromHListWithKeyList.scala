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
package interop.shapeless

import TypeMap._
import shapeless._

sealed trait FromHListWithKeyList[L <: HList, K <: HList] {
  type Out <: TypeMap
  def apply(l: L): Out
}

object FromHListWithKeyList {
  type Aux[L <: HList, K <: HList, O <: TypeMap] = FromHListWithKeyList[L, K] { type Out = O }

  implicit def hnil: FromHListWithKeyList.Aux[HNil, HNil, Tip] = new FromHListWithKeyList[HNil, HNil] {
    override type Out = Tip
    override def apply(l: HNil): Tip = Tip
  }

  implicit def hcons[H, T <: HList, KH, KT <: HList, OT <: TypeMap, O <: TypeMap](implicit t: FromHListWithKeyList.Aux[T, KT, OT],
                                                                                  insert: Insert.Aux[OT, KH, H, O]
                                                                                 ): FromHListWithKeyList.Aux[H :: T, KH :: KT, O] =
    new FromHListWithKeyList[H :: T, KH :: KT] {
      override type Out = O
      override def apply(l: H :: T): O = insert(t(l.tail), l.head)
    }
}
