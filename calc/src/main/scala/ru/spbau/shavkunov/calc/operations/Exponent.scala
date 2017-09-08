package ru.spbau.shavkunov.calc.operations

object Exponent extends Operator {

  override def getAssociative = Associative.Left

  override def getArity = 1

  override def getPrecedence = 4

  override def apply(tokens: List[Double]): Double = {
    return Math.exp(tokens.head)
  }

}