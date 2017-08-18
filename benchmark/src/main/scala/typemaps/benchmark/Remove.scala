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

class Remove {
  import Maps._

  @Benchmark
  def removeTypeMap(): Unit = {
    typeMap.remove[Unit]
    typeMap.remove[Boolean]
    typeMap.remove[Short]
    typeMap.remove[Int]
    typeMap.remove[Long]
    typeMap.remove[Float]
    typeMap.remove[Double]
  }


  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def removeScalaMap(): Unit = {
    scalaMap - Tags.unit
    scalaMap - Tags.boolean
    scalaMap - Tags.short
    scalaMap - Tags.int
    scalaMap - Tags.long
    scalaMap - Tags.float
    scalaMap - Tags.double
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def removeShapelessHMap(): Unit = {
    shapelessHMap - SingletonTags.unit
    shapelessHMap - SingletonTags.boolean
    shapelessHMap - SingletonTags.short
    shapelessHMap - SingletonTags.int
    shapelessHMap - SingletonTags.long
    shapelessHMap - SingletonTags.float
    shapelessHMap - SingletonTags.double
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def removeShapelessRecord(): Unit = {
    shapelessRecord - Tags.unit
    shapelessRecord - Tags.boolean
    shapelessRecord - Tags.short
    shapelessRecord - Tags.int
    shapelessRecord - Tags.long
    shapelessRecord - Tags.float
    shapelessRecord - Tags.double
  }
}
