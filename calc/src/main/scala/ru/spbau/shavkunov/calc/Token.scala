package ru.spbau.shavkunov.calc

import ru.spbau.shavkunov.calc.TokenType.TokenType
import ru.spbau.shavkunov.calc.operations.{Addition, Operator}

class Token(var tokenType: TokenType, var value: String) {
  var operator = new Addition()
  // TODO create operator

  def this(tokenType: TokenType) {
    this(tokenType: TokenType, null)
  }

}