package typemaps

import TypeMap._

import scala.annotation.implicitNotFound

@implicitNotFound("${K} is not present in the map")
sealed trait Lookup[M <: TypeMap, K] {
  type Out
  def apply(m: M): Out
}

sealed trait LookupLow {
  implicit def binL[KA, A, L <: TypeMap, R <: TypeMap, K, O](implicit l: Lookup.Aux[L, K, O]
                                                            ): Lookup.Aux[Bin[KA, A, L, R], K, O] =
    new Lookup[Bin[KA, A, L, R], K] {
      override type Out = O
      override def apply(m: Bin[KA, A, L, R]): O = l(m.l)
    }

  implicit def binR[KA, A, L <: TypeMap, R <: TypeMap, K, O](implicit r: Lookup.Aux[R, K, O]
                                                            ): Lookup.Aux[Bin[KA, A, L, R], K, O] =
    new Lookup[Bin[KA, A, L, R], K] {
      override type Out = O
      override def apply(m: Bin[KA, A, L, R]): O = r(m.r)
    }
}

object Lookup extends LookupLow {
  type Aux[M <: TypeMap, K, O] = Lookup[M, K] { type Out = O }

  implicit def head[K, A, L <: TypeMap, R <: TypeMap]: Lookup.Aux[Bin[K, A, L, R], K, A] =
    new Lookup[Bin[K, A, L, R], K] {
      override type Out = A
      override def apply(m: Bin[K, A, L, R]): A = m.a
    }

  implicit def binLHead[KA, A, LA, LL <: TypeMap, LR <: TypeMap, R <: TypeMap, K]: Lookup.Aux[Bin[KA, A, Bin[K, LA, LL, LR], R], K, LA] =
    new Lookup[Bin[KA, A, Bin[K, LA, LL, LR], R], K] {
      override type Out = LA
      override def apply(m: Bin[KA, A, Bin[K, LA, LL, LR], R]): LA = m.l.a
    }

  implicit def binRHead[KA, A, L <: TypeMap, RA, RL <: TypeMap, RR <: TypeMap, K]: Lookup.Aux[Bin[KA, A, L, Bin[K, RA, RL, RR]], K, RA] =
    new Lookup[Bin[KA, A, L, Bin[K, RA, RL, RR]], K] {
      override type Out = RA
      override def apply(m: Bin[KA, A, L, Bin[K, RA, RL, RR]]): RA = m.r.a
    }
}