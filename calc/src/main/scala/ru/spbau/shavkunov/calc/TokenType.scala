package ru.spbau.shavkunov.calc

object TokenType extends Enumeration {
  type TokenType = Value

  val Number = Value
  val Operator = Value
  val LeftBracket = Value
  val RightBracket = Value
}