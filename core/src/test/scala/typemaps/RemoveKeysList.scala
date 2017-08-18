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
import shapeless.ops.hlist.Prepend

sealed trait RemoveKeysList[M <: HList, K <: HList] {
  type Out <: HList
  def apply(m: M): Out
}

object RemoveKeysList {
  type Aux[M <: HList, K <: HList, O <: HList] = RemoveKeysList[M, K] { type Out = O }

  val a = 1 :: 2 :: 3 :: HNil

  def apply[K <: HList]: Fn[K] = new Fn

  class Fn[K <: HList] {
    def apply[M <: HList](m: M)(implicit ev: RemoveKeysList[M, K]): ev.Out = ev(m)
  }

  implicit val hnil: RemoveKeysList.Aux[HNil, HNil, HNil] = new RemoveKeysList[HNil, HNil] {
    override type Out = HNil
    override def apply(m: HNil): HNil = HNil
  }

  implicit val tip: RemoveKeysList.Aux[TypeMap.Tip :: HNil, HNil, HNil] = new RemoveKeysList[TypeMap.Tip :: HNil, HNil] {
    override type Out = HNil
    override def apply(m: TypeMap.Tip :: HNil): HNil = HNil
  }

  implicit def hcons[H <: TypeMap, T <: HList, KH, KT <: HList, OH <: HList, OT <: HList, O <: HList](implicit head: RemoveKeys.Aux[H, KH :: KT, OH],
                                                                                                      tail: RemoveKeysList.Aux[T, KT, OT],
                                                                                                      prepend: Prepend.Aux[OH, OT, O]
                                                                                                     ): RemoveKeysList.Aux[H :: T, KH :: KT, O] =
    new RemoveKeysList[H :: T, KH :: KT] {
      override type Out = O
      override def apply(m: H :: T): O = {
        prepend(head(m.head), tail(m.tail))
      }
    }
}