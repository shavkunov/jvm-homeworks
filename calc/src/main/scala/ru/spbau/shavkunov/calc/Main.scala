package ru.spbau.shavkunov.calc

object Main {

  def main(args: Array[String]): Unit = {
    val expression = "5 + (5 + 5) + (((5) + (5 + 5)))"


    val evaluator = new Evaluator()
    println(evaluator.eval(expression))
  }

}