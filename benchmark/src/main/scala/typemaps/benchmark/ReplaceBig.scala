package typemaps.benchmark

import org.openjdk.jmh.annotations.Benchmark
import typemaps._
import Syntax._
import shapeless.record._
import shapeless.syntax.singleton._

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
    scalaMapBig.updated(Tags.unit, 10)
    scalaMapBig.updated(Tags.boolean, 10)
    scalaMapBig.updated(Tags.short, 10)
    scalaMapBig.updated(Tags.int, 10)
    scalaMapBig.updated(Tags.long, 10)
    scalaMapBig.updated(Tags.float, 10)
    scalaMapBig.updated(Tags.double, 10)
    scalaMapBig.updated(Tags.bigDecimal, 10)
    scalaMapBig.updated(Tags.bigInt, 10)
    scalaMapBig.updated(Tags.char, 10)
    scalaMapBig.updated(Tags.symbol, 10)
    scalaMapBig.updated(Tags.string, 10)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def replaceShapelessHMap(): Unit = {
    shapelessHMapBig + (SingletonTags.unit -> 10)
    shapelessHMapBig + (SingletonTags.boolean -> 10)
    shapelessHMapBig + (SingletonTags.short -> 10)
    shapelessHMapBig + (SingletonTags.int -> 10)
    shapelessHMapBig + (SingletonTags.long -> 10)
    shapelessHMapBig + (SingletonTags.float -> 10)
    shapelessHMapBig + (SingletonTags.double -> 10)
    shapelessHMapBig + (SingletonTags.bigDecimal -> 10)
    shapelessHMapBig + (SingletonTags.bigInt -> 10)
    shapelessHMapBig + (SingletonTags.char -> 10)
    shapelessHMapBig + (SingletonTags.symbol -> 10)
    shapelessHMapBig + (SingletonTags.string -> 10)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def replaceShapelessRecord(): Unit = {
    shapelessRecordBig + (Tags.unit ->> 10)
    shapelessRecordBig + (Tags.boolean ->> 10)
    shapelessRecordBig + (Tags.short ->> 10)
    shapelessRecordBig + (Tags.int ->> 10)
    shapelessRecordBig + (Tags.long ->> 10)
    shapelessRecordBig + (Tags.float ->> 10)
    shapelessRecordBig + (Tags.double ->> 10)
    shapelessRecordBig + (Tags.bigDecimal ->> 10)
    shapelessRecordBig + (Tags.bigInt ->> 10)
    shapelessRecordBig + (Tags.char ->> 10)
    shapelessRecordBig + (Tags.symbol ->> 10)
    shapelessRecordBig + (Tags.string ->> 10)
  }
}
