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

import shapeless.HList._
import shapeless._

trait Types {
  // It doesn't really matter what these are, we merely want a list of distinct types and values to go with them
  protected type Types =
    Unit :: Boolean :: Short :: Int ::
      Long :: Float :: Double :: String ::
      Option[Unit] :: Option[Boolean] :: Option[Short] :: Option[Int] ::
      Option[Long] :: Option[Float] :: Option[Double] ::
      HNil

  // Some of the tests take a very long time to compile when using 15 types, but behave more reasonably with 6 types
  protected type TypesShort =
    Unit :: Boolean :: Short ::
      Long :: Float :: Double ::
      HNil

  protected type ValuesShort =
    Int :: Char :: String ::
      Option[Int] :: Unit :: Char ::
      HNil

  protected val values =
    1 :: 'a' :: "c" :: 3.0 ::
      Some(4) :: () :: 'b' :: List(42) ::
      false :: Set(true) :: Map('b -> 37) :: 2L ::
      Some('c) :: "foo" :: "bar" ::
      HNil

  protected val valuesShort =
    1 :: 'a' :: "c" ::
      Some(4) :: () :: 'b' ::
      HNil
}
