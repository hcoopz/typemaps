package typemaps.benchmark

import org.openjdk.jmh.annotations.Benchmark
import typemaps._
import Syntax._

class ChangeKeyBig {
  import Maps._

  @Benchmark
  def updateTypeMap(): Unit = {
    typeMapBig.updateWithKey[Unit, Option[Unit]].const(10)
    typeMapBig.updateWithKey[Boolean, Option[Unit]].const(10)
    typeMapBig.updateWithKey[Short, Option[Unit]].const(10)
    typeMapBig.updateWithKey[Int, Option[Unit]].const(10)
    typeMapBig.updateWithKey[Long, Option[Unit]].const(10)
    typeMapBig.updateWithKey[Float, Option[Unit]].const(10)
    typeMapBig.updateWithKey[Double, Option[Unit]].const(10)
    typeMapBig.updateWithKey[BigDecimal, Option[Unit]].const(10)
    typeMapBig.updateWithKey[BigInt, Option[Unit]].const(10)
    typeMapBig.updateWithKey[Char, Option[Unit]].const(10)
    typeMapBig.updateWithKey[Symbol, Option[Unit]].const(10)
    typeMapBig.updateWithKey[String, Option[Unit]].const(10)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def updateScalaMap(): Unit = {
    (scalaMapBig - MapTags.unit).updated(MapTags.optionUnit, 10)
    (scalaMapBig - MapTags.boolean).updated(MapTags.optionUnit, 10)
    (scalaMapBig - MapTags.short).updated(MapTags.optionUnit, 10)
    (scalaMapBig - MapTags.int).updated(MapTags.optionUnit, 10)
    (scalaMapBig - MapTags.long).updated(MapTags.optionUnit, 10)
    (scalaMapBig - MapTags.float).updated(MapTags.optionUnit, 10)
    (scalaMapBig - MapTags.double).updated(MapTags.optionUnit, 10)
    (scalaMapBig - MapTags.bigDecimal).updated(MapTags.optionUnit, 10)
    (scalaMapBig - MapTags.bigInt).updated(MapTags.optionUnit, 10)
    (scalaMapBig - MapTags.char).updated(MapTags.optionUnit, 10)
    (scalaMapBig - MapTags.symbol).updated(MapTags.optionUnit, 10)
    (scalaMapBig - MapTags.string).updated(MapTags.optionUnit, 10)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def updateShapelessHMap(): Unit = {
    shapelessHMapBig - HMapTags.unit + (HMapTags.optionUnit -> 10)
    shapelessHMapBig - HMapTags.boolean + (HMapTags.optionUnit -> 10)
    shapelessHMapBig - HMapTags.short + (HMapTags.optionUnit -> 10)
    shapelessHMapBig - HMapTags.int + (HMapTags.optionUnit -> 10)
    shapelessHMapBig - HMapTags.long + (HMapTags.optionUnit -> 10)
    shapelessHMapBig - HMapTags.float + (HMapTags.optionUnit -> 10)
    shapelessHMapBig - HMapTags.double + (HMapTags.optionUnit -> 10)
    shapelessHMapBig - HMapTags.bigDecimal + (HMapTags.optionUnit -> 10)
    shapelessHMapBig - HMapTags.bigInt + (HMapTags.optionUnit -> 10)
    shapelessHMapBig - HMapTags.char + (HMapTags.optionUnit -> 10)
    shapelessHMapBig - HMapTags.symbol + (HMapTags.optionUnit -> 10)
    shapelessHMapBig - HMapTags.string + (HMapTags.optionUnit -> 10)
  }
}
