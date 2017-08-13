package typemaps
package interop

import _root_.shapeless._

import scala.language.implicitConversions

package object shapeless {
  implicit def typeMapShapelessSyntax[M <: TypeMap](m: M): TypeMapShapelessSyntax[M] = new TypeMapShapelessSyntax[M](m)
  implicit def hlistSyntax[L <: HList](l: L): HListSyntax[L] = new HListSyntax[L](l)
}
