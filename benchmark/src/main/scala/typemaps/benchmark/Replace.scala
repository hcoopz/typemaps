package typemaps.benchmark

import org.openjdk.jmh.annotations.Benchmark
import typemaps._
import Syntax._
import shapeless._

class Replace {
  import Maps._

  @Benchmark
  def replaceTypeMap(): Unit = {
    typeMap.update[Unit].apply(_ => 10)
    typeMap.update[Boolean].apply(_ => 10)
    typeMap.update[Short].apply(_ => 10)
    typeMap.update[Int].apply(_ => 10)
    typeMap.update[Long].apply(_ => 10)
    typeMap.update[Float].apply(_ => 10)
    typeMap.update[Double].apply(_ => 10)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def replaceScalaMap(): Unit = {
    scalaMap.updated('unit, 10)
    scalaMap.updated('boolean, 10)
    scalaMap.updated('short, 10)
    scalaMap.updated('int, 10)
    scalaMap.updated('long, 10)
    scalaMap.updated('float, 10)
    scalaMap.updated('double, 10)
  }

  import HMapTags._

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def replaceShapelessHMap(): Unit = {
    shapelessHMap + (tags.unit -> 10)
    shapelessHMap + (tags.boolean -> 10)
    shapelessHMap + (tags.short -> 10)
    shapelessHMap + (tags.int -> 10)
    shapelessHMap + (tags.long -> 10)
    shapelessHMap + (tags.float -> 10)
    shapelessHMap + (tags.double -> 10)
  }
}
