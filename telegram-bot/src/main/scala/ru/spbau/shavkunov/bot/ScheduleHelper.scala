package ru.spbau.shavkunov.bot

import java.util

import org.boon.json.JsonFactory

/**
  * Object that provides desirable commands
  */
object ScheduleHelper {
  val noLessonsMessage = "Расслабься, сейчас нет пары :)"

  /**
    * Link to google sheet with schedule
    */
  val scheduleSpreadsheetId = "1aKnrG37vTZw6xyiHV4NJ0ZMeUU1sy0f2hTOHNEQFDGo"
  val apiKey: String = GoogleKey.apiKey

  /**
    * Download json schedule as string
    * @return our schedule as string
    */
  def getScheduleData: String = {
    val sheetUrl = s"https://sheets.googleapis.com/v4/spreadsheets/" +
                   s"$scheduleSpreadsheetId/values/Schedule302!B4:G9?key=$apiKey"
    val json = scala.io.Source.fromURL(sheetUrl).mkString

    return json
  }

  /**
    * Identifying the order of current class
    * @return order of current class
    */
  def getCurrentClassNumber(): Int = {
    val time = TimeParser.getCurrentTime()

    var currentClassNumber = -1
    for (lessonTime <- TimeTable.getTiming().keys) {
      val currentSeconds = TimeParser.getTimeInSeconds(time)
      if (currentSeconds >= lessonTime) {
        currentClassNumber = TimeTable.getTiming()(lessonTime)
      }
    }

    return currentClassNumber
  }

  /**
    * Get row specified by day. If shift parameter set to zero then method will return current day timetable.
    * Otherwise, timetable of "shifted" day. If shift is set to one, then returns next day and so on.
    * @param shift shift parameter
    * @return specified timetable
    */
  def getDay(day: Int, shift: Int): util.List[Any] = {
    val schedule = getScheduleData

    val mapper = JsonFactory.create
    val json: util.Map[String, Any] = mapper.fromJson(schedule).asInstanceOf[util.Map[String, Any]]
    val values: util.List[Any] = json.get("values").asInstanceOf[util.List[Any]]

    val dayJson: util.List[Any] = values.get(TimeParser.getDay(day, shift)).asInstanceOf[util.List[Any]]

    return dayJson
  }

  /**
    * Same as getCurrentClass but with explicit parameters
    * @param currentDay current day
    * @param currentClassNumber current order of class
    * @return class name
    */
  def getCurrentClass(currentDay: Int, currentClassNumber: Int): String = {
    val currentDayJson: util.List[Any] = getDay(currentDay, 0)

    if (currentClassNumber < 0 || currentClassNumber >= currentDayJson.size()) {
      return noLessonsMessage
    }

    val currentClass = currentDayJson.get(currentClassNumber).asInstanceOf[String]

    if (currentClass == "") {
      return noLessonsMessage
    }

    return currentClass
  }

  /**
    * Getting current class name or message that now there is no lessons
    * @return current class name
    */
  def getCurrentClass(): String = {
    val currentDay = TimeParser.getDayOfWeek(0)
    val currentClassNumber = getCurrentClassNumber()

    return getCurrentClass(currentDay, currentClassNumber)
  }

  /**
    * Same as getNextClass but with explicit parameters
    * @param currentDay current day
    * @param currentClassNumber current order of class
    * @return class name
    */
  def getNextClass(currentDay: Int, currentClassNumber: Int): String = {
    val currentDayJson: util.List[Any] = getDay(currentDay, 0)

    var nextClass = ""
    if (currentClassNumber + 1 < currentDayJson.size) {
      return currentDayJson.stream
                           .skip(currentClassNumber + 1)
                           .filter(obj => !obj.asInstanceOf[String].equals(""))
                           .findFirst
                           .get
                           .asInstanceOf[String]
    }

    if (currentClassNumber + 1 >= currentDayJson.size) {
      var nextDayJson: util.List[Any] = getDay(currentDay, 1)

      nextClass = nextDayJson.stream
        .filter(obj => !obj.asInstanceOf[String].equals(""))
        .findFirst
        .get
        .asInstanceOf[String]
    }

    return nextClass
  }

  /**
    * Getting next class name
    * @return next class name
    */
  def getNextClass(): String = {
    val currentDay = TimeParser.getDayOfWeek(0)
    val currentClassNumber = getCurrentClassNumber()

    getNextClass(currentDay, currentClassNumber)
  }
}