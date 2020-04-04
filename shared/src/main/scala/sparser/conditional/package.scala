package sparser

import sparser.conditional.parser.{ConditionAst, ConditionalParser}
import sparser.conditional.tokenizer.ConditionalTokenizer

package object conditional {
  type Expression = ConditionAst
  type Error      = String

  def evaluate(str: String): Either[Error, Boolean] =
    evaluate(str, Map.empty[String, Any])

  def evaluate[A](str: String, vars: Map[String, Any]): Either[Error, Boolean] =
    evaluate(str, e => vars.get(e))

  def evaluate(str: String, vars: String => Option[Any]): Either[Error, Boolean] =
    parse(str).flatMap(ast => run(ast, vars))

  def run(expr: Expression, vars: String => Option[Any]): Either[Error, Boolean] =
    ConditionalEvaluator.eval(expr, vars)

  def parse(str: String): Either[String, Expression] =
    for {
      tokens <- ConditionalTokenizer(str)
      expr   <- ConditionalParser(tokens)
    } yield expr
}
