package sparser

package object conditional {
  sealed trait Param
  case class Number(v: Double)   extends Param
  case class Variable(n: String) extends Param

  sealed trait Condition                                  extends Product with Serializable
  case class Equal(p: Param, value: Double)               extends Condition
  case class Between(p: Param, low: Double, high: Double) extends Condition
  case class GreaterThan(p: Param, value: Double)         extends Condition
  case class GreaterEqual(p: Param, value: Double)        extends Condition
  case class LessThan(p: Param, value: Double)            extends Condition
  case class LessEqual(p: Param, value: Double)           extends Condition
  case class Not(condition: Condition)                    extends Condition
  case class And(v1: Condition, v2: Condition)            extends Condition
  case class Or(v1: Condition, v2: Condition)             extends Condition
}
