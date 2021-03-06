package sparser.template.tokenizer

import sparser.util.BaseRegexParser

object TemplateTokenizer extends BaseRegexParser {
  override def skipWhitespace = false

  val expressionRegex = """\{\{[a-z_A-Z0-9\|\s\.]+\}\}""".r

  def plainText: Parser[PlainText] =
    notMatch(expressionRegex) ^^ PlainText

  def expression: Parser[Expression] =
    expressionRegex ^^ { s =>
      Expression(
        s.substring(2).dropRight(2).trim
      )
    }

  def tokens: Parser[List[TemplateToken]] =
    rep1(expression | plainText)

  def apply(str: String): Either[String, List[TemplateToken]] =
    parse(tokens, str) match {
      case Success(result, _) => Right(result)
      case NoSuccess(msg, _)  => Left(msg)
    }

}
