package typemaps

sealed trait Upsert[M <: TypeMap, K, A, B] {
  type Out <: TypeMap
  def apply(m: M, update: A => B, insert: => B): Out
}

object Upsert {
  type Aux[M <: TypeMap, K, A, B, O <: TypeMap] = Upsert[M, K, A, B] { type Out = O }

  // A key can be inserted into a map iff it cannot be updated in the map

  implicit def insert[M <: TypeMap, K, A, B, O <: TypeMap](implicit i: Insert.Aux[M, K, B, O]
                                                          ): Upsert.Aux[M, K, A, B, O] =
    new Upsert[M, K, A, B] {
      override type Out = O
      override def apply(m: M, update: (A) => B, insert: => B): O = i(m, insert)
    }

  implicit def update[M <: TypeMap, K, A, B, O <: TypeMap](implicit u: Update.Aux[M, K, A, B, O]
                                                          ): Upsert.Aux[M, K, A, B, O] =
    new Upsert[M, K, A, B] {
      override type Out = O
      override def apply(m: M, update: (A) => B, insert: => B): O = u(m, update)
    }

  final class UpsertFn[M <: TypeMap, K, A](val m: M) extends AnyVal {
    @inline def apply[B](update: A => B, insert: => B)(implicit upsert: Upsert[M, K, A, B]): upsert.Out = upsert(m, update, insert)
    @inline def using[B](update: A => B, insert: => B)(implicit upsert: Upsert[M, K, A, B]): upsert.Out = upsert(m, update, insert)
  }

  final class UpsertSetFn[M <: TypeMap, A](val m: M) extends AnyVal {
    @inline def apply(update: A => A, insert: => A)(implicit upsert: Upsert[M, A, A, A]): upsert.Out = upsert(m, update, insert)
    @inline def using(update: A => A, insert: => A)(implicit upsert: Upsert[M, A, A, A]): upsert.Out = upsert(m, update, insert)
  }
}
