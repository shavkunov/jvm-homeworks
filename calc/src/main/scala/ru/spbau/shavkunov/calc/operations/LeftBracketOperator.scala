package ru.spbau.shavkunov.calc.operations

/**
  * Left bracket as a operator
  */
object LeftBracketOperator extends Operator {

  override def getAssociative = Associative.Left

  override def getArity = 0

  override def getPrecedence = 1

  override def apply(tokens: List[Double]): Double = {
    return 0
  }

}