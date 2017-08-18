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

final class TypeMapSyntax[M <: TypeMap](val m: M) extends AnyVal {
  @inline def insert[K]: Insert.InsertFn[M, K] = new Insert.InsertFn(m)
  @inline def insertSet[A](a: A)(implicit insert: Insert[M, A, A]): insert.Out = insert(m, a)

  @inline def apply[K](implicit lookup: Lookup[M, K]): lookup.Out = lookup(m)
  @inline def lookup[K](implicit lookup: Lookup[M, K]): lookup.Out = lookup(m)

  @inline def prettyPrint(implicit prettyPrint: PrettyPrint[M]): String = prettyPrint(m)

  @inline def update[K](implicit lookup: Lookup[M, K]): Update.UpdateFn[M, K, lookup.Out] = new Update.UpdateFn(m)
  @inline def updateWithKey[KA, KB](implicit lookup: Lookup[M, KA]): UpdateWithKey.UpdateWithKeyFn[M, KA, KB, lookup.Out] = new UpdateWithKey.UpdateWithKeyFn(m)

  @inline def updateSet[A]: Update.UpdateSetFn[M, A] = new Update.UpdateSetFn(m)
  @inline def updateSetWithKey[A]: UpdateWithKey.UpdateWithKeySetFn[M, A] = new UpdateWithKey.UpdateWithKeySetFn(m)

  @inline def upsert[K](implicit lookup: Lookup[M, K]): Upsert.UpsertFn[M, K, lookup.Out] = new Upsert.UpsertFn(m)
  @inline def upsertWithKey[KA, KB](implicit lookup: Lookup[M, KA]): UpsertWithKey.UpsertWithKeyFn[M, KA, KB, lookup.Out] = new UpsertWithKey.UpsertWithKeyFn(m)

  @inline def upsertSet[A]: Upsert.UpsertSetFn[M, A] = new Upsert.UpsertSetFn(m)
  @inline def upsertSetWithKey[A]: UpsertWithKey.UpsertWithKeySetFn[M, A] = new UpsertWithKey.UpsertWithKeySetFn(m)

  @inline def remove[K](implicit remove: Remove[M, K]): remove.Out = remove(m)
}
