import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DataAnalyzerTest extends AnyFlatSpec with Matchers {

  "DataAnalyzer" should "analyze user behavior data correctly" in {
    val userBehaviorData = List(
      UserBehavior("1", "101", 5.0, 1234567890.0),
      UserBehavior("1", "102", 3.5, 1234567891.0),
      UserBehavior("2", "101", 4.0, 1234567892.0),
      UserBehavior("3", "103", 4.5, 1234567893.0)
    )

    val result = DataAnalyzer.analyzeData(userBehaviorData)

    result.userActivity should contain ("1" -> 2)
    result.userActivity should contain ("2" -> 1)
    result.userActivity should contain ("3" -> 1)

    result.itemPopularity should contain ("101" -> 2)
    result.itemPopularity should contain ("102" -> 1)
    result.itemPopularity should contain ("103" -> 1)

    result.ratingDistribution should contain (5.0 -> 1)
    result.ratingDistribution should contain (3.5 -> 1)
    result.ratingDistribution should contain (4.0 -> 1)
    result.ratingDistribution should contain (4.5 -> 1)
  }
}
