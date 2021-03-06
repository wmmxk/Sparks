// Databricks notebook source exported at Sat, 24 Oct 2015 13:25:24 UTC

// MAGIC %md
// MAGIC <a rel="license" href="http://creativecommons.org/licenses/by-nc-nd/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-nc-nd/4.0/88x31.png" /></a><br />This work is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nc-nd/4.0/">Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License</a>.

// COMMAND ----------

// MAGIC %md
// MAGIC # Wikipedia: TF-IDF with Normalization for K-Means
// MAGIC  
// MAGIC In this lab, we explore generating a k-means model to cluster Wikipedia articles.  This clustering could be used as part of an exploratory data analysis (EDA) process or as a way to build features for a supervised learning technique.
// MAGIC  
// MAGIC We'll create a `Pipeline` that can be used to make the cluster predictions.  This lab will make use of `RegexTokenizer`, `HashingTF`, `IDF`, `Normalizer`, `Pipeline`, and `KMeans`.  You'll also see how to perform a stratified random sample.

// COMMAND ----------

// MAGIC %md
// MAGIC Load in the data.

// COMMAND ----------

val dfSmall = sqlContext.read.load("/mnt/ml-class/smallwiki.parquet")

// COMMAND ----------

// MAGIC %md
// MAGIC Filter out non-relevant data.

// COMMAND ----------

val parsed = dfSmall.filter(($"title" !== "<PARSE ERROR>") &&
                              $"redirect_title".isNull &&
                              $"text".isNotNull)
parsed.take(1)

// COMMAND ----------

// MAGIC %md
// MAGIC Use a regular expression to tokenize (split into words).  Pattern defaults to matching the separator, but can be set to match tokens instead.

// COMMAND ----------

import org.apache.spark.ml.feature.RegexTokenizer
 
val tokenizer = new RegexTokenizer()
  .setInputCol("text")
  .setOutputCol("words")
  .setPattern("\\W+")

// COMMAND ----------

// MAGIC %md
// MAGIC Create a `HashingTF` transformer to hash words to buckets with counts, then use an `IDF` estimator to compute inverse-document frequency for buckets based on how frequently words have hashed to those buckets in the given documents.  Next, normalize the tf-idf values so that the \\( l^2 \\) norm is one for each row.

// COMMAND ----------

import org.apache.spark.ml.feature.{IDF, HashingTF, Normalizer}
 
val hashingTF = new HashingTF()
  .setNumFeatures(10000)
  .setInputCol(tokenizer.getOutputCol)
  .setOutputCol("hashingTF")
 
val idf = new IDF()
  .setMinDocFreq(10)
  .setInputCol(hashingTF.getOutputCol)
  .setOutputCol("idf")
 
val normalizer = new Normalizer()
  .setInputCol(idf.getOutputCol)
  .setOutputCol("features")

// COMMAND ----------

// MAGIC %md
// MAGIC Now, let's build the `KMeans` estimator and a `Pipeline` that will contain all of the stages.  We'll then call fit on the `Pipeline` which will give us back a `PipelineModel`.  This will take about a minute to run.

// COMMAND ----------

import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.clustering.KMeans
 
val kmeans = new KMeans()
  .setFeaturesCol("features")
  .setPredictionCol("prediction")
  .setK(5)
  .setSeed(0)
 
val pipeline = new Pipeline()
  .setStages(Array(tokenizer, hashingTF, idf, normalizer, kmeans))
 
val model = pipeline.fit(parsed)

// COMMAND ----------

// MAGIC %md
// MAGIC Let's take a look at a sample of the data to see if we can see a pattern between predicted clusters and titles.  We'll use a stratified sample to over-weight the less frequent predictions for inspection purposes.

// COMMAND ----------

val predictions = model.transform(parsed)
val stratifiedMap = Map(0 -> .03, 1 -> .04, 2 -> .06, 3 -> .4, 4 -> .005)
val sampleDF = predictions.stat.sampleBy("prediction", stratifiedMap, 0)
display(sampleDF.select("title", "prediction").orderBy("prediction"))

// COMMAND ----------

predictions.columns

// COMMAND ----------

display(predictions.select("features"))
