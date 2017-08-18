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
sealed trait UpdateWithKey[M <: TypeMap, KA, A, KB, B] {
  type Out <: TypeMap
  def apply(m: M, f: A => B): Out
  def const(m: M, f: B): Out
}

object UpdateWithKey {
  type Aux[M <: TypeMap, KA, A, KB, B, O <: TypeMap] = UpdateWithKey[M, KA, A, KB, B] { type Out = O }
  type Monomorphic[M <: TypeMap, K, A] = UpdateWithKey.Aux[M, K, A, K, A, M]

  sealed trait NotEqual[A, B]
  object NotEqual {
    implicit def neq[A, B]: NotEqual[A, B] = new NotEqual[A, B] {}

    @implicitAmbiguous("${A} is equal to ${A}")
    implicit def eq1[A]: NotEqual[A, A] = new NotEqual[A, A] {}
    implicit def eq2[A]: NotEqual[A, A] = new NotEqual[A, A] {}
  }

  // If KB can be inserted into both sides then it is not already present
  implicit def head[KA, A, L <: TypeMap, R <: TypeMap, KB, B](implicit l: Insert[L, KB, B],
                                                              r: Insert[R, KB, B]
                                                             ): UpdateWithKey.Aux[Bin[KA, A, L, R], KA, A, KB, B, Bin[KB, B, L, R]] =
    new UpdateWithKey[Bin[KA, A, L, R], KA, A, KB, B] {
      override type Out = Bin[KB, B, L, R]
      override def apply(m: Bin[KA, A, L, R], f: (A) => B): Bin[KB, B, L, R] = Bin(f(m.a), m.l, m.r)
      override def const(m: Bin[KA, A, L, R], b: B): Bin[KB, B, L, R] = Bin(b, m.l, m.r)
    }

  // UpdateWithKey in L, ensure KB is not equal to KA or present in LL, LR, or R
  implicit def binLHead[KA, A, LK, LA, LL <: TypeMap, LR <: TypeMap, R <: TypeMap, KB, B](implicit ll: Insert[LL, KB, B],
                                                                                          lr: Insert[LR, KB, B],
                                                                                          r: Insert[R, KB, B],
                                                                                          a: NotEqual[KA, KB]
                                                                                         ): UpdateWithKey.Aux[Bin[KA, A, Bin[LK, LA, LL, LR], R], LK, LA, KB, B, Bin[KA, A, Bin[KB, B, LL, LR], R]] =
    new UpdateWithKey[Bin[KA, A, Bin[LK, LA, LL, LR], R], LK, LA, KB, B] {
      override type Out = Bin[KA, A, Bin[KB, B, LL, LR], R]
      override def apply(m: Bin[KA, A, Bin[LK, LA, LL, LR], R], f: LA => B): Bin[KA, A, Bin[KB, B, LL, LR], R] = Bin(m.a, Bin(f(m.l.a), m.l.l, m.l.r), m.r)
      override def const(m: Bin[KA, A, Bin[LK, LA, LL, LR], R], b: B): Bin[KA, A, Bin[KB, B, LL, LR], R] = Bin(m.a, Bin(b, m.l.l, m.l.r), m.r)
    }

  // UpdateWithKey in R, ensure KB is not equal to KA or present in L, RL, or RR
  implicit def binRHead[KA, A, L <: TypeMap, RK, RA, RL <: TypeMap, RR <: TypeMap, KB, B](implicit l: Insert[L, KB, B],
                                                                                          rl: Insert[RL, KB, B],
                                                                                          rr: Insert[RR, KB, B],
                                                                                          a: NotEqual[KA, KB]
                                                                                         ): UpdateWithKey.Aux[Bin[KA, A, L, Bin[RK, RA, RL, RR]], RK, RA, KB, B, Bin[KA, A, L, Bin[KB, B, RL, RR]]] =
    new UpdateWithKey[Bin[KA, A, L, Bin[RK, RA, RL, RR]], RK, RA, KB, B] {
      override type Out = Bin[KA, A, L, Bin[KB, B, RL, RR]]
      override def apply(m: Bin[KA, A, L, Bin[RK, RA, RL, RR]], f: RA => B): Bin[KA, A, L, Bin[KB, B, RL, RR]] = Bin(m.a, m.l, Bin(f(m.r.a), m.r.l, m.r.r))
      override def const(m: Bin[KA, A, L, Bin[RK, RA, RL, RR]], b: B): Bin[KA, A, L, Bin[KB, B, RL, RR]] = Bin(m.a, m.l, Bin(b, m.r.l, m.r.r))
    }

