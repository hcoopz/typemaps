package typemaps.benchmark

import org.openjdk.jmh.annotations.Benchmark
import typemaps._
import Syntax._

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
    scalaMap(MapTags.unit)
    scalaMap(MapTags.boolean)
    scalaMap(MapTags.short)
    scalaMap(MapTags.int)
    scalaMap(MapTags.long)
    scalaMap(MapTags.float)
    scalaMap(MapTags.double)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def lookupShapelessHMap(): Unit = {
    shapelessHMap.get(HMapTags.unit).get
    shapelessHMap.get(HMapTags.boolean).get
    shapelessHMap.get(HMapTags.short).get
    shapelessHMap.get(HMapTags.int).get
    shapelessHMap.get(HMapTags.long).get
    shapelessHMap.get(HMapTags.float).get
    shapelessHMap.get(HMapTags.double).get
  }
}
