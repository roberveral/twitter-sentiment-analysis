package com.github.roberveral.twitteranalisis.sentiment

import java.util.Properties

import Sentiment.Sentiment
import edu.stanford.nlp.ling.CoreAnnotations
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations
import edu.stanford.nlp.pipeline.StanfordCoreNLP
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations

import scala.collection.convert.wrapAll._

/**
  * Performs a sentiment analysis on a given text.
  *
  * @author Roberto Veral
  * Created on 14/06/2016.
  */
object SentimentAnalyzer {
  private val props = new Properties()
  props.setProperty("annotators", "tokenize, ssplit, parse, sentiment")
  private val pipeline: StanfordCoreNLP = new StanfordCoreNLP(props)

  /**
    * Obtains the sentiment expressed in the given text.
    * The input cannot be null or empty.
    * @param text input text to obtain the sentiment
    * @return either the obtained sentiment, or an error message if the argument
    *         is null or empty.
    */
  def getSentiment(text: String): Either[String, Sentiment] = Option(text) match {
    case Some(input) => Right(extractSentiment(input))
    case None => Left("Input cannot be null or empty")
  }

  private def extractSentiment(text: String): Sentiment =
    pipeline.process(text).get(classOf[CoreAnnotations.SentencesAnnotation])
      .map(sentence => (sentence, sentence.get(classOf[SentimentCoreAnnotations.AnnotatedTree])))
      .map { case (sentence, tree) => (sentence.toString, Sentiment.toSentiment(RNNCoreAnnotations.getPredictedClass(tree))) }
      .maxBy { case (sentence, _) => sentence.length }
      ._2
}
