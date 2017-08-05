package typemaps

sealed trait TypeMap

object TypeMap {
  final case object Tip extends TypeMap
  type Tip = Tip.type
  final case class Bin[KA, A, L <: TypeMap, R <: TypeMap] private[typemaps] (a: A, l: L, r: R) extends TypeMap

  def empty: Tip = Tip

  @inline def singletonSet[A](a: A): Bin[A, A, Tip, Tip] = Bin(a, Tip, Tip)
  @inline def singleton[K, A](a: A): Bin[K, A, Tip, Tip] = Bin(a, Tip, Tip)
}

