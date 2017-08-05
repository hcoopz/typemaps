package typemaps

import typemaps.TypeMap._

import scala.annotation.implicitNotFound

@implicitNotFound("Can insert one of the keys in {M} again")
private[this] sealed trait CannotInsertExistingKeys[M]

private[this] object CannotInsertExistingKeys {
  def apply[M <: TypeMap](implicit evidence: CannotInsertExistingKeys[M]): Unit = {}

  private[this] sealed trait Helper[M <: TypeMap, N <: TypeMap]
  private[this] object Helper {
    private[this] class Instance[M <: TypeMap, N <: TypeMap] extends Helper[M, N]

    implicit def tip[M <: TypeMap]: Helper[M, Tip] = new Instance

    implicit def bin[M <: TypeMap, K, A, L <: TypeMap, R <: TypeMap](implicit l: Helper[M, L],
                                                                     r: Helper[M, R],
                                                                     evidence: CannotInsertKey[M, K]
                                                                    ): Helper[M, Bin[K, A, L, R]] =
      new Instance
  }

  implicit def m[M <: TypeMap](implicit helper: Helper[M, M]): CannotInsertExistingKeys[M] = new CannotInsertExistingKeys[M] {}
}
