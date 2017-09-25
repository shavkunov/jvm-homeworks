package ru.spbau.shavkunov.bot

object TimeTable {
  def getTiming(): Map[String, Int] = {
    val timing = Map("00:00" -> -1,
                     "10:00" -> 0,
                     "12:00" -> 1,
                     "14:30" -> 2,
                     "16:30" -> 3,
                     "18:30" -> 4,
                     "19:00" -> 5,
                     "21:30" -> 6)

    return timing
  }
}
