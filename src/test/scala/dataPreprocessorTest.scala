import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DataPreprocessorTest extends AnyFlatSpec with Matchers {

  "DataPreprocessor" should "clean data by removing invalid records" in {
    val userBehaviorData = List(
      UserBehavior("1", "101", 5.0, 1234567890.0),
      UserBehavior("", "102", 3.5, 1234567891.0),      
      UserBehavior("2", "103", -1.0, 1234567892.0),    
      UserBehavior("3", "104", 6.0, 1234567893.0),     
      UserBehavior("4", "105", 4.0, -1234567894.0)     
    )

    val cleanedData = DataPreprocessor.cleanData(userBehaviorData)

    cleanedData should have size 1
    cleanedData.head.userId shouldBe "1"
    cleanedData.head.itemId shouldBe "101"
    cleanedData.head.rating shouldBe 5.0
    cleanedData.head.timestamp shouldBe 1234567890.0
  }
}
