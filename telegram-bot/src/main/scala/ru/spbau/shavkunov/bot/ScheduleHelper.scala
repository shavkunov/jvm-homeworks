package ru.spbau.shavkunov.bot

import java.text.SimpleDateFormat
import java.util
import java.util.{Calendar, Date}

import org.boon.json.JsonFactory
import org.boon.json.ObjectMapper

import scala.collection.mutable
import scala.collection.parallel.immutable

object ScheduleHelper {
  val noLessonsMessage = "Расслабься, сейчас нет пары :)"
  val scheduleSpreadsheetId = "1aKnrG37vTZw6xyiHV4NJ0ZMeUU1sy0f2hTOHNEQFDGo"
  val apiKey: String = GoogleKey.apiKey

  def getScheduleData: String = {
    val sheetUrl = s"https://sheets.googleapis.com/v4/spreadsheets/" +
                   s"$scheduleSpreadsheetId/values/Schedule302!B4:G9?key=$apiKey"
    val json = scala.io.Source.fromURL(sheetUrl).mkString

    return json
  }

  def getCurrentTime(): String = {
    val today = Calendar.getInstance().getTime
    val minuteFormat = new SimpleDateFormat("mm")
    val hourFormat = new SimpleDateFormat("hh")

    val currentHour = hourFormat.format(today)
    val currentMinute = minuteFormat.format(today)

    return s"$currentHour:$currentMinute"
  }

  def getDayOfWeek(shift: Int): Int = {
    val today = Calendar.getInstance().getTime
    val dayFormat = new SimpleDateFormat("EEEE")

    var day = 0
    dayFormat.format(today) match {
      case "Monday" => day = 0
      case "Tuesday" => day = 1
      case "Wednesday" => day = 2
      case "Thursday" => day = 3
      case "Friday" => day = 4
      case "Saturday" => day = 5
      case _ => day = 6
    }

    var result = (day + shift) % 7

    if (result == 6 && shift > 0) {
      result = 0
    }

    return result
  }

  def getClassNumber(shift: Int): Int = {
    val time = getCurrentTime()

    var currentClassNumber = -1
    for (lessonTime <- TimeTable.getTiming().keys) {
      if (time >= lessonTime) {
        currentClassNumber = TimeTable.getTiming()(lessonTime)
      }
    }

    return currentClassNumber + shift
  }


  def getDay(shift: Int): util.List[Any] = {
    val currentDay = getDayOfWeek(shift)
    val currentClassNumber = getClassNumber(0)
    val schedule = getScheduleData

    val mapper = JsonFactory.create
    val json: util.Map[String, Any] = mapper.fromJson(schedule).asInstanceOf[util.Map[String, Any]]
    val values: util.List[Any] = json.get("values").asInstanceOf[util.List[Any]]

    val currentDayJson: util.List[Any] = values.get(currentDay).asInstanceOf[util.List[Any]]

    return currentDayJson
  }

  def getCurrentClass(): String = {
    val currentDay = getDayOfWeek(0)
    val currentClassNumber = getClassNumber(0)
    val schedule = getScheduleData

    if (currentDay == 6 || currentClassNumber >= 6 || currentClassNumber == -1) {
      return noLessonsMessage
    }

    val currentDayJson: util.List[Any] = getDay(0)
    val currentClass = currentDayJson.get(currentClassNumber).asInstanceOf[String]

    if (currentClass == "") {
      return noLessonsMessage
    }

    return currentClass
  }

  def getNextClass(): String = {
    val currentDay = getDayOfWeek(0)
    val currentClassNumber = getClassNumber(0)
    val schedule = getScheduleData

    val currentDayJson: util.List[Any] = getDay(0)

    var nextClass = ""
    if (currentClassNumber + 1 < currentDayJson.size) {
      return currentDayJson.get(currentClassNumber + 1).asInstanceOf[String]
    }

    if (currentDay == 6 || currentClassNumber + 1 >= currentDayJson.size) {
      var nextDayJson: util.List[Any] = getDay(1)

      if (currentDay == 5 || currentClassNumber + 1 >= currentDayJson.size) {
        nextDayJson = getDay(2)
      }

      nextClass = nextDayJson.stream
                             .filter(obj => !obj.asInstanceOf[String].equals(""))
                             .findFirst
                             .get
                             .asInstanceOf[String]
    }

    return nextClass
  }
}