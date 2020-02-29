package sparser.arithmetic

import scala.util.parsing.combinator._

object ArithmeticParser extends JavaTokenParsers {

  def constant: Parser[Number] =
    floatingPointNumber ^^ (c => Number(c.toDouble))

  def variable: Parser[Variable] =
    "${" ~> ident ~ rep(ident | ".") <~ "}" ^^ (n => Variable(n._1 + n._2.mkString("")))

  def function: Parser[Token] =
    ident ~ ("(" ~> precedence4 ~ rep("," ~> precedence4) <~ ")") ^^ {
      case n ~ (e ~ es) => OperatorN(n, e :: es)
    }

  def precedence1: Parser[Token] =
    constant | variable | function | "(" ~> precedence4 <~ ")"

  def precedence2: Parser[Token] =
    precedence1 ~ rep("^" ~ precedence1) ^^ {
      case x ~ ls =>
        ls.foldLeft[Token](x) {
          case (d1, "^" ~ d2) => Operator2("^", d1, d2)
        }
    }

  def precedence3: Parser[Token] =
    precedence2 ~ rep(("*" | "/") ~ precedence2) ^^ {
      case x ~ ls =>
        ls.foldLeft[Token](x) {
          case (d1, "*" ~ d2) => Operator2("*", d1, d2)
          case (d1, "/" ~ d2) => Operator2("/", d1, d2)
        }
    }

  def precedence4: Parser[Token] =
    precedence3 ~ rep(("+" | "-") ~ precedence3) ^^ {
      case x ~ ls =>
        ls.foldLeft[Token](x) {
          case (d1, "+" ~ d2) => Operator2("+", d1, d2)
          case (d1, "-" ~ d2) => Operator2("-", d1, d2)
        }
    }

  def expression: Parser[Token] =
    precedence4

  def program: Parser[Token] =
    phrase(expression)

  def apply(str: String): Either[String, Token] =
    parse(program, str) match {
      case Success(result, _) => Right(result)
      case NoSuccess(msg, _)  => Left(msg)
    }
}
