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
    * Download schedule as json
    * @return our schedule as json
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
  def getDay(shift: Int): util.List[Any] = {
    val currentDay = TimeParser.getDayOfWeek(shift)
    val schedule = getScheduleData

    val mapper = JsonFactory.create
    val json: util.Map[String, Any] = mapper.fromJson(schedule).asInstanceOf[util.Map[String, Any]]
    val values: util.List[Any] = json.get("values").asInstanceOf[util.List[Any]]

    val currentDayJson: util.List[Any] = values.get(currentDay).asInstanceOf[util.List[Any]]

    return currentDayJson
  }

  /**
    * Getting current class name or message that now there is no lessons
    * @return current class name
    */
  def getCurrentClass(): String = {
    val currentDay = TimeParser.getDayOfWeek(0)
    val currentClassNumber = getCurrentClassNumber()

    val currentDayJson: util.List[Any] = getDay(0)

    if (currentClassNumber < currentDayJson.size() || currentClassNumber >= currentDayJson.size()) {
      return noLessonsMessage
    }

    if (currentDay == 7) {
      return noLessonsMessage
    }

    val currentClass = currentDayJson.get(currentClassNumber).asInstanceOf[String]

    if (currentClass == "") {
      return noLessonsMessage
    }

    return currentClass
  }

  /**
    * Getting next class name
    * @return next class name
    */
  def getNextClass(): String = {
    val currentDay = TimeParser.getDayOfWeek(0)
    val currentClassNumber = getCurrentClassNumber()

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