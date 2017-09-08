package ru.spbau.shavkunov.calc.operations

/**
  * Dividing numbers operation
  */
object Divide extends Operation {

  override def getPrecedence: Int = 3

  override def apply(tokens: List[Double]): Double = {
    return tokens.head / tokens(1)
  }

  override def getArity: Int = 2

  override def getAssociative = Associative.Left

}