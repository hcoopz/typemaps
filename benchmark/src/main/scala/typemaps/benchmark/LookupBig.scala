package typemaps.benchmark

import org.openjdk.jmh.annotations.Benchmark
import typemaps._
import shapeless.record._

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
    scalaMapBig(Tags.unit)
    scalaMapBig(Tags.boolean)
    scalaMapBig(Tags.short)
    scalaMapBig(Tags.int)
    scalaMapBig(Tags.long)
    scalaMapBig(Tags.float)
    scalaMapBig(Tags.double)
    scalaMapBig(Tags.bigDecimal)
    scalaMapBig(Tags.bigInt)
    scalaMapBig(Tags.char)
    scalaMapBig(Tags.symbol)
    scalaMapBig(Tags.string)
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def lookupShapelessHMap(): Unit = {
    shapelessHMapBig.get(SingletonTags.unit).get
    shapelessHMapBig.get(SingletonTags.boolean).get
    shapelessHMapBig.get(SingletonTags.short).get
    shapelessHMapBig.get(SingletonTags.int).get
    shapelessHMapBig.get(SingletonTags.long).get
    shapelessHMapBig.get(SingletonTags.float).get
    shapelessHMapBig.get(SingletonTags.double).get
    shapelessHMapBig.get(SingletonTags.bigDecimal).get
    shapelessHMapBig.get(SingletonTags.bigInt).get
    shapelessHMapBig.get(SingletonTags.char).get
    shapelessHMapBig.get(SingletonTags.symbol).get
    shapelessHMapBig.get(SingletonTags.string).get
  }

  // Not exactly the same because we have to provide values for keys
  @Benchmark
  def lookupShapelessRecord(): Unit = {
    shapelessRecordBig(Tags.unit)
    shapelessRecordBig(Tags.boolean)
    shapelessRecordBig(Tags.short)
    shapelessRecordBig(Tags.int)
    shapelessRecordBig(Tags.long)
    shapelessRecordBig(Tags.float)
    shapelessRecordBig(Tags.double)
    shapelessRecordBig(Tags.bigDecimal)
    shapelessRecordBig(Tags.bigInt)
    shapelessRecordBig(Tags.char)
    shapelessRecordBig(Tags.symbol)
    shapelessRecordBig(Tags.string)
  }
}
