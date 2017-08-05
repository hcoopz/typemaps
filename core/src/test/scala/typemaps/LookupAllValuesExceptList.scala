package typemaps

import shapeless._

sealed trait LookupAllValuesExceptList[M <: HList, K <: HList, E <: HList, V <: HList] {
  def apply(m: M, values: V): Unit
}

object LookupAllValuesExceptList {
  def apply[K <: HList, E <: HList]: Fn[K, E] = new Fn

  class Fn[K <: HList, E <: HList] {
    def apply[M <: HList, V <: HList](m: M, values: V)(implicit ev: LookupAllValuesExceptList[M, K, E, V]): Unit = ev(m, values)
  }

  implicit def hnil[K <: HList, V <: HList]: LookupAllValuesExceptList[HNil, K, HNil, V] = new LookupAllValuesExceptList[HNil, K, HNil, V] {
    override def apply(m: HNil, values: V): Unit = {}
  }

  implicit def hcons[H <: TypeMap, T <: HList, K <: HList, EH, ET <: HList, V <: HList](implicit head: LookupAllValuesExcept[H, K, EH, V],
                                                                                        tail: LookupAllValuesExceptList[T, K, ET, V]
                                                                                       ): LookupAllValuesExceptList[H :: T, K, EH :: ET, V] =
    new LookupAllValuesExceptList[H :: T, K, EH :: ET, V] {
      override def apply(m: H :: T, values: V): Unit = {
        head(m.head, values)
        tail(m.tail, values)
      }
    }
}