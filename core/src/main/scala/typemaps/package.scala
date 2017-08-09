import scala.language.implicitConversions

package object typemaps {
  implicit def typeMapSyntax[M <: TypeMap](m: M): TypeMapSyntax[M] = new TypeMapSyntax[M](m)
}
