import org.apache.spark.sql.SparkSession
import org.apache.spark.graphx._
import org.neo4j.spark._

val neo = Neo4j(spark.sparkContext)

val data = ranks.map(t => Map("id" -> t._1, "rank" -> t._2))

neo.saveGraph(data, "Entity", ("id", "id"), "HAS_PAGE_RANK", ("rank", "rank"))


object MovieLensPageRank {
  def main(args: Array[String]) {
    val spark = SparkSession.builder.appName("MovieLensPageRank").getOrCreate()

    // Load the data
    val ratings = spark.read
      .format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load("ratings.csv")

    // Create the graph
    val users = ratings.select("User_Id").rdd.distinct().map(row => (row.getInt(0).toLong, "user"))
    val movies = ratings.select("Movie_Id").rdd.distinct().map(row => (row.getInt(0).toLong, "movie"))
    val vertices = users.union(movies)
    val edges = ratings.rdd.map(row => Edge(row.getInt(0).toLong, row.getInt(1).toLong, row.getInt(2).toLong))
    val graph = Graph(vertices, edges)

    // Run PageRank
    val ranks = graph.pageRank(0.0001).vertices

    // Print the results
    ranks.collect().foreach(println)

    spark.stop()
  }
}
