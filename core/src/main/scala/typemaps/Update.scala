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

@implicitNotFound("{K} is not present in the map")
sealed trait Update[M <: TypeMap, K, A, B] {
  type Out <: TypeMap
  def apply(m: M, f: A => B): Out
  def const(m: M, f: B): Out
}

object Update {
  type Aux[M <: TypeMap, K, A, B, O <: TypeMap] = Update[M, K, A, B] { type Out = O }
  type Monomorphic[M <: TypeMap, K, A] = Update.Aux[M, K, A, A, M]

  implicit def head[K, A, L <: TypeMap, R <: TypeMap, B]: Update.Aux[Bin[K, A, L, R], K, A, B, Bin[K, B, L, R]] =
    new Update[Bin[K, A, L, R], K, A, B] {
      override type Out = Bin[K, B, L, R]
      override def apply(m: Bin[K, A, L, R], f: (A) => B): Bin[K, B, L, R] = Bin(f(m.a), m.l, m.r)
      override def const(m: Bin[K, A, L, R], b: B): Bin[K, B, L, R] = Bin(b, m.l, m.r)
    }

  implicit def binLHead[K, A, LK, LA, LL <: TypeMap, LR <: TypeMap, R <: TypeMap, B]: Update.Aux[Bin[K, A, Bin[LK, LA, LL, LR], R], LK, LA, B, Bin[K, A, Bin[LK, B, LL, LR], R]] =
    new Update[Bin[K, A, Bin[LK, LA, LL, LR], R], LK, LA, B] {
      override type Out = Bin[K, A, Bin[LK, B, LL, LR], R]
      override def apply(m: Bin[K, A, Bin[LK, LA, LL, LR], R], f: (LA) => B): Bin[K, A, Bin[LK, B, LL, LR], R] = Bin(m.a, Bin(f(m.l.a), m.l.l, m.l.r), m.r)
      override def const(m: Bin[K, A, Bin[LK, LA, LL, LR], R], b: B): Bin[K, A, Bin[LK, B, LL, LR], R] = Bin(m.a, Bin(b, m.l.l, m.l.r), m.r)
    }

  implicit def binRHead[K, A, L <: TypeMap, RK, RA, RL <: TypeMap, RR <: TypeMap, B]: Update.Aux[Bin[K, A, L, Bin[RK, RA, RL, RR]], RK, RA, B, Bin[K, A, L, Bin[RK, B, RL, RR]]] =
    new Update[Bin[K, A, L, Bin[RK, RA, RL, RR]], RK, RA, B] {
      override type Out = Bin[K, A, L, Bin[RK, B, RL, RR]]
      override def apply(m: Bin[K, A, L, Bin[RK, RA, RL, RR]], f: (RA) => B): Bin[K, A, L, Bin[RK, B, RL, RR]] = Bin(m.a, m.l, Bin(f(m.r.a), m.r.l, m.r.r))
      override def const(m: Bin[K, A, L, Bin[RK, RA, RL, RR]], b: B): Bin[K, A, L, Bin[RK, B, RL, RR]] = Bin(m.a, m.l, Bin(b, m.r.l, m.r.r))
    }

  implicit def binLL[KA, A, LK, LA, LL <: TypeMap, LR <: TypeMap, R <: TypeMap, K, AA, B, O <: TypeMap](implicit ll: Update.Aux[LL, K, AA, B, O]
                                                                                                       ): Update.Aux[Bin[KA, A, Bin[LK, LA, LL, LR], R], K, AA, B, Bin[KA, A, Bin[LK, LA, O, LR], R]] =
    new Update[Bin[KA, A, Bin[LK, LA, LL, LR], R], K, AA, B] {
      override type Out = Bin[KA, A, Bin[LK, LA, O, LR], R]
      override def apply(m: Bin[KA, A, Bin[LK, LA, LL, LR], R], f: (AA) => B): Bin[KA, A, Bin[LK, LA, O, LR], R] = Bin(m.a, Bin(m.l.a, ll(m.l.l, f), m.l.r), m.r)
      override def const(m: Bin[KA, A, Bin[LK, LA, LL, LR], R], b: B): Bin[KA, A, Bin[LK, LA, O, LR], R] = Bin(m.a, Bin(m.l.a, ll.const(m.l.l, b), m.l.r), m.r)
    }

