package sparser.conditional

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers._

class ConditionalEvaluatorSpec extends AnyFunSuite {

  test("Equal condition") {
    evaluate("status:200", Map("status" -> 200)) shouldBe Right(true)
  }

  test("Between condition") {
    evaluate("status:[200 TO 299]", Map("status" -> 200)) shouldBe Right(true)
  }

  test("Greater than condition") {
    evaluate("status > 200", Map("status" -> 200)) shouldBe Right(false)
  }

  test("one AND condition") {
    val vars = Map("status" -> 200, "count" -> 50)
    evaluate("status <= 200 AND count:[0 TO 100]", vars) shouldBe Right(true)
  }

  test("two AND conditions") {
    val vars1 = Map("status" -> 200, "count" -> 50, "size" -> 200)
    evaluate("status <= 200 AND count:[0 TO 100] AND size:200", vars1) shouldBe Right(true)

    val vars2 = Map("status" -> 200, "count" -> 50, "size" -> 201)
    evaluate("status <= 200 AND count:[0 TO 100] AND size:200", vars2) shouldBe Right(false)
  }

  test("one OR condition") {
    val vars1 = Map("status" -> 200, "count" -> 120)
    evaluate("status <= 200 OR count:[0 TO 100]", vars1) shouldBe Right(true)

    val vars2 = Map("status" -> 201, "count" -> 120)
    evaluate("status <= 200 OR count:[0 TO 100]", vars2) shouldBe Right(false)
  }

  test("two OR conditions") {
    val vars1 = Map("status" -> 201d, "count" -> 120d, "size" -> 200d)
    evaluate("status <= 200 OR count:[0 TO 100] OR size:200", vars1) shouldBe Right(true)

    val vars2 = Map("status" -> 201, "count" -> 120, "size" -> 201)
    evaluate("status <= 200 OR count:[0 TO 100] OR size:200", vars2) shouldBe Right(false)

    val vars3 = Map("status" -> 201, "count" -> 120)
    evaluate("status <= 200 OR count:[0 TO 100] OR size:200", vars3) shouldBe Left("Invalid Variable size")
  }

  test("And and OR conditions inside parenthesis") {
    val vars1 = Map("status" -> 200, "count" -> 120, "size" -> 200)
    evaluate("status <= 200 AND (count:[0 TO 100] OR size:200)", vars1) shouldBe Right(true)

    val vars2 = Map("status" -> 200, "count" -> 120, "size" -> 201)
    evaluate("status <= 200 AND (count:[0 TO 100] OR size:200)", vars2) shouldBe Right(false)
  }

  test("Quoted strings") {
    val vars = Map("level" -> "error message")
    evaluate("""level:"error message" """, vars) shouldBe Right(true)

    val vars1 = Map("level" -> "error message1")
    evaluate("""level:"error message" """, vars1) shouldBe Right(false)
  }

  test("Not condition") {
    val var1 = Map("level" -> "Info")
    evaluate("""NOT level:"Info" """, var1) shouldBe Right(false)

    val var2 = Map("level" -> "Error")
    evaluate("""NOT level:"Info" """, var2) shouldBe Right(true)
  }
}
