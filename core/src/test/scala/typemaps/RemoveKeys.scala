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

sealed trait RemoveKeys[M <: TypeMap, K <: HList] {
  type Out <: HList
  def apply(m: M): Out
}

object RemoveKeys {
  type Aux[M <: TypeMap, K <: HList, O <: HList] = RemoveKeys[M, K] { type Out = O }

  def apply[K <: HList]: Fn[K] = new Fn

  class Fn[K <: HList] {
    def apply[M <: TypeMap](m: M)(implicit ev: RemoveKeys[M, K]): ev.Out = ev(m)
  }

  implicit def hnil[M <: TypeMap]: RemoveKeys.Aux[M, HNil, HNil] = new RemoveKeys[M, HNil] {
    override type Out = HNil
    override def apply(m: M): HNil = HNil
  }

  implicit def hcons[M <: TypeMap, H, T <: HList, OH <: TypeMap, OT <: HList](implicit remove: Remove.Aux[M, H, OH],
                                                                              tail: RemoveKeys.Aux[M, T, OT]
                                                                             ): RemoveKeys.Aux[M, H :: T, OH :: OT] =
    new RemoveKeys[M, H :: T] {
      override type Out = OH :: OT
      override def apply(m: M): OH :: OT = {
        remove(m) :: tail(m)
      }
    }
}