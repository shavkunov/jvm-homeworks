package ru.spbau.shavkunov.calc.evaluation

import ru.spbau.shavkunov.calc.evaluation.TokenType.TokenType
import ru.spbau.shavkunov.calc.operations._

/**
  * Object representing token in arithmetic expression
  * @param tokenType kind of token
  * @param value if token is a Number then it's arithmetic value
  */
class Token(var tokenType: TokenType, var value: String) {
  // field is not null when token's type is operator
  var operator: Operator = null
  if (tokenType == TokenType.Operator || tokenType == TokenType.LeftBracket) {
    value match {
      case "+" => operator = Addition
      case "−" => operator = Substraction
      case "/" => operator = Divide
      case "*" => operator = Multiply
      case "exp" => operator = Exponent
      case _ => operator = LeftBracketOperator
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