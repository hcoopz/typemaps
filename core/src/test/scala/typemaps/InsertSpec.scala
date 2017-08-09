package typemaps

import org.scalatest.{FlatSpec, Matchers}
import shapeless.HList._
import shapeless._

class InsertSpec extends FlatSpec with Matchers with TypeMapMatchers with Types {
  "Inserting elements into a TypeMap" should "construct a balanced, constraint-satisfying TypeMap containing the types and values" in {
    val m1 = TypeMap.singletonSet(3)
    m1 shouldBe aBalancedTypeMap
    m1 should satisfyTheTypeMapConstraint

    val m2 = m1.insertSet('a')
    m2 shouldBe aBalancedTypeMap
    m2 should satisfyTheTypeMapConstraint

    val m3 = m2.insertSet("foo")
    m3 shouldBe aBalancedTypeMap
    m3 should satisfyTheTypeMapConstraint

    val m4 = m3.insertSet(false)
    m4 shouldBe aBalancedTypeMap
    m4 should satisfyTheTypeMapConstraint

    val m5 = m4.insertSet(())
    m5 shouldBe aBalancedTypeMap
    m5 should satisfyTheTypeMapConstraint

    val typeMaps = BuildTypeMaps[Types](values)
    CheckTypeMaps(typeMaps)
  }

  it should "yield a TypeMap containing the inserted values" in {
    val m1 = TypeMap.singletonSet(3)
    m1.lookup[Int] shouldEqual 3

    val m2 = m1.insertSet('a')
    m2.lookup[Int] shouldEqual 3
    m2.lookup[Char] shouldEqual 'a'

    val m3 = m2.insertSet("foo")
    m3.lookup[Int] shouldEqual 3
    m3.lookup[Char] shouldEqual 'a'
    m3.lookup[String] shouldEqual "foo"

    val m4 = m3.insertSet(false)
    m4.lookup[Int] shouldEqual 3
    m4.lookup[Char] shouldEqual 'a'
    m4.lookup[String] shouldEqual "foo"
    m4.lookup[Boolean] shouldEqual false

    val m5 = m4.insert[Unit]('bar)
    m5.lookup[Int] shouldEqual 3
    m5.lookup[Char] shouldEqual 'a'
    m5.lookup[String] shouldEqual "foo"
    m5.lookup[Boolean] shouldEqual false
    m5.lookup[Unit] shouldEqual 'bar

    val typeMap = BuildTypeMaps[Types](values).head
    LookupAllValues[Types](typeMap, values)

    val typeMaps = BuildTypeMaps[TypesShort](valuesShort)
    LookupAllValuesList[TypesShort](typeMaps, valuesShort)
  }

  "Attempting to insert a value into a TypeMap with a key that already exists" should "fail to compile" in {
    val m1 = TypeMap.singletonSet(3)
    CannotInsertKey[Int](m1)

    val m2 = m1.insertSet('a')
    CannotInsertKey[Int](m2)
    CannotInsertKey[Char](m2)

    val m3 = m2.insertSet("foo")
    CannotInsertKey[Int](m3)
    CannotInsertKey[Char](m3)
    CannotInsertKey[String](m3)

    val m4 = m3.insertSet(false)
    CannotInsertKey[Int](m4)
    CannotInsertKey[Char](m4)
    CannotInsertKey[String](m4)
    CannotInsertKey[Boolean](m4)

    val m5 = m4.insertSet(())
    CannotInsertKey[Int](m5)
    CannotInsertKey[Char](m5)
    CannotInsertKey[String](m5)
    CannotInsertKey[Boolean](m5)
    CannotInsertKey[Unit](m5)

    val typeMap = BuildTypeMaps[Types](values).head
    CannotInsertKeys[Types](typeMap)

    val typeMaps = BuildTypeMaps[TypesShort](valuesShort)
    CannotInsertKeysList[TypesShort](typeMaps)
  }
}