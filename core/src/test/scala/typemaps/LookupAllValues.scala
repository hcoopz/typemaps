package typemaps

import org.scalatest.Matchers
import shapeless._

sealed trait LookupAllValues[M <: TypeMap, K <: HList, V <: HList] {
  def apply(m: M, values: V): Unit
}

object LookupAllValues extends Matchers {
  def apply[K <: HList]: Fn[K] = new Fn

  class Fn[K <: HList] {
    def apply[M <: TypeMap, V <: HList](m: M, values: V)(implicit ev: LookupAllValues[M, K, V]): Unit = ev(m, values)
  }

  implicit def hnil[M <: TypeMap]: LookupAllValues[M, HNil, HNil] = new LookupAllValues[M, HNil, HNil] {
    override def apply(m: M, values: HNil): Unit = {}
  }

  implicit def hcons[M <: TypeMap, KH, KT <: HList, VH, VT <: HList](implicit lookup: Lookup.Aux[M, KH, VH],
                                                                     tail: LookupAllValues[M, KT, VT]
                                                                    ): LookupAllValues[M, KH :: KT, VH :: VT] =
    new LookupAllValues[M, KH :: KT, VH :: VT] {
      override def apply(m: M, values: VH :: VT): Unit = {
        lookup(m) shouldEqual values.head
        tail(m, values.tail)
      }
    }
}