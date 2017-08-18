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

sealed trait TypeMap

object TypeMap {
  final case object Tip extends TypeMap
  type Tip = Tip.type
  final case class Bin[KA, A, L <: TypeMap, R <: TypeMap] private[typemaps] (a: A, l: L, r: R) extends TypeMap

  def empty: Tip = Tip

  final class SingletonFn[K] {
    def apply[A](a: A): Bin[K, A, Tip, Tip] = Bin(a, Tip, Tip)
  }

  @inline def singletonSet[A](a: A): Bin[A, A, Tip, Tip] = Bin(a, Tip, Tip)
  @inline def singleton[K]: SingletonFn[K] = new SingletonFn
}

