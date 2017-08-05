package typemaps

import org.scalatest.Matchers
import shapeless._

sealed trait CheckTypeMaps[L <: HList] {
  def apply(l: L): Unit
}

object CheckTypeMaps extends Matchers with TypeMapMatchers {
  def apply[L <: HList](l: L)(implicit check: CheckTypeMaps[L]): Unit = check(l)

  implicit val hnil: CheckTypeMaps[HNil] = new CheckTypeMaps[HNil] {
    override def apply(l: HNil): Unit = {}
  }

  implicit def hcons[H <: TypeMap, T <: HList](implicit tail: CheckTypeMaps[T]): CheckTypeMaps[H :: T] =
    new CheckTypeMaps[H :: T] {
      override def apply(l: H :: T): Unit = {
        tail(l.tail)
        l.head shouldBe aBalancedTypeMap
        l.head should satisfyTheTypeMapConstraint
      }
    }
}

