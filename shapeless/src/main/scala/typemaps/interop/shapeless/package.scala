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
package interop

import _root_.shapeless._

import scala.language.implicitConversions

package object shapeless {
  implicit def typeMapShapelessSyntax[M <: TypeMap](m: M): TypeMapShapelessSyntax[M] = new TypeMapShapelessSyntax[M](m)
  implicit def hlistSyntax[L <: HList](l: L): HListSyntax[L] = new HListSyntax[L](l)
}
