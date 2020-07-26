package sparser.template.tokenizer

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers._

class TokenizerSpec extends AnyFunSuite {

  test("UserInfo- Name: {{name}}, LastName: {{last_name}} should be tokenized") {
    val tokens = TemplateTokenizer("UserInfo- Name: {{name}}, LastName: {{last_name}}")
    val expected = List(
      PlainText("UserInfo- Name: "),
      Expression("name"),
      PlainText(", LastName: "),
      Expression("last_name")
    )
    tokens shouldBe Right(expected)
  }

  test("It should detect two following expressions") {
    val tokens = TemplateTokenizer("{{expr1}}{{expr2}}{{expr3}}-{{expr4}}")
    val expected = List(
      Expression("expr1"),
      Expression("expr2"),
      Expression("expr3"),
      PlainText("-"),
      Expression("expr4")
    )

    tokens shouldBe Right(expected)
  }

  test("It should detect a fully plain text as one token") {
    val text     = "It is a plain text and we expect to see one token in the result"
    val tokens   = TemplateTokenizer(text)
    val expected = List(PlainText(text))

    tokens shouldBe Right(expected)
  }

  test("It should detect one expression only string") {
    val tokens   = TemplateTokenizer("{{expr1}}")
    val expected = List(Expression("expr1"))

    tokens shouldBe Right(expected)
  }

  test("It should parse multi line strings") {
    val text =
      """
        |test long text
        |for testing multi line
        |this text contains only one {{placeholder}} which should be detected.
        |Is it detected?
        |""".stripMargin
    val tokens = TemplateTokenizer(text)
    val expected = List(
      PlainText("""
                  |test long text
                  |for testing multi line
                  |this text contains only one """.stripMargin),
      Expression("placeholder"),
      PlainText(""" which should be detected.
                  |Is it detected?
                  |""".stripMargin)
    )
    tokens shouldBe Right(expected)
  }

  test("It should detect expression with pipe") {
    val tokens   = TemplateTokenizer("Test string {{ name | upper }}")
    val expected = List(PlainText("Test string "), Expression("name | upper"))
    tokens shouldBe Right(expected)
  }

  test("Templates with space separator") {
    val tokens   = TemplateTokenizer("{{ firstName }}  {{ lastName}}")
    val expected = List(Expression("firstName"), PlainText("  "), Expression("lastName"))

    tokens shouldBe Right(expected)
  }
}
