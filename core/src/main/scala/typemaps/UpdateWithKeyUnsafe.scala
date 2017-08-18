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

import scala.annotation.{implicitAmbiguous, implicitNotFound}

@implicitNotFound("${KA} is not present in the map")
sealed trait UpdateWithKeyUnsafe[M <: TypeMap, KA, A, KB, B] {
  type Out <: TypeMap
  private[typemaps] def apply(m: M, f: A => B): Out
  private[typemaps] def const(m: M, f: B): Out
}

object UpdateWithKeyUnsafe {
  type Aux[M <: TypeMap, KA, A, KB, B, O <: TypeMap] = UpdateWithKeyUnsafe[M, KA, A, KB, B] { type Out = O }

  sealed trait NotEqual[A, B]
  object NotEqual {
    implicit def neq[A, B]: NotEqual[A, B] = new NotEqual[A, B] {}

    @implicitAmbiguous("${A} is equal to ${A}")
    implicit def eq1[A]: NotEqual[A, A] = new NotEqual[A, A] {}
    implicit def eq2[A]: NotEqual[A, A] = new NotEqual[A, A] {}
  }

  implicit def head[KA, A, L <: TypeMap, R <: TypeMap, KB, B]: UpdateWithKeyUnsafe.Aux[Bin[KA, A, L, R], KA, A, KB, B, Bin[KB, B, L, R]] =
    new UpdateWithKeyUnsafe[Bin[KA, A, L, R], KA, A, KB, B] {
      override type Out = Bin[KB, B, L, R]
      private[typemaps] override def apply(m: Bin[KA, A, L, R], f: (A) => B): Bin[KB, B, L, R] = Bin(f(m.a), m.l, m.r)
      private[typemaps] override def const(m: Bin[KA, A, L, R], b: B): Bin[KB, B, L, R] = Bin(b, m.l, m.r)
    }

  implicit def binLHead[KA, A, LK, LA, LL <: TypeMap, LR <: TypeMap, R <: TypeMap, KB, B]: UpdateWithKeyUnsafe.Aux[Bin[KA, A, Bin[LK, LA, LL, LR], R], LK, LA, KB, B, Bin[KA, A, Bin[KB, B, LL, LR], R]] =
    new UpdateWithKeyUnsafe[Bin[KA, A, Bin[LK, LA, LL, LR], R], LK, LA, KB, B] {
      override type Out = Bin[KA, A, Bin[KB, B, LL, LR], R]
      private[typemaps] override def apply(m: Bin[KA, A, Bin[LK, LA, LL, LR], R], f: LA => B): Bin[KA, A, Bin[KB, B, LL, LR], R] = Bin(m.a, Bin(f(m.l.a), m.l.l, m.l.r), m.r)
      private[typemaps] override def const(m: Bin[KA, A, Bin[LK, LA, LL, LR], R], b: B): Bin[KA, A, Bin[KB, B, LL, LR], R] = Bin(m.a, Bin(b, m.l.l, m.l.r), m.r)
    }

  implicit def binRHead[KA, A, L <: TypeMap, RK, RA, RL <: TypeMap, RR <: TypeMap, KB, B]: UpdateWithKeyUnsafe.Aux[Bin[KA, A, L, Bin[RK, RA, RL, RR]], RK, RA, KB, B, Bin[KA, A, L, Bin[KB, B, RL, RR]]] =
    new UpdateWithKeyUnsafe[Bin[KA, A, L, Bin[RK, RA, RL, RR]], RK, RA, KB, B] {
      override type Out = Bin[KA, A, L, Bin[KB, B, RL, RR]]
      private[typemaps] override def apply(m: Bin[KA, A, L, Bin[RK, RA, RL, RR]], f: RA => B): Bin[KA, A, L, Bin[KB, B, RL, RR]] = Bin(m.a, m.l, Bin(f(m.r.a), m.r.l, m.r.r))
      private[typemaps] override def const(m: Bin[KA, A, L, Bin[RK, RA, RL, RR]], b: B): Bin[KA, A, L, Bin[KB, B, RL, RR]] = Bin(m.a, m.l, Bin(b, m.r.l, m.r.r))
    }

