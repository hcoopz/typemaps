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

class Replace {
  import Maps._

  @Benchmark
  def replaceTypeMap(): Unit = {
    typeMap.update[Unit].const(10)
    typeMap.update[Boolean].const(10)
    typeMap.update[Short].const(10)
    typeMap.update[Int].const(10)
    typeMap.update[Long].const(10)
    typeMap.update[Float].const(10)
    typeMap.update[Double].const(10)
  }


  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def replaceScalaMap(): Unit = {
    scalaMap.updated(Tags.unit, 10)
    scalaMap.updated(Tags.boolean, 10)
    scalaMap.updated(Tags.short, 10)
    scalaMap.updated(Tags.int, 10)
    scalaMap.updated(Tags.long, 10)
    scalaMap.updated(Tags.float, 10)
    scalaMap.updated(Tags.double, 10)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def replaceShapelessHMap(): Unit = {
    shapelessHMap + (SingletonTags.unit -> 10)
    shapelessHMap + (SingletonTags.boolean -> 10)
    shapelessHMap + (SingletonTags.short -> 10)
    shapelessHMap + (SingletonTags.int -> 10)
    shapelessHMap + (SingletonTags.long -> 10)
    shapelessHMap + (SingletonTags.float -> 10)
    shapelessHMap + (SingletonTags.double -> 10)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def replaceShapelessRecord(): Unit = {
    shapelessRecord + (Tags.unit ->> 10)
    shapelessRecord + (Tags.boolean ->> 10)
    shapelessRecord + (Tags.short ->> 10)
    shapelessRecord + (Tags.int ->> 10)
    shapelessRecord + (Tags.long ->> 10)
    shapelessRecord + (Tags.float ->> 10)
    shapelessRecord + (Tags.double ->> 10)
  }
}
