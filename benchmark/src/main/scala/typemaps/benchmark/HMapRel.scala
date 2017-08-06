package typemaps.benchmark

class HMapRel[A, B]

object HMapRel {
  private[this] def mkRel[A](a: A): HMapRel[A, Int] = new HMapRel[A, Int]

  implicit val unit = mkRel(HMapTags.unit)
  implicit val boolean = mkRel(HMapTags.boolean)
  implicit val short = mkRel(HMapTags.short)
  implicit val int = mkRel(HMapTags.int)
  implicit val long = mkRel(HMapTags.long)
  implicit val float = mkRel(HMapTags.float)
  implicit val double = mkRel(HMapTags.double)
  implicit val bigDecimal = mkRel(HMapTags.bigDecimal)
  implicit val bigInt = mkRel(HMapTags.bigInt)
  implicit val char = mkRel(HMapTags.char)
  implicit val symbol = mkRel(HMapTags.symbol)
  implicit val string = mkRel(HMapTags.string)
  implicit val optionUnit = mkRel(HMapTags.optionUnit)
}
