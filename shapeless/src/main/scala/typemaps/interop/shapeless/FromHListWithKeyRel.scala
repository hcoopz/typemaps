package typemaps
package interop.shapeless

import TypeMap._
import shapeless._

import scala.language.higherKinds

sealed trait FromHListWithKeyRel[L <: HList, R[_, _]] {
  type Out <: TypeMap
  def apply(l: L): Out
}

object FromHListWithKeyRel {
  type Aux[L <: HList, R[_, _], O <: TypeMap] = FromHListWithKeyRel[L, R] { type Out = O }

  implicit def hnil[R[_, _]]: FromHListWithKeyRel.Aux[HNil, R, Tip] = new FromHListWithKeyRel[HNil, R] {
    override type Out = Tip
    override def apply(l: HNil): Tip = Tip
  }

  implicit def hcons[H, T <: HList, R[_, _], K, OT <: TypeMap, O <: TypeMap](implicit r: R[H, K],
                                                                             t: FromHListWithKeyRel.Aux[T, R, OT],
                                                                             insert: Insert.Aux[OT, K, H, O]
                                                                            ): FromHListWithKeyRel.Aux[H :: T, R, O] =
    new FromHListWithKeyRel[H :: T, R] {
      override type Out = O
      override def apply(l: H :: T): O = insert(t(l.tail), l.head)
    }
}
