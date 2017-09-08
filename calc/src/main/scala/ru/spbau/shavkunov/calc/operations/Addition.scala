package ru.spbau.shavkunov.calc.operations

import ru.spbau.shavkunov.calc.Token

object Addition extends Operator {

  override def getPrecedence: Int = 2

  override def apply(tokens: List[Double]): Double = {
    var result: Double = 0
    for (token <- tokens) {
      result += token
    }

    return result
  }

  override def getArity: Int = 2

  override def getAssociative = Associative.Left

}