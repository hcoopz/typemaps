package typemaps.benchmark

import org.openjdk.jmh.annotations.Benchmark
import typemaps._
import shapeless._
import shapeless.syntax.singleton._
import shapeless.record._

class Insert {
  @Benchmark
  def insertTypeMap(): Unit = {
    TypeMap.empty
      .insert[Unit](1)
      .insert[Boolean](2)
      .insert[Short](3)
      .insert[Int](4)
      .insert[Long](5)
      .insert[Float](6)
      .insert[Double](7)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def insertScalaMap(): Unit = {
    Map.empty
      .updated(Tags.unit, 1)
      .updated(Tags.boolean, 2)
      .updated(Tags.short, 3)
      .updated(Tags.int, 4)
      .updated(Tags.long, 5)
      .updated(Tags.float, 6)
      .updated(Tags.double, 7)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def insertShapelessHMap(): Unit = {
    HMap.empty[HMapRel]
      .+(SingletonTags.unit -> 1)
      .+(SingletonTags.boolean -> 2)
      .+(SingletonTags.short -> 3)
      .+(SingletonTags.int -> 4)
      .+(SingletonTags.long -> 5)
      .+(SingletonTags.float -> 6)
      .+(SingletonTags.double -> 7)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def insertShapelessHMapBuilder(): Unit = {
    new HMapBuilder[HMapRel].apply(
      SingletonTags.unit -> 1,
      SingletonTags.boolean -> 2,
      SingletonTags.short -> 3,
      SingletonTags.int -> 4,
      SingletonTags.long -> 5,
      SingletonTags.float -> 6,
      SingletonTags.double -> 7
    )
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def insertShapelessRecord(): Unit = {
    HNil
      .+(Tags.unit ->> 1)
      .+(Tags.boolean ->> 2)
      .+(Tags.short ->> 3)
      .+(Tags.int ->> 4)
      .+(Tags.long ->> 5)
      .+(Tags.float ->> 6)
      .+(Tags.double ->> 7)
  }
}

