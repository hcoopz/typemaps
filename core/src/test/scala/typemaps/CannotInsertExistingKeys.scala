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

import typemaps.TypeMap._

import scala.annotation.implicitNotFound

@implicitNotFound("Can insert one of the keys in {M} again")
private[this] sealed trait CannotInsertExistingKeys[M]

private[this] object CannotInsertExistingKeys {
  def apply[M <: TypeMap](implicit evidence: CannotInsertExistingKeys[M]): Unit = {}

  private[this] sealed trait Helper[M <: TypeMap, N <: TypeMap]
  private[this] object Helper {
    private[this] class Instance[M <: TypeMap, N <: TypeMap] extends Helper[M, N]

    implicit def tip[M <: TypeMap]: Helper[M, Tip] = new Instance

    implicit def bin[M <: TypeMap, K, A, L <: TypeMap, R <: TypeMap](implicit l: Helper[M, L],
                                                                     r: Helper[M, R],
                                                                     evidence: CannotInsertKey[M, K]
                                                                    ): Helper[M, Bin[K, A, L, R]] =
      new Instance
  }

  implicit def m[M <: TypeMap](implicit helper: Helper[M, M]): CannotInsertExistingKeys[M] = new CannotInsertExistingKeys[M] {}
}
