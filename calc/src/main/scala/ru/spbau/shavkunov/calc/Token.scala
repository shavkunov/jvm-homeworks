package ru.spbau.shavkunov.calc

import ru.spbau.shavkunov.calc.TokenType.TokenType
import ru.spbau.shavkunov.calc.operations._

/**
  * Object representing token in arithmetic expression
  * @param tokenType kind of token
  * @param value if token is a Number then it's arithmetic value
  */
class Token(var tokenType: TokenType, var value: String) {
  // field is not null when token's type is operator
  var operation: Operation = null
  if (tokenType == TokenType.Operation) {
    value match {
      case "+" => operation = Addition
      case "-" => operation = Substraction
      case "/" => operation = Divide
      case "*" => operation = Multiply
      case "exp" => operation = Exponent
    }
  }

  /**
    * Creating not number tokens
    * @param tokenType kind of token
    */
  def this(tokenType: TokenType) {
    this(tokenType: TokenType, null)
  }

}