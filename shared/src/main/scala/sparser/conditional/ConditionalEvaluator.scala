package sparser.conditional

import scala.util.{Failure, Success, Try}

object ConditionalEvaluator {

  def eval(token: Condition, vars: String => Option[Any]): Either[String, Boolean] =
    token match {
      case Equal(p, v)            => value(p, vars).map(_ == v)
      case Between(p, low, high)  => double(p, vars).map(v => v >= low && v <= high)
      case GreaterThan(p, value)  => double(p, vars).map(_ > value)
      case GreaterEqual(p, value) => double(p, vars).map(_ >= value)
      case LessThan(p, value)     => double(p, vars).map(_ < value)
      case LessEqual(p, value)    => double(p, vars).map(_ <= value)
      case Not(condition)         => eval(condition, vars).map(!_)
      case And(e1, e2)            => for (v1 <- eval(e1, vars); v2 <- eval(e2, vars)) yield v1 & v2
      case Or(e1, e2)             => for (v1 <- eval(e1, vars); v2 <- eval(e2, vars)) yield v1 | v2
    }

  private def value(p: Param, vars: String => Option[Any]) = p match {
    case Number(v)   => Right(v)
    case Variable(n) => vars(n).toRight[String](s"Invalid Variable $n")
  }

  private def double(p: Param, vars: String => Option[Any]) =
    value(p, vars).flatMap { d =>
      Try(d.toString.toDouble) match {
        case Failure(e) => Left(e.getMessage)
        case Success(v) => Right(v)
      }
    }
}
