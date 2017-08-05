package typemaps

import scala.annotation.{implicitAmbiguous, implicitNotFound}

@implicitNotFound("Can update ${KA} to ${KB} in ${M}")
private[this] sealed trait CannotUpdateKey[M <: TypeMap, KA, KB]

private[this] object CannotUpdateKey {
  implicit def succeed[M <: TypeMap, KA, KB]: CannotUpdateKey[M, KA, KB] = new CannotUpdateKey[M, KA, KB] {}

  @implicitAmbiguous("Can update the key in the map")
  implicit def fail1[M <: TypeMap, KA, KB, A](implicit lookup: Lookup.Aux[M, KA, A],
                                              update: Update[M, KA, A, KB, Unit]): CannotUpdateKey[M, KA, KB] = new CannotUpdateKey[M, KA, KB] {}
  implicit def fail2[M <: TypeMap, KA, KB, A](implicit lookup: Lookup.Aux[M, KA, A],
                                              update: Update[M, KA, A, KB, Unit]): CannotUpdateKey[M, KA, KB] = new CannotUpdateKey[M, KA, KB] {}

  class Fn[KA, KB] {
    def apply[M <: TypeMap](m: M)(implicit ev: CannotUpdateKey[M, KA, KB]): Unit = {}
  }

  def apply[KA, KB]: Fn[KA, KB] = new Fn
}
