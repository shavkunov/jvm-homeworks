package ru.spbau.shavkunov.calc

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Class provides evaluation of arithmetic expressions
  */
class Evaluator {
  val parser = new Parser()

  /**
    * Creating reverse polish notation(postfix notation) of tokens
    * @param tokens arithmetic tokens
    * @return postfix notation
    */
  private def getPostfixNotation(tokens: List[Token]): List[Token] = {
    var postfixNotation = mutable.ListBuffer[Token]()
    val operatorsStack = mutable.Stack[Token]()

    for (token <- tokens) {

      token.tokenType match {
        case TokenType.Number => {
          postfixNotation += token
        }

        case TokenType.Operation => {
          while (operatorsStack.nonEmpty &&
                 operatorsStack.top.operation.getPrecedence >= token.operation.getPrecedence) {
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

  /**
    * Calculation result of postfix notation
    * @param postfix postfix notation of arithmetic expression
    * @return result of calculating
    */
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

        case TokenType.Operation => {
          var operands = new ListBuffer[Double]()

          for (iteration <- 1 to token.operation.getArity) {
            operands += resultingStack.pop()
          }

          // stack changes order of operands
          operands = operands.reverse

          val result = token.operation.apply(operands.toList)
          resultingStack.push(result)
        }
      }
    }

    return resultingStack.pop()
  }

  /**
    * Evaluation of arithmetic expression
    * @param expression arithmetic expression
    * @return result of calculating
    */
  def eval(expression: String): Double = {
    val tokens = parser.parse(expression)
    val postfix = getPostfixNotation(tokens)
    val result = evaluateAnswer(postfix)

    return result
  }

}