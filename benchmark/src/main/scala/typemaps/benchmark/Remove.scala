package typemaps.benchmark

import org.openjdk.jmh.annotations.Benchmark
import typemaps._
import Syntax._
import shapeless._

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
    scalaMap - 'unit
    scalaMap - 'boolean
    scalaMap - 'short
    scalaMap - 'int
    scalaMap - 'long
    scalaMap - 'float
    scalaMap - 'double
  }

  import HMapTags._

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def removeShapelessHMap(): Unit = {
    shapelessHMap - tags.unit
    shapelessHMap - tags.boolean
    shapelessHMap - tags.short
    shapelessHMap - tags.int
    shapelessHMap - tags.long
    shapelessHMap - tags.float
    shapelessHMap - tags.double
  }
}
