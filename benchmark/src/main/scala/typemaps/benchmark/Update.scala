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
import shapeless.syntax.singleton._

class Update {
  import Maps._

  @Benchmark
  def updateTypeMap(): Unit = {
    typeMap.update[Unit].apply(_ + 1)
    typeMap.update[Boolean].apply(_ + 1)
    typeMap.update[Short].apply(_ + 1)
    typeMap.update[Int].apply(_ + 1)
    typeMap.update[Long].apply(_ + 1)
    typeMap.update[Float].apply(_ + 1)
    typeMap.update[Double].apply(_ + 1)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def updateScalaMap(): Unit = {
    scalaMap.updated(Tags.unit, scalaMap(Tags.unit) + 1)
    scalaMap.updated(Tags.boolean, scalaMap(Tags.boolean) + 1)
    scalaMap.updated(Tags.short, scalaMap(Tags.short) + 1)
    scalaMap.updated(Tags.int, scalaMap(Tags.int) + 1)
    scalaMap.updated(Tags.long, scalaMap(Tags.long) + 1)
    scalaMap.updated(Tags.float, scalaMap(Tags.float) + 1)
    scalaMap.updated(Tags.double, scalaMap(Tags.double) + 1)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def updateShapelessHMap(): Unit = {
    shapelessHMap + (SingletonTags.unit -> (shapelessHMap.get(SingletonTags.unit).get + 1))
    shapelessHMap + (SingletonTags.boolean -> (shapelessHMap.get(SingletonTags.boolean).get + 1))
    shapelessHMap + (SingletonTags.short -> (shapelessHMap.get(SingletonTags.short).get + 1))
    shapelessHMap + (SingletonTags.int -> (shapelessHMap.get(SingletonTags.int).get + 1))
    shapelessHMap + (SingletonTags.long -> (shapelessHMap.get(SingletonTags.long).get + 1))
    shapelessHMap + (SingletonTags.float -> (shapelessHMap.get(SingletonTags.float).get + 1))
    shapelessHMap + (SingletonTags.double -> (shapelessHMap.get(SingletonTags.double).get + 1))
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def updateShapelessRecord(): Unit = {
    shapelessRecord + (Tags.unit ->> (shapelessRecord(Tags.unit) + 1))
    shapelessRecord + (Tags.boolean ->> (shapelessRecord(Tags.boolean) + 1))
    shapelessRecord + (Tags.short ->> (shapelessRecord(Tags.short) + 1))
    shapelessRecord + (Tags.int ->> (shapelessRecord(Tags.int) + 1))
    shapelessRecord + (Tags.long ->> (shapelessRecord(Tags.long) + 1))
    shapelessRecord + (Tags.float ->> (shapelessRecord(Tags.float) + 1))
    shapelessRecord + (Tags.double ->> (shapelessRecord(Tags.double) + 1))
  }
}
