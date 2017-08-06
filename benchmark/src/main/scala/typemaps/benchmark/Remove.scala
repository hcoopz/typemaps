package typemaps.benchmark

import org.openjdk.jmh.annotations.Benchmark
import typemaps._
import Syntax._
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
