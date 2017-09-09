package ru.spbau.shavkunov.calc.evaluation

/**
  * Describing kinds of tokens
  */
object TokenType extends Enumeration {
  type TokenType = Value

  /**
    * Numeric token
    */
  val Number = Value

  /**
    * One of supported operations token
    */
  val Operator = Value

  /**
    * Left bracket
    */
  val LeftBracket = Value

  /**
    * Right bracket
    */
  val RightBracket = Value
}