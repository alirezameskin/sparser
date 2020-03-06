package sparser.template.parser

case class FunctionCall(func: String, args: Seq[String] = Nil)

sealed trait TemplateAST
case class Text(value: String)                         extends TemplateAST
case class Variable(name: String)                      extends TemplateAST
case class Operation(op: FunctionCall, v: TemplateAST) extends TemplateAST
