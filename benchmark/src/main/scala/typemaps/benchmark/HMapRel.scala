package typemaps.benchmark

class HMapRel[A, B]

object HMapRel {
  private[this] def mkRel[A](a: A): HMapRel[A, Int] = new HMapRel[A, Int]

  implicit val unit = mkRel(SingletonTags.unit)
  implicit val boolean = mkRel(SingletonTags.boolean)
  implicit val short = mkRel(SingletonTags.short)
  implicit val int = mkRel(SingletonTags.int)
  implicit val long = mkRel(SingletonTags.long)
  implicit val float = mkRel(SingletonTags.float)
  implicit val double = mkRel(SingletonTags.double)
  implicit val bigDecimal = mkRel(SingletonTags.bigDecimal)
  implicit val bigInt = mkRel(SingletonTags.bigInt)
  implicit val char = mkRel(SingletonTags.char)
  implicit val symbol = mkRel(SingletonTags.symbol)
  implicit val string = mkRel(SingletonTags.string)
  implicit val optionUnit = mkRel(SingletonTags.optionUnit)
}
