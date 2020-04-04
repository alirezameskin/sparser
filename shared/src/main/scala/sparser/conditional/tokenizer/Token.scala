package sparser.conditional.tokenizer

sealed trait Token
case class NumberToken(value: Double) extends Token
case class TextToken(value: String)   extends Token
case object DoubleQuoteToken          extends Token
case object DotToken                  extends Token
case object ColonToken                extends Token
case object LeftParenthesisToken      extends Token
case object RightParenthesisToken     extends Token
case object LeftBracketToken          extends Token
case object RightBracketToken         extends Token
case object GreaterThanToken          extends Token
case object GreaterEqualToken         extends Token
case object LowerThanToken            extends Token
case object LowerEqualToken           extends Token
case object AndToken                  extends Token
case object OrToken                   extends Token
case object NotToken                  extends Token
case object ToToken                   extends Token
