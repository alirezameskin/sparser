package sparser.template.parser

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers._
import sparser.template.FunctionCall
import sparser.template.tokenizer.{Expression, PlainText}

class TemplateParserSpec extends AnyFunSuite {
  test("It should detect AST from Tokens") {
    val expr     = List(PlainText("Text1"), PlainText("Text2"), Expression("var1"), Expression("var_name"))
    val tokens   = TemplateExpressionParser(expr)
    val expected = List(Text("Text1"), Text("Text2"), Variable("var1"), Variable("var_name"))

    tokens shouldBe Right(expected)
  }

  test("Test with pipe") {
    val expr   = List(PlainText("Name"), Expression("name | upper | lower"))
    val tokens = TemplateExpressionParser(expr)
    val expected =
      List(Text("Name"), Operation(FunctionCall("lower"), Operation(FunctionCall("upper"), Variable("name"))))

    tokens shouldBe Right(expected)
  }
}
