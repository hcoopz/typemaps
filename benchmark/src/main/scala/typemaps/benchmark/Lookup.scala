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

class Lookup {
  import Maps._

  @Benchmark
  def lookupTypeMap(): Unit = {
    typeMap.lookup[Unit]
    typeMap.lookup[Boolean]
    typeMap.lookup[Short]
    typeMap.lookup[Int]
    typeMap.lookup[Long]
    typeMap.lookup[Float]
    typeMap.lookup[Double]
  }


  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def lookupScalaMap(): Unit = {
    scalaMap(Tags.unit)
    scalaMap(Tags.boolean)
    scalaMap(Tags.short)
    scalaMap(Tags.int)
    scalaMap(Tags.long)
    scalaMap(Tags.float)
    scalaMap(Tags.double)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def lookupShapelessHMap(): Unit = {
    shapelessHMap.get(SingletonTags.unit).get
    shapelessHMap.get(SingletonTags.boolean).get
    shapelessHMap.get(SingletonTags.short).get
    shapelessHMap.get(SingletonTags.int).get
    shapelessHMap.get(SingletonTags.long).get
    shapelessHMap.get(SingletonTags.float).get
    shapelessHMap.get(SingletonTags.double).get
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def lookupShapelessRecord(): Unit = {
    shapelessRecord(Tags.unit)
    shapelessRecord(Tags.boolean)
    shapelessRecord(Tags.short)
    shapelessRecord(Tags.int)
    shapelessRecord(Tags.long)
    shapelessRecord(Tags.float)
    shapelessRecord(Tags.double)
  }
}
