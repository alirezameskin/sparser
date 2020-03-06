package sparser

package object arithmetic {

  def tokenize(str: String): Either[String, Token] =
    ArithmeticParser(str)

  def evaluate(str: String): Either[String, Double] =
    evaluate(str, Map.empty[String, Double])

  def evaluate[A](str: String, vars: Map[String, Double]): Either[String, Double] =
    evaluate(str, e => vars.get(e))

  def evaluate(str: String, vars: String => Option[Double]): Either[String, Double] =
    tokenize(str).flatMap(ast => ArithmeticEvaluator.eval(ast, vars))
}
