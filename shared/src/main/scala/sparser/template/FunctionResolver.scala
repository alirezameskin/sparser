package sparser.template

trait FunctionResolver {
  def resolve: PartialFunction[FunctionCall, String => String]
}
