import AssemblyKeys._

name := "TwitterAnalisis"

version := "0.1"

scalaVersion := "2.10.6"

libraryDependencies += "org.apache.spark" % "spark-core_2.10" % "1.6.1"
libraryDependencies += "org.apache.spark" % "spark-sql_2.10" % "1.6.1"
libraryDependencies += "org.apache.spark" % "spark-hive_2.10" % "1.6.1"
libraryDependencies += "org.apache.spark" % "spark-yarn_2.10" % "1.6.1"
libraryDependencies += "org.apache.spark" % "spark-streaming_2.10" % "1.6.1"
libraryDependencies += "org.apache.spark" % "spark-streaming-twitter_2.10" % "1.6.1"

libraryDependencies += "edu.stanford.nlp" % "stanford-corenlp" % "3.5.1"
libraryDependencies += "edu.stanford.nlp" % "stanford-corenlp" % "3.5.1" classifier "models"

assemblySettings
mergeStrategy in assembly := {
  case m if m.toLowerCase.endsWith("manifest.mf")          => MergeStrategy.discard
  case m if m.toLowerCase.matches("meta-inf.*\\.sf$")      => MergeStrategy.discard
  case "log4j.properties"                                  => MergeStrategy.discard
  case m if m.toLowerCase.startsWith("meta-inf/services/") => MergeStrategy.filterDistinctLines
  case "reference.conf"                                    => MergeStrategy.concat
  case _                                                   => MergeStrategy.first
}
    