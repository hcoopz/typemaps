package typemaps
package interop.shapeless

import shapeless._

import scala.language.higherKinds

final class HListSyntax[L <: HList](val l: L) extends AnyVal {
  @inline def toTypeMap(implicit fromHList: FromHList[L]): fromHList.Out = fromHList(l)

  @inline def toTypeMapWithKeyList[K <: HList](implicit fromHListWithKeyList: FromHListWithKeyList[L, K]): fromHListWithKeyList.Out = fromHListWithKeyList(l)

  @inline def toTypeMapWithKeyRel[R[_, _]](implicit fromHListWithKeyRel: FromHListWithKeyRel[L, R]): fromHListWithKeyRel.Out = fromHListWithKeyRel(l)
}