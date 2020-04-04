package sparser

import sparser.template.parser.{TemplateAST, TemplateExpressionParser}
import sparser.template.tokenizer.TemplateTokenizer
import sparser.util.sequence

package object template {

  type Error   = String
  type Program = List[TemplateAST]

  implicit def functionResolver = DefaultFunctionResolver

  def evaluate[A](str: String, vars: Map[String, String]): Either[Error, String] =
    evaluate(str, e => vars.get(e))

  def evaluate(str: String, vars: String => Option[String]): Either[Error, String] =
    for {
      prg    <- parse(str)
      result <- run(prg, vars)
    } yield result

  def run(program: Program, vars: String => Option[String]): Either[Error, String] =
    sequence(program.map(a => TemplateEvaluator.eval(a, vars)))
      .map(_.mkString(""))

  def parse(str: String): Either[Error, Program] =
    for {
      tokens <- TemplateTokenizer(str)
      ast    <- TemplateExpressionParser(tokens)
    } yield ast
}
