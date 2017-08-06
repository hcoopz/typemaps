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
    Map.empty[Symbol, Int]
      .updated('unit, 1)
      .updated('boolean, 2)
      .updated('short, 3)
      .updated('int, 4)
      .updated('long, 5)
      .updated('float, 6)
      .updated('double, 7)
  }

  import HMapTags._

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def insertShapelessHMap(): Unit = {
    HMap.empty[Rel]
      .+(tags.unit -> 1)
      .+(tags.boolean -> 2)
      .+(tags.short -> 3)
      .+(tags.int -> 4)
      .+(tags.long -> 5)
      .+(tags.float -> 6)
      .+(tags.double -> 7)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def insertShapelessHMapBuilder(): Unit = {
    new HMapBuilder[Rel].apply(
      tags.unit -> 1,
      tags.boolean -> 2,
      tags.short -> 3,
      tags.int -> 4,
      tags.long -> 5,
      tags.float -> 6,
      tags.double -> 7
    )
  }
}
