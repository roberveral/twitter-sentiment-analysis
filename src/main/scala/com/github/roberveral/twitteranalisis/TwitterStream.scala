package com.github.roberveral.twitteranalisis

import java.util.Date

import com.github.roberveral.twitteranalisis.sentiment.Sentiment.Sentiment
import com.github.roberveral.twitteranalisis.sentiment.{Sentiment, SentimentAnalyzer}
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkContext, SparkConf}

case class TwitSentiment(username: String, createdAt: Date, text: String, sentiment: Sentiment)

/**
  * Performs a streaming sentiment analysis of the incoming twits.
  *
  * @author Roberto Veral
  * Created on 01/06/2016.
  */
object TwitterStream {
  def main(args: Array[String]) {
    /* Initialize the Spark configuration properties */
    val conf = new SparkConf().setAppName("twitter-analisis-stream")
    /* Creates a SparkContext execution context */
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")
    /* Creates the streaming context in microbatches of tw seconds */
    val ssc = new StreamingContext(sc, Seconds(2))
    /* Sets the Twitter API Credentials from the command line arguments */
    System.setProperty("twitter4j.oauth.consumerKey", args(0))
    System.setProperty("twitter4j.oauth.consumerSecret", args(1))
    System.setProperty("twitter4j.oauth.accessToken", args(2))
    System.setProperty("twitter4j.oauth.accessTokenSecret", args(3))
    /* Creates a stream of twits for the given filter */
    val stream = TwitterUtils.createStream(ssc, None, Array(args(4)))
    /* Extracts the sentiment only for the english twits */
    val data = stream.filter(_.getLang == "en")
      .map(status => TwitSentiment(status.getUser.getName, status.getCreatedAt, status.getText,
        SentimentAnalyzer.getSentiment(status.getText).right.getOrElse(Sentiment.UNKNOWN)))
    /* Shows the result */
    data.print()
    /* Starts the stream */
    ssc.start()
    ssc.awaitTermination()
  }
}
