package typemaps

import TypeMap._

import scala.annotation.implicitNotFound

@implicitNotFound("${K} is not present in the map")
sealed trait Lookup[M <: TypeMap, K] {
  type Out
  def apply(m: M): Out
}

object Lookup {
  type Aux[M <: TypeMap, K, O] = Lookup[M, K] { type Out = O }

  implicit def head[K, A, L <: TypeMap, R <: TypeMap]: Lookup.Aux[Bin[K, A, L, R], K, A] =
    new Lookup[Bin[K, A, L, R], K] {
      override type Out = A
      override def apply(m: Bin[K, A, L, R]): A = m.a
    }

  implicit def headL[KA, A, L <: TypeMap, R <: TypeMap, K, O](implicit l: Lookup.Aux[L, K, O]
                                                             ): Lookup.Aux[Bin[KA, A, L, R], K, O] =
    new Lookup[Bin[KA, A, L, R], K] {
      override type Out = O
      override def apply(m: Bin[KA, A, L, R]): O = l(m.l)
    }

  implicit def headR[KA, A, L <: TypeMap, R <: TypeMap, K, O](implicit r: Lookup.Aux[R, K, O]
                                                             ): Lookup.Aux[Bin[KA, A, L, R], K, O] =
    new Lookup[Bin[KA, A, L, R], K] {
      override type Out = O
      override def apply(m: Bin[KA, A, L, R]): O = r(m.r)
    }
}