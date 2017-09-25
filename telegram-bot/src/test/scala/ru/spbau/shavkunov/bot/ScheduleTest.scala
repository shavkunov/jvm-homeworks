package ru.spbau.shavkunov.bot

import java.text.SimpleDateFormat
import java.util.Calendar

import org.boon.json.JsonFactory
import ru.spbau.shavkunov.bot.ScheduleHelper.{getClassNumber, getDayOfWeek, getScheduleData, noLessonsMessage}

import scala.util.matching.Regex

class ScheduleTest extends org.scalatest.FunSuite {

  test("current data") {
    val today = Calendar.getInstance().getTime
    val dayFormat = new SimpleDateFormat("EEEE")

    println(dayFormat.format(today))
  }

  test("json test") {
    println(ScheduleHelper.getCurrentClass())
  }

  test("next class test") {
    println(ScheduleHelper.getNextClass())
  }

  test("test regex") {
    val getCurrentClassPattern: Regex = raw"[Кк]акая сейчас пара[\\?]?".r
    val getNextClassPattern: Regex = raw"[Кк]акая следующая пара?".r

    println("какая сейчас пара".matches(getCurrentClassPattern.toString()))
  }

}