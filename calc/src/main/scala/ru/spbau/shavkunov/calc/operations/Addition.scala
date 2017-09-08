package ru.spbau.shavkunov.calc.operations

import ru.spbau.shavkunov.calc.Token

class Addition extends Operator {

  override def getPrecedence: Int = {
    return 1
  }

  override def apply(tokens: List[Double]): Double = {
    var result: Double = 0
    for (token <- tokens) {
      result += token
    }

    return result
  }

  override def getArity: Int = {
    return 2
  }

}