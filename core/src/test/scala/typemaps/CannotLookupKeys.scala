package typemaps

import shapeless._

sealed trait CannotLookupKeys[M <: HList, K <: HList]

object CannotLookupKeys {
  def apply[K <: HList]: Fn[K] = new Fn

  class Fn[K <: HList] {
    def apply[M <: HList](m: M)(implicit ev: CannotLookupKeys[M, K]): Unit = {}
  }

  implicit val hnil: CannotLookupKeys[HNil, HNil] = new CannotLookupKeys[HNil, HNil] {}

  implicit def hcons[H <: TypeMap, T <: HList, KH, KT <: HList](implicit head: CannotLookupKey[H, KH],
                                                                tail: CannotLookupKeys[T, KT]
                                                               ): CannotLookupKeys[H :: T, KH :: KT] =
    new CannotLookupKeys[H :: T, KH :: KT] {}
}
