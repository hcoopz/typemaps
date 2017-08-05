package typemaps

import shapeless.HList._
import shapeless._

trait Types {
  // It doesn't really matter what these are, we merely want a list of distinct types and values to go with them
  protected type Types =
    Unit :: Boolean :: Short :: Int ::
      Long :: Float :: Double :: String ::
      Option[Unit] :: Option[Boolean] :: Option[Short] :: Option[Int] ::
      Option[Long] :: Option[Float] :: Option[Double] ::
      HNil

  // Some of the tests take a very long time to compile when using 15 types, but behave more reasonably with 6 types
  protected type TypesShort =
    Unit :: Boolean :: Short ::
      Long :: Float :: Double ::
      HNil

  protected type ValuesShort =
    Int :: Char :: String ::
      Option[Int] :: Unit :: Char ::
      HNil

  protected val values =
    1 :: 'a' :: "c" :: 3.0 ::
      Some(4) :: () :: 'b' :: List(42) ::
      false :: Set(true) :: Map('b -> 37) :: 2L ::
      Some('c) :: "foo" :: "bar" ::
      HNil

  protected val valuesShort =
    1 :: 'a' :: "c" ::
      Some(4) :: () :: 'b' ::
      HNil
}
