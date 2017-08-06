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

  val scalaMap = Map(
    'unit -> 1,
    'boolean -> 2,
    'short -> 3,
    'int -> 4,
    'long -> 5,
    'float -> 6,
    'double -> 7
  )

  import HMapTags._

  val shapelessHMap = new HMapBuilder[Rel].apply(
    tags.unit -> 1,
    tags.boolean -> 2,
    tags.short -> 3,
    tags.int -> 4,
    tags.long -> 5,
    tags.float -> 6,
    tags.double -> 7
  )
}
