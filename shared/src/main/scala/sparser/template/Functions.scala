package sparser.template

object Functions {
  val unary: (String) => Option[String => String] = {
    case "upper" => Some(_.toUpperCase)
    case "lower" => Some(_.toLowerCase)
    case "quote" => Some(s => '"' + s + '"')
  }
}
