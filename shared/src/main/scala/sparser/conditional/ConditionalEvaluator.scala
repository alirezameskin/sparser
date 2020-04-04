package sparser.conditional

import sparser.conditional.parser._

import scala.util.{Failure, Success, Try}

object ConditionalEvaluator {

  def eval(ast: ConditionAst, vars: String => Option[Any]): Either[String, Boolean] =
    ast match {
      case expr: ConditionExpr => evalExpr(expr, vars)
      case _                   => Left("Invalid Expression")
    }

  private def evalExpr(expr: ConditionExpr, vars: String => Option[Any]): Either[String, Boolean] = expr match {
    case Equal(p, v)            => value(p, vars).map(_ == v)
    case Between(p, low, high)  => double(p, vars).map(v => v >= low && v <= high)
    case GreaterThan(p, value)  => double(p, vars).map(_ > value)
    case GreaterEqual(p, value) => double(p, vars).map(_ >= value)
    case LessThan(p, value)     => double(p, vars).map(_ < value)
    case LessEqual(p, value)    => double(p, vars).map(_ <= value)
    case Not(condition)         => eval(condition, vars).map(!_)
    case And(e1, e2)            => for (v1 <- evalExpr(e1, vars); v2 <- evalExpr(e2, vars)) yield v1 & v2
    case Or(e1, e2)             => for (v1 <- evalExpr(e1, vars); v2 <- evalExpr(e2, vars)) yield v1 | v2
  }

  private def value(p: ConditionParam, vars: String => Option[Any]) = p match {
    case Constant(v) => Right(v)
    case Variable(n) => vars(n).toRight[String](s"Invalid Variable $n")
  }

  private def double(p: ConditionParam, vars: String => Option[Any]) =
    value(p, vars).flatMap { d =>
      Try(d.toString.toDouble) match {
        case Failure(e) => Left(e.getMessage)
        case Success(v) => Right(v)
      }
    }
}
