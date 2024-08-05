import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RecommenderTest extends AnyFlatSpec with Matchers {

  "Recommender" should "generate correct item similarity" in {
    val userBehaviorData = List(
      UserBehavior("1", "101", 5.0, 1234567890.0),
      UserBehavior("1", "102", 3.5, 1234567891.0),
      UserBehavior("2", "101", 4.0, 1234567892.0),
      UserBehavior("3", "103", 4.5, 1234567893.0)
    )

    val itemSimilarity = Recommender.calculateItemSimilarity(userBehaviorData)

    itemSimilarity should not be empty
    itemSimilarity.values.foreach { similarity =>
      similarity should be >= 0.0
      similarity should be <= 1.0
    }
  }

  "Recommender" should "generate recommendations for users" in {
    val userBehaviorData = List(
      UserBehavior("1", "101", 5.0, 1234567890.0),
      UserBehavior("1", "102", 3.5, 1234567891.0),
      UserBehavior("2", "101", 4.0, 1234567892.0),
      UserBehavior("3", "103", 4.5, 1234567893.0)
    )

    val itemSimilarity = Recommender.calculateItemSimilarity(userBehaviorData)
    val recommendations = Recommender.generateRecommendations(userBehaviorData, itemSimilarity)

    recommendations should not be empty
    recommendations.foreach { recommendation =>
      recommendation.recommendedItems should not be empty
    }
  }
}
