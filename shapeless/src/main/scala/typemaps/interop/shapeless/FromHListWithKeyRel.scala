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

import scala.language.higherKinds

sealed trait FromHListWithKeyRel[L <: HList, R[_, _]] {
  type Out <: TypeMap
  def apply(l: L): Out
}

object FromHListWithKeyRel {
  type Aux[L <: HList, R[_, _], O <: TypeMap] = FromHListWithKeyRel[L, R] { type Out = O }

  implicit def hnil[R[_, _]]: FromHListWithKeyRel.Aux[HNil, R, Tip] = new FromHListWithKeyRel[HNil, R] {
    override type Out = Tip
    override def apply(l: HNil): Tip = Tip
  }

  implicit def hcons[H, T <: HList, R[_, _], K, OT <: TypeMap, O <: TypeMap](implicit r: R[H, K],
                                                                             t: FromHListWithKeyRel.Aux[T, R, OT],
                                                                             insert: Insert.Aux[OT, K, H, O]
                                                                            ): FromHListWithKeyRel.Aux[H :: T, R, O] =
    new FromHListWithKeyRel[H :: T, R] {
      override type Out = O
      override def apply(l: H :: T): O = insert(t(l.tail), l.head)
    }
}
