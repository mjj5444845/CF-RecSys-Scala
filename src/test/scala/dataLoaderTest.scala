import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DataLoaderTest extends AnyFlatSpec with Matchers {

  "DataLoader" should "load data from CSV correctly" in {
    val filePath = "data/test_user_behavior.csv"
    val data = DataLoader.loadCSV(filePath)
    
    data should not be empty
    data.head shouldBe a [UserBehavior]
    data.head.userId shouldBe "1"
    data.head.itemId shouldBe "101"
    data.head.rating shouldBe 5.0
    data.head.timestamp shouldBe 1234567890.0
  }
}
