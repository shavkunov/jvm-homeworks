package ru.spbau.shavkunov.calc.operations

import ru.spbau.shavkunov.calc.Token

trait Operator {
  // TODO associative
  def getArity: Int
  def getPrecedence: Int
  def apply(tokens: List[Double]): Double
}