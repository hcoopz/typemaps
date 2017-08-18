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

final class TypeMapShapelessSyntax[M <: TypeMap](val m: M) extends AnyVal {
  // Defaults to HNil if no type is specified
  @inline def toSpecifiedHList[L <: HList](implicit toHList: ToSpecifiedHList[M, L]): L = toHList(m)

  @inline def toHList(implicit toHList: ToHList[M]): toHList.Out = toHList(m)

  @inline def mapValues[HF <: Poly](implicit mapValues: MapValues[M, HF]): mapValues.Out = mapValues(m)
}