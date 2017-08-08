package typemaps

import org.scalatest.{FlatSpec, Matchers}
import shapeless.HList._
import shapeless._
import typemaps.Syntax._

class RemoveSpec extends FlatSpec with Matchers with TypeMapMatchers with Types {
  "Removing an element from a TypeMap" should "return a balanced, constraint-satisfying TypeMap containing the remaining types and values but not the removed keys" in {
    val m0 = TypeMap.singletonSet(3).insertSet('a').insertSet("foo").insertSet(false).insert[Unit]('bar)

    val m1 = m0.remove[Int]
    m1 shouldBe aBalancedTypeMap
    m1 should satisfyTheTypeMapConstraint
    LookupAllValues[Char :: String :: Boolean :: Unit :: HNil](m1, 'a' :: "foo" :: false :: 'bar :: HNil)
    CannotLookupKey[Int](m1)

    val m2 = m0.remove[Char]
    m2 shouldBe aBalancedTypeMap
    m2 should satisfyTheTypeMapConstraint
    LookupAllValues[Int :: String :: Boolean :: Unit :: HNil](m2, 3 :: "foo" :: false :: 'bar :: HNil)
    CannotLookupKey[Char](m2)

    val m3 = m0.remove[String]
    m3 shouldBe aBalancedTypeMap
    m3 should satisfyTheTypeMapConstraint
    LookupAllValues[Int :: Char :: Boolean :: Unit :: HNil](m3, 3 :: 'a' :: false :: 'bar :: HNil)
    CannotLookupKey[String](m3)

    val m4 = m0.remove[Boolean]
    m4 shouldBe aBalancedTypeMap
    m4 should satisfyTheTypeMapConstraint
    LookupAllValues[Int :: Char :: String :: Unit :: HNil](m4, 3 :: 'a' :: "foo" :: 'bar :: HNil)
    CannotLookupKey[Boolean](m4)

    val m5 = m0.remove[Unit]
    m5 shouldBe aBalancedTypeMap
    m5 should satisfyTheTypeMapConstraint
    LookupAllValues[Int :: Char :: String :: Boolean :: HNil](m5, 3 :: 'a' :: "foo" :: false :: HNil)
    CannotLookupKey[Unit](m5)

    val typeMaps = BuildTypeMaps[TypesShort](valuesShort)
    val removed = RemoveKeys[TypesShort](typeMaps.head)
    CheckTypeMaps(removed)
    CannotLookupKeys[TypesShort](removed)
    LookupAllValuesExceptList[TypesShort, TypesShort](removed, valuesShort)

    val typeMapsAll = BuildTypeMaps[Types](values)
    val removedAll = RemoveKeysList[Types](typeMapsAll)
    CheckTypeMaps(removedAll)
  }
}
