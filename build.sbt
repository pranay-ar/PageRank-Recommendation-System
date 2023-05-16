name := "MovieLensPageRank"

version := "1.0"

scalaVersion := "2.12.10"

libraryDependencies += "org.apache.spark" %% "spark-core" % "3.1.2"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.1.2"
libraryDependencies += "org.apache.spark" %% "spark-graphx" % "3.1.2"
libraryDependencies += "org.neo4j.spark" %% "neo4j-connector" % "4.0.1_for_spark_2.4"

