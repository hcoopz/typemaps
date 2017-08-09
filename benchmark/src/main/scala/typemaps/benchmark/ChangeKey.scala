package typemaps.benchmark

import org.openjdk.jmh.annotations.Benchmark
import typemaps._
import shapeless.syntax.singleton._
import shapeless.record._

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
    (scalaMap - Tags.unit).updated(Tags.optionUnit, 10)
    (scalaMap - Tags.boolean).updated(Tags.optionUnit, 10)
    (scalaMap - Tags.short).updated(Tags.optionUnit, 10)
    (scalaMap - Tags.int).updated(Tags.optionUnit, 10)
    (scalaMap - Tags.long).updated(Tags.optionUnit, 10)
    (scalaMap - Tags.float).updated(Tags.optionUnit, 10)
    (scalaMap - Tags.double).updated(Tags.optionUnit, 10)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def updateShapelessHMap(): Unit = {
    shapelessHMap - SingletonTags.unit + (SingletonTags.optionUnit -> 10)
    shapelessHMap - SingletonTags.boolean + (SingletonTags.optionUnit -> 10)
    shapelessHMap - SingletonTags.short + (SingletonTags.optionUnit -> 10)
    shapelessHMap - SingletonTags.int + (SingletonTags.optionUnit -> 10)
    shapelessHMap - SingletonTags.long + (SingletonTags.optionUnit -> 10)
    shapelessHMap - SingletonTags.float + (SingletonTags.optionUnit -> 10)
    shapelessHMap - SingletonTags.double + (SingletonTags.optionUnit -> 10)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def updateShapelessRecord(): Unit = {
    shapelessRecord - Tags.unit + (Tags.optionUnit ->> 10)
    shapelessRecord - Tags.boolean + (Tags.optionUnit ->> 10)
    shapelessRecord - Tags.short + (Tags.optionUnit ->> 10)
    shapelessRecord - Tags.int + (Tags.optionUnit ->> 10)
    shapelessRecord - Tags.long + (Tags.optionUnit ->> 10)
    shapelessRecord - Tags.float + (Tags.optionUnit ->> 10)
    shapelessRecord - Tags.double + (Tags.optionUnit ->> 10)
  }
}
