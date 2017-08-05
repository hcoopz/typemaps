package typemaps

import scala.annotation.{implicitAmbiguous, implicitNotFound}

@implicitNotFound("Can lookup ${K} in ${M}")
private[this] sealed trait CannotLookupKey[M <: TypeMap, K]

private[this] object CannotLookupKey {
  implicit def succeed[M <: TypeMap, K]: CannotLookupKey[M, K] = new CannotLookupKey[M, K] {}

  @implicitAmbiguous("Can lookup the key in the map")
  implicit def fail1[M <: TypeMap, K](implicit insert: Lookup[M, K]): CannotLookupKey[M, K] = new CannotLookupKey[M, K] {}
  implicit def fail2[M <: TypeMap, K](implicit insert: Lookup[M, K]): CannotLookupKey[M, K] = new CannotLookupKey[M, K] {}

  class Fn[K] {
    def apply[M <: TypeMap](m: M)(implicit ev: CannotLookupKey[M, K]): Unit = {}
  }

  def apply[K]: Fn[K] = new Fn
}
