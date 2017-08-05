package typemaps

import shapeless.HList._
import shapeless._
import typemaps.TypeMap._

sealed trait BuildTypeMaps[K <: HList, V <: HList] {
  type Out <: HList
  def apply(values: V): Out
}

object BuildTypeMaps {
  type Aux[K <: HList, V <: HList, O <: HList] = BuildTypeMaps[K, V] { type Out = O }

  class BuildFn[K <: HList] {
    def apply[V <: HList](values: V)(implicit build: BuildTypeMaps[K, V]): build.Out = build(values)
  }

  def apply[K <: HList]: BuildFn[K] = new BuildFn

  implicit val hnil: BuildTypeMaps.Aux[HNil, HNil, Tip :: HNil] = new BuildTypeMaps[HNil, HNil] {
    override type Out = Tip :: HNil
    override def apply(values: HNil): Tip :: HNil = {
      Tip :: HNil
    }
  }

  implicit def hcons[KH, KT <: HList, VH, VT <: HList, OH <: TypeMap, OT <: HList, O <: TypeMap](implicit tail: BuildTypeMaps.Aux[KT, VT, OH :: OT],
                                                                                                 insert: Insert.Aux[OH, KH, VH, O]): BuildTypeMaps.Aux[KH :: KT, VH :: VT, O :: OH :: OT] =
    new BuildTypeMaps[KH :: KT, VH :: VT] {
      override type Out = O :: OH :: OT
      override def apply(values: VH :: VT): O :: OH :: OT = {
        val oh :: ot = tail(values.tail)
        insert(oh, values.head) :: oh :: ot
      }
    }
}
