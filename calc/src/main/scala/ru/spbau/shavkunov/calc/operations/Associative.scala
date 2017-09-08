package ru.spbau.shavkunov.calc.operations

/**
  * Declaring of operation association. It can be left or right.
  */
object Associative extends Enumeration {
  type Associative = Value

  val Left = Value
  val Right = Value
}
