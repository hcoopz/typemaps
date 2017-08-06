package typemaps.benchmark

import org.openjdk.jmh.annotations.Benchmark
import typemaps._
import Syntax._
import shapeless._

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
      .updated(MapTags.unit, 1)
      .updated(MapTags.boolean, 2)
      .updated(MapTags.short, 3)
      .updated(MapTags.int, 4)
      .updated(MapTags.long, 5)
      .updated(MapTags.float, 6)
      .updated(MapTags.double, 7)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def insertShapelessHMap(): Unit = {
    HMap.empty[HMapRel]
      .+(HMapTags.unit -> 1)
      .+(HMapTags.boolean -> 2)
      .+(HMapTags.short -> 3)
      .+(HMapTags.int -> 4)
      .+(HMapTags.long -> 5)
      .+(HMapTags.float -> 6)
      .+(HMapTags.double -> 7)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def insertShapelessHMapBuilder(): Unit = {
    new HMapBuilder[HMapRel].apply(
      HMapTags.unit -> 1,
      HMapTags.boolean -> 2,
      HMapTags.short -> 3,
      HMapTags.int -> 4,
      HMapTags.long -> 5,
      HMapTags.float -> 6,
      HMapTags.double -> 7
    )
  }
}
