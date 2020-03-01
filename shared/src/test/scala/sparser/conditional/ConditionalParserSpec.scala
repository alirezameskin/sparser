package sparser.conditional

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers._

class ConditionalParserSpec extends AnyFunSuite {

  test("Equal condition") {
    val token    = ConditionalParser("status:200")
    val expected = Equal(Variable("status"), 200d)

    token shouldBe Right(expected)
  }

  test("Between condition") {
    val token    = ConditionalParser("status: [200 to 299]")
    val expected = Between(Variable("status"), 200d, 299d)

    token shouldBe Right(expected)
  }

  test("Greater than condition") {
    val token    = ConditionalParser("status > 200")
    val expected = GreaterThan(Variable("status"), 200d)

    token shouldBe Right(expected)
  }

  test("Greater equal condition") {
    val token    = ConditionalParser("status >= 200")
    val expected = GreaterEqual(Variable("status"), 200d)

    token shouldBe Right(expected)
  }

  test("Less than condition") {
    val token    = ConditionalParser("status < 200")
    val expected = LessThan(Variable("status"), 200d)

    token shouldBe Right(expected)
  }

  test("Less equal condition") {
    val token    = ConditionalParser("status <= 200")
    val expected = LessEqual(Variable("status"), 200d)

    token shouldBe Right(expected)
  }

  test("one AND condition") {
    val token    = ConditionalParser("status <= 200 AND count:[0 TO 100]")
    val expected = And(LessEqual(Variable("status"), 200d), Between(Variable("count"), 0d, 100d))

    token shouldBe Right(expected)
  }

  test("two AND conditions") {
    val token = ConditionalParser("status <= 200 AND count:[0 TO 100] AND size:200")
    val expected = And(
      And(
        LessEqual(Variable("status"), 200d),
        Between(Variable("count"), 0d, 100d)
      ),
      Equal(Variable("size"), 200d)
    )

    token shouldBe Right(expected)
  }

  test("one OR condition") {
    val token    = ConditionalParser("status <= 200 OR count:[0 TO 100]")
    val expected = Or(LessEqual(Variable("status"), 200d), Between(Variable("count"), 0d, 100d))

    token shouldBe Right(expected)
  }

  test("two OR conditions") {
    val token = ConditionalParser("status <= 200 OR count:[0 TO 100] OR size:200")
    val expected = Or(
      Or(
        LessEqual(Variable("status"), 200d),
        Between(Variable("count"), 0d, 100d)
      ),
      Equal(Variable("size"), 200d)
    )

    token shouldBe Right(expected)
  }

  test("And and OR conditions together") {
    val token = ConditionalParser("status <= 200 AND count:[0 TO 100] OR size:200")
    val expected = Or(
      And(
        LessEqual(Variable("status"), 200d),
        Between(Variable("count"), 0d, 100d)
      ),
      Equal(Variable("size"), 200d)
    )

    token shouldBe Right(expected)
  }
}
