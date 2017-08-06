package typemaps.benchmark

import org.openjdk.jmh.annotations.Benchmark
import typemaps._
import Syntax._

class LookupBig {
  import Maps._

  @Benchmark
  def lookupTypeMap(): Unit = {
    typeMapBig.lookup[Unit]
    typeMapBig.lookup[Boolean]
    typeMapBig.lookup[Short]
    typeMapBig.lookup[Int]
    typeMapBig.lookup[Long]
    typeMapBig.lookup[Float]
    typeMapBig.lookup[Double]
    typeMapBig.lookup[BigDecimal]
    typeMapBig.lookup[BigInt]
    typeMapBig.lookup[Char]
    typeMapBig.lookup[Symbol]
    typeMapBig.lookup[String]
  }


  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def lookupScalaMap(): Unit = {
    scalaMapBig(MapTags.unit)
    scalaMapBig(MapTags.boolean)
    scalaMapBig(MapTags.short)
    scalaMapBig(MapTags.int)
    scalaMapBig(MapTags.long)
    scalaMapBig(MapTags.float)
    scalaMapBig(MapTags.double)
    scalaMapBig(MapTags.bigDecimal)
    scalaMapBig(MapTags.bigInt)
    scalaMapBig(MapTags.char)
    scalaMapBig(MapTags.symbol)
    scalaMapBig(MapTags.string)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def lookupShapelessHMap(): Unit = {
    shapelessHMapBig.get(HMapTags.unit).get
    shapelessHMapBig.get(HMapTags.boolean).get
    shapelessHMapBig.get(HMapTags.short).get
    shapelessHMapBig.get(HMapTags.int).get
    shapelessHMapBig.get(HMapTags.long).get
    shapelessHMapBig.get(HMapTags.float).get
    shapelessHMapBig.get(HMapTags.double).get
    shapelessHMapBig.get(HMapTags.bigDecimal).get
    shapelessHMapBig.get(HMapTags.bigInt).get
    shapelessHMapBig.get(HMapTags.char).get
    shapelessHMapBig.get(HMapTags.symbol).get
    shapelessHMapBig.get(HMapTags.string).get
  }
}
