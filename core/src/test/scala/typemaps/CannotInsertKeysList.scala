package typemaps

import TypeMap._
import shapeless._

sealed trait CannotInsertKeysList[M <: HList, K <: HList] {
  def apply(m: M): Unit
}

object CannotInsertKeysList {
  def apply[K <: HList]: Fn[K] = new Fn

  class Fn[K <: HList] {
    def apply[M <: HList](m: M)(implicit ev: CannotInsertKeysList[M, K]): Unit = ev(m)
  }

  implicit val hnil: CannotInsertKeysList[HNil, HNil] = new CannotInsertKeysList[HNil, HNil] {
    override def apply(m: HNil): Unit = {}
  }

  implicit val tip: CannotInsertKeysList[Tip :: HNil, HNil] = new CannotInsertKeysList[Tip :: HNil, HNil] {
    override def apply(m: Tip :: HNil): Unit = {}
  }

  implicit def hcons[H <: TypeMap, T <: HList, KH, KT <: HList](implicit head: CannotInsertKeys[H, KH :: KT],
                                                                tail: CannotInsertKeysList[T, KT]
                                                               ): CannotInsertKeysList[H :: T, KH :: KT] =
    new CannotInsertKeysList[H :: T, KH :: KT] {
      override def apply(m: H :: T): Unit = {}
    }
}