  implicit def binLR[KA, A, LK, LA, LL <: TypeMap, LR <: TypeMap, R <: TypeMap, K, AA, B, O <: TypeMap](implicit lr: Update.Aux[LR, K, AA, B, O]
                                                                                                       ): Update.Aux[Bin[KA, A, Bin[LK, LA, LL, LR], R], K, AA, B, Bin[KA, A, Bin[LK, LA, LL, O], R]] =
    new Update[Bin[KA, A, Bin[LK, LA, LL, LR], R], K, AA, B] {
      override type Out = Bin[KA, A, Bin[LK, LA, LL, O], R]
      override def apply(m: Bin[KA, A, Bin[LK, LA, LL, LR], R], f: (AA) => B): Bin[KA, A, Bin[LK, LA, LL, O], R] = Bin(m.a, Bin(m.l.a, m.l.l, lr(m.l.r, f)), m.r)
      override def const(m: Bin[KA, A, Bin[LK, LA, LL, LR], R], b: B): Bin[KA, A, Bin[LK, LA, LL, O], R] = Bin(m.a, Bin(m.l.a, m.l.l, lr.const(m.l.r, b)), m.r)
    }

  implicit def binRL[KA, A, L <: TypeMap, RK, RA, RL <: TypeMap, RR <: TypeMap, K, AA, B, O <: TypeMap](implicit rl: Update.Aux[RL, K, AA, B, O]
                                                                                                       ): Update.Aux[Bin[KA, A, L, Bin[RK, RA, RL, RR]], K, AA, B, Bin[KA, A, L, Bin[RK, RA, O, RR]]] =
    new Update[Bin[KA, A, L, Bin[RK, RA, RL, RR]], K, AA, B] {
      override type Out = Bin[KA, A, L, Bin[RK, RA, O, RR]]
      override def apply(m: Bin[KA, A, L, Bin[RK, RA, RL, RR]], f: (AA) => B): Bin[KA, A, L, Bin[RK, RA, O, RR]] = Bin(m.a, m.l, Bin(m.r.a, rl(m.r.l, f), m.r.r))
      override def const(m: Bin[KA, A, L, Bin[RK, RA, RL, RR]], b: B): Bin[KA, A, L, Bin[RK, RA, O, RR]] = Bin(m.a, m.l, Bin(m.r.a, rl.const(m.r.l, b), m.r.r))
    }

  implicit def binRR[KA, A, L <: TypeMap, RK, RA, RL <: TypeMap, RR <: TypeMap, K, AA, B, O <: TypeMap](implicit rr: Update.Aux[RR, K, AA, B, O]
                                                                                                       ): Update.Aux[Bin[KA, A, L, Bin[RK, RA, RL, RR]], K, AA, B, Bin[KA, A, L, Bin[RK, RA, RL, O]]] =
    new Update[Bin[KA, A, L, Bin[RK, RA, RL, RR]], K, AA, B] {
      override type Out = Bin[KA, A, L, Bin[RK, RA, RL, O]]
      override def apply(m: Bin[KA, A, L, Bin[RK, RA, RL, RR]], f: (AA) => B): Bin[KA, A, L, Bin[RK, RA, RL, O]] = Bin(m.a, m.l, Bin(m.r.a, m.r.l, rr(m.r.r, f)))
      override def const(m: Bin[KA, A, L, Bin[RK, RA, RL, RR]], b: B): Bin[KA, A, L, Bin[RK, RA, RL, O]] = Bin(m.a, m.l, Bin(m.r.a, m.r.l, rr.const(m.r.r, b)))
    }

  final class UpdateFn[M <: TypeMap, K, A](val m: M) extends AnyVal {
    @inline def apply[B](f: A => B)(implicit update: Update[M, K, A, B]): update.Out = update(m, f)
    @inline def using[B](f: A => B)(implicit update: Update[M, K, A, B]): update.Out = update(m, f)
    @inline def const[B](b: B)(implicit update: Update[M, K, A, B]): update.Out = update.const(m, b)
  }

  final class UpdateSetFn[M <: TypeMap, A](val m: M) extends AnyVal {
    @inline def apply(f: A => A)(implicit update: Update[M, A, A, A]): update.Out = update(m, f)
    @inline def using(f: A => A)(implicit update: Update[M, A, A, A]): update.Out = update(m, f)
    @inline def const(a: A)(implicit update: Update[M, A, A, A]): update.Out = update.const(m, a)
  }
}
