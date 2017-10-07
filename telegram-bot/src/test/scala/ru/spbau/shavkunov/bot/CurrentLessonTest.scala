package ru.spbau.shavkunov.bot

import java.util

import org.boon.json.JsonFactory
import org.scalatest.{FlatSpec, Matchers}

class CurrentLessonTest extends FlatSpec with Matchers {
  val schedule = ScheduleHelper.getScheduleData
  val mapper = JsonFactory.create
  val json: util.Map[String, Any] = mapper.fromJson(schedule).asInstanceOf[util.Map[String, Any]]
  val values: util.List[Any] = json.get("values").asInstanceOf[util.List[Any]]

  "Monday first pair" should "be formal langs" in {
    val monday = 0
    val pairNumber = 0

    val lesson = ScheduleHelper.getCurrentClass(monday, pairNumber)
    val formalLangs = values.get(monday).asInstanceOf[util.List[Any]].get(pairNumber)

    lesson shouldBe formalLangs
  }

  "Monday last pair" should "be db" in {
    val monday = 0
    val pairNumber = 3

    val lesson = ScheduleHelper.getCurrentClass(monday, pairNumber)
    val database = values.get(monday).asInstanceOf[util.List[Any]].get(pairNumber)

    lesson shouldBe database
  }

  "Monday relax" should "be no lessons" in {
    val monday = 0
    val pairNumber = 5

    val lesson = ScheduleHelper.getCurrentClass(monday, pairNumber)

    lesson shouldBe ScheduleHelper.noLessonsMessage
  }

  "Tuesday first pair" should "be algo" in {
    val tuesday = 1
    val pairNumber = 0

    val lesson = ScheduleHelper.getCurrentClass(tuesday, pairNumber)
    val algo = values.get(tuesday).asInstanceOf[util.List[Any]].get(pairNumber)

    lesson shouldBe algo
  }

  "Tuesday second pair" should "be diff equations" in {
    val tuesday = 1
    val pairNumber = 2

    val lesson = ScheduleHelper.getCurrentClass(tuesday, pairNumber)
    val diff = values.get(tuesday).asInstanceOf[util.List[Any]].get(pairNumber)

    lesson shouldBe diff
  }

  "Tuesday last" should "be complexity" in {
    val tuesday = 1
    val pairNumber = 4

    val lesson = ScheduleHelper.getCurrentClass(tuesday, pairNumber)
    val complexity = values.get(tuesday).asInstanceOf[util.List[Any]].get(pairNumber)

    lesson shouldBe complexity
  }

  "Thursday first pair" should "be optimizations" in {
    val thursday = 3
    val pairNumber = 0

    val lesson = ScheduleHelper.getCurrentClass(thursday, pairNumber)
    val optimizations = values.get(thursday).asInstanceOf[util.List[Any]].get(pairNumber)

    lesson shouldBe optimizations
  }

  "Thursday fourth pair" should "be relax" in {
    val thursday = 3
    val pairNumber = 4

    val lesson = ScheduleHelper.getCurrentClass(thursday, pairNumber)

    lesson shouldBe ScheduleHelper.noLessonsMessage
  }

  "Thursday last" should "be bio" in {
    val thursday = 3
    val pairNumber = 5

    val lesson = ScheduleHelper.getCurrentClass(thursday, pairNumber)
    val bio = values.get(thursday).asInstanceOf[util.List[Any]].get(pairNumber)

    lesson shouldBe bio
  }

}