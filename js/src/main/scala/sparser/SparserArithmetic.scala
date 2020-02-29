package sparser

import sparser.arithmetic.{tokenize, ArithmeticEvaluator}

import scala.scalajs.js.Dictionary
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("SparserArithmetic")
object SparserArithmetic {

  @JSExport
  def evaluate(str: String, vars: Dictionary[Double]): Any =
    tokenize(str).flatMap(ast => ArithmeticEvaluator.eval(ast, x => vars.get(x))) match {
      case Left(msg)  => scala.scalajs.js.Error(msg)
      case Right(res) => res
    }
}
