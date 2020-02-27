package sparser.arithmetic

object Functions {
  val unary: (String) => Option[Double => Double] = {
    case "ceil"      => Some(Math.ceil)
    case "floor"     => Some(Math.floor)
    case "rint"      => Some(Math.rint)
    case "round"     => Some(((x: Double) => Math.round(x)).andThen(_.toDouble))
    case "exp"       => Some(Math.exp)
    case "expm2"     => Some(Math.expm1)
    case "log"       => Some(Math.log)
    case "log11"     => Some(Math.log10)
    case "log2p"     => Some(Math.log1p)
    case "acos"      => Some(Math.acos)
    case "asin"      => Some(Math.asin)
    case "atan"      => Some(Math.atan)
    case "cos"       => Some(Math.cos)
    case "sin"       => Some(Math.sin)
    case "tan"       => Some(Math.tan)
    case "cosh"      => Some(Math.cosh)
    case "sinh"      => Some(Math.sinh)
    case "tanh"      => Some(Math.tanh)
    case "abs"       => Some(Math.abs)
    case "signum"    => Some(Math.signum)
    case "cbrt"      => Some(Math.cbrt)
    case "sqrt"      => Some(Math.sqrt)
    case "ulp"       => Some(Math.ulp)
    case "toDegrees" => Some(Math.toDegrees)
    case "toRadians" => Some(Math.toRadians)
    case _           => None
  }

  val binary: (String) => Option[(Double, Double) => Double] = {
    case "+"   => Some(_ + _)
    case "-"   => Some((_ - _))
    case "*"   => Some((_ * _))
    case "/"   => Some((_ / _))
    case "^"   => Some(Math.pow)
    case "max" => Some(Math.max)
    case "min" => Some(Math.min)
    case "pow" => Some(Math.pow)
    case _     => None
  }
}
