package ru.spbau.shavkunov.calc

import scala.collection.mutable

class Parser() {
  // TODO : move to constants
  val numberPattern = "\\d+".r
  val leftBracketPattern = "\\(".r
  val rightBracketPattern = "\\)".r
  val operatorPattern = "[+-/*]".r
  val patterns = List(numberPattern, leftBracketPattern, rightBracketPattern, operatorPattern)

  def parse(expression: String): mutable.MutableList[Token] = {
    val removedSpaces = expression.replace(" ", "")
    val tokens = tokenize(removedSpaces)

    return tokens
  }

  private def tokenize(string: String): mutable.MutableList[Token] = {
    var tokens = mutable.MutableList[Token]()

    val emptyMatch = ""
    var expression = string
    while (expression.length > 0) {
      for (pattern <- patterns) {
        val value = pattern.findFirstIn(expression).getOrElse(emptyMatch)

        var token: Token = null
        if (value != emptyMatch) {
          value.charAt(0) match {
            case this.leftBracketPattern(_*) => token = new Token(TokenType.LeftBracket, value)
            case this.numberPattern(_*) => token = new Token(TokenType.Number, value)
            case this.rightBracketPattern(_*) => token = new Token(TokenType.RightBracket, value)
            case this.operatorPattern(_*) => token = new Token(TokenType.Operator, value)
          }

          expression = pattern.replaceFirstIn(expression, "")
          tokens += token
        }
      }
    }

    return tokens
  }

}