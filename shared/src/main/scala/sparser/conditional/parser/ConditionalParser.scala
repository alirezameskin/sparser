package sparser.conditional.parser

import sparser.conditional.tokenizer._

import scala.util.parsing.combinator.Parsers
import scala.util.parsing.input.{NoPosition, Position, Reader}

object ConditionalParser extends Parsers {
  override type Elem = Token

  class ConditionTokenReader(tokens: Seq[Token]) extends Reader[Token] {
    override def first: Token        = tokens.head
    override def atEnd: Boolean      = tokens.isEmpty
    override def pos: Position       = NoPosition
    override def rest: Reader[Token] = new ConditionTokenReader(tokens.tail)
  }

  def number: Parser[Double] =
    accept("number", {
      case n: NumberToken => n.value
    })

  def text: Parser[String] =
    accept("string", {
      case s: TextToken => s.value
    })

  def quotedString: Parser[String] =
    DoubleQuoteToken ~> rep(text) <~ DoubleQuoteToken ^^ (s => s.mkString(" "))

  def variable: Parser[ConditionParam] =
    text ~ rep(text | DotToken) ^^ (n => Variable(n._1 + n._2.mkString("")))

  def equal: Parser[Equal] =
    variable ~ ColonToken ~ (number | quotedString | text) ^^ { case v ~ _ ~ d => Equal(v, d) }

  def between: Parser[Between] =
    variable ~ ColonToken ~ LeftBracketToken ~ number ~ ToToken ~ number ~ RightBracketToken ^^ {
      case v ~ _ ~ _ ~ l ~ _ ~ h ~ _ => Between(v, l, h)
    }

  def greaterThan: Parser[GreaterThan] =
    variable ~ GreaterThanToken ~ number ^^ { case v ~ _ ~ d => GreaterThan(v, d) }

  def greaterEqual: Parser[GreaterEqual] =
    variable ~ GreaterEqualToken ~ number ^^ { case v ~ _ ~ d => GreaterEqual(v, d) }

  def lessThan: Parser[LessThan] =
    variable ~ LowerThanToken ~ number ^^ { case v ~ _ ~ d => LessThan(v, d) }

  def lessEqual: Parser[LessEqual] =
    variable ~ LowerEqualToken ~ number ^^ { case v ~ _ ~ d => LessEqual(v, d) }

  def andOr: Parser[ConditionExpr] =
    expression ~ rep((AndToken | OrToken) ~ expression) ^^ {
      case x ~ ls =>
        ls.foldLeft[ConditionExpr](x) {
          case (e1, AndToken ~ e2) => And(e1, e2)
          case (e1, OrToken ~ e2)  => Or(e1, e2)
        }
    }

  def parenthesis: Parser[ConditionExpr] =
    LeftParenthesisToken ~> andOr <~ RightParenthesisToken

  def not: Parser[Not] =
    NotToken ~> expression ^^ Not

  def expression: Parser[ConditionExpr] =
    parenthesis | not | between | lessEqual | lessThan | greaterEqual | greaterThan | equal

  def program: Parser[ConditionAst] =
    phrase(andOr | expression)

  def apply(tokens: List[Token]): Either[String, ConditionAst] = {
    val reader = new ConditionTokenReader(tokens)

    program(reader) match {
      case Success(result, _) => Right(result)
      case NoSuccess(msg, _)  => Left(msg)
    }
  }
}
