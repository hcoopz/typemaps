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

  implicit def binLL[KA, A, LK, LA, LL <: TypeMap, LR <: TypeMap, R <: TypeMap, K, O](implicit ll: Lookup.Aux[LL, K, O]
                                                                                     ): Lookup.Aux[Bin[KA, A, Bin[LK, LA, LL, LR], R], K, O] =
    new Lookup[Bin[KA, A, Bin[LK, LA, LL, LR], R], K] {
      override type Out = O
      override def apply(m: Bin[KA, A, Bin[LK, LA, LL, LR], R]): O = ll(m.l.l)
    }

  implicit def binLR[KA, A, LK, LA, LL <: TypeMap, LR <: TypeMap, R <: TypeMap, K, O](implicit lr: Lookup.Aux[LR, K, O]
                                                                                     ): Lookup.Aux[Bin[KA, A, Bin[LK, LA, LL, LR], R], K, O] =
    new Lookup[Bin[KA, A, Bin[LK, LA, LL, LR], R], K] {
      override type Out = O
      override def apply(m: Bin[KA, A, Bin[LK, LA, LL, LR], R]): O = lr(m.l.r)
    }

  implicit def binRL[KA, A, L <: TypeMap, RK, RA, RL <: TypeMap, RR <: TypeMap, K, O](implicit rl: Lookup.Aux[RL, K, O]
                                                                                     ): Lookup.Aux[Bin[KA, A, L, Bin[RK, RA, RL, RR]], K, O] =
    new Lookup[Bin[KA, A, L, Bin[RK, RA, RL, RR]], K] {
      override type Out = O
      override def apply(m: Bin[KA, A, L, Bin[RK, RA, RL, RR]]): O = rl(m.r.l)
    }

  implicit def binRR[KA, A, L <: TypeMap, RK, RA, RL <: TypeMap, RR <: TypeMap, K, O](implicit rr: Lookup.Aux[RR, K, O]
                                                                                     ): Lookup.Aux[Bin[KA, A, L, Bin[RK, RA, RL, RR]], K, O] =
    new Lookup[Bin[KA, A, L, Bin[RK, RA, RL, RR]], K] {
      override type Out = O
      override def apply(m: Bin[KA, A, L, Bin[RK, RA, RL, RR]]): O = rr(m.r.r)
    }
}