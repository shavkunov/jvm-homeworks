package ru.spbau.shavkunov.calc

import ru.spbau.shavkunov.calc.evaluation.{Parser, TokenType}
import ru.spbau.shavkunov.calc.operations._

class ParserTest extends org.scalatest.FunSuite {

  test("correct tokens from expression") {
    val parser = new Parser
    val list = parser.parse("1 +    2 * ( exp(100 / 4) )")

    assert(list.head.value == "1")
    assert(list(1).operator == Addition)
    assert(list(2).value == "2")
    assert(list(3).operator == Multiply)
    assert(list(4).tokenType == TokenType.LeftBracket)
    assert(list(5).operator == Exponent)
    assert(list(6).tokenType == TokenType.LeftBracket)
    assert(list(7).value == "100")
    assert(list(8).operator == Divide)
    assert(list(9).value == "4")
    assert(list(10).tokenType == TokenType.RightBracket)
    assert(list(11).tokenType == TokenType.RightBracket)
  }

}
