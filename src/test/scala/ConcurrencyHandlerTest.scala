import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scala.concurrent.Await
import scala.concurrent.duration._

class ConcurrencyHandlerTest extends AnyFlatSpec with Matchers {

  "ConcurrencyHandler" should "concurrently calculate item similarity" in {
    val userBehaviorData = List(
      UserBehavior("1", "101", 5.0, 1234567890.0),
      UserBehavior("1", "102", 3.5, 1234567891.0),
      UserBehavior("2", "101", 4.0, 1234567892.0),
      UserBehavior("3", "103", 4.5, 1234567893.0)
    )

    val itemSimilarityFuture = ConcurrencyHandler.calculateItemSimilarityConcurrently(userBehaviorData)
    val itemSimilarity = Await.result(itemSimilarityFuture, 5.seconds)

    itemSimilarity should not be empty
    itemSimilarity.values.foreach { similarity =>
      similarity should be >= 0.0
      similarity should be <= 1.0
    }
  }

  "ConcurrencyHandler" should "concurrently generate recommendations" in {
    val userBehaviorData = List(
      UserBehavior("1", "101", 5.0, 1234567890.0),
      UserBehavior("1", "102", 3.5, 1234567891.0),
      UserBehavior("2", "101", 4.0, 1234567892.0),
      UserBehavior("3", "103", 4.5, 1234567893.0)
    )

    val itemSimilarity = Await.result(ConcurrencyHandler.calculateItemSimilarityConcurrently(userBehaviorData), 5.seconds)
    val recommendationsFuture = ConcurrencyHandler.generateRecommendationsConcurrently(userBehaviorData, itemSimilarity)
    val recommendations = Await.result(recommendationsFuture, 5.seconds)

    recommendations should not be empty
    recommendations.foreach { recommendation =>
      recommendation.recommendedItems should not be empty
    }
  }
}
