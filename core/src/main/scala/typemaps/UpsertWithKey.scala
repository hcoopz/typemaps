package typemaps

sealed trait UpsertWithKey[M <: TypeMap, KA, A, KB, B] {
  type Out <: TypeMap
  def apply(m: M, update: A => B, insert: => B): Out
}

object UpsertWithKey {
  type Aux[M <: TypeMap, KA, A, KB, B, O <: TypeMap] = UpsertWithKey[M, KA, A, KB, B] { type Out = O }

  // A key can be inserted into a map iff it cannot be updated in the map

  implicit def insert[M <: TypeMap, KA, A, KB, B, O <: TypeMap](implicit i: Insert.Aux[M, KB, B, O]
                                                               ): UpsertWithKey.Aux[M, KA, A, KB, B, O] =
    new UpsertWithKey[M, KA, A, KB, B] {
      override type Out = O
      override def apply(m: M, update: (A) => B, insert: => B): O = i(m, insert)
    }

  implicit def update[M <: TypeMap, KA, A, KB, B, O <: TypeMap](implicit u: UpdateWithKey.Aux[M, KA, A, KB, B, O]
                                                               ): UpsertWithKey.Aux[M, KA, A, KB, B, O] =
    new UpsertWithKey[M, KA, A, KB, B] {
      override type Out = O
      override def apply(m: M, update: (A) => B, insert: => B): O = u(m, update)
    }

  final class UpsertWithKeyFn[M <: TypeMap, KA, A, KB](val m: M) extends AnyVal {
    @inline def apply[B](update: A => B, insert: => B)(implicit upsert: UpsertWithKey[M, KA, A, KB, B]): upsert.Out = upsert(m, update, insert)
    @inline def using[B](update: A => B, insert: => B)(implicit upsert: UpsertWithKey[M, KA, A, KB, B]): upsert.Out = upsert(m, update, insert)
  }

  final class UpsertWithKeySetFn[M <: TypeMap, A](val m: M) extends AnyVal {
    @inline def apply[B](update: A => B, insert: => B)(implicit upsert: UpsertWithKey[M, A, A, B, B]): upsert.Out = upsert(m, update, insert)
    @inline def using[B](update: A => B, insert: => B)(implicit upsert: UpsertWithKey[M, A, A, B, B]): upsert.Out = upsert(m, update, insert)
  }
}
