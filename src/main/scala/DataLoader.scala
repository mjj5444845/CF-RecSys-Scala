package SmartRecommendationSystem


import com.github.tototoshi.csv._
import java.io.File

case class UserBehavior(userId: String, itemId: String, rating: Double, timestamp: Double)

object DataLoader{
    def loadCSV(filePath:String): List[UserBehavior] = {
        val reader = CSVReader.open(new File(filePath))
        val data = reader.allWithHeaders()
        reader.close()
        data.map{ row => UserBehavior(
            row("userId"),
            row("itemId"),
            row("rating").toDouble,
            row("timestamp").toDouble
        )
        }       
    }


    def main(args: Array[String]): Unit = {
        val filePath = "/home/jeffrey/Documents/learning/scala/SmartRecommendationSystem/data/user_behavior.csv"
        val userBehaviorData = loadCSV(filePath)
        userBehaviorData.foreach(println)
    }
}
