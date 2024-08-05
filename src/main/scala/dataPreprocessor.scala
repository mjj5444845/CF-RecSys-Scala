package SmartRecommendationSystem

object DataPreprocessor {

  def cleanData(userBehaviorData: List[UserBehavior]): List[UserBehavior] = {
    val nonNullData = userBehaviorData.filter { behavior =>
      behavior.userId.nonEmpty && behavior.itemId.nonEmpty && behavior.rating >= 0.0 && behavior.timestamp > 0.0
    }
    
    val cleanedData = nonNullData.filter { behavior =>
      behavior.rating >= 0.0 && behavior.rating <= 5.0
    }

    cleanedData
  }

  def main(args: Array[String]): Unit = {
    val filePath = "data/user_behavior.csv"
    val userBehaviorData = DataLoader.loadCSV(filePath)
    val cleanedData = cleanData(userBehaviorData)
    
    cleanedData.foreach(println)
  }
}
