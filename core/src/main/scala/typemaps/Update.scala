package typemaps

import TypeMap._

import scala.annotation.{implicitAmbiguous, implicitNotFound}

@implicitNotFound("{K} is not present in the map")
sealed trait Update[M <: TypeMap, KA, A, KB, B] {
  type Out <: TypeMap
  def apply(m: M, f: A => B): Out
  def const(m: M, f: B): Out
}

object Update {
  type Aux[M <: TypeMap, KA, A, KB, B, O <: TypeMap] = Update[M, KA, A, KB, B] { type Out = O }
  type Monomorphic[M <: TypeMap, K, A] = Update.Aux[M, K, A, K, A, M]

  sealed trait NotEqual[A, B]
  object NotEqual {
    implicit def neq[A, B]: NotEqual[A, B] = new NotEqual[A, B] {}

    @implicitAmbiguous("${A} is equal to ${A}")
    implicit def eq1[A]: NotEqual[A, A] = new NotEqual[A, A] {}
    implicit def eq2[A]: NotEqual[A, A] = new NotEqual[A, A] {}
  }

  // If KB can be inserted into both sides then it is not already present
  implicit def binA[KA, A, L <: TypeMap, R <: TypeMap, KB, B](implicit l: Insert[L, KB, B],
                                                              r: Insert[R, KB, B]
                                                             ): Update.Aux[Bin[KA, A, L, R], KA, A, KB, B, Bin[KB, B, L, R]] =
    new Update[Bin[KA, A, L, R], KA, A, KB, B] {
      override type Out = Bin[KB, B, L, R]
      override def apply(m: Bin[KA, A, L, R], f: (A) => B): Bin[KB, B, L, R] = Bin(f(m.a), m.l, m.r)
      override def const(m: Bin[KA, A, L, R], b: B): Bin[KB, B, L, R] = Bin(b, m.l, m.r)
    }

  // Update in LHS, ensure KB is not equal to KA or present in the RHS
  implicit def binL[KA, A, L <: TypeMap, R <: TypeMap, KAA, AA, KB, B, O <: TypeMap](implicit l: Update.Aux[L, KAA, AA, KB, B, O],
                                                                                     r: Insert[R, KB, B],
                                                                                     a: NotEqual[KA, KB]
                                                                                    ): Update.Aux[Bin[KA, A, L, R], KAA, AA, KB, B, Bin[KA, A, O, R]] =
    new Update[Bin[KA, A, L, R], KAA, AA, KB, B] {
      override type Out = Bin[KA, A, O, R]
      override def apply(m: Bin[KA, A, L, R], f: (AA) => B): Bin[KA, A, O, R] = Bin(m.a, l(m.l, f), m.r)
      override def const(m: Bin[KA, A, L, R], b: B): Bin[KA, A, O, R] = Bin(m.a, l.const(m.l, b), m.r)
    }

  // Update in RHS, ensure KB is not equal to KA or present in the LHS
  implicit def binR[KA, A, L <: TypeMap, R <: TypeMap, KAA, AA, KB, B, O <: TypeMap](implicit r: Update.Aux[R, KAA, AA, KB, B, O],
                                                                                     l: Insert[L, KB, B],
                                                                                     a: NotEqual[KA, KB]
                                                                                    ): Update.Aux[Bin[KA, A, L, R], KAA, AA, KB, B, Bin[KA, A, L, O]] =
    new Update[Bin[KA, A, L, R], KAA, AA, KB, B] {
      override type Out = Bin[KA, A, L, O]
      override def apply(m: Bin[KA, A, L, R], f: (AA) => B): Bin[KA, A, L, O] = Bin(m.a, m.l, r(m.r, f))
      override def const(m: Bin[KA, A, L, R], b: B): Bin[KA, A, L, O] = Bin(m.a, m.l, r.const(m.r, b))
    }

  final class UpdateFn[M <: TypeMap, KA, KB, A](val m: M) extends AnyVal {
    @inline def apply[B](f: A => B)(implicit update: Update[M, KA, A, KB, B]): update.Out = update(m, f)
    @inline def using[B](f: A => B)(implicit update: Update[M, KA, A, KB, B]): update.Out = update(m, f)
    @inline def const[B](b: B)(implicit update: Update[M, KA, A, KB, B]): update.Out = update.const(m, b)
  }

  final class UpdateSetFn[M <: TypeMap, A](val m: M) extends AnyVal {
    @inline def apply[B](f: A => B)(implicit update: Update[M, A, A, B, B]): update.Out = update(m, f)
    @inline def using[B](f: A => B)(implicit update: Update[M, A, A, B, B]): update.Out = update(m, f)
    @inline def const[B](b: B)(implicit update: Update[M, A, A, B, B]): update.Out = update.const(m, b)
  }
}
