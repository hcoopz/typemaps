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
}
