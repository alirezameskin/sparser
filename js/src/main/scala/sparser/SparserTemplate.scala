package sparser

import sparser.template.{parse, TemplateEvaluator}
import sparser.util.sequence

import scala.scalajs.js.Dictionary
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("SparserTemplate")
object SparserTemplate {

  @JSExport
  def evaluate(str: String, vars: Dictionary[String]): Any =
    parse(str)
      .flatMap(asts => sequence(asts.map(a => TemplateEvaluator.eval(a, vars.get(_)))))
      .map(_.mkString("")) match {
      case Left(msg)  => scala.scalajs.js.Error(msg)
      case Right(res) => res
    }
}
