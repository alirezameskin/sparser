package sparser.template

package object parser {
  sealed trait TemplateAST
  case class Text(value: String)                   extends TemplateAST
  case class Variable(name: String)                extends TemplateAST
  case class Operation(op: String, v: TemplateAST) extends TemplateAST

  case class FunctionCall(func: String)
}
