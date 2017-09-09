package ru.spbau.shavkunov.calc

import ru.spbau.shavkunov.calc.evaluation.Evaluator
import ru.spbau.shavkunov.calc.exceptions.{EmptyExpressionException, InvalidParentException, MissingOperandException, UnsupportedTypeOfExpressionException}

class ErrorsTest extends org.scalatest.FunSuite {

  test("invalid parents") {
    val evaluator = new Evaluator
    assertThrows[InvalidParentException] {
      evaluator.eval("123 + (1 + 1")
    }

    assertThrows[InvalidParentException] {
      evaluator.eval("123 + 1 + 1)")
    }

    assertThrows[InvalidParentException] {
      evaluator.eval("123 + 1 + ()1)")
    }

    assertThrows[InvalidParentException] {
      evaluator.eval(")123 + 1 + ()1)")
    }
  }

  test("missing operand") {
    val evaluator = new Evaluator
    assertThrows[MissingOperandException] {
      evaluator.eval("123 + ")
    }

    assertThrows[MissingOperandException] {
      evaluator.eval("123 * ")
    }

    assertThrows[MissingOperandException] {
      evaluator.eval("123 / ")
    }

    assertThrows[MissingOperandException] {
      evaluator.eval("exp ")
    }

    assertThrows[MissingOperandException] {
      evaluator.eval("123 + 1234/")
    }

    assertThrows[MissingOperandException] {
      evaluator.eval("exp/ ")
    }

    assertThrows[MissingOperandException] {
      evaluator.eval("+ 1")
    }

    assertThrows[MissingOperandException] {
      evaluator.eval("/ 1234")
    }
  }

  test("empty expression") {
    val evaluator = new Evaluator
    assertThrows[EmptyExpressionException] {
      evaluator.eval("")
    }
  }

  test("invalid expression") {
    val evaluator = new Evaluator
    assertThrows[UnsupportedTypeOfExpressionException] {
      evaluator.eval("sin(10)")
    }

    assertThrows[UnsupportedTypeOfExpressionException] {
      evaluator.eval("testing message")
    }

    assertThrows[UnsupportedTypeOfExpressionException] {
      evaluator.eval("10^3")
    }
  }

}
