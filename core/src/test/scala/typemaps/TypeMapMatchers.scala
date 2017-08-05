package typemaps

import TypeMap._
import org.scalatest.matchers.{BeMatcher, MatchResult, Matcher}

trait TypeMapMatchers {
  class BalancedMatcher extends BeMatcher[TypeMap] {
    override def apply(left: TypeMap): MatchResult = {
      def heights(node: TypeMap, depth: Int): Set[Int] = node match {
        case Tip => Set(depth)
        case Bin(_, l, r) => heights(l, depth + 1) ++ heights(r, depth + 1)
      }

      val balanced = heights(left, 0).toList.sorted match {
        case List(_) => true
        case List(a, b) if b == a + 1 => true
        case _ => false
      }

      MatchResult(balanced, s"$left was not balanced", s"$left was balanced")
    }
  }
  val aBalancedTypeMap = new BalancedMatcher

  class SatisfyConstraintMatcher extends Matcher[TypeMap] {
    override def apply(left: TypeMap): MatchResult = {
      def check(node: TypeMap): Option[Int] = node match {
        case Tip => Some(0)
        case Bin(_, l, r) =>
          check(l).flatMap { sizeL =>
            check(r).flatMap { sizeR =>
              if (sizeL <= sizeR && sizeR <= sizeL + 1) {
                Some(sizeL + sizeR + 1)
              } else {
                None
              }
            }
          }
      }

      val satisfiesConstraint = check(left).isDefined

      MatchResult(satisfiesConstraint, s"$left does not satisfy the constraint that size(LHS) <= size(RHS) <= size(LHS) + 1 at all nodes", s"$left satisfies the constraint that size(LHS) <= size(RHS) <= size(LHS) + 1 at all nodes")
    }
  }
  val satisfyTheTypeMapConstraint = new SatisfyConstraintMatcher
}