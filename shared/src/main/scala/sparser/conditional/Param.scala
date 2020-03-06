package sparser.conditional

sealed trait Param
case class Number(v: Double)   extends Param
case class Variable(n: String) extends Param
