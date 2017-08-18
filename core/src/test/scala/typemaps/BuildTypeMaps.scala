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

import shapeless.HList._
import shapeless._
import typemaps.TypeMap._

sealed trait BuildTypeMaps[K <: HList, V <: HList] {
  type Out <: HList
  def apply(values: V): Out
}

object BuildTypeMaps {
  type Aux[K <: HList, V <: HList, O <: HList] = BuildTypeMaps[K, V] { type Out = O }

  class BuildFn[K <: HList] {
    def apply[V <: HList](values: V)(implicit build: BuildTypeMaps[K, V]): build.Out = build(values)
  }

  def apply[K <: HList]: BuildFn[K] = new BuildFn

  implicit val hnil: BuildTypeMaps.Aux[HNil, HNil, Tip :: HNil] = new BuildTypeMaps[HNil, HNil] {
    override type Out = Tip :: HNil
    override def apply(values: HNil): Tip :: HNil = {
      Tip :: HNil
    }
  }

  implicit def hcons[KH, KT <: HList, VH, VT <: HList, OH <: TypeMap, OT <: HList, O <: TypeMap](implicit tail: BuildTypeMaps.Aux[KT, VT, OH :: OT],
                                                                                                 insert: Insert.Aux[OH, KH, VH, O]): BuildTypeMaps.Aux[KH :: KT, VH :: VT, O :: OH :: OT] =
    new BuildTypeMaps[KH :: KT, VH :: VT] {
      override type Out = O :: OH :: OT
      override def apply(values: VH :: VT): O :: OH :: OT = {
        val oh :: ot = tail(values.tail)
        insert(oh, values.head) :: oh :: ot
      }
    }
}
