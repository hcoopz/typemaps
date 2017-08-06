package typemaps.benchmark

import org.openjdk.jmh.annotations.Benchmark
import typemaps._
import Syntax._

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
    scalaMap - MapTags.unit
    scalaMap - MapTags.boolean
    scalaMap - MapTags.short
    scalaMap - MapTags.int
    scalaMap - MapTags.long
    scalaMap - MapTags.float
    scalaMap - MapTags.double
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def removeShapelessHMap(): Unit = {
    shapelessHMap - HMapTags.unit
    shapelessHMap - HMapTags.boolean
    shapelessHMap - HMapTags.short
    shapelessHMap - HMapTags.int
    shapelessHMap - HMapTags.long
    shapelessHMap - HMapTags.float
    shapelessHMap - HMapTags.double
  }
}
