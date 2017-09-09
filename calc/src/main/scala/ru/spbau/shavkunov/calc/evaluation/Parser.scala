package ru.spbau.shavkunov.calc.evaluation

import ru.spbau.shavkunov.calc.exceptions.{EmptyExpressionException, UnsupportedTypeOfExpressionException}

import scala.collection.mutable

/**
  * This class provides parsing and tokenizing arithmetic expressions
  */
class Parser() {
  // TODO : move to constants
  // these patterns supposed to match appropriate tokens
  val numberPattern = raw"^(\d+)".r
  val leftBracketPattern = raw"^\(".r
  val rightBracketPattern = raw"^\)".r
  val operatorPattern = raw"^([+−/*]|exp)".r
  val patterns = List(numberPattern, leftBracketPattern, rightBracketPattern, operatorPattern)

  /**
    * Parsing arithmetic expression into list of tokens
    * @param expression arithmetic expression with supported operations TODO: link to operation names
    * @return list of tokens
    */
  def parse(expression: String): List[Token] = {
    if (expression == "") {
      throw new EmptyExpressionException
    }

    val removedSpaces = expression.replace(" ", "")
    val tokens = tokenize(removedSpaces)

    return tokens
  }

  /**
    * Creating list of tokens in string with supported operations and without whitespaces
    * @param string arithmetic expression
    * @return list of tokens
    */
  private def tokenize(string: String): List[Token] = {
    var tokens = mutable.MutableList[Token]()

    val emptyMatch = ""
    var expression = string
    while (expression.length > 0) {
      var isMatched = false
      for (pattern <- patterns) {
        val value = pattern.findFirstIn(expression).getOrElse(emptyMatch)

        var token: Token = null
        if (value != emptyMatch) {
          value match {
            case this.numberPattern(_*) => token = new Token(TokenType.Number, value)
            case this.operatorPattern(_*) => token = new Token(TokenType.Operator, value)
            case this.leftBracketPattern(_*) => token = new Token(TokenType.LeftBracket, value)
            case this.rightBracketPattern(_*) => token = new Token(TokenType.RightBracket, value)
          }

          expression = pattern.replaceFirstIn(expression, "")
          tokens += token
          isMatched = true
        }
      }

      if (!isMatched) {
        throw new UnsupportedTypeOfExpressionException
      }
    }

    return tokens.toList
  }

}