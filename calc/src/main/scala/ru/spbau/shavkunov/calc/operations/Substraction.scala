package ru.spbau.shavkunov.calc.operations

/**
  * Substracting numbers
  */
object Substraction extends Operation {

  override def getAssociative = Associative.Left

  override def getArity = 2

  override def getPrecedence = 2

  override def apply(tokens: List[Double]): Double = {
    return tokens.head - tokens(1)
  }

}