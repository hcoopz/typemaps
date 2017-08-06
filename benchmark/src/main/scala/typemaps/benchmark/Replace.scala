package typemaps.benchmark

import org.openjdk.jmh.annotations.Benchmark
import typemaps._
import Syntax._
import shapeless.record._
import shapeless.syntax.singleton._

class Replace {
  import Maps._

  @Benchmark
  def replaceTypeMap(): Unit = {
    typeMap.update[Unit].const(10)
    typeMap.update[Boolean].const(10)
    typeMap.update[Short].const(10)
    typeMap.update[Int].const(10)
    typeMap.update[Long].const(10)
    typeMap.update[Float].const(10)
    typeMap.update[Double].const(10)
  }


  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def replaceScalaMap(): Unit = {
    scalaMap.updated(Tags.unit, 10)
    scalaMap.updated(Tags.boolean, 10)
    scalaMap.updated(Tags.short, 10)
    scalaMap.updated(Tags.int, 10)
    scalaMap.updated(Tags.long, 10)
    scalaMap.updated(Tags.float, 10)
    scalaMap.updated(Tags.double, 10)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def replaceShapelessHMap(): Unit = {
    shapelessHMap + (SingletonTags.unit -> 10)
    shapelessHMap + (SingletonTags.boolean -> 10)
    shapelessHMap + (SingletonTags.short -> 10)
    shapelessHMap + (SingletonTags.int -> 10)
    shapelessHMap + (SingletonTags.long -> 10)
    shapelessHMap + (SingletonTags.float -> 10)
    shapelessHMap + (SingletonTags.double -> 10)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def replaceShapelessRecord(): Unit = {
    shapelessRecord + (Tags.unit ->> 10)
    shapelessRecord + (Tags.boolean ->> 10)
    shapelessRecord + (Tags.short ->> 10)
    shapelessRecord + (Tags.int ->> 10)
    shapelessRecord + (Tags.long ->> 10)
    shapelessRecord + (Tags.float ->> 10)
    shapelessRecord + (Tags.double ->> 10)
  }
}
