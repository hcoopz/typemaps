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

import scala.annotation.{implicitAmbiguous, implicitNotFound}

@implicitNotFound("Can lookup ${K} in ${M}")
private[this] sealed trait CannotLookupKey[M <: TypeMap, K]

private[this] object CannotLookupKey {
  implicit def succeed[M <: TypeMap, K]: CannotLookupKey[M, K] = new CannotLookupKey[M, K] {}

  @implicitAmbiguous("Can lookup the key in the map")
  implicit def fail1[M <: TypeMap, K](implicit insert: Lookup[M, K]): CannotLookupKey[M, K] = new CannotLookupKey[M, K] {}
  implicit def fail2[M <: TypeMap, K](implicit insert: Lookup[M, K]): CannotLookupKey[M, K] = new CannotLookupKey[M, K] {}

  class Fn[K] {
    def apply[M <: TypeMap](m: M)(implicit ev: CannotLookupKey[M, K]): Unit = {}
  }

  def apply[K]: Fn[K] = new Fn
}
