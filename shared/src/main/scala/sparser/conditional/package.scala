package sparser

package object conditional {

  def tokenize(str: String): Either[String, Condition] =
    ConditionalParser(str)

  def evaluate(str: String): Either[String, Boolean] =
    evaluate(str, Map.empty[String, Any])

  def evaluate[A](str: String, vars: Map[String, Any]): Either[String, Boolean] =
    evaluate(str, e => vars.get(e))

  def evaluate(str: String, vars: String => Option[Any]): Either[String, Boolean] =
    tokenize(str).flatMap(ast => ConditionalEvaluator.eval(ast, vars))

}
