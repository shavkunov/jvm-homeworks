package ru.spbau.shavkunov.bot

/**
  * Starting application
  */
object Main extends App {
  val token = "446610734:AAGy2gvqIC9lGlhYnwO2sUkWZJDLEDfD2H8"

  val bot = new ScheduleBot(token)

  bot.run()
}