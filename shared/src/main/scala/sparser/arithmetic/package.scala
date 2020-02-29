package sparser

package object arithmetic {

  sealed trait Token                                     extends Product with Serializable
  case class Number(value: Double)                       extends Token
  case class Variable(name: String)                      extends Token
  case class Operator2(op: String, v1: Token, v2: Token) extends Token
  case class OperatorN(op: String, vs: List[Token])      extends Token

  def tokenize(str: String): Either[String, Token] =
    ArithmeticParser(str)

  def evaluate(str: String): Either[String, Double] =
    evaluate(str, Map.empty[String, Double])

  def evaluate[A](str: String, vars: Map[String, Double]): Either[String, Double] =
    evaluate(str, e => vars.get(e))

  def evaluate(str: String, vars: String => Option[Double]): Either[String, Double] =
    tokenize(str).flatMap(ast => ArithmeticEvaluator.eval(ast, vars))
}
