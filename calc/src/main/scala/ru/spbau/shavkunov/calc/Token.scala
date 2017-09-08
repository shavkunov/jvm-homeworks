package ru.spbau.shavkunov.calc

import ru.spbau.shavkunov.calc.TokenType.TokenType
import ru.spbau.shavkunov.calc.operations._

class Token(var tokenType: TokenType, var value: String) {
  var operator: Operator = null
  if (tokenType == TokenType.Operator) {
    value match {
      case "+" => operator = Addition
      case "-" => operator = Substraction
      case "/" => operator = Divide
      case "*" => operator = Multiply
      case "exp" => operator = Exponent
    }
  }

  def this(tokenType: TokenType) {
    this(tokenType: TokenType, null)
  }

}