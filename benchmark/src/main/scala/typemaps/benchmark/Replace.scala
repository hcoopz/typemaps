package typemaps.benchmark

import org.openjdk.jmh.annotations.Benchmark
import typemaps._
import Syntax._

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
    scalaMap.updated(MapTags.unit, 10)
    scalaMap.updated(MapTags.boolean, 10)
    scalaMap.updated(MapTags.short, 10)
    scalaMap.updated(MapTags.int, 10)
    scalaMap.updated(MapTags.long, 10)
    scalaMap.updated(MapTags.float, 10)
    scalaMap.updated(MapTags.double, 10)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def replaceShapelessHMap(): Unit = {
    shapelessHMap + (HMapTags.unit -> 10)
    shapelessHMap + (HMapTags.boolean -> 10)
    shapelessHMap + (HMapTags.short -> 10)
    shapelessHMap + (HMapTags.int -> 10)
    shapelessHMap + (HMapTags.long -> 10)
    shapelessHMap + (HMapTags.float -> 10)
    shapelessHMap + (HMapTags.double -> 10)
  }
}
