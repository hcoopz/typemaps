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

sealed trait CheckTypeMaps[L <: HList] {
  def apply(l: L): Unit
}

object CheckTypeMaps extends Matchers with TypeMapMatchers {
  def apply[L <: HList](l: L)(implicit check: CheckTypeMaps[L]): Unit = check(l)

  implicit val hnil: CheckTypeMaps[HNil] = new CheckTypeMaps[HNil] {
    override def apply(l: HNil): Unit = {}
  }

  implicit def hcons[H <: TypeMap, T <: HList](implicit tail: CheckTypeMaps[T]): CheckTypeMaps[H :: T] =
    new CheckTypeMaps[H :: T] {
      override def apply(l: H :: T): Unit = {
        tail(l.tail)
        l.head shouldBe aBalancedTypeMap
        l.head should satisfyTheTypeMapConstraint
      }
    }
}

