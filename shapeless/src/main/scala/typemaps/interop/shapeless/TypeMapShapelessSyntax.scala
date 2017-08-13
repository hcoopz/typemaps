package typemaps
package interop.shapeless

import shapeless._

final class TypeMapShapelessSyntax[M <: TypeMap](val m: M) extends AnyVal {
  // Defaults to HNil if no type is specified
  @inline def toSpecifiedHList[L <: HList](implicit toHList: ToSpecifiedHList[M, L]): L = toHList(m)

  @inline def toHList(implicit toHList: ToHList[M]): toHList.Out = toHList(m)

  @inline def mapValues[HF <: Poly](implicit mapValues: MapValues[M, HF]): mapValues.Out = mapValues(m)
}