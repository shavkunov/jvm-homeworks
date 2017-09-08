package ru.spbau.shavkunov.calc.operations

object Divide extends Operator {

  override def getPrecedence: Int = 3

  override def apply(tokens: List[Double]): Double = {
    return tokens.head / tokens(1)
  }

  override def getArity: Int = 2

  override def getAssociative = Associative.Left

}