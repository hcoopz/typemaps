package typemaps

import org.scalatest.Matchers
import shapeless._

sealed trait CannotInsertKeys[M <: TypeMap, K <: HList] {
  def apply(m: M): Unit
}

object CannotInsertKeys extends Matchers {
  def apply[K <: HList]: Fn[K] = new Fn

  class Fn[K <: HList] {
    def apply[M <: TypeMap](m: M)(implicit ev: CannotInsertKeys[M, K]): Unit = {}
  }

  implicit def hnil[M <: TypeMap]: CannotInsertKeys[M, HNil] = new CannotInsertKeys[M, HNil] {
    override def apply(m: M): Unit = {}
  }

  implicit def hcons[M <: TypeMap, KH, KT <: HList](implicit head: CannotInsertKey[M, KH],
                                                    tail: CannotInsertKeys[M, KT]
                                                   ): CannotInsertKeys[M, KH :: KT] =
    new CannotInsertKeys[M, KH :: KT] {
      override def apply(m: M): Unit = {}
    }
}
