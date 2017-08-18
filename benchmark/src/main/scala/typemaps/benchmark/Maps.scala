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
package typemaps.benchmark

import org.openjdk.jmh.annotations.Benchmark
import typemaps._
import shapeless._
import shapeless.syntax.singleton._

object Maps {
  val typeMap = TypeMap.empty
    .insert[Unit](1)
    .insert[Boolean](2)
    .insert[Short](3)
    .insert[Int](4)
    .insert[Long](5)
    .insert[Float](6)
    .insert[Double](7)

  val typeMapBig = TypeMap.empty
    .insert[Unit](1)
    .insert[Boolean](2)
    .insert[Short](3)
    .insert[Int](4)
    .insert[Long](5)
    .insert[Float](6)
    .insert[Double](7)
    .insert[BigDecimal](8)
    .insert[BigInt](9)
    .insert[Char](10)
    .insert[Symbol](11)
    .insert[String](12)

  val scalaMap = Map(
    Tags.unit -> 1,
    Tags.boolean -> 2,
    Tags.short -> 3,
    Tags.int -> 4,
    Tags.long -> 5,
    Tags.float -> 6,
    Tags.double -> 7
  )

  val scalaMapBig = Map(
    Tags.unit -> 1,
    Tags.boolean -> 2,
    Tags.short -> 3,
    Tags.int -> 4,
    Tags.long -> 5,
    Tags.float -> 6,
    Tags.double -> 7,
    Tags.bigDecimal -> 8,
    Tags.bigInt -> 9,
    Tags.char -> 10,
    Tags.symbol -> 11,
    Tags.string -> 12
  )

  val shapelessHMap = new HMapBuilder[HMapRel].apply(
    SingletonTags.unit -> 1,
    SingletonTags.boolean -> 2,
    SingletonTags.short -> 3,
    SingletonTags.int -> 4,
    SingletonTags.long -> 5,
    SingletonTags.float -> 6,
    SingletonTags.double -> 7
  )

  val shapelessHMapBig = new HMapBuilder[HMapRel].apply(
    SingletonTags.unit -> 1,
    SingletonTags.boolean -> 2,
    SingletonTags.short -> 3,
    SingletonTags.int -> 4,
    SingletonTags.long -> 5,
    SingletonTags.float -> 6,
    SingletonTags.double -> 7,
    SingletonTags.bigDecimal -> 8,
    SingletonTags.bigInt -> 9,
    SingletonTags.char -> 10,
    SingletonTags.symbol -> 11,
    SingletonTags.string -> 12
  )

  val shapelessRecord =
    (Tags.unit ->> 1) ::
      (Tags.boolean ->> 2) ::
      (Tags.short ->> 3) ::
      (Tags.int ->> 4) ::
      (Tags.long ->> 5) ::
      (Tags.float ->> 6) ::
      (Tags.double ->> 7) ::
      HNil

  val shapelessRecordBig =
    (Tags.unit ->> 1) ::
      (Tags.boolean ->> 2) ::
      (Tags.short ->> 3) ::
      (Tags.int ->> 4) ::
      (Tags.long ->> 5) ::
      (Tags.float ->> 6) ::
      (Tags.double ->> 7) ::
      (Tags.bigDecimal ->> 8) ::
      (Tags.bigInt ->> 9) ::
      (Tags.char ->> 10) ::
      (Tags.symbol ->> 11) ::
      (Tags.string ->> 12) ::
      HNil
}
