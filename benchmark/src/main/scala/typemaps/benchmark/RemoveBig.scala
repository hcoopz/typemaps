package typemaps.benchmark

import org.openjdk.jmh.annotations.Benchmark
import typemaps._
import shapeless.record._

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
    scalaMapBig - Tags.unit
    scalaMapBig - Tags.boolean
    scalaMapBig - Tags.short
    scalaMapBig - Tags.int
    scalaMapBig - Tags.long
    scalaMapBig - Tags.float
    scalaMapBig - Tags.double
    scalaMapBig - Tags.bigDecimal
    scalaMapBig - Tags.bigInt
    scalaMapBig - Tags.char
    scalaMapBig - Tags.symbol
    scalaMapBig - Tags.string
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def removeShapelessHMap(): Unit = {
    shapelessHMapBig - SingletonTags.unit
    shapelessHMapBig - SingletonTags.boolean
    shapelessHMapBig - SingletonTags.short
    shapelessHMapBig - SingletonTags.int
    shapelessHMapBig - SingletonTags.long
    shapelessHMapBig - SingletonTags.float
    shapelessHMapBig - SingletonTags.double
    shapelessHMapBig - SingletonTags.bigDecimal
    shapelessHMapBig - SingletonTags.bigInt
    shapelessHMapBig - SingletonTags.char
    shapelessHMapBig - SingletonTags.symbol
    shapelessHMapBig - SingletonTags.string
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def removeShapelessRecord(): Unit = {
    shapelessRecordBig - Tags.unit
    shapelessRecordBig - Tags.boolean
    shapelessRecordBig - Tags.short
    shapelessRecordBig - Tags.int
    shapelessRecordBig - Tags.long
    shapelessRecordBig - Tags.float
    shapelessRecordBig - Tags.double
    shapelessRecordBig - Tags.bigDecimal
    shapelessRecordBig - Tags.bigInt
    shapelessRecordBig - Tags.char
    shapelessRecordBig - Tags.symbol
    shapelessRecordBig - Tags.string
  }
}
