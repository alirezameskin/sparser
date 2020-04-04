package sparser.conditional.parser

sealed trait ConditionAst extends Product with Serializable

sealed trait ConditionExpr                                       extends ConditionAst
case class Equal(p: ConditionParam, value: Any)                  extends ConditionExpr
case class Between(p: ConditionParam, low: Double, high: Double) extends ConditionExpr
case class GreaterThan(p: ConditionParam, value: Double)         extends ConditionExpr
case class GreaterEqual(p: ConditionParam, value: Double)        extends ConditionExpr
case class LessThan(p: ConditionParam, value: Double)            extends ConditionExpr
case class LessEqual(p: ConditionParam, value: Double)           extends ConditionExpr
case class Not(condition: ConditionExpr)                         extends ConditionExpr
case class And(v1: ConditionExpr, v2: ConditionExpr)             extends ConditionExpr
case class Or(v1: ConditionExpr, v2: ConditionExpr)              extends ConditionExpr

sealed trait ConditionParam    extends ConditionAst
case class Constant(v: Double) extends ConditionParam
case class Variable(n: String) extends ConditionParam
