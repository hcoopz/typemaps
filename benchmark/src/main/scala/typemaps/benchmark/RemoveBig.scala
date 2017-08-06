package typemaps.benchmark

import org.openjdk.jmh.annotations.Benchmark
import typemaps._
import Syntax._

class RemoveBig {
  import Maps._

  @Benchmark
  def removeTypeMap(): Unit = {
    typeMapBig.remove[Unit]
    typeMapBig.remove[Boolean]
    typeMapBig.remove[Short]
    typeMapBig.remove[Int]
    typeMapBig.remove[Long]
    typeMapBig.remove[Float]
    typeMapBig.remove[Double]
    typeMapBig.remove[BigDecimal]
    typeMapBig.remove[BigInt]
    typeMapBig.remove[Char]
    typeMapBig.remove[Symbol]
    typeMapBig.remove[String]
  }


  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def removeScalaMap(): Unit = {
    scalaMapBig - MapTags.unit
    scalaMapBig - MapTags.boolean
    scalaMapBig - MapTags.short
    scalaMapBig - MapTags.int
    scalaMapBig - MapTags.long
    scalaMapBig - MapTags.float
    scalaMapBig - MapTags.double
    scalaMapBig - MapTags.bigDecimal
    scalaMapBig - MapTags.bigInt
    scalaMapBig - MapTags.char
    scalaMapBig - MapTags.symbol
    scalaMapBig - MapTags.string
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def removeShapelessHMap(): Unit = {
    shapelessHMapBig - HMapTags.unit
    shapelessHMapBig - HMapTags.boolean
    shapelessHMapBig - HMapTags.short
    shapelessHMapBig - HMapTags.int
    shapelessHMapBig - HMapTags.long
    shapelessHMapBig - HMapTags.float
    shapelessHMapBig - HMapTags.double
    shapelessHMapBig - HMapTags.bigDecimal
    shapelessHMapBig - HMapTags.bigInt
    shapelessHMapBig - HMapTags.char
    shapelessHMapBig - HMapTags.symbol
    shapelessHMapBig - HMapTags.string
  }
}
