package typemaps.benchmark

import org.openjdk.jmh.annotations.Benchmark
import typemaps._
import Syntax._
import shapeless._

class InsertBig {
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
      .insert[BigDecimal](8)
      .insert[BigInt](9)
      .insert[Char](10)
      .insert[Symbol](11)
      .insert[String](12)
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
      .updated(MapTags.bigDecimal, 8)
      .updated(MapTags.bigInt, 9)
      .updated(MapTags.char, 10)
      .updated(MapTags.symbol, 11)
      .updated(MapTags.string, 12)
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
      .+(HMapTags.bigDecimal -> 8)
      .+(HMapTags.bigInt -> 9)
      .+(HMapTags.char -> 10)
      .+(HMapTags.symbol -> 11)
      .+(HMapTags.string -> 12)
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
      HMapTags.double -> 7,
      HMapTags.bigDecimal -> 8,
      HMapTags.bigInt -> 9,
      HMapTags.char -> 10,
      HMapTags.symbol -> 11,
      HMapTags.string -> 12
    )
  }
}
