package com.github.roberveral.twitteranalisis.sentiment

/**
  * Enumeration that represents a sentiment.
  *
  * @author Roberto Veral
  * Created on 14/06/2016.
  */
object Sentiment extends Enumeration {
  type Sentiment = Value
  val UNKNOWN, VERY_NEGATIVE, NEGATIVE, NEUTRAL, POSITIVE, VERY_POSITIVE = Value

  def toSentiment(value: Int): Sentiment = value match {
    case 0 => VERY_NEGATIVE
    case 1 => NEGATIVE
    case 2 => NEUTRAL
    case 3 => POSITIVE
    case 4 => VERY_POSITIVE
  }
}
