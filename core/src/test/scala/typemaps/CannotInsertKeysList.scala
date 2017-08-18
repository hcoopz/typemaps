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

sealed trait CannotInsertKeysList[M <: HList, K <: HList] {
  def apply(m: M): Unit
}

object CannotInsertKeysList {
  def apply[K <: HList]: Fn[K] = new Fn

  class Fn[K <: HList] {
    def apply[M <: HList](m: M)(implicit ev: CannotInsertKeysList[M, K]): Unit = ev(m)
  }

  implicit val hnil: CannotInsertKeysList[HNil, HNil] = new CannotInsertKeysList[HNil, HNil] {
    override def apply(m: HNil): Unit = {}
  }

  implicit val tip: CannotInsertKeysList[Tip :: HNil, HNil] = new CannotInsertKeysList[Tip :: HNil, HNil] {
    override def apply(m: Tip :: HNil): Unit = {}
  }

  implicit def hcons[H <: TypeMap, T <: HList, KH, KT <: HList](implicit head: CannotInsertKeys[H, KH :: KT],
                                                                tail: CannotInsertKeysList[T, KT]
                                                               ): CannotInsertKeysList[H :: T, KH :: KT] =
    new CannotInsertKeysList[H :: T, KH :: KT] {
      override def apply(m: H :: T): Unit = {}
    }
}
