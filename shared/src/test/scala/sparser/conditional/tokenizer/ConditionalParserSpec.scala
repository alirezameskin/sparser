package sparser.conditional.tokenizer

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers._

class ConditionalParserSpec extends AnyFunSuite {

  test("Equal condition") {
    val token    = ConditionalTokenizer("status:200")
    val expected = List(TextToken("status"), ColonToken, NumberToken(200d))

    token shouldBe Right(expected)
  }

  test("Between condition") {
    val token = ConditionalTokenizer("status:[200 TO 299]")
    val expected =
      List(
        TextToken("status"),
        ColonToken,
        LeftBracketToken,
        NumberToken(200),
        ToToken,
        NumberToken(299),
        RightBracketToken
      )

    token shouldBe Right(expected)
  }
}
