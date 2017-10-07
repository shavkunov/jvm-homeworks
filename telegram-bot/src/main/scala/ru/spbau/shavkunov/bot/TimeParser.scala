package ru.spbau.shavkunov.bot

import java.text.SimpleDateFormat
import java.util.Calendar

/**
  * Object provides methods for working with time
  */
object TimeParser {

  /**
    * Gets current time in HH:MM format
    * @return current time
    */
  def getCurrentTime(): String = {
    val today = Calendar.getInstance().getTime

    val minuteFormat = new SimpleDateFormat("MM")
    val hourFormat = new SimpleDateFormat("HH")

    val currentHour = hourFormat.format(today)
    val currentMinute = minuteFormat.format(today)
    val result = s"$currentHour:$currentMinute"

    return result
  }

  /**
    * Get day of week with shift parameter.
    * if shift parameter is set to zero then returns current day of week
    * If shift parameter is set to one then returns next day and so on.
    * @param shift shift parameter
    * @return day of week
    */
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

    var result = (day + shift + 7) % 7

    if (result == 6 && shift > 0) {
      result = 0
    }

    return result
  }

  /**
    * From time in format HH:MM get time in seconds
    * @param time time in format HH:MM
    * @return seconds if this time
    */
  def getTimeInSeconds(time: String): Int = {
    val creationTime = time.toString.split(':')
    val creationSeconds = creationTime(0).toInt*3600 + creationTime(1).toInt*60

    return creationSeconds
  }

}