  // UpdateWithKey in LL, ensure KB is not equal to KA or LK, or present in LR or R
  implicit def binLL[KA, A, LK, LA, LL <: TypeMap, LR <: TypeMap, R <: TypeMap, KAA, AA, KB, B, O <: TypeMap](implicit ll: UpdateWithKey.Aux[LL, KAA, AA, KB, B, O],
                                                                                                              lr: Insert[LR, KB, B],
                                                                                                              r: Insert[R, KB, B],
                                                                                                              a: NotEqual[KA, KB],
                                                                                                              l: NotEqual[LK, KB]
                                                                                                             ): UpdateWithKey.Aux[Bin[KA, A, Bin[LK, LA, LL, LR], R], KAA, AA, KB, B, Bin[KA, A, Bin[LK, LA, O, LR], R]] =
    new UpdateWithKey[Bin[KA, A, Bin[LK, LA, LL, LR], R], KAA, AA, KB, B] {
      override type Out = Bin[KA, A, Bin[LK, LA, O, LR], R]
      override def apply(m: Bin[KA, A, Bin[LK, LA, LL, LR], R], f: (AA) => B): Bin[KA, A, Bin[LK, LA, O, LR], R] = Bin(m.a, Bin(m.l.a, ll(m.l.l, f), m.l.r), m.r)
      override def const(m: Bin[KA, A, Bin[LK, LA, LL, LR], R], b: B): Bin[KA, A, Bin[LK, LA, O, LR], R] = Bin(m.a, Bin(m.l.a, ll.const(m.l.l, b), m.l.r), m.r)
    }

  // UpdateWithKey in LR, ensure KB is not equal to KA or LK, or present in LL or R
  implicit def binLR[KA, A, LK, LA, LL <: TypeMap, LR <: TypeMap, R <: TypeMap, KAA, AA, KB, B, O <: TypeMap](implicit lr: UpdateWithKey.Aux[LR, KAA, AA, KB, B, O],
                                                                                                              ll: Insert[LL, KB, B],
                                                                                                              r: Insert[R, KB, B],
                                                                                                              a: NotEqual[KA, KB],
                                                                                                              l: NotEqual[LK, KB]
                                                                                                             ): UpdateWithKey.Aux[Bin[KA, A, Bin[LK, LA, LL, LR], R], KAA, AA, KB, B, Bin[KA, A, Bin[LK, LA, LL, O], R]] =
    new UpdateWithKey[Bin[KA, A, Bin[LK, LA, LL, LR], R], KAA, AA, KB, B] {
      override type Out = Bin[KA, A, Bin[LK, LA, LL, O], R]
      override def apply(m: Bin[KA, A, Bin[LK, LA, LL, LR], R], f: (AA) => B): Bin[KA, A, Bin[LK, LA, LL, O], R] = Bin(m.a, Bin(m.l.a, m.l.l, lr(m.l.r, f)), m.r)
      override def const(m: Bin[KA, A, Bin[LK, LA, LL, LR], R], b: B): Bin[KA, A, Bin[LK, LA, LL, O], R] = Bin(m.a, Bin(m.l.a, m.l.l, lr.const(m.l.r, b)), m.r)
    }

