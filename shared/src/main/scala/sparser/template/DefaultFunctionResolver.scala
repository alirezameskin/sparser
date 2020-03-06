package sparser.template

import sparser.template.parser.FunctionCall

object DefaultFunctionResolver extends FunctionResolver {

  def isNum(s: String): Boolean = s.matches("[0-9]+")

  override def resolve = {
    case FunctionCall("upper", Nil)                   => _.toUpperCase
    case FunctionCall("lower", Nil)                   => _.toLowerCase
    case FunctionCall("quote", Nil)                   => s => "\"%s\"".format(s)
    case FunctionCall("repeat", t :: Nil) if isNum(t) => s => s * t.toInt
  }
}
