package sparser.conditional.tokenizer

import scala.util.parsing.combinator.JavaTokenParsers

object ConditionalTokenizer extends JavaTokenParsers {

  def doubleQuote: Parser[Token]      = "\"" ^^ (_ => DoubleQuoteToken)
  def dot: Parser[Token]              = "." ^^ (_ => DotToken)
  def colon: Parser[Token]            = ":" ^^ (_ => ColonToken)
  def leftParenthesis: Parser[Token]  = "(" ^^ (_ => LeftParenthesisToken)
  def rightParenthesis: Parser[Token] = ")" ^^ (_ => RightParenthesisToken)
  def leftBracket: Parser[Token]      = "[" ^^ (_ => LeftBracketToken)
  def rightBracket: Parser[Token]     = "]" ^^ (_ => RightBracketToken)
  def greaterThan: Parser[Token]      = ">" ^^ (_ => GreaterThanToken)
  def greaterEqual: Parser[Token]     = ">=" ^^ (_ => GreaterEqualToken)
  def lessThan: Parser[Token]         = "<" ^^ (_ => LowerThanToken)
  def lessEqual: Parser[Token]        = "<=" ^^ (_ => LowerEqualToken)
  def and: Parser[Token]              = "AND" ^^ (_ => AndToken)
  def or: Parser[Token]               = "OR" ^^ (_ => OrToken)
  def not: Parser[Token]              = "NOT" ^^ (_ => NotToken)
  def to: Parser[Token]               = "TO" ^^ (_ => ToToken)
  def number: Parser[NumberToken]     = floatingPointNumber ^^ (n => NumberToken(n.toDouble))
  def text: Parser[Token]             = ident ^^ (s => TextToken(s))

  def tokens: Parser[List[Token]] =
    phrase(
      rep1(
        and | or | not | to |
          greaterEqual | lessEqual | greaterThan | lessThan |
          dot | colon | doubleQuote |
          leftParenthesis | rightParenthesis | leftBracket | rightBracket |
          number | text
      )
    )

  def apply(str: String): Either[String, List[Token]] =
    parse(tokens, str) match {
      case Success(result, _) => Right(result)
      case NoSuccess(msg, _)  => Left(msg)
    }
}
