package typemaps.benchmark

import org.openjdk.jmh.annotations.Benchmark
import typemaps._
import Syntax._
import shapeless._

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
    scalaMap.updated('unit, scalaMap('unit) + 1)
    scalaMap.updated('boolean, scalaMap('boolean) + 1)
    scalaMap.updated('short, scalaMap('short) + 1)
    scalaMap.updated('int, scalaMap('int) + 1)
    scalaMap.updated('long, scalaMap('long) + 1)
    scalaMap.updated('float, scalaMap('float) + 1)
    scalaMap.updated('double, scalaMap('double) + 1)
  }

  import HMapTags._

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def updateShapelessHMap(): Unit = {
    shapelessHMap + (tags.unit -> (shapelessHMap.get(tags.unit).get + 1))
    shapelessHMap + (tags.boolean -> (shapelessHMap.get(tags.boolean).get + 1))
    shapelessHMap + (tags.short -> (shapelessHMap.get(tags.short).get + 1))
    shapelessHMap + (tags.int -> (shapelessHMap.get(tags.int).get + 1))
    shapelessHMap + (tags.long -> (shapelessHMap.get(tags.long).get + 1))
    shapelessHMap + (tags.float -> (shapelessHMap.get(tags.float).get + 1))
    shapelessHMap + (tags.double -> (shapelessHMap.get(tags.double).get + 1))
  }
}
