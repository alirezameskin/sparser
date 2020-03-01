package sparser.conditional

import scala.util.parsing.combinator.JavaTokenParsers

object ConditionalParser extends JavaTokenParsers {

  def double: Parser[Double] =
    floatingPointNumber ^^ (_.toDouble)

  def constant: Parser[Number] =
    double ^^ Number

  def variable: Parser[Variable] =
    ident ~ rep(ident | ".") ^^ (n => Variable(n._1 + n._2.mkString("")))

  def equal: Parser[Equal] =
    variable ~ ":" ~ double ^^ { case v ~ _ ~ d => Equal(v, d) }

  def to: Parser[String] =
    "to" | "TO" | "tO" | "To"

  def between: Parser[Between] =
    variable ~ (":" ~ "[") ~ double ~ to ~ double ~ "]" ^^ { case v ~ _ ~ l ~ _ ~ h ~ _ => Between(v, l, h) }

  def greaterThan: Parser[GreaterThan] =
    variable ~ ">" ~ double ^^ { case v ~ _ ~ d => GreaterThan(v, d) }

  def greaterEqual: Parser[GreaterEqual] =
    variable ~ ">=" ~ double ^^ { case v ~ _ ~ d => GreaterEqual(v, d) }

  def lessThan: Parser[LessThan] =
    variable ~ "<" ~ double ^^ { case v ~ _ ~ d => LessThan(v, d) }

  def lessEqual: Parser[LessEqual] =
    variable ~ "<=" ~ double ^^ { case v ~ _ ~ d => LessEqual(v, d) }

  def andOr: Parser[Condition] =
    expression ~ rep(("AND" | "OR") ~ expression) ^^ {
      case x ~ ls =>
        ls.foldLeft[Condition](x) {
          case (e1, "AND" ~ e2) => And(e1, e2)
          case (e1, "OR" ~ e2)  => Or(e1, e2)
        }
    }

  def expression: Parser[Condition] =
    equal | between | lessEqual | lessThan | greaterEqual | greaterThan

  def program: Parser[Condition] =
    phrase(andOr | expression)

  def apply(str: String): Either[String, Condition] =
    parse(program, str) match {
      case Success(result, _) => Right(result)
      case NoSuccess(msg, _)  => Left(msg)
    }
}
