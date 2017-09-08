package ru.spbau.shavkunov.calc.operations

/**
  * Multiplying numbers
  */
object Multiply extends Operation {

  override def getAssociative = Associative.Left

  override def getArity = 2

  override def getPrecedence = 3

  override def apply(tokens: List[Double]): Double = {
    return tokens.head * tokens(1)
  }

}