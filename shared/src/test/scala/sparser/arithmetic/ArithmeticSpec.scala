package sparser.arithmetic

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers._

class ArithmeticSpec extends AnyFunSuite {
  test("(2 + 4) should return 6") {
    evaluate("2 + 4") shouldBe Right(6)
  }

  test("(rount(2.1) + ceil(2.4) should be 5.0") {
    evaluate("round(2.1) + ceil(2.4)") shouldBe Right(5.0d)
  }

  test("(23.4 + ${num} + sqrt(12) + round(2 + round(3.5))) should be Right(4)") {
    evaluate("23 + ${num} + sqrt(9) + round(2 + round(3.5))", Map("num" -> 10d)) shouldBe Right(42.0d)
  }

  test("rnd(4.5) should fail") {
    evaluate("rnd(450)").isLeft shouldBe (true)
  }
}
