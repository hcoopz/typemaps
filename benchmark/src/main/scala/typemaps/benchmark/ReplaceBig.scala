package typemaps.benchmark

import org.openjdk.jmh.annotations.Benchmark
import typemaps._
import Syntax._

class ReplaceBig {
  import Maps._

  @Benchmark
  def replaceTypeMap(): Unit = {
    typeMapBig.update[Unit].const(10)
    typeMapBig.update[Boolean].const(10)
    typeMapBig.update[Short].const(10)
    typeMapBig.update[Int].const(10)
    typeMapBig.update[Long].const(10)
    typeMapBig.update[Float].const(10)
    typeMapBig.update[Double].const(10)
    typeMapBig.update[BigDecimal].const(10)
    typeMapBig.update[BigInt].const(10)
    typeMapBig.update[Char].const(10)
    typeMapBig.update[Symbol].const(10)
    typeMapBig.update[String].const(10)
  }


  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def replaceScalaMap(): Unit = {
    scalaMapBig.updated(MapTags.unit, 10)
    scalaMapBig.updated(MapTags.boolean, 10)
    scalaMapBig.updated(MapTags.short, 10)
    scalaMapBig.updated(MapTags.int, 10)
    scalaMapBig.updated(MapTags.long, 10)
    scalaMapBig.updated(MapTags.float, 10)
    scalaMapBig.updated(MapTags.double, 10)
    scalaMapBig.updated(MapTags.bigDecimal, 10)
    scalaMapBig.updated(MapTags.bigInt, 10)
    scalaMapBig.updated(MapTags.char, 10)
    scalaMapBig.updated(MapTags.symbol, 10)
    scalaMapBig.updated(MapTags.string, 10)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def replaceShapelessHMap(): Unit = {
    shapelessHMapBig + (HMapTags.unit -> 10)
    shapelessHMapBig + (HMapTags.boolean -> 10)
    shapelessHMapBig + (HMapTags.short -> 10)
    shapelessHMapBig + (HMapTags.int -> 10)
    shapelessHMapBig + (HMapTags.long -> 10)
    shapelessHMapBig + (HMapTags.float -> 10)
    shapelessHMapBig + (HMapTags.double -> 10)
    shapelessHMapBig + (HMapTags.bigDecimal -> 10)
    shapelessHMapBig + (HMapTags.bigInt -> 10)
    shapelessHMapBig + (HMapTags.char -> 10)
    shapelessHMapBig + (HMapTags.symbol -> 10)
    shapelessHMapBig + (HMapTags.string -> 10)
  }
}
