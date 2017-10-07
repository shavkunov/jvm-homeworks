package ru.spbau.shavkunov.bot

import info.mukel.telegrambot4s.api.declarative.Commands
import info.mukel.telegrambot4s.api.{Polling, TelegramBot}

import scala.util.matching.Regex

/**
  * Schedule Bot for helping with schedule!
  * We can ask about current or next lesson if you forgot your class.
  * @param token telegram bot token(ask botfather)
  */
class ScheduleBot(val token: String) extends TelegramBot with Polling with Commands {
  val getCurrentClassPattern: Regex = raw"[Кк]акая сейчас пара[\\?]?".r
  val getNextClassPattern: Regex = raw"[Кк]акая следующая пара[\\?]?".r
  val anotherMessage = "Ничего не понял, нормально спроси"

  onMessage {
    implicit message =>
      message.text.get match {
        case this.getCurrentClassPattern() => reply(ScheduleHelper.getCurrentClass())
        case this.getNextClassPattern() => reply(ScheduleHelper.getNextClass())
        case _ => reply(this.anotherMessage)
      }
  }
}