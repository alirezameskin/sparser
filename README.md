# sparser
Dynamic String Parser library


## Examples (Arithmetic)
```scala

  import sparser.arithmetic

  val res1 = arithmetic.evaluate("round(2.1) + ceil(2.4)")
  //res1: Right(5.0)

  val res2 = arithmetic.evaluate(
    "23.4 + ${num} + sqrt(12 * 34) + round(2 + round(3.5))",
    Map("num" -> 10D)
  )
  //res2: Right(59.59)

  val res3 = arithmetic.evaluate(
    "23.4 + ${num} + sqrt(12 * 34) + round(2 + round(3.5))",
    (_ => Some(10))
  )
  //res3: Right(59.59)

  val res4 = arithmetic.evaluate("round(2.1 + ceil(2.3))")
  //res4: Right(5.0)

  val res5 = arithmetic.evaluate("rnd(450)")
  //res5: Left(Invalid Operation(Unary) rnd(450.0))
```

## Examples (Template)
```scala
  import sparser.template

  val res0 = template.evaluate("Result {{ placeholder | upper }}", Map("placeholder" -> "value1"))
  //resRight(Result VALUE1)

  val res1 = template.evaluate("Result {{ placeholder | lower | quote }}", Map("placeholder" -> "value1"))
  //result Right(Result "value1")
```
