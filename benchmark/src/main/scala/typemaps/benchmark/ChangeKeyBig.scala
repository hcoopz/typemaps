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
import shapeless.syntax.singleton._
import shapeless.record._

class ChangeKeyBig {
  import Maps._

  @Benchmark
  def updateTypeMap(): Unit = {
    typeMapBig.updateWithKey[Unit, Option[Unit]].const(10)
    typeMapBig.updateWithKey[Boolean, Option[Unit]].const(10)
    typeMapBig.updateWithKey[Short, Option[Unit]].const(10)
    typeMapBig.updateWithKey[Int, Option[Unit]].const(10)
    typeMapBig.updateWithKey[Long, Option[Unit]].const(10)
    typeMapBig.updateWithKey[Float, Option[Unit]].const(10)
    typeMapBig.updateWithKey[Double, Option[Unit]].const(10)
    typeMapBig.updateWithKey[BigDecimal, Option[Unit]].const(10)
    typeMapBig.updateWithKey[BigInt, Option[Unit]].const(10)
    typeMapBig.updateWithKey[Char, Option[Unit]].const(10)
    typeMapBig.updateWithKey[Symbol, Option[Unit]].const(10)
    typeMapBig.updateWithKey[String, Option[Unit]].const(10)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def updateScalaMap(): Unit = {
    (scalaMapBig - Tags.unit).updated(Tags.optionUnit, 10)
    (scalaMapBig - Tags.boolean).updated(Tags.optionUnit, 10)
    (scalaMapBig - Tags.short).updated(Tags.optionUnit, 10)
    (scalaMapBig - Tags.int).updated(Tags.optionUnit, 10)
    (scalaMapBig - Tags.long).updated(Tags.optionUnit, 10)
    (scalaMapBig - Tags.float).updated(Tags.optionUnit, 10)
    (scalaMapBig - Tags.double).updated(Tags.optionUnit, 10)
    (scalaMapBig - Tags.bigDecimal).updated(Tags.optionUnit, 10)
    (scalaMapBig - Tags.bigInt).updated(Tags.optionUnit, 10)
    (scalaMapBig - Tags.char).updated(Tags.optionUnit, 10)
    (scalaMapBig - Tags.symbol).updated(Tags.optionUnit, 10)
    (scalaMapBig - Tags.string).updated(Tags.optionUnit, 10)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def updateShapelessHMap(): Unit = {
    shapelessHMapBig - SingletonTags.unit + (SingletonTags.optionUnit -> 10)
    shapelessHMapBig - SingletonTags.boolean + (SingletonTags.optionUnit -> 10)
    shapelessHMapBig - SingletonTags.short + (SingletonTags.optionUnit -> 10)
    shapelessHMapBig - SingletonTags.int + (SingletonTags.optionUnit -> 10)
    shapelessHMapBig - SingletonTags.long + (SingletonTags.optionUnit -> 10)
    shapelessHMapBig - SingletonTags.float + (SingletonTags.optionUnit -> 10)
    shapelessHMapBig - SingletonTags.double + (SingletonTags.optionUnit -> 10)
    shapelessHMapBig - SingletonTags.bigDecimal + (SingletonTags.optionUnit -> 10)
    shapelessHMapBig - SingletonTags.bigInt + (SingletonTags.optionUnit -> 10)
    shapelessHMapBig - SingletonTags.char + (SingletonTags.optionUnit -> 10)
    shapelessHMapBig - SingletonTags.symbol + (SingletonTags.optionUnit -> 10)
    shapelessHMapBig - SingletonTags.string + (SingletonTags.optionUnit -> 10)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def updateShapelessRecord(): Unit = {
    shapelessRecordBig - Tags.unit + (Tags.optionUnit ->> 10)
    shapelessRecordBig - Tags.boolean + (Tags.optionUnit ->> 10)
    shapelessRecordBig - Tags.short + (Tags.optionUnit ->> 10)
    shapelessRecordBig - Tags.int + (Tags.optionUnit ->> 10)
    shapelessRecordBig - Tags.long + (Tags.optionUnit ->> 10)
    shapelessRecordBig - Tags.float + (Tags.optionUnit ->> 10)
    shapelessRecordBig - Tags.double + (Tags.optionUnit ->> 10)
    shapelessRecordBig - Tags.bigDecimal + (Tags.optionUnit ->> 10)
    shapelessRecordBig - Tags.bigInt + (Tags.optionUnit ->> 10)
    shapelessRecordBig - Tags.char + (Tags.optionUnit ->> 10)
    shapelessRecordBig - Tags.symbol + (Tags.optionUnit ->> 10)
    shapelessRecordBig - Tags.string + (Tags.optionUnit ->> 10)
  }
}
