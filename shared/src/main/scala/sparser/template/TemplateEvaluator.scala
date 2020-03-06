package sparser.template

import sparser.template.parser._

object TemplateEvaluator {

  private def function(op: FunctionCall, err: String)(implicit R: FunctionResolver): Either[String, String => String] =
    if (R.resolve.isDefinedAt(op)) Right(R.resolve.apply(op)) else Left(err)

  def eval(token: TemplateAST, vars: String => Option[String])(implicit R: FunctionResolver): Either[String, String] =
    token match {
      case Text(t)        => Right(t)
      case Variable(name) => vars(name).toRight[String](s"Invalid Variable ${name}")
      case Operation(op, e1) =>
        for {
          f  <- function(op, s"Invalid Function $op")(R)
          v1 <- eval(e1, vars)
        } yield f(v1)
    }

}