  // UpdateWithKey in RL, ensure KB is not equal to KA or RK, or present in L or RR
  implicit def binRL[KA, A, L <: TypeMap, RK, RA, RL <: TypeMap, RR <: TypeMap, KAA, AA, KB, B, O <: TypeMap](implicit rl: UpdateWithKey.Aux[RL, KAA, AA, KB, B, O],
                                                                                                              rr: Insert[RR, KB, B],
                                                                                                              l: Insert[L, KB, B],
                                                                                                              a: NotEqual[KA, KB],
                                                                                                              r: NotEqual[RK, KB]
                                                                                                             ): UpdateWithKey.Aux[Bin[KA, A, L, Bin[RK, RA, RL, RR]], KAA, AA, KB, B, Bin[KA, A, L, Bin[RK, RA, O, RR]]] =
    new UpdateWithKey[Bin[KA, A, L, Bin[RK, RA, RL, RR]], KAA, AA, KB, B] {
      override type Out = Bin[KA, A, L, Bin[RK, RA, O, RR]]
      override def apply(m: Bin[KA, A, L, Bin[RK, RA, RL, RR]], f: (AA) => B): Bin[KA, A, L, Bin[RK, RA, O, RR]] = Bin(m.a, m.l, Bin(m.r.a, rl(m.r.l, f), m.r.r))
      override def const(m: Bin[KA, A, L, Bin[RK, RA, RL, RR]], b: B): Bin[KA, A, L, Bin[RK, RA, O, RR]] = Bin(m.a, m.l, Bin(m.r.a, rl.const(m.r.l, b), m.r.r))
    }

  // UpdateWithKey in RL, ensure KB is not equal to KA or RK, or present in L or RR
  implicit def binRR[KA, A, L <: TypeMap, RK, RA, RL <: TypeMap, RR <: TypeMap, KAA, AA, KB, B, O <: TypeMap](implicit rr: UpdateWithKey.Aux[RR, KAA, AA, KB, B, O],
                                                                                                              rl: Insert[RL, KB, B],
                                                                                                              l: Insert[L, KB, B],
                                                                                                              a: NotEqual[KA, KB],
                                                                                                              r: NotEqual[RK, KB]
                                                                                                             ): UpdateWithKey.Aux[Bin[KA, A, L, Bin[RK, RA, RL, RR]], KAA, AA, KB, B, Bin[KA, A, L, Bin[RK, RA, RL, O]]] =
    new UpdateWithKey[Bin[KA, A, L, Bin[RK, RA, RL, RR]], KAA, AA, KB, B] {
      override type Out = Bin[KA, A, L, Bin[RK, RA, RL, O]]
      override def apply(m: Bin[KA, A, L, Bin[RK, RA, RL, RR]], f: (AA) => B): Bin[KA, A, L, Bin[RK, RA, RL, O]] = Bin(m.a, m.l, Bin(m.r.a, m.r.l, rr(m.r.r, f)))
      override def const(m: Bin[KA, A, L, Bin[RK, RA, RL, RR]], b: B): Bin[KA, A, L, Bin[RK, RA, RL, O]] = Bin(m.a, m.l, Bin(m.r.a, m.r.l, rr.const(m.r.r, b)))
    }

  final class UpdateWithKeyFn[M <: TypeMap, KA, KB, A](val m: M) extends AnyVal {
    @inline def apply[B](f: A => B)(implicit update: UpdateWithKey[M, KA, A, KB, B]): update.Out = update(m, f)
    @inline def using[B](f: A => B)(implicit update: UpdateWithKey[M, KA, A, KB, B]): update.Out = update(m, f)
    @inline def const[B](b: B)(implicit update: UpdateWithKey[M, KA, A, KB, B]): update.Out = update.const(m, b)
  }

  final class UpdateWithKeySetFn[M <: TypeMap, A](val m: M) extends AnyVal {
    @inline def apply[B](f: A => B)(implicit update: UpdateWithKey[M, A, A, B, B]): update.Out = update(m, f)
    @inline def using[B](f: A => B)(implicit update: UpdateWithKey[M, A, A, B, B]): update.Out = update(m, f)
    @inline def const[B](b: B)(implicit update: UpdateWithKey[M, A, A, B, B]): update.Out = update.const(m, b)
  }
}
