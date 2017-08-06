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

  implicit def binA[K, A, L <: TypeMap, R <: TypeMap, B]: Update.Aux[Bin[K, A, L, R], K, A, B, Bin[K, B, L, R]] =
    new Update[Bin[K, A, L, R], K, A, B] {
      override type Out = Bin[K, B, L, R]
      override def apply(m: Bin[K, A, L, R], f: (A) => B): Bin[K, B, L, R] = Bin(f(m.a), m.l, m.r)
      override def const(m: Bin[K, A, L, R], b: B): Bin[K, B, L, R] = Bin(b, m.l, m.r)
    }

  implicit def binL[KA, A, L <: TypeMap, R <: TypeMap, K, AA, B, O <: TypeMap](implicit l: Update.Aux[L, K, AA, B, O]
                                                                              ): Update.Aux[Bin[KA, A, L, R], K, AA, B, Bin[KA, A, O, R]] =
    new Update[Bin[KA, A, L, R], K, AA, B] {
      override type Out = Bin[KA, A, O, R]
      override def apply(m: Bin[KA, A, L, R], f: (AA) => B): Bin[KA, A, O, R] = Bin(m.a, l(m.l, f), m.r)
      override def const(m: Bin[KA, A, L, R], b: B): Bin[KA, A, O, R] = Bin(m.a, l.const(m.l, b), m.r)
    }

  // Update in RHS, ensure KB is not equal to KA or present in the LHS
  implicit def binR[KA, A, L <: TypeMap, R <: TypeMap, K, AA, B, O <: TypeMap](implicit r: Update.Aux[R, K, AA, B, O]
                                                                              ): Update.Aux[Bin[KA, A, L, R], K, AA, B, Bin[KA, A, L, O]] =
    new Update[Bin[KA, A, L, R], K, AA, B] {
      override type Out = Bin[KA, A, L, O]
      override def apply(m: Bin[KA, A, L, R], f: (AA) => B): Bin[KA, A, L, O] = Bin(m.a, m.l, r(m.r, f))
      override def const(m: Bin[KA, A, L, R], b: B): Bin[KA, A, L, O] = Bin(m.a, m.l, r.const(m.r, b))
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
