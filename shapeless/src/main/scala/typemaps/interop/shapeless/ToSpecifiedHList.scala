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

import shapeless._

sealed trait ToSpecifiedHList[M <: TypeMap, L <: HList] {
  def apply(m: M): L
}

sealed trait ToSpecifiedHListLow {
  implicit def hcons[M <: TypeMap, H, T <: HList](implicit t: ToSpecifiedHList[M, T],
                                                  lookup: Lookup.Aux[M, H, H]
                                                 ): ToSpecifiedHList[M, H :: T] =
    new ToSpecifiedHList[M, H :: T] {
      override def apply(m: M): H :: T = lookup(m) :: t(m)
    }
}

object ToSpecifiedHList extends ToSpecifiedHListLow {
  implicit def hnil[M <: TypeMap]: ToSpecifiedHList[M, HNil] = new ToSpecifiedHList[M, HNil] {
    override def apply(m: M): HNil = HNil
  }
}