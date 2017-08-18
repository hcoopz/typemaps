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

import org.scalatest.Matchers
import shapeless._

sealed trait UpdatedValuesList[M <: HList, K1 <: HList, V1 <: HList, K2 <: HList, V2 <: HList] {
  def apply(m: M, values1: V1, values2: V2): Unit
}

object UpdatedValuesList extends Matchers {
  def apply[K <: HList]: Fn[K] = new Fn

  class Fn[K <: HList] {
    def apply[M <: HList, V <: HList](m: M, values: V)(implicit ev: UpdatedValuesList[M, K, V, HNil, HNil]): Unit = ev(m, values, HNil)
  }

  implicit def last[M <: TypeMap, K1, V1, K2 <: HList, V2 <: HList](implicit head: Lookup.Aux[M, K1, Unit],
                                                                    tail: LookupAllValues[M, K2, V2]
                                                                   ): UpdatedValuesList[M :: HNil, K1 :: HNil, V1 :: HNil, K2, V2] = new UpdatedValuesList[M :: HNil, K1 :: HNil, V1 :: HNil, K2, V2] {
    override def apply(m: M :: HNil, values1: V1 :: HNil, values2: V2): Unit = {
      head(m.head) shouldEqual ()
      tail(m.head, values2)
    }
  }

  implicit def hcons[MH <: TypeMap, MT <: HList, K1H, K1T <: HList, V1H, V1T <: HList, K2 <: HList, V2 <: HList](implicit head1: Lookup.Aux[MH, K1H, Unit],
                                                                                                                 headPre: LookupAllValues[MH, K1T, V1T],
                                                                                                                 headPost: LookupAllValues[MH, K2, V2],
                                                                                                                 tail: UpdatedValuesList[MT, K1T, V1T, K1H :: K2, V1H :: V2]
                                                                                                                ): UpdatedValuesList[MH :: MT, K1H :: K1T, V1H :: V1T, K2, V2] =
    new UpdatedValuesList[MH :: MT, K1H :: K1T, V1H :: V1T, K2, V2] {
      override def apply(m: MH :: MT, values1: V1H :: V1T, values2: V2): Unit = {
        head1(m.head) shouldEqual ()
        headPre(m.head, values1.tail)
        headPost(m.head, values2)
        tail(m.tail, values1.tail, values1.head :: values2)
      }
    }
}