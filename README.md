# Small Recommender System using CF
This is a Scala project about small Collaborative Filtering Recommender System. I used this project to learn Scala.

I am still working on this project.

## How to Run the code
Download the directory and use the following command on Linux Terminal

```
sbt run
```

## File Directory
```
SmartRecommendationSystem/
├── data/
│   └── user_behavior.csv
├── reports/
│   └── final_report.txt
├── src/
│   ├── main/
│   │   ├── scala/
│   │   │   ├── DataLoader.scala
│   │   │   ├── DataPreprocessor.scala
│   │   │   ├── DataAnalyzer.scala
│   │   │   ├── Recommender.scala
│   │   │   ├── ConcurrencyHandler.scala
│   │   │   └── FinalReportGenerator.scala
│   ├── test/
│   │   ├── scala/
│   │   │   ├── DataLoaderTest.scala
│   │   │   ├── DataPreprocessorTest.scala
│   │   │   ├── DataAnalyzerTest.scala
│   │   │   ├── RecommenderTest.scala
│   │   │   └── ConcurrencyHandlerTest.scala
├── build.sbt
└── README.md

```