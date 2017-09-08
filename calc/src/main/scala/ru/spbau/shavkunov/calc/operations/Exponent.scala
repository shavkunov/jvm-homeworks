package ru.spbau.shavkunov.calc.operations

/**
  * Calculation of exponent
  */
object Exponent extends Operation {

  override def getAssociative = Associative.Left

  override def getArity = 1

  override def getPrecedence = 4

  override def apply(tokens: List[Double]): Double = {
    return Math.exp(tokens.head)
  }

}