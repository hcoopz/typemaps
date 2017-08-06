package typemaps.benchmark

import org.openjdk.jmh.annotations.Benchmark
import typemaps._
import Syntax._

class ChangeKey {
  import Maps._

  @Benchmark
  def updateTypeMap(): Unit = {
    typeMap.updateWithKey[Unit, Option[Unit]].const(10)
    typeMap.updateWithKey[Boolean, Option[Unit]].const(10)
    typeMap.updateWithKey[Short, Option[Unit]].const(10)
    typeMap.updateWithKey[Int, Option[Unit]].const(10)
    typeMap.updateWithKey[Long, Option[Unit]].const(10)
    typeMap.updateWithKey[Float, Option[Unit]].const(10)
    typeMap.updateWithKey[Double, Option[Unit]].const(10)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def updateScalaMap(): Unit = {
    (scalaMap - MapTags.unit).updated(MapTags.optionUnit, 10)
    (scalaMap - MapTags.boolean).updated(MapTags.optionUnit, 10)
    (scalaMap - MapTags.short).updated(MapTags.optionUnit, 10)
    (scalaMap - MapTags.int).updated(MapTags.optionUnit, 10)
    (scalaMap - MapTags.long).updated(MapTags.optionUnit, 10)
    (scalaMap - MapTags.float).updated(MapTags.optionUnit, 10)
    (scalaMap - MapTags.double).updated(MapTags.optionUnit, 10)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def updateShapelessHMap(): Unit = {
    shapelessHMap - HMapTags.unit + (HMapTags.optionUnit -> 10)
    shapelessHMap - HMapTags.boolean + (HMapTags.optionUnit -> 10)
    shapelessHMap - HMapTags.short + (HMapTags.optionUnit -> 10)
    shapelessHMap - HMapTags.int + (HMapTags.optionUnit -> 10)
    shapelessHMap - HMapTags.long + (HMapTags.optionUnit -> 10)
    shapelessHMap - HMapTags.float + (HMapTags.optionUnit -> 10)
    shapelessHMap - HMapTags.double + (HMapTags.optionUnit -> 10)
  }
}
