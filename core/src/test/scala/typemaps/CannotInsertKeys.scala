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

sealed trait CannotInsertKeys[M <: TypeMap, K <: HList] {
  def apply(m: M): Unit
}

object CannotInsertKeys extends Matchers {
  def apply[K <: HList]: Fn[K] = new Fn

  class Fn[K <: HList] {
    def apply[M <: TypeMap](m: M)(implicit ev: CannotInsertKeys[M, K]): Unit = {}
  }

  implicit def hnil[M <: TypeMap]: CannotInsertKeys[M, HNil] = new CannotInsertKeys[M, HNil] {
    override def apply(m: M): Unit = {}
  }

  implicit def hcons[M <: TypeMap, KH, KT <: HList](implicit head: CannotInsertKey[M, KH],
                                                    tail: CannotInsertKeys[M, KT]
                                                   ): CannotInsertKeys[M, KH :: KT] =
    new CannotInsertKeys[M, KH :: KT] {
      override def apply(m: M): Unit = {}
    }
}
