package typemaps

import org.scalatest.{FlatSpec, Matchers}
import shapeless.HList._
import shapeless._

class UpdateSpec extends FlatSpec with Matchers with TypeMapMatchers with Types {
  "Updating a value in a TypeMap to the same type" should "update the value at the key" in {
    val m0 = TypeMap.singletonSet(3).insertSet('a').insertSet("foo").insertSet(false).insert[Unit]('bar)

    val m1 = m0.update[Int].apply(_ + 1)
    m1 shouldBe aBalancedTypeMap
    m1 should satisfyTheTypeMapConstraint
    LookupAllValues[Int :: Char :: String :: Boolean :: Unit :: HNil](m1, 4 :: 'a' :: "foo" :: false :: 'bar :: HNil)

    val m2 = m0.update[Char].apply(_ => 'b')
    m2 shouldBe aBalancedTypeMap
    m2 should satisfyTheTypeMapConstraint
    LookupAllValues[Int :: Char :: String :: Boolean :: Unit :: HNil](m2, 3 :: 'b' :: "foo" :: false :: 'bar :: HNil)

    val m3 = m0.update[String].apply(_ + "d")
    m3 shouldBe aBalancedTypeMap
    m3 should satisfyTheTypeMapConstraint
    LookupAllValues[Int :: Char :: String :: Boolean :: Unit :: HNil](m3, 3 :: 'a' :: "food" :: false :: 'bar :: HNil)

    val m4 = m0.update[Boolean].apply(x => !x)
    m4 shouldBe aBalancedTypeMap
    m4 should satisfyTheTypeMapConstraint
    LookupAllValues[Int :: Char :: String :: Boolean :: Unit :: HNil](m4, 3 :: 'a' :: "foo" :: true :: 'bar :: HNil)

    val m5 = m0.update[Unit].apply(_ => 'baz)
    m5 shouldBe aBalancedTypeMap
    m5 should satisfyTheTypeMapConstraint
    LookupAllValues[Int :: Char :: String :: Boolean :: Unit :: HNil](m5, 3 :: 'a' :: "foo" :: false :: 'baz :: HNil)
  }

  "Updating a value in a TypeMap to a different type" should "update the value at the key" in {
    val m0 = TypeMap.singletonSet(3).insertSet('a').insertSet("foo").insertSet(false).insert[Unit]('bar)

    val m1 = m0.update[Int].apply(_ => "foo")
    m1 shouldBe aBalancedTypeMap
    m1 should satisfyTheTypeMapConstraint
    LookupAllValues[Int :: Char :: String :: Boolean :: Unit :: HNil](m1, "foo" :: 'a' :: "foo" :: false :: 'bar :: HNil)

    val m2 = m0.update[Char].apply(_ => "bar")
    m2 shouldBe aBalancedTypeMap
    m2 should satisfyTheTypeMapConstraint
    LookupAllValues[Int :: Char :: String :: Boolean :: Unit :: HNil](m2, 3 :: "bar" :: "foo" :: false :: 'bar :: HNil)

    val m3 = m0.update[String].apply(_ => 4)
    m3 shouldBe aBalancedTypeMap
    m3 should satisfyTheTypeMapConstraint
    LookupAllValues[Int :: Char :: String :: Boolean :: Unit :: HNil](m3, 3 :: 'a' :: 4 :: false :: 'bar :: HNil)

    val m4 = m0.update[Boolean].apply(_ => "baz")
    m4 shouldBe aBalancedTypeMap
    m4 should satisfyTheTypeMapConstraint
    LookupAllValues[Int :: Char :: String :: Boolean :: Unit :: HNil](m4, 3 :: 'a' :: "foo" :: "baz" :: 'bar :: HNil)

    val m5 = m0.update[Unit].apply(_ => "quux")
    m5 shouldBe aBalancedTypeMap
    m5 should satisfyTheTypeMapConstraint
    LookupAllValues[Int :: Char :: String :: Boolean :: Unit :: HNil](m5, 3 :: 'a' :: "foo" :: false :: "quux" :: HNil)

    val typeMap = BuildTypeMaps[Types](values).head
    val updated = UpdateValues[Types](typeMap)
    CheckTypeMaps(updated)
    UpdatedValuesList[Types](updated, values)
  }

  "Updating a key and value in a TypeMap" should "update the key and value" in {
    val m0 = TypeMap.singletonSet(3).insertSet('a').insertSet("foo").insertSet(false).insert[Unit]('bar)

    val m1 = m0.updateWithKey[Int, Float].apply(_ => "foo")
    m1 shouldBe aBalancedTypeMap
    m1 should satisfyTheTypeMapConstraint
    LookupAllValues[Float :: Char :: String :: Boolean :: Unit :: HNil](m1, "foo" :: 'a' :: "foo" :: false :: 'bar :: HNil)

    val m2 = m0.updateWithKey[Char, Float].apply(_ => "bar")
    m2 shouldBe aBalancedTypeMap
    m2 should satisfyTheTypeMapConstraint
    LookupAllValues[Int :: Float :: String :: Boolean :: Unit :: HNil](m2, 3 :: "bar" :: "foo" :: false :: 'bar :: HNil)

    val m3 = m0.updateWithKey[String, Float].apply(_ => 4)
    m3 shouldBe aBalancedTypeMap
    m3 should satisfyTheTypeMapConstraint
    LookupAllValues[Int :: Char :: Float :: Boolean :: Unit :: HNil](m3, 3 :: 'a' :: 4 :: false :: 'bar :: HNil)

    val m4 = m0.updateWithKey[Boolean, Float].apply(_ => "baz")
    m4 shouldBe aBalancedTypeMap
    m4 should satisfyTheTypeMapConstraint
    LookupAllValues[Int :: Char :: String :: Float :: Unit :: HNil](m4, 3 :: 'a' :: "foo" :: "baz" :: 'bar :: HNil)

    val m5 = m0.updateWithKey[Unit, Float].apply(_ => "quux")
    m5 shouldBe aBalancedTypeMap
    m5 should satisfyTheTypeMapConstraint
    LookupAllValues[Int :: Char :: String :: Boolean :: Float :: HNil](m5, 3 :: 'a' :: "foo" :: false :: "quux" :: HNil)
  }

  "Updating a key in a TypeMap" should "refuse to update a key to another key in the map" in {
    val m0 = TypeMap.singletonSet(3).insertSet('a').insertSet("foo").insertSet(false).insert[Unit]('bar)

    CannotUpdateKey[Int, Char](m0)
    CannotUpdateKey[Int, String](m0)
    CannotUpdateKey[Int, Boolean](m0)
    CannotUpdateKey[Int, Unit](m0)

    CannotUpdateKey[Char, Int](m0)
    CannotUpdateKey[Char, String](m0)
    CannotUpdateKey[Char, Boolean](m0)
    CannotUpdateKey[Char, Unit](m0)

    CannotUpdateKey[String, Int](m0)
    CannotUpdateKey[String, Char](m0)
    CannotUpdateKey[String, Boolean](m0)
    CannotUpdateKey[String, Unit](m0)

    CannotUpdateKey[Boolean, Int](m0)
    CannotUpdateKey[Boolean, Char](m0)
    CannotUpdateKey[Boolean, String](m0)
    CannotUpdateKey[Boolean, Unit](m0)

    CannotUpdateKey[Unit, Int](m0)
    CannotUpdateKey[Unit, Char](m0)
    CannotUpdateKey[Unit, String](m0)
    CannotUpdateKey[Unit, Boolean](m0)
  }
}
