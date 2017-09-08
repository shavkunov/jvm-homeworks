package ru.spbau.shavkunov.calc.operations

import ru.spbau.shavkunov.calc.Token
import ru.spbau.shavkunov.calc.operations.Associative.Associative

trait Operator {
  def getAssociative: Associative
  def getArity: Int
  def getPrecedence: Int
  def apply(tokens: List[Double]): Double
}