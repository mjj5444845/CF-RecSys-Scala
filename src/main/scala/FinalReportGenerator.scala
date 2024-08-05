package SmartRecommendationSystem

import java.io.{BufferedWriter, FileWriter}
import scala.concurrent.Await
import scala.concurrent.duration._

// 导入其他文件中的类型和对象
import DataLoader._
import DataPreprocessor._
import DataAnalyzer._
import Recommender._
import ConcurrencyHandler._

object FinalReportGenerator {

  def generateReport(userBehaviorData: List[UserBehavior], analysisResult: AnalysisResult, recommendations: List[Recommendation]): Unit = {
    val reportFile = "reports/final_report.txt"
    val writer = new BufferedWriter(new FileWriter(reportFile))

    // 写入分析结果
    writer.write("用户行为数据分析报告\n")
    writer.write("========================\n\n")

    writer.write("用户活跃度分析:\n")
    analysisResult.userActivity.foreach { case (userId, count) =>
      writer.write(s"用户 $userId 有 $count 次活动\n")
    }
    writer.write("\n")

    writer.write("商品受欢迎度分析:\n")
    analysisResult.itemPopularity.foreach { case (itemId, count) =>
      writer.write(s"商品 $itemId 有 $count 次评分\n")
    }
    writer.write("\n")

    writer.write("评分分布分析:\n")
    analysisResult.ratingDistribution.foreach { case (rating, count) =>
      writer.write(s"评分 $rating 出现了 $count 次\n")
    }
    writer.write("\n")

    // 写入推荐结果
    writer.write("推荐结果:\n")
    writer.write("==========\n\n")

    recommendations.foreach { recommendation =>
      writer.write(s"用户 ${recommendation.userId} 推荐的商品: ${recommendation.recommendedItems.mkString(", ")}\n")
    }

    writer.close()
    println(s"报告已生成：$reportFile")
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
