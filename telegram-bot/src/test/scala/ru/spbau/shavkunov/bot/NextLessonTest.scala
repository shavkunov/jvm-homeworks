package ru.spbau.shavkunov.bot

import java.util

import org.boon.json.JsonFactory
import org.scalatest.{FlatSpec, Matchers}

class NextLessonTest extends FlatSpec with Matchers {
  val schedule = ScheduleHelper.getScheduleData
  val mapper = JsonFactory.create
  val json: util.Map[String, Any] = mapper.fromJson(schedule).asInstanceOf[util.Map[String, Any]]
  val values: util.List[Any] = json.get("values").asInstanceOf[util.List[Any]]

  "Monday second pair" should "be formal langs" in {
    val monday = 0
    val pairNumber = 0

    val lesson = ScheduleHelper.getNextClass(monday, pairNumber)
    val jvm = values.get(monday).asInstanceOf[util.List[Any]].get(pairNumber + 1)

    lesson shouldBe jvm
  }

  "first pair after monday" should "be algo" in {
    val monday = 0
    val pairNumber = 3

    val lesson = ScheduleHelper.getNextClass(monday, pairNumber)
    val algo = values.get(monday + 1).asInstanceOf[util.List[Any]].get(0)

    lesson shouldBe algo
  }

  "first pair after tuesday" should "be se" in {
    val tuesday = 1
    val pairNumber = 4

    val lesson = ScheduleHelper.getNextClass(tuesday, pairNumber)
    val se = values.get(tuesday + 1).asInstanceOf[util.List[Any]].get(1)

    lesson shouldBe se
  }

  "last pair of thursday" should "be bio" in {
    val thursday = 3
    val pairNumber = 2

    val lesson = ScheduleHelper.getNextClass(thursday, pairNumber)
    val bio = values.get(thursday).asInstanceOf[util.List[Any]].get(5)

    lesson shouldBe bio
  }

  "first pair after friday" should "be linux" in {
    val friday = 4
    val pairNumber = 4

    val lesson = ScheduleHelper.getNextClass(friday, pairNumber)
    val linux = values.get(friday + 1).asInstanceOf[util.List[Any]].get(0)

    lesson shouldBe linux
  }

  "first pair after saturday" should "be formal languages" in {
    val saturday = 5
    val pairNumber = 3

    val lesson = ScheduleHelper.getNextClass(saturday, pairNumber)
    val formalLanguages = values.get(0).asInstanceOf[util.List[Any]].get(0)

    lesson shouldBe formalLanguages
  }
}