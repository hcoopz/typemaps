package typemaps

import shapeless._

sealed trait RemoveKeys[M <: TypeMap, K <: HList] {
  type Out <: HList
  def apply(m: M): Out
}

object RemoveKeys {
  type Aux[M <: TypeMap, K <: HList, O <: HList] = RemoveKeys[M, K] { type Out = O }

  def apply[K <: HList]: Fn[K] = new Fn

  class Fn[K <: HList] {
    def apply[M <: TypeMap](m: M)(implicit ev: RemoveKeys[M, K]): ev.Out = ev(m)
  }

  implicit def hnil[M <: TypeMap]: RemoveKeys.Aux[M, HNil, HNil] = new RemoveKeys[M, HNil] {
    override type Out = HNil
    override def apply(m: M): HNil = HNil
  }

  implicit def hcons[M <: TypeMap, H, T <: HList, OH <: TypeMap, OT <: HList](implicit remove: Remove.Aux[M, H, OH],
                                                                              tail: RemoveKeys.Aux[M, T, OT]
                                                                             ): RemoveKeys.Aux[M, H :: T, OH :: OT] =
    new RemoveKeys[M, H :: T] {
      override type Out = OH :: OT
      override def apply(m: M): OH :: OT = {
        remove(m) :: tail(m)
      }
    }
}