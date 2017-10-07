package ru.spbau.shavkunov.bot

import java.text.SimpleDateFormat
import java.util.Calendar

import scala.util.matching.Regex

class RegexTest extends org.scalatest.FunSuite {

  test("test current class regex") {
    val getCurrentClassPattern: Regex = new ScheduleBot("").getCurrentClassPattern
    assert("какая сейчас пара".matches(getCurrentClassPattern.toString()))
    assert("Какая сейчас пара".matches(getCurrentClassPattern.toString()))
  }

  test("test next class regex") {
    val getNextClassPattern: Regex = new ScheduleBot("").getNextClassPattern
    assert("какая следующая пара".matches(getNextClassPattern.toString()))
    assert("Какая следующая пара".matches(getNextClassPattern.toString()))
  }

}