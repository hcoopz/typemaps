package typemaps.benchmark

import org.openjdk.jmh.annotations.Benchmark
import typemaps._
import Syntax._
import shapeless._

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
    scalaMap('unit)
    scalaMap('boolean)
    scalaMap('short)
    scalaMap('int)
    scalaMap('long)
    scalaMap('float)
    scalaMap('double)
  }

  import HMapTags._

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def lookupShapelessHMap(): Unit = {
    shapelessHMap.get(tags.unit).get
    shapelessHMap.get(tags.boolean).get
    shapelessHMap.get(tags.short).get
    shapelessHMap.get(tags.int).get
    shapelessHMap.get(tags.long).get
    shapelessHMap.get(tags.float).get
    shapelessHMap.get(tags.double).get
  }
}
