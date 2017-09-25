package ru.spbau.shavkunov.bot

import info.mukel.telegrambot4s.api.declarative.Commands
import info.mukel.telegrambot4s.api.{Polling, TelegramBot}
import info.mukel.telegrambot4s.models.{InlineKeyboardButton, InlineKeyboardMarkup}

import scala.util.matching.Regex

class ScheduleBot(val token: String) extends TelegramBot with Polling with Commands {
  val getCurrentClassPattern: Regex = raw"[Кк]акая сейчас пара[\\?]?".r
  val getNextClassPattern: Regex = raw"[Кк]акая следующая пара[\\?]?".r
  val anotherMessage = "Нормально спроси"

  onMessage {
    implicit message =>
      message.text.get match {
        case this.getCurrentClassPattern() => reply(ScheduleHelper.getCurrentClass())
        case this.getNextClassPattern() => reply(ScheduleHelper.getNextClass())
        case _ => reply(this.anotherMessage)
      }
  }
}