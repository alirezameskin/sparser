# sparser
Dynamic String Parser library

## Adding an sbt dependency
To depend on sparser in sbt, add something like this to your build.sbt:

```scala
resolvers += "sparser" at "https://dl.bintray.com/meskin/sparser/"

libraryDependencies += "com.github.alirezameskin" %% "sparser" % "0.0.5"
```
### Scala.js
Sparser is also available for Scala.js :

```scala
resolvers += "sparser" at "https://dl.bintray.com/meskin/sparser/"

libraryDependencies += "com.github.alirezameskin" %%% "sparser" % "0.0.5"
```


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
  //res0: Right(Result VALUE1)

  val res1 = template.evaluate("Result {{ placeholder | lower | quote }}", Map("placeholder" -> "value1"))
  //res1: Right(Result "value1")

  val res2 = template.evaluate("{{name | repeat 3}}", Map("name" -> "Alireza "))
  //res2: Right(Alireza Alireza Alireza)
```

## Examples (Conditional)
```scala
  import sparser.conditional

  val res0 = conditional.evaluate("status:200", Map("status" -> 200))
  //res0 : Right(true)

  val vars1 = Map("status" -> 200, "count" -> 120, "size" -> 200)
  val res1 = conditional.evaluate("status <= 200 AND (count:[0 TO 100] OR size:200)", vars1)
  //res1: Right(true)

```
