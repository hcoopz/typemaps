package typemaps
package interop.shapeless

import TypeMap._
import shapeless._

sealed trait FromHListWithKeyList[L <: HList, K <: HList] {
  type Out <: TypeMap
  def apply(l: L): Out
}

object FromHListWithKeyList {
  type Aux[L <: HList, K <: HList, O <: TypeMap] = FromHListWithKeyList[L, K] { type Out = O }

  implicit def hnil: FromHListWithKeyList.Aux[HNil, HNil, Tip] = new FromHListWithKeyList[HNil, HNil] {
    override type Out = Tip
    override def apply(l: HNil): Tip = Tip
  }

  implicit def hcons[H, T <: HList, KH, KT <: HList, OT <: TypeMap, O <: TypeMap](implicit t: FromHListWithKeyList.Aux[T, KT, OT],
                                                                                  insert: Insert.Aux[OT, KH, H, O]
                                                                                 ): FromHListWithKeyList.Aux[H :: T, KH :: KT, O] =
    new FromHListWithKeyList[H :: T, KH :: KT] {
      override type Out = O
      override def apply(l: H :: T): O = insert(t(l.tail), l.head)
    }
}
