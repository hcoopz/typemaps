package typemaps.benchmark

import org.openjdk.jmh.annotations.Benchmark
import typemaps._
import Syntax._

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
    scalaMap.updated(MapTags.unit, scalaMap(MapTags.unit) + 1)
    scalaMap.updated(MapTags.boolean, scalaMap(MapTags.boolean) + 1)
    scalaMap.updated(MapTags.short, scalaMap(MapTags.short) + 1)
    scalaMap.updated(MapTags.int, scalaMap(MapTags.int) + 1)
    scalaMap.updated(MapTags.long, scalaMap(MapTags.long) + 1)
    scalaMap.updated(MapTags.float, scalaMap(MapTags.float) + 1)
    scalaMap.updated(MapTags.double, scalaMap(MapTags.double) + 1)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def updateShapelessHMap(): Unit = {
    shapelessHMap + (HMapTags.unit -> (shapelessHMap.get(HMapTags.unit).get + 1))
    shapelessHMap + (HMapTags.boolean -> (shapelessHMap.get(HMapTags.boolean).get + 1))
    shapelessHMap + (HMapTags.short -> (shapelessHMap.get(HMapTags.short).get + 1))
    shapelessHMap + (HMapTags.int -> (shapelessHMap.get(HMapTags.int).get + 1))
    shapelessHMap + (HMapTags.long -> (shapelessHMap.get(HMapTags.long).get + 1))
    shapelessHMap + (HMapTags.float -> (shapelessHMap.get(HMapTags.float).get + 1))
    shapelessHMap + (HMapTags.double -> (shapelessHMap.get(HMapTags.double).get + 1))
  }
}
