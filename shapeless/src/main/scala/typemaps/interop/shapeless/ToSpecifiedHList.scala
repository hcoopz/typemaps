package typemaps
package interop.shapeless

import shapeless._

sealed trait ToSpecifiedHList[M <: TypeMap, L <: HList] {
  def apply(m: M): L
}

sealed trait ToSpecifiedHListLow {
  implicit def hcons[M <: TypeMap, H, T <: HList](implicit t: ToSpecifiedHList[M, T],
                                                  lookup: Lookup.Aux[M, H, H]
                                                 ): ToSpecifiedHList[M, H :: T] =
    new ToSpecifiedHList[M, H :: T] {
      override def apply(m: M): H :: T = lookup(m) :: t(m)
    }
}

object ToSpecifiedHList extends ToSpecifiedHListLow {
  implicit def hnil[M <: TypeMap]: ToSpecifiedHList[M, HNil] = new ToSpecifiedHList[M, HNil] {
    override def apply(m: M): HNil = HNil
  }
}