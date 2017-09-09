package ru.spbau.shavkunov.calc

import ru.spbau.shavkunov.calc.evaluation.Evaluator

class EvaluatorTest extends org.scalatest.FunSuite {
  test("Assert correct complex evaluation") {
    val evaluator = new Evaluator

    val expression = "1 + 2 + 3 + 4"
    val expression1 = "2 * (2 + 3)"
    val expression2 = "2 * (2 + 3) / 10"
    val expression3 = "(10 - 1/5)*10"
    val expression4 = "1 + 2 * 3 + exp(3) + 100/exp(10)"
    assert(evaluator.eval(expression) == 10)
    assert(evaluator.eval(expression1) == 10)
    assert(evaluator.eval(expression2) == 1)
    assert(evaluator.eval(expression3) == 98)
    assert(evaluator.eval(expression4) == 5.284913284854943E8)
  }

  test("addition") {
    val evaluator = new Evaluator
    assert(evaluator.eval("1 + 2 + 3 + 4") == 10)
  }

  test("substraction") {
    val evaluator = new Evaluator
    assert(evaluator.eval("100 - 50 - 20") == 30)
  }

  test("divide") {
    val evaluator = new Evaluator
    assert(evaluator.eval("100/10/10") == 1)
    assert(evaluator.eval("1000/10000") == 0.1)
  }

  test("multiply") {
    val evaluator = new Evaluator
    assert(evaluator.eval("2*2*2*2") == 16)
    assert(evaluator.eval("5*5*5*5") == 625)
  }

  test("exponent") {
    val evaluator = new Evaluator
    assert(evaluator.eval("exp(1)") == Math.E)
    assert(evaluator.eval("exp(10)") == Math.exp(10))
    assert(evaluator.eval("exp 1") == Math.E)
  }

  test("brackets priority") {
    val evaluator = new Evaluator
    assert(evaluator.eval("2*(5 + 4)") == 18)
    assert(evaluator.eval("(10 + 5)/3") == 5)
    assert(evaluator.eval("2 - (5 + 4)") == -7)
    assert(evaluator.eval("5*exp(1)") == 5*Math.E)
  }

  test("correct postfix notation") {
    val evaluator = new Evaluator
    val expression = "3 + 4 * 2 / ( 1 − 5 ) * exp(2) * exp(3)"
    evaluator.eval(expression)
  }
}