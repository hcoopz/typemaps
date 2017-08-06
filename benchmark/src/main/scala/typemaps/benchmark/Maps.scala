package typemaps.benchmark

import org.openjdk.jmh.annotations.Benchmark
import typemaps._
import Syntax._
import shapeless._

object Maps {
  val typeMap = TypeMap.empty
    .insert[Unit](1)
    .insert[Boolean](2)
    .insert[Short](3)
    .insert[Int](4)
    .insert[Long](5)
    .insert[Float](6)
    .insert[Double](7)

  val typeMapBig = TypeMap.empty
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

  val scalaMap = Map(
    MapTags.unit -> 1,
    MapTags.boolean -> 2,
    MapTags.short -> 3,
    MapTags.int -> 4,
    MapTags.long -> 5,
    MapTags.float -> 6,
    MapTags.double -> 7
  )

  val scalaMapBig = Map(
    MapTags.unit -> 1,
    MapTags.boolean -> 2,
    MapTags.short -> 3,
    MapTags.int -> 4,
    MapTags.long -> 5,
    MapTags.float -> 6,
    MapTags.double -> 7,
    MapTags.bigDecimal -> 8,
    MapTags.bigInt -> 9,
    MapTags.char -> 10,
    MapTags.symbol -> 11,
    MapTags.string -> 12
  )

  val shapelessHMap = new HMapBuilder[HMapRel].apply(
    HMapTags.unit -> 1,
    HMapTags.boolean -> 2,
    HMapTags.short -> 3,
    HMapTags.int -> 4,
    HMapTags.long -> 5,
    HMapTags.float -> 6,
    HMapTags.double -> 7
  )

  val shapelessHMapBig = new HMapBuilder[HMapRel].apply(
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
