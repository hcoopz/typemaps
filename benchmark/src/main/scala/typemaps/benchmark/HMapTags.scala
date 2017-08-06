package typemaps.benchmark

import shapeless.syntax.singleton._

object HMapTags {
  object tags {
    val unit = 'unit.narrow
    val boolean = 'boolean.narrow
    val short = 'short.narrow
    val int = 'int.narrow
    val long = 'long.narrow
    val float = 'float.narrow
    val double = 'double.narrow
  }

  class Rel[A, B]

  private[this] def mkRel[A](a: A): Rel[A, Int] = new Rel[A, Int]
  implicit val unit = mkRel(tags.unit)
  implicit val boolean = mkRel(tags.boolean)
  implicit val short = mkRel(tags.short)
  implicit val int = mkRel(tags.int)
  implicit val long = mkRel(tags.long)
  implicit val float = mkRel(tags.float)
  implicit val double = mkRel(tags.double)
}