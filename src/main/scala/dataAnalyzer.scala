package SmartRecommendationSystem

import scala.collection.mutable

case class AnalysisResult(userActivity: Map[String, Int], itemPopularity: Map[String, Int], ratingDistribution: Map[Double, Int])

object DataAnalyzer {

  def analyzeData(userBehaviorData: List[UserBehavior]): AnalysisResult = {
    val userActivity = mutable.Map[String, Int]().withDefaultValue(0)
    val itemPopularity = mutable.Map[String, Int]().withDefaultValue(0)
    val ratingDistribution = mutable.Map[Double, Int]().withDefaultValue(0)

    userBehaviorData.foreach { behavior =>
      userActivity(behavior.userId) += 1
      itemPopularity(behavior.itemId) += 1
      ratingDistribution(behavior.rating) += 1
    }

    AnalysisResult(userActivity.toMap, itemPopularity.toMap, ratingDistribution.toMap)
  }

  def main(args: Array[String]): Unit = {
    val filePath = "data/user_behavior.csv"
    val userBehaviorData = DataLoader.loadCSV(filePath)
    val analysisResult = analyzeData(userBehaviorData)
    
    println("User Activity Analysis:")
    analysisResult.userActivity.foreach { case (userId, count) =>
      println(s"User $userId has $count activities.")
    }
    
    println("\nItem Popularity Analysis:")
    analysisResult.itemPopularity.foreach { case (itemId, count) =>
      println(s"Item $itemId has $count ratings.")
    }
    
    println("\nRating Distribution Analysis:")
    analysisResult.ratingDistribution.foreach { case (rating, count) =>
      println(s"Rating $rating appears $count times.")
    }
  }
}
