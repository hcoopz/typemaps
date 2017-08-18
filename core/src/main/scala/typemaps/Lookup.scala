/*
    Copyright (c) 2017 Harriet Cooper

    This file is part of typemaps.

    Typemaps is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Typemaps is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with typemaps.  If not, see <http://www.gnu.org/licenses/>.
 */
package typemaps

import TypeMap._

import scala.annotation.implicitNotFound

@implicitNotFound("${K} is not present in the map")
sealed trait Lookup[M <: TypeMap, K] {
  type Out
  def apply(m: M): Out
}

sealed trait LookupLow {
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

  implicit def binLLHead[KA, A, LK, LA, LLK, LLA, LLL <: TypeMap, LLR <: TypeMap, LR <: TypeMap, R <: TypeMap]: Lookup.Aux[Bin[KA, A, Bin[LK, LA, Bin[LLK, LLA, LLL, LLR], LR], R], LLK, LLA] =
    new Lookup[Bin[KA, A, Bin[LK, LA, Bin[LLK, LLA, LLL, LLR], LR], R], LLK] {
      override type Out = LLA
      override def apply(m: Bin[KA, A, Bin[LK, LA, Bin[LLK, LLA, LLL, LLR], LR], R]): LLA = m.l.l.a
    }

  implicit def binLRHead[KA, A, LK, LA, LL <: TypeMap, LRK, LRA, LRL <: TypeMap, LRR <: TypeMap, LR <: TypeMap, R <: TypeMap]: Lookup.Aux[Bin[KA, A, Bin[LK, LA, LL, Bin[LRK, LRA, LRL, LRR]], R], LRK, LRA] =
    new Lookup[Bin[KA, A, Bin[LK, LA, LL, Bin[LRK, LRA, LRL, LRR]], R], LRK] {
      override type Out = LRA
      override def apply(m: Bin[KA, A, Bin[LK, LA, LL, Bin[LRK, LRA, LRL, LRR]], R]): LRA = m.l.r.a
    }

  implicit def binRLHead[KA, A, L <: TypeMap, RK, RA, RLK, RLA, RLL <: TypeMap, RLR <: TypeMap, RR <: TypeMap]: Lookup.Aux[Bin[KA, A, L, Bin[RK, RA, Bin[RLK, RLA, RLL, RLR], RR]], RLK, RLA] =
    new Lookup[Bin[KA, A, L, Bin[RK, RA, Bin[RLK, RLA, RLL, RLR], RR]], RLK] {
      override type Out = RLA
      override def apply(m: Bin[KA, A, L, Bin[RK, RA, Bin[RLK, RLA, RLL, RLR], RR]]): RLA = m.r.l.a
    }

  implicit def binRRHead[KA, A, L <: TypeMap, RK, RA, RL <: TypeMap, RRK, RRA, RRL <: TypeMap, RRR <: TypeMap]: Lookup.Aux[Bin[KA, A, L, Bin[RK, RA, RL, Bin[RRK, RRA, RRL, RRR]]], RRK, RRA] =
    new Lookup[Bin[KA, A, L, Bin[RK, RA, RL, Bin[RRK, RRA, RRL, RRR]]], RRK] {
      override type Out = RRA
      override def apply(m: Bin[KA, A, L, Bin[RK, RA, RL, Bin[RRK, RRA, RRL, RRR]]]): RRA = m.r.r.a
    }
}