package typemaps

sealed trait Upsert[M <: TypeMap, KA, A, KB, B] {
  type Out <: TypeMap
  def apply(m: M, update: A => B, insert: => B): Out
}

object Upsert {
  type Aux[M <: TypeMap, KA, A, KB, B, O <: TypeMap] = Upsert[M, KA, A, KB, B] { type Out = O }

  // A key can be inserted into a map iff it cannot be updated in the map

  implicit def insert[M <: TypeMap, KA, A, KB, B, O <: TypeMap](implicit i: Insert.Aux[M, KB, B, O]
                                                               ): Upsert.Aux[M, KA, A, KB, B, O] =
    new Upsert[M, KA, A, KB, B] {
      override type Out = O
      override def apply(m: M, update: (A) => B, insert: => B): O = i(m, insert)
    }

  implicit def update[M <: TypeMap, KA, A, KB, B, O <: TypeMap](implicit u: Update.Aux[M, KA, A, KB, B, O]
                                                               ): Upsert.Aux[M, KA, A, KB, B, O] =
    new Upsert[M, KA, A, KB, B] {
      override type Out = O
      override def apply(m: M, update: (A) => B, insert: => B): O = u(m, update)
    }

  final class UpsertFn[M <: TypeMap, KA, A, KB](val m: M) extends AnyVal {
    @inline def apply[B](update: A => B, insert: => B)(implicit upsert: Upsert[M, KA, A, KB, B]): upsert.Out = upsert(m, update, insert)
    @inline def using[B](update: A => B, insert: => B)(implicit upsert: Upsert[M, KA, A, KB, B]): upsert.Out = upsert(m, update, insert)
  }

  final class UpsertSetFn[M <: TypeMap, A](val m: M) extends AnyVal {
    @inline def apply[B](update: A => B, insert: => B)(implicit upsert: Upsert[M, A, A, B, B]): upsert.Out = upsert(m, update, insert)
    @inline def using[B](update: A => B, insert: => B)(implicit upsert: Upsert[M, A, A, B, B]): upsert.Out = upsert(m, update, insert)
  }
}
