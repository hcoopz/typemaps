package typemaps.benchmark

import org.openjdk.jmh.annotations.Benchmark
import typemaps._
import Syntax._
import shapeless.syntax.singleton._
import shapeless.record._

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
    scalaMapBig.updated(Tags.unit, scalaMapBig(Tags.unit) + 1)
    scalaMapBig.updated(Tags.boolean, scalaMapBig(Tags.boolean) + 1)
    scalaMapBig.updated(Tags.short, scalaMapBig(Tags.short) + 1)
    scalaMapBig.updated(Tags.int, scalaMapBig(Tags.int) + 1)
    scalaMapBig.updated(Tags.long, scalaMapBig(Tags.long) + 1)
    scalaMapBig.updated(Tags.float, scalaMapBig(Tags.float) + 1)
    scalaMapBig.updated(Tags.double, scalaMapBig(Tags.double) + 1)
    scalaMapBig.updated(Tags.bigDecimal, scalaMapBig(Tags.bigDecimal) + 1)
    scalaMapBig.updated(Tags.bigInt, scalaMapBig(Tags.bigInt) + 1)
    scalaMapBig.updated(Tags.char, scalaMapBig(Tags.char) + 1)
    scalaMapBig.updated(Tags.symbol, scalaMapBig(Tags.symbol) + 1)
    scalaMapBig.updated(Tags.string, scalaMapBig(Tags.string) + 1)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def updateShapelessHMap(): Unit = {
    shapelessHMapBig + (SingletonTags.unit -> (shapelessHMapBig.get(SingletonTags.unit).get + 1))
    shapelessHMapBig + (SingletonTags.boolean -> (shapelessHMapBig.get(SingletonTags.boolean).get + 1))
    shapelessHMapBig + (SingletonTags.short -> (shapelessHMapBig.get(SingletonTags.short).get + 1))
    shapelessHMapBig + (SingletonTags.int -> (shapelessHMapBig.get(SingletonTags.int).get + 1))
    shapelessHMapBig + (SingletonTags.long -> (shapelessHMapBig.get(SingletonTags.long).get + 1))
    shapelessHMapBig + (SingletonTags.float -> (shapelessHMapBig.get(SingletonTags.float).get + 1))
    shapelessHMapBig + (SingletonTags.double -> (shapelessHMapBig.get(SingletonTags.double).get + 1))
    shapelessHMapBig + (SingletonTags.bigDecimal -> (shapelessHMapBig.get(SingletonTags.bigDecimal).get + 1))
    shapelessHMapBig + (SingletonTags.bigInt -> (shapelessHMapBig.get(SingletonTags.bigInt).get + 1))
    shapelessHMapBig + (SingletonTags.char -> (shapelessHMapBig.get(SingletonTags.char).get + 1))
    shapelessHMapBig + (SingletonTags.symbol -> (shapelessHMapBig.get(SingletonTags.symbol).get + 1))
    shapelessHMapBig + (SingletonTags.string -> (shapelessHMapBig.get(SingletonTags.string).get + 1))
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def updateShapelessRecord(): Unit = {
    shapelessRecordBig + (Tags.unit ->> (shapelessRecordBig(Tags.unit) + 1))
    shapelessRecordBig + (Tags.boolean ->> (shapelessRecordBig(Tags.boolean) + 1))
    shapelessRecordBig + (Tags.short ->> (shapelessRecordBig(Tags.short) + 1))
    shapelessRecordBig + (Tags.int ->> (shapelessRecordBig(Tags.int) + 1))
    shapelessRecordBig + (Tags.long ->> (shapelessRecordBig(Tags.long) + 1))
    shapelessRecordBig + (Tags.float ->> (shapelessRecordBig(Tags.float) + 1))
    shapelessRecordBig + (Tags.double ->> (shapelessRecordBig(Tags.double) + 1))
    shapelessRecordBig + (Tags.bigDecimal ->> (shapelessRecordBig(Tags.bigDecimal) + 1))
    shapelessRecordBig + (Tags.bigInt ->> (shapelessRecordBig(Tags.bigInt) + 1))
    shapelessRecordBig + (Tags.char ->> (shapelessRecordBig(Tags.char) + 1))
    shapelessRecordBig + (Tags.symbol ->> (shapelessRecordBig(Tags.symbol) + 1))
    shapelessRecordBig + (Tags.string ->> (shapelessRecordBig(Tags.string) + 1))
  }
}
