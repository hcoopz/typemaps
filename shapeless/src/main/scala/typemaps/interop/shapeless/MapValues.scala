package typemaps
package interop.shapeless

import shapeless._
import TypeMap._

sealed trait MapValues[M <: TypeMap, HF <: Poly] {
  type Out <: TypeMap
  def apply(m: M): Out
}

object MapValues {
  type Aux[M <: TypeMap, HF <: Poly, O <: TypeMap] = MapValues[M, HF] { type Out = O }

  implicit def tip[HF <: Poly]: MapValues.Aux[Tip, HF, Tip] = new MapValues[Tip, HF] {
    override type Out = Tip
    override def apply(m: Tip): Tip = Tip
  }

  implicit def bin[K, A, L <: TypeMap, R <: TypeMap, HF <: Poly, B, OL <: TypeMap, OR <: TypeMap](implicit a: PolyDefns.Case1.Aux[HF, A, B],
                                                                                                  l: MapValues.Aux[L, HF, OL],
                                                                                                  r: MapValues.Aux[R, HF, OR]
                                                                                                 ): MapValues.Aux[Bin[K, A, L, R], HF, Bin[K, B, OL, OR]] =
    new MapValues[Bin[K, A, L, R], HF] {
      override type Out = Bin[K, B, OL, OR]
      override def apply(m: Bin[K, A, L, R]): Bin[K, B, OL, OR] = Bin(a(m.a), l(m.l), r(m.r))
    }
}
