package sparser.template

import sparser.template.parser._

object TemplateEvaluator {

  def eval(token: TemplateAST, vars: String => Option[String]): Either[String, String] = token match {
    case Text(t)        => Right(t)
    case Variable(name) => vars(name).toRight[String](s"Invalid Variable ${name}")
    case Operation(op, e1) =>
      for {
        f  <- Functions.unary(op).toRight[String](s"Invalid Function $op")
        v1 <- eval(e1, vars)
      } yield f(v1)
  }

}
