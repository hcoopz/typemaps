package typemaps

import shapeless._

sealed trait UpdateValues[M <: TypeMap, K <: HList] {
  type Out <: HList
  def apply(m: M): Out
}

object UpdateValues {
  type Aux[M <: TypeMap, K <: HList, O <: HList] = UpdateValues[M, K] { type Out = O }

  def apply[K <: HList]: Fn[K] = new Fn

  class Fn[K <: HList] {
    def apply[M <: TypeMap](m: M)(implicit ev: UpdateValues[M, K]): ev.Out = ev(m)
  }

  implicit def hnil[M <: TypeMap]: UpdateValues.Aux[M, HNil, HNil] = new UpdateValues[M, HNil] {
    override type Out = HNil
    override def apply(m: M): HNil = HNil
  }

  implicit def hcons[M <: TypeMap, H, T <: HList, A, OH <: TypeMap, OT <: HList](implicit lookup: Lookup.Aux[M, H, A],
                                                                                 head: Update.Aux[M, H, A, H, Unit, OH],
                                                                                 tail: UpdateValues.Aux[M, T, OT]
                                                                                ): UpdateValues.Aux[M, H :: T, OH :: OT] =
    new UpdateValues[M, H :: T] {
      override type Out = OH :: OT
      override def apply(m: M): OH :: OT = {
        head(m, _ => ()) :: tail(m)
      }
    }
}