  implicit def binLL[KA, A, LK, LA, LL <: TypeMap, LR <: TypeMap, R <: TypeMap, KAA, AA, KB, B, O <: TypeMap](implicit ll: UpdateWithKeyUnsafe.Aux[LL, KAA, AA, KB, B, O]
                                                                                                             ): UpdateWithKeyUnsafe.Aux[Bin[KA, A, Bin[LK, LA, LL, LR], R], KAA, AA, KB, B, Bin[KA, A, Bin[LK, LA, O, LR], R]] =
    new UpdateWithKeyUnsafe[Bin[KA, A, Bin[LK, LA, LL, LR], R], KAA, AA, KB, B] {
      override type Out = Bin[KA, A, Bin[LK, LA, O, LR], R]
      private[typemaps] override def apply(m: Bin[KA, A, Bin[LK, LA, LL, LR], R], f: (AA) => B): Bin[KA, A, Bin[LK, LA, O, LR], R] = Bin(m.a, Bin(m.l.a, ll(m.l.l, f), m.l.r), m.r)
      private[typemaps] override def const(m: Bin[KA, A, Bin[LK, LA, LL, LR], R], b: B): Bin[KA, A, Bin[LK, LA, O, LR], R] = Bin(m.a, Bin(m.l.a, ll.const(m.l.l, b), m.l.r), m.r)
    }

  implicit def binLR[KA, A, LK, LA, LL <: TypeMap, LR <: TypeMap, R <: TypeMap, KAA, AA, KB, B, O <: TypeMap](implicit lr: UpdateWithKeyUnsafe.Aux[LR, KAA, AA, KB, B, O]
                                                                                                             ): UpdateWithKeyUnsafe.Aux[Bin[KA, A, Bin[LK, LA, LL, LR], R], KAA, AA, KB, B, Bin[KA, A, Bin[LK, LA, LL, O], R]] =
    new UpdateWithKeyUnsafe[Bin[KA, A, Bin[LK, LA, LL, LR], R], KAA, AA, KB, B] {
      override type Out = Bin[KA, A, Bin[LK, LA, LL, O], R]
      private[typemaps] override def apply(m: Bin[KA, A, Bin[LK, LA, LL, LR], R], f: (AA) => B): Bin[KA, A, Bin[LK, LA, LL, O], R] = Bin(m.a, Bin(m.l.a, m.l.l, lr(m.l.r, f)), m.r)
      private[typemaps] override def const(m: Bin[KA, A, Bin[LK, LA, LL, LR], R], b: B): Bin[KA, A, Bin[LK, LA, LL, O], R] = Bin(m.a, Bin(m.l.a, m.l.l, lr.const(m.l.r, b)), m.r)
    }

  implicit def binRL[KA, A, L <: TypeMap, RK, RA, RL <: TypeMap, RR <: TypeMap, KAA, AA, KB, B, O <: TypeMap](implicit rl: UpdateWithKeyUnsafe.Aux[RL, KAA, AA, KB, B, O]
                                                                                                             ): UpdateWithKeyUnsafe.Aux[Bin[KA, A, L, Bin[RK, RA, RL, RR]], KAA, AA, KB, B, Bin[KA, A, L, Bin[RK, RA, O, RR]]] =
    new UpdateWithKeyUnsafe[Bin[KA, A, L, Bin[RK, RA, RL, RR]], KAA, AA, KB, B] {
      override type Out = Bin[KA, A, L, Bin[RK, RA, O, RR]]
      private[typemaps] override def apply(m: Bin[KA, A, L, Bin[RK, RA, RL, RR]], f: (AA) => B): Bin[KA, A, L, Bin[RK, RA, O, RR]] = Bin(m.a, m.l, Bin(m.r.a, rl(m.r.l, f), m.r.r))
      private[typemaps] override def const(m: Bin[KA, A, L, Bin[RK, RA, RL, RR]], b: B): Bin[KA, A, L, Bin[RK, RA, O, RR]] = Bin(m.a, m.l, Bin(m.r.a, rl.const(m.r.l, b), m.r.r))
    }

  implicit def binRR[KA, A, L <: TypeMap, RK, RA, RL <: TypeMap, RR <: TypeMap, KAA, AA, KB, B, O <: TypeMap](implicit rr: UpdateWithKeyUnsafe.Aux[RR, KAA, AA, KB, B, O]
                                                                                                             ): UpdateWithKeyUnsafe.Aux[Bin[KA, A, L, Bin[RK, RA, RL, RR]], KAA, AA, KB, B, Bin[KA, A, L, Bin[RK, RA, RL, O]]] =
    new UpdateWithKeyUnsafe[Bin[KA, A, L, Bin[RK, RA, RL, RR]], KAA, AA, KB, B] {
      override type Out = Bin[KA, A, L, Bin[RK, RA, RL, O]]
      private[typemaps] override def apply(m: Bin[KA, A, L, Bin[RK, RA, RL, RR]], f: (AA) => B): Bin[KA, A, L, Bin[RK, RA, RL, O]] = Bin(m.a, m.l, Bin(m.r.a, m.r.l, rr(m.r.r, f)))
      private[typemaps] override def const(m: Bin[KA, A, L, Bin[RK, RA, RL, RR]], b: B): Bin[KA, A, L, Bin[RK, RA, RL, O]] = Bin(m.a, m.l, Bin(m.r.a, m.r.l, rr.const(m.r.r, b)))
    }
}
