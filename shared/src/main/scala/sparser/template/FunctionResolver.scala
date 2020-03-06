package sparser.template

import sparser.template.parser.FunctionCall

trait FunctionResolver {
  def resolve: PartialFunction[FunctionCall, String => String]
}
