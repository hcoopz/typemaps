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
import TypeMap._

sealed trait MapValues[M <: TypeMap, HF <: Poly] {
  type Out <: TypeMap
  def apply(m: M): Out
}

object MapValues {
  type Aux[M <: TypeMap, HF <: Poly, O <: TypeMap] = MapValues[M, HF] { type Out = O }

  implicit def tip[HF <: Poly]: MapValues.Aux[Tip, HF, Tip] = new MapValues[Tip, HF] {
    override type Out = Tip
    override def apply(m: Tip): Tip = Tip
  }

  implicit def bin[K, A, L <: TypeMap, R <: TypeMap, HF <: Poly, B, OL <: TypeMap, OR <: TypeMap](implicit a: PolyDefns.Case1.Aux[HF, A, B],
                                                                                                  l: MapValues.Aux[L, HF, OL],
                                                                                                  r: MapValues.Aux[R, HF, OR]
                                                                                                 ): MapValues.Aux[Bin[K, A, L, R], HF, Bin[K, B, OL, OR]] =
    new MapValues[Bin[K, A, L, R], HF] {
      override type Out = Bin[K, B, OL, OR]
      override def apply(m: Bin[K, A, L, R]): Bin[K, B, OL, OR] = Bin(a(m.a), l(m.l), r(m.r))
    }
}
