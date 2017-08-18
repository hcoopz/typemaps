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

import scala.language.higherKinds

final class HListSyntax[L <: HList](val l: L) extends AnyVal {
  @inline def toTypeMap(implicit fromHList: FromHList[L]): fromHList.Out = fromHList(l)

  @inline def toTypeMapWithKeyList[K <: HList](implicit fromHListWithKeyList: FromHListWithKeyList[L, K]): fromHListWithKeyList.Out = fromHListWithKeyList(l)

  @inline def toTypeMapWithKeyRel[R[_, _]](implicit fromHListWithKeyRel: FromHListWithKeyRel[L, R]): fromHListWithKeyRel.Out = fromHListWithKeyRel(l)
}