package ru.spbau.shavkunov.calc

import scala.io.StdIn

object Main {

  def main(args: Array[String]): Unit = {
    Console.print("Enter expression:\n")

    val evaluator = new Evaluator()
    Console.print(evaluator.eval(StdIn.readLine()))
  }

}