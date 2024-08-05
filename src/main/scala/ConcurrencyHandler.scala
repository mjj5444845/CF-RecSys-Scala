package SmartRecommendationSystem


import scala.concurrent.{Future, ExecutionContext}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection.mutable

object ConcurrencyHandler {

  // calculate the item similarity matrix concurrently
  def calculateItemSimilarityConcurrently(userBehaviorData: List[UserBehavior]): Future[Map[(String, String), Double]] = Future {
    Recommender.calculateItemSimilarity(userBehaviorData)
  }

  // generate commendation concurrently
  def generateRecommendationsConcurrently(userBehaviorData: List[UserBehavior], itemSimilarity: Map[(String, String), Double]): Future[List[Recommendation]] = Future {
    Recommender.generateRecommendations(userBehaviorData, itemSimilarity)
  }

  def main(args: Array[String]): Unit = {
    val filePath = "data/user_behavior.csv"
    val userBehaviorData = DataLoader.loadCSV(filePath)
    val cleanedData = DataPreprocessor.cleanData(userBehaviorData)
    
    val itemSimilarityFuture = calculateItemSimilarityConcurrently(cleanedData)

    itemSimilarityFuture.foreach { itemSimilarity =>
      val recommendationsFuture = generateRecommendationsConcurrently(cleanedData, itemSimilarity)
      
      recommendationsFuture.foreach { recommendations =>
        recommendations.foreach { recommendation =>
          println(s"User ${recommendation.userId} is recommended items: ${recommendation.recommendedItems.mkString(", ")}")
        }
      }
    }

    // prevent the main thread early exit
    Thread.sleep(5000)
  }
}
