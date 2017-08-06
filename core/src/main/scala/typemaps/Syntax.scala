package typemaps

import TypeMap._

object Syntax {
  implicit final class TypeMapSyntax[M <: TypeMap](val m: M) extends AnyVal {
    @inline def insert[K]: Insert.InsertFn[M, K] = new Insert.InsertFn(m)
    @inline def insertSet[A](a: A)(implicit insert: Insert[M, A, A]): insert.Out = insert(m, a)

    @inline def apply[K](implicit lookup: Lookup[M, K]): lookup.Out = lookup(m)
    @inline def lookup[K](implicit lookup: Lookup[M, K]): lookup.Out = lookup(m)

    @inline def prettyPrint(implicit prettyPrint: PrettyPrint[M]): String = prettyPrint(m)

    @inline def update[K](implicit lookup: Lookup[M, K]): Update.UpdateFn[M, K, K, lookup.Out] = new Update.UpdateFn(m)
    @inline def updateWithKey[KA, KB](implicit lookup: Lookup[M, KA]): Update.UpdateFn[M, KA, KB, lookup.Out] = new Update.UpdateFn(m)

    @inline def updateSet[A]: Update.UpdateSetFn[M, A] = new Update.UpdateSetFn(m)

    @inline def upsert[K](implicit lookup: Lookup[M, K]): Upsert.UpsertFn[M, K, K, lookup.Out] = new Upsert.UpsertFn(m)
    @inline def upsertWithKey[KA, KB](implicit lookup: Lookup[M, KA]): Upsert.UpsertFn[M, KA, KB, lookup.Out] = new Upsert.UpsertFn(m)

    @inline def upsertSet[A](update: A => A, insert: => A)(implicit upsert: Upsert[M, A, A, A, A]): upsert.Out = upsert(m, update, insert)
    @inline def upsertSetWithKey[A]: Upsert.UpsertSetFn[M, A] = new Upsert.UpsertSetFn(m)

    @inline def remove[K](implicit remove: Remove[M, K]): remove.Out = remove(m)
  }

  implicit final class IdSyntax[A](val a: A) extends AnyVal {
    @inline def singletonTypeMap[K]: Bin[K, A, Tip, Tip] = Bin(a, Tip, Tip)
    @inline def singletonTypeSet: Bin[A, A, Tip, Tip] = Bin(a, Tip, Tip)
  }
}