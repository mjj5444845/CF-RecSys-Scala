package SmartRecommendationSystem

import scala.collection.mutable

case class Recommendation(userId: String, recommendedItems: List[String])

object Recommender {

  // Calculate Item Similarity Matrix
  def calculateItemSimilarity(userBehaviorData: List[UserBehavior]): Map[(String, String), Double] = {
    val itemRatings = mutable.Map[String, mutable.Map[String, Double]]().withDefaultValue(mutable.Map[String, Double]())
    
    // Build item rating Matrix
    userBehaviorData.foreach { behavior =>
      if (!itemRatings.contains(behavior.itemId)) {
        itemRatings(behavior.itemId) = mutable.Map[String, Double]()
      }
      itemRatings(behavior.itemId)(behavior.userId) = behavior.rating
    }
    
    // Calculate Item Similarity
    val itemPairs = for {
      item1 <- itemRatings.keys
      item2 <- itemRatings.keys if item1 < item2
    } yield (item1, item2)
    
    val itemSimilarity = itemPairs.map { case (item1, item2) =>
      val usersWhoRatedBoth = itemRatings(item1).keySet.intersect(itemRatings(item2).keySet)
      if (usersWhoRatedBoth.nonEmpty) {
        val dotProduct = usersWhoRatedBoth.map(user => itemRatings(item1)(user) * itemRatings(item2)(user)).sum
        val magnitude1 = math.sqrt(itemRatings(item1).values.map(r => r * r).sum)
        val magnitude2 = math.sqrt(itemRatings(item2).values.map(r => r * r).sum)
        val similarity = dotProduct / (magnitude1 * magnitude2)
        ((item1, item2), similarity)
      } else {
        ((item1, item2), 0.0)
      }
    }.toMap

    itemSimilarity
  }

  // Generate recommendation for every user
  def generateRecommendations(userBehaviorData: List[UserBehavior], itemSimilarity: Map[(String, String), Double]): List[Recommendation] = {
    val userRatings = mutable.Map[String, mutable.Map[String, Double]]().withDefaultValue(mutable.Map[String, Double]())
    
    // Build user rating Matrix
    userBehaviorData.foreach { behavior =>
      if (!userRatings.contains(behavior.userId)) {
        userRatings(behavior.userId) = mutable.Map[String, Double]()
      }
      userRatings(behavior.userId)(behavior.itemId) = behavior.rating
    }
    
    // Generate Recommendation
    val recommendations = userRatings.map { case (userId, ratings) =>
      val scoredItems = mutable.Map[String, Double]().withDefaultValue(0.0)
      
      ratings.foreach { case (itemId, rating) =>
        itemSimilarity.foreach { case ((item1, item2), similarity) =>
          if (itemId == item1 && !ratings.contains(item2)) {
            scoredItems(item2) += similarity * rating
          } else if (itemId == item2 && !ratings.contains(item1)) {
            scoredItems(item1) += similarity * rating
          }
        }
      }
      
      val recommendedItems = scoredItems.toList.sortBy(-_._2).take(5).map(_._1)
      Recommendation(userId, recommendedItems)
    }.toList

    recommendations
  }

  def main(args: Array[String]): Unit = {
    val filePath = "data/user_behavior.csv"
    val userBehaviorData = DataLoader.loadCSV(filePath)
    val cleanedData = DataPreprocessor.cleanData(userBehaviorData)
    val itemSimilarity = calculateItemSimilarity(cleanedData)
    val recommendations = generateRecommendations(cleanedData, itemSimilarity)
    
    recommendations.foreach { recommendation =>
      println(s"User ${recommendation.userId} is recommended items: ${recommendation.recommendedItems.mkString(", ")}")
    }
  }
}
