package sparser.template

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers._

class TemplateSpec extends AnyFunSuite {
  test("Simple expression should work") {
    val result = evaluate("This is a simple text with one {{ placeholder }}", Map("placeholder" -> "value1"))
    result shouldBe Right("This is a simple text with one value1")
  }

  test("Simple expression with one function") {
    val result = evaluate("Result {{ placeholder | upper }}", Map("placeholder" -> "value1"))
    result shouldBe Right("Result VALUE1")
  }

  test("Simple expression with two functions") {
    val result = evaluate("Result {{ placeholder | upper | lower}}", Map("placeholder" -> "valUe1"))
    result shouldBe Right("Result value1")
  }
}
