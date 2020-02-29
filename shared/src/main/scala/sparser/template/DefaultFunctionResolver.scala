package sparser.template

object DefaultFunctionResolver extends FunctionResolver {

  def isNum(s: String): Boolean = s.matches("[0-9]+")

  override def resolve = {
    case FunctionCall("upper", Nil)                   => _.toUpperCase
    case FunctionCall("lower", Nil)                   => _.toLowerCase
    case FunctionCall("quote", Nil)                   => s => '"' + s + '"'
    case FunctionCall("repeat", t :: Nil) if isNum(t) => s => s * t.toInt
  }
}
