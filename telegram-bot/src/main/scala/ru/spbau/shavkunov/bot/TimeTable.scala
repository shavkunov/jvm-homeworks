package ru.spbau.shavkunov.bot

import scala.collection.immutable.HashMap

object TimeTable {
  def getTiming(): HashMap[Int, Int] = {
    var timing = new HashMap[Int, Int]()
    timing += (ScheduleHelper.getTimeInSeconds("00:00") -> -1)
    timing += (ScheduleHelper.getTimeInSeconds("10:00") -> 0)
    timing += (ScheduleHelper.getTimeInSeconds("12:00") -> 1)
    timing += (ScheduleHelper.getTimeInSeconds("14:30") -> 2)
    timing += (ScheduleHelper.getTimeInSeconds("16:30") -> 3)
    timing += (ScheduleHelper.getTimeInSeconds("18:30") -> 4)
    timing += (ScheduleHelper.getTimeInSeconds("19:00") -> 5)
    timing += (ScheduleHelper.getTimeInSeconds("21:30") -> 6)

    return timing
  }
}
