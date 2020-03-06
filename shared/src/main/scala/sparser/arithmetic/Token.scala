package sparser.arithmetic

sealed trait Token                                     extends Product with Serializable
case class Number(value: Double)                       extends Token
case class Variable(name: String)                      extends Token
case class Operator2(op: String, v1: Token, v2: Token) extends Token
case class OperatorN(op: String, vs: List[Token])      extends Token
