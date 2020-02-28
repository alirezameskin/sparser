package sparser.template.parser

import sparser.template.BaseRegexParser
import sparser.template.tokenizer._
import sparser.util.sequence

object TemplateExpressionParser extends BaseRegexParser {

  def variable: Parser[Variable] =
    ident ~ rep(ident | ".") ^^ (n => Variable(n._1 + n._2.mkString("")))

  def function: Parser[FunctionCall] =
    ident ^^ FunctionCall

  def expression: Parser[TemplateAST] =
    variable ~ rep("|" ~ function) ^^ {
      case v ~ op =>
        op.foldLeft[TemplateAST](v) {
          case (d1, _ ~ op) => Operation(op.func, d1)
        }
    }

  def program: Parser[TemplateAST] =
    phrase(expression)

  def apply(tokens: Seq[TemplateToken]): Either[String, List[TemplateAST]] = {
    val parts = tokens.map {
      case Expression(expr) =>
        parse(program, expr) match {
          case Success(result, _) => Right(result)
          case NoSuccess(msg, _)  => Left(s"Error: $msg")
        }
      case PlainText(v) => Right(Text(v))
    }.toList

    sequence(parts)
  }
}
