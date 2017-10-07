package ru.spbau.shavkunov.bot

import scala.collection.immutable.HashMap

object TimeTable {
  def getTiming(): HashMap[Int, Int] = {
    var timing = new HashMap[Int, Int]()
    timing += (TimeParser.getTimeInSeconds("00:00") -> -1)
    timing += (TimeParser.getTimeInSeconds("10:00") -> 0)
    timing += (TimeParser.getTimeInSeconds("12:00") -> 1)
    timing += (TimeParser.getTimeInSeconds("14:30") -> 2)
    timing += (TimeParser.getTimeInSeconds("16:30") -> 3)
    timing += (TimeParser.getTimeInSeconds("18:30") -> 4)
    timing += (TimeParser.getTimeInSeconds("19:00") -> 5)
    timing += (TimeParser.getTimeInSeconds("21:30") -> 6)

    return timing
  }
}
