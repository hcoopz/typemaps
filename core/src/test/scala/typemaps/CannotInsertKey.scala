package typemaps

import scala.annotation.{implicitAmbiguous, implicitNotFound}

@implicitNotFound("Can insert ${K} into ${M}")
private[this] sealed trait CannotInsertKey[M <: TypeMap, K]

private[this] object CannotInsertKey {
  implicit def succeed[M <: TypeMap, K]: CannotInsertKey[M, K] = new CannotInsertKey[M, K] {}

  @implicitAmbiguous("Can insert the key into the map")
  implicit def fail1[M <: TypeMap, K](implicit insert: Insert[M, K, Unit]): CannotInsertKey[M, K] = new CannotInsertKey[M, K] {}
  implicit def fail2[M <: TypeMap, K](implicit insert: Insert[M, K, Unit]): CannotInsertKey[M, K] = new CannotInsertKey[M, K] {}

  class Fn[K] {
    def apply[M <: TypeMap](m: M)(implicit ev: CannotInsertKey[M, K]): Unit = {}
  }

  def apply[K]: Fn[K] = new Fn
}

