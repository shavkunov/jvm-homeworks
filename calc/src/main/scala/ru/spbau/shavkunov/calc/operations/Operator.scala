package ru.spbau.shavkunov.calc.operations

import ru.spbau.shavkunov.calc.operations.Associative.Associative

/**
  * Interface of operation, which can be applied to it's arguments
  */
trait Operator {

  /**
    * @return associative of operation
    */
  def getAssociative: Associative

  /**
    * @return arity of operation
    */
  def getArity: Int

  /**
    * @return precedence of operation
    */
  def getPrecedence: Int

  /**
    * @param tokens operation arguments in natural order
    * @return result of applying operation
    */
  def apply(tokens: List[Double]): Double
}