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

@implicitNotFound("Can update ${KA} to ${KB} in ${M}")
private[this] sealed trait CannotUpdateKey[M <: TypeMap, KA, KB]

private[this] object CannotUpdateKey {
  implicit def succeed[M <: TypeMap, KA, KB]: CannotUpdateKey[M, KA, KB] = new CannotUpdateKey[M, KA, KB] {}

  @implicitAmbiguous("Can update the key in the map")
  implicit def fail1[M <: TypeMap, KA, KB, A](implicit lookup: Lookup.Aux[M, KA, A],
                                              update: UpdateWithKey[M, KA, A, KB, Unit]): CannotUpdateKey[M, KA, KB] = new CannotUpdateKey[M, KA, KB] {}
  implicit def fail2[M <: TypeMap, KA, KB, A](implicit lookup: Lookup.Aux[M, KA, A],
                                              update: UpdateWithKey[M, KA, A, KB, Unit]): CannotUpdateKey[M, KA, KB] = new CannotUpdateKey[M, KA, KB] {}

  class Fn[KA, KB] {
    def apply[M <: TypeMap](m: M)(implicit ev: CannotUpdateKey[M, KA, KB]): Unit = {}
  }

  def apply[KA, KB]: Fn[KA, KB] = new Fn
}
