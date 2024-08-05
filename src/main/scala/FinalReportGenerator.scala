package SmartRecommendationSystem

import java.io.{BufferedWriter, FileWriter}
import scala.concurrent.Await
import scala.concurrent.duration._

import DataLoader._
import DataPreprocessor._
import DataAnalyzer._
import Recommender._
import ConcurrencyHandler._

object FinalReportGenerator {

  def generateReport(userBehaviorData: List[UserBehavior], analysisResult: AnalysisResult, recommendations: List[Recommendation]): Unit = {
    val reportFile = "reports/final_report.txt"
    val writer = new BufferedWriter(new FileWriter(reportFile))

    writer.write("User Behavior Data Analysis Report\n")
    writer.write("========================\n\n")

    writer.write("User Activity Analysis:\n")
    analysisResult.userActivity.foreach { case (userId, count) =>
      writer.write(s"User $userId has $count times activity\n")
    }
    writer.write("\n")

    writer.write("Product Popularity analysis:\n")
    analysisResult.itemPopularity.foreach { case (itemId, count) =>
      writer.write(s"Item $itemId has $count ratings\n")
    }
    writer.write("\n")

    writer.write("Rating Distribution:\n")
    analysisResult.ratingDistribution.foreach { case (rating, count) =>
      writer.write(s"Rating $rating appears $count times\n")
    }
    writer.write("\n")

    writer.write("Recommendation Result:\n")
    writer.write("==========\n\n")

    recommendations.foreach { recommendation =>
      writer.write(s"User ${recommendation.userId} recommends: ${recommendation.recommendedItems.mkString(", ")}\n")
    }

    writer.close()
    println(s"Report Generated：$reportFile")
  }

  def main(args: Array[String]): Unit = {
    val filePath = "data/user_behavior.csv"
    val userBehaviorData = DataLoader.loadCSV(filePath)
    val cleanedData = DataPreprocessor.cleanData(userBehaviorData)

    val analysisResult = DataAnalyzer.analyzeData(cleanedData)

    val itemSimilarityFuture = ConcurrencyHandler.calculateItemSimilarityConcurrently(cleanedData)
    val itemSimilarity = Await.result(itemSimilarityFuture, 10.seconds)

    val recommendationsFuture = ConcurrencyHandler.generateRecommendationsConcurrently(cleanedData, itemSimilarity)
    val recommendations = Await.result(recommendationsFuture, 10.seconds)

    generateReport(cleanedData, analysisResult, recommendations)
  }
}
