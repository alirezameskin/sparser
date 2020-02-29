package sparser.arithmetic

import sparser.util.sequence

object ArithmeticEvaluator {

  def eval(token: Token, vars: String => Option[Double]): Either[String, Double] =
    token match {
      case Number(n) =>
        Right(n)

      case Variable(name) =>
        vars(name).toRight[String](s"Invalid Variable $name")

      case Operator2(op, e1, e2) =>
        for {
          f  <- Functions.binary(op).toRight[String](s"Invalid Operation $op")
          v1 <- eval(e1, vars)
          v2 <- eval(e2, vars)
        } yield f(v1, v2)

      case OperatorN(op, vs) =>
        sequence(vs.map(e => eval(e, vars))).flatMap {
          case a :: Nil =>
            Functions
              .unary(op)
              .map(f => f(a))
              .toRight(s"Invalid Operation(Unary) $op($a)")

          case a :: b :: Nil =>
            Functions
              .binary(op)
              .map(f => f(a, b))
              .toRight(s"Invalid Operation(Binary) $op($a, $b)")

          case _ =>
            val args = vs.mkString(",")
            Left(s"Invalid function call: $op($args)")
        }
    }
}
