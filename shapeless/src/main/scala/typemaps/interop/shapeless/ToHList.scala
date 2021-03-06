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

sealed trait ToHList[M <: TypeMap] {
  type Out <: HList
  def apply(m: M): Out
}

object ToHList {
  type Aux[M <: TypeMap, O <: HList] = ToHList[M] { type Out = O }

  sealed trait Helper[M <: TypeMap, In <: HList] {
    type Out <: HList
    private[ToHList] def apply(m: M, in: In): Out
  }

  object Helper {
    type Aux[M <: TypeMap, I <: HList, O <: HList] = Helper[M, I] { type Out = O }
  }

  implicit def tip[In <: HList]: Helper.Aux[Tip, In, In] = new Helper[Tip, In] {
    override type Out = In
    override def apply(m: Tip, in: In): In = in
  }

  implicit def bin[A, L <: TypeMap, R <: TypeMap, In <: HList, OR <: HList, OL <: HList](implicit r: Helper.Aux[R, In, OR],
                                                                                         l: Helper.Aux[L, OR, OL]
                                                                                        ): Helper.Aux[Bin[A, A, L, R], In, A :: OL] =
    new Helper[Bin[A, A, L, R], In] {
      override type Out = A :: OL
      override private[ToHList] def apply(m: Bin[A, A, L, R], in: In): A :: OL = m.a :: l(m.l, r(m.r, in))
    }

  implicit def toHList[M <: TypeMap, O <: HList](implicit helper: Helper.Aux[M, HNil, O]
                                                ): ToHList.Aux[M, O] =
    new ToHList[M] {
      override type Out = O
      override def apply(m: M): O = helper(m, HNil)
    }
}
