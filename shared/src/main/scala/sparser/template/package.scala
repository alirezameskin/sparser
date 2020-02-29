package sparser

import sparser.template.parser.{TemplateAST, TemplateExpressionParser}
import sparser.template.tokenizer.TemplateTokenizer
import sparser.util.sequence

package object template {
  case class FunctionCall(func: String, args: Seq[String] = Nil)

  implicit def functionResolver = DefaultFunctionResolver

  def evaluate[A](str: String, vars: Map[String, String])(implicit R: FunctionResolver): Either[String, String] =
    evaluate(str, e => vars.get(e))

  def evaluate(str: String, vars: String => Option[String]): Either[String, String] =
    parse(str)
      .flatMap { asts =>
        sequence(asts.map(a => TemplateEvaluator.eval(a, vars)))
      }
      .map(_.mkString(""))

  def parse(str: String): Either[String, List[TemplateAST]] =
    for {
      tokens <- TemplateTokenizer(str)
      ast    <- TemplateExpressionParser(tokens)
    } yield ast
}
