package ru.spbau.shavkunov.calc

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class Evaluator {
  val parser = new Parser()

  private def getPostfixNotation(tokens: mutable.MutableList[Token]): List[Token] = {
    var postfixNotation = mutable.ListBuffer[Token]()
    val operatorsStack = mutable.Stack[Token]()

    for (token <- tokens) {

      token.tokenType match {
        case TokenType.Number => {
          postfixNotation += token
        }

        case TokenType.Operator => {
          while (operatorsStack.nonEmpty &&
                 operatorsStack.top.operator.getPrecedence >= token.operator.getPrecedence) {
            postfixNotation += operatorsStack.pop()
          }

          operatorsStack.push(token)
        }

        case TokenType.LeftBracket => {
          operatorsStack.push(token)
        }

        case TokenType.RightBracket => {
          while (operatorsStack.top.tokenType != TokenType.LeftBracket) {
            postfixNotation += operatorsStack.pop()
          }

          // pops left bracket
          /* if the stack runs out without finding a left bracket, then there are
		      mismatched parentheses. */
          operatorsStack.pop()
        }
      }
    }

    while (operatorsStack.nonEmpty) {
      postfixNotation += operatorsStack.pop()
    }

    return postfixNotation.toList
  }

  private def evaluateAnswer(postfix: List[Token]): Double = {
    val resultingStack = mutable.Stack[Double]()

    var tokens = mutable.Stack[Token]()
    tokens ++= postfix
    while (tokens.nonEmpty) {
      val token = tokens.pop()

      token.tokenType match {
        case TokenType.Number => {
          resultingStack.push(token.value.toDouble)
        }

        case TokenType.Operator => {
          var operands = new ListBuffer[Double]()

          for (iteration <- 1 to token.operator.getArity) {
            operands += resultingStack.pop()
          }

          // stack changes order of operands
          operands = operands.reverse

          val result = token.operator.apply(operands.toList)
          resultingStack.push(result)
        }
      }
    }

    return resultingStack.pop()
  }

  def eval(expression: String): Double = {
    val tokens = parser.parse(expression)
    val postfix = getPostfixNotation(tokens)
    val result = evaluateAnswer(postfix)

    return result
  }

}