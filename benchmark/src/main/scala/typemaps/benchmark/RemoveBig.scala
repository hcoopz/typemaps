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
import shapeless.record._

class RemoveBig {
  import Maps._

  @Benchmark
  def removeTypeMap(): Unit = {
    typeMapBig.remove[Unit]
    typeMapBig.remove[Boolean]
    typeMapBig.remove[Short]
    typeMapBig.remove[Int]
    typeMapBig.remove[Long]
    typeMapBig.remove[Float]
    typeMapBig.remove[Double]
    typeMapBig.remove[BigDecimal]
    typeMapBig.remove[BigInt]
    typeMapBig.remove[Char]
    typeMapBig.remove[Symbol]
    typeMapBig.remove[String]
  }


  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def removeScalaMap(): Unit = {
    scalaMapBig - Tags.unit
    scalaMapBig - Tags.boolean
    scalaMapBig - Tags.short
    scalaMapBig - Tags.int
    scalaMapBig - Tags.long
    scalaMapBig - Tags.float
    scalaMapBig - Tags.double
    scalaMapBig - Tags.bigDecimal
    scalaMapBig - Tags.bigInt
    scalaMapBig - Tags.char
    scalaMapBig - Tags.symbol
    scalaMapBig - Tags.string
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def removeShapelessHMap(): Unit = {
    shapelessHMapBig - SingletonTags.unit
    shapelessHMapBig - SingletonTags.boolean
    shapelessHMapBig - SingletonTags.short
    shapelessHMapBig - SingletonTags.int
    shapelessHMapBig - SingletonTags.long
    shapelessHMapBig - SingletonTags.float
    shapelessHMapBig - SingletonTags.double
    shapelessHMapBig - SingletonTags.bigDecimal
    shapelessHMapBig - SingletonTags.bigInt
    shapelessHMapBig - SingletonTags.char
    shapelessHMapBig - SingletonTags.symbol
    shapelessHMapBig - SingletonTags.string
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def removeShapelessRecord(): Unit = {
    shapelessRecordBig - Tags.unit
    shapelessRecordBig - Tags.boolean
    shapelessRecordBig - Tags.short
    shapelessRecordBig - Tags.int
    shapelessRecordBig - Tags.long
    shapelessRecordBig - Tags.float
    shapelessRecordBig - Tags.double
    shapelessRecordBig - Tags.bigDecimal
    shapelessRecordBig - Tags.bigInt
    shapelessRecordBig - Tags.char
    shapelessRecordBig - Tags.symbol
    shapelessRecordBig - Tags.string
  }
}
