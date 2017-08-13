package typemaps
package interop.shapeless

import shapeless._
import TypeMap._

sealed trait ToHList[M <: TypeMap] {
  type Out <: HList
  def apply(m: M): Out
}

object ToHList {
  type Aux[M <: TypeMap, O <: HList] = ToHList[M] { type Out = O }

  sealed trait Helper[M <: TypeMap, In <: HList] {
    type Out <: HList
    private[ToHList] def apply(m: M, in: In): Out
  }

  object Helper {
    type Aux[M <: TypeMap, I <: HList, O <: HList] = Helper[M, I] { type Out = O }
  }

  implicit def tip[In <: HList]: Helper.Aux[Tip, In, In] = new Helper[Tip, In] {
    override type Out = In
    override def apply(m: Tip, in: In): In = in
  }

  implicit def bin[A, L <: TypeMap, R <: TypeMap, In <: HList, OR <: HList, OL <: HList](implicit r: Helper.Aux[R, In, OR],
                                                                                         l: Helper.Aux[L, OR, OL]
                                                                                        ): Helper.Aux[Bin[A, A, L, R], In, A :: OL] =
    new Helper[Bin[A, A, L, R], In] {
      override type Out = A :: OL
      override private[ToHList] def apply(m: Bin[A, A, L, R], in: In): A :: OL = m.a :: l(m.l, r(m.r, in))
    }

  implicit def toHList[M <: TypeMap, O <: HList](implicit helper: Helper.Aux[M, HNil, O]
                                                ): ToHList.Aux[M, O] =
    new ToHList[M] {
      override type Out = O
      override def apply(m: M): O = helper(m, HNil)
    }
}
