package typemaps
package interop.shapeless

import TypeMap._
import shapeless._

sealed trait FromHList[L <: HList] {
  type Out <: TypeMap
  def apply(l: L): Out
}

object FromHList {
  type Aux[L <: HList, O <: TypeMap] = FromHList[L] { type Out = O }

  implicit def hnil: FromHList.Aux[HNil, Tip] = new FromHList[HNil] {
    override type Out = Tip
    override def apply(l: HNil): Tip = Tip
  }

  implicit def hcons[H, T <: HList, OT <: TypeMap, O <: TypeMap](implicit t: FromHList.Aux[T, OT],
                                                                 insert: Insert.Aux[OT, H, H, O]
                                                                ): FromHList.Aux[H :: T, O] =
    new FromHList[H :: T] {
      override type Out = O
      override def apply(l: H :: T): O = insert(t(l.tail), l.head)
    }
}
