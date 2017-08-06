package typemaps.benchmark

import org.openjdk.jmh.annotations.Benchmark
import typemaps._
import Syntax._

class UpdateBig {
  import Maps._

  @Benchmark
  def updateTypeMap(): Unit = {
    typeMapBig.update[Unit].apply(_ + 1)
    typeMapBig.update[Boolean].apply(_ + 1)
    typeMapBig.update[Short].apply(_ + 1)
    typeMapBig.update[Int].apply(_ + 1)
    typeMapBig.update[Long].apply(_ + 1)
    typeMapBig.update[Float].apply(_ + 1)
    typeMapBig.update[Double].apply(_ + 1)
    typeMapBig.update[BigDecimal].apply(_ + 1)
    typeMapBig.update[BigInt].apply(_ + 1)
    typeMapBig.update[Char].apply(_ + 1)
    typeMapBig.update[Symbol].apply(_ + 1)
    typeMapBig.update[String].apply(_ + 1)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def updateScalaMap(): Unit = {
    scalaMapBig.updated(MapTags.unit, scalaMapBig(MapTags.unit) + 1)
    scalaMapBig.updated(MapTags.boolean, scalaMapBig(MapTags.boolean) + 1)
    scalaMapBig.updated(MapTags.short, scalaMapBig(MapTags.short) + 1)
    scalaMapBig.updated(MapTags.int, scalaMapBig(MapTags.int) + 1)
    scalaMapBig.updated(MapTags.long, scalaMapBig(MapTags.long) + 1)
    scalaMapBig.updated(MapTags.float, scalaMapBig(MapTags.float) + 1)
    scalaMapBig.updated(MapTags.double, scalaMapBig(MapTags.double) + 1)
    scalaMapBig.updated(MapTags.bigDecimal, scalaMapBig(MapTags.bigDecimal) + 1)
    scalaMapBig.updated(MapTags.bigInt, scalaMapBig(MapTags.bigInt) + 1)
    scalaMapBig.updated(MapTags.char, scalaMapBig(MapTags.char) + 1)
    scalaMapBig.updated(MapTags.symbol, scalaMapBig(MapTags.symbol) + 1)
    scalaMapBig.updated(MapTags.string, scalaMapBig(MapTags.string) + 1)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def updateShapelessHMap(): Unit = {
    shapelessHMapBig + (HMapTags.unit -> (shapelessHMapBig.get(HMapTags.unit).get + 1))
    shapelessHMapBig + (HMapTags.boolean -> (shapelessHMapBig.get(HMapTags.boolean).get + 1))
    shapelessHMapBig + (HMapTags.short -> (shapelessHMapBig.get(HMapTags.short).get + 1))
    shapelessHMapBig + (HMapTags.int -> (shapelessHMapBig.get(HMapTags.int).get + 1))
    shapelessHMapBig + (HMapTags.long -> (shapelessHMapBig.get(HMapTags.long).get + 1))
    shapelessHMapBig + (HMapTags.float -> (shapelessHMapBig.get(HMapTags.float).get + 1))
    shapelessHMapBig + (HMapTags.double -> (shapelessHMapBig.get(HMapTags.double).get + 1))
    shapelessHMapBig + (HMapTags.bigDecimal -> (shapelessHMapBig.get(HMapTags.bigDecimal).get + 1))
    shapelessHMapBig + (HMapTags.bigInt -> (shapelessHMapBig.get(HMapTags.bigInt).get + 1))
    shapelessHMapBig + (HMapTags.char -> (shapelessHMapBig.get(HMapTags.char).get + 1))
    shapelessHMapBig + (HMapTags.symbol -> (shapelessHMapBig.get(HMapTags.symbol).get + 1))
    shapelessHMapBig + (HMapTags.string -> (shapelessHMapBig.get(HMapTags.string).get + 1))
  }
}
