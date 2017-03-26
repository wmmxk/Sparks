{"version":"NotebookV1","origId":503877321549052,"name":"90-NLP-KMeans","language":"scala","commands":[{"version":"CommandV1","origId":503877321549054,"guid":"09ef6e12-7e1f-4da9-8a24-f394e0443b29","subtype":"command","commandType":"auto","position":1.0,"command":"%md ####Unsupervised Learning: Text Document Clustering via KMeans\n\nKMeans is an *unsupervised* algorithm. This means it can build a model on *unlabeled data*. (KMeans will group vectors into clusters based on the position (in space) of the vectors themselves, relative to one another, with no human-audited labels.)\n\nThis fact about unsupervised learning makes it easy to train a model on large amounts of unlabeled data. But the challenge is determining whether the results have any actual use in reality.\n\nE.g., with KMeans, do the clusters correspond to anything useful for the business? With topic modeling (another unsupervised technique) do the \"topic\" correspond to coherent, useful topics?\n\nIt's easy to imagine that with too few clusters or topics, or without good feature processing, we will get a valid but garbage model.","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"e828392d-d8bf-4274-893a-71d98d449685"},{"version":"CommandV1","origId":503877321549055,"guid":"1aee1dfd-5be7-4b23-b211-f231cd8b1613","subtype":"command","commandType":"auto","position":2.0,"command":"%md ####Reviewing our Data\n\nIn this example, we will use the mini 20 Newsgroups dataset, that we used earlier, which is a random subset of the original 20 Newsgroups dataset. Each newsgroup is stored in a subdirectory, with each article stored as a separate file.\n\nThe mini dataset consists of 100 articles from the following 20 Usenet newsgroups:\n\nalt.atheism\ncomp.graphics\ncomp.os.ms-windows.misc\ncomp.sys.ibm.pc.hardware\ncomp.sys.mac.hardware\ncomp.windows.x\nmisc.forsale\nrec.autos\nrec.motorcycles\nrec.sport.baseball\nrec.sport.hockey\nsci.crypt\nsci.electronics\nsci.med\nsci.space\nsoc.religion.christian\ntalk.politics.guns\ntalk.politics.mideast\ntalk.politics.misc\ntalk.religion.misc\n\nSome of the newsgroups seem pretty similar on first glance, such as comp.sys.ibm.pc.hardware and comp.sys.mac.hardware, which may affect our results.\n\n__The nice thing about this dataset is that the newsgroup title itself is a form of labeling. That is, we can easily test the hypothesis that the vocabulary used in post documents will in general support a clustering that corresponds to set of newsgroups itself.__","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"d1c1803a-9e24-4bd6-9426-1faab6ee9f7e"},{"version":"CommandV1","origId":503877321549056,"guid":"f8b02a1d-e1f2-48fa-80f9-8078ee028922","subtype":"command","commandType":"auto","position":3.0,"command":"val corpus = spark.read.parquet(\"/databricks-datasets/news20.binary/data-001/training\")\nval corpusDF = corpus.drop('label).drop('id).withColumnRenamed(\"topic\", \"label\")","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"3ef01c22-2421-4cf6-8d5e-4d8fb4acb282"},{"version":"CommandV1","origId":503877321549057,"guid":"6d66829b-ad0b-42c8-8471-d7a19c1f0304","subtype":"command","commandType":"auto","position":4.0,"command":"import org.apache.spark.ml.feature.RegexTokenizer\n\nval tokenizer = new RegexTokenizer()\n  .setPattern(\"[\\\\W_]+\")\n  .setMinTokenLength(4) // Filter away tokens with length < 4\n  .setInputCol(\"text\")\n  .setOutputCol(\"tokens\")\n\n// Tokenize document\nval tokenizedDF = tokenizer.transform(corpusDF)","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"69a001c8-c591-470b-9181-238b03dfffef"},{"version":"CommandV1","origId":503877321549058,"guid":"76f8c1d2-7a0a-4c0d-af0e-f673451c31f0","subtype":"command","commandType":"auto","position":5.0,"command":"import org.apache.spark.ml.feature.StopWordsRemover\n\nval remover = new StopWordsRemover()\n  .setInputCol(\"tokens\")\n  .setOutputCol(\"filtered\")\n\nval filteredDF = remover.transform(tokenizedDF)","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"0130bd22-d759-429c-a8a1-392c93d7a456"},{"version":"CommandV1","origId":503877321549059,"guid":"4fb6d1a3-94ea-4cbe-a35a-0c3ae2187091","subtype":"command","commandType":"auto","position":6.0,"command":"import org.apache.spark.ml.feature.{StringIndexer, IndexToString, VectorIndexer}\n\n// Index labels, adding metadata to the label column.\n// Fit on whole dataset to include all labels in index.\nval labelIndexer = new StringIndexer()\n  .setInputCol(\"label\")\n  .setOutputCol(\"indexedLabel\")\n  .setHandleInvalid(\"skip\")\n  .fit(filteredDF)","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"e9f59171-618d-46d9-b050-3973bcf5e1b3"},{"version":"CommandV1","origId":503877321549060,"guid":"39ad35c3-a8a2-4956-b31a-16ce139e3b86","subtype":"command","commandType":"auto","position":7.0,"command":"filteredDF.show","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"45da6184-7b2c-4407-9ea9-e4a0e6fa9071"},{"version":"CommandV1","origId":503877321549061,"guid":"c3998dfc-cda6-4395-b8da-0e7cd85fd572","subtype":"command","commandType":"auto","position":8.0,"command":"%md Introducing the TF-IDF method for vectorizing a \"bag of words\"\n\nTF: \"Term Frequency\"\n  * normalized for the length of the document\n  * hashed into a fixed-length set of buckets (\"the hashing trick\") so that we don't have an extremely high number of dimensions (count of all distinct tokens)\n  * downside: there will be some hash collisions, where unrelated words get mapped to the same \"dimension\"\n  \nIDF: \"Inverse Document Frequency\"\n  * Normalize word counts based on how frequently a word occurs in the corpus.\n  * Logarithmic transformation so that words which occur in literally every document (100% or 1.0) get weighted down to 0 (ln 1)\n  * Rare words are weighted heavily\n  * Helpful where rare, technical vocabulary constitutes distinguishing features","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"13c035f5-4b38-4721-ae55-f147f4ea373a"},{"version":"CommandV1","origId":503877321549062,"guid":"c0686743-2d6a-4ad7-b8cc-1f562f1c78e7","subtype":"command","commandType":"auto","position":9.0,"command":"import org.apache.spark.ml.feature._\n\nval hashingTF = new HashingTF().setInputCol(\"filtered\").setOutputCol(\"hashingTF\").setNumFeatures(20000)\nval featurizedDataDF = hashingTF.transform(filteredDF)\n\nval idf = new IDF().setInputCol(\"hashingTF\").setOutputCol(\"idfOutput\")\nval idfModel = idf.fit(featurizedDataDF)\n\nval trimmedTFIDFOutput = idfModel.transform(featurizedDataDF).drop(\"text\", \"tokens\", \"filtered\", \"hashingTF\")","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"51e0da9e-d4bd-4e13-aa07-89fd9f0d81cf"},{"version":"CommandV1","origId":503877321549063,"guid":"c2e6759a-0f57-4424-872d-486f739a0b55","subtype":"command","commandType":"auto","position":10.0,"command":"%md Normalizer: What and Why?\n\n* What: Normalizes vectors to unit length using a lp norm (default p=2, so l2 or Euclidean norm)\n  * Can think of this as projecting vectors in n-dimensional space onto the unit (n-1)-sphere, removing 1 dimension\n  \n* Why: Uneven and unpredicted numbers of hash collisions (from the hashing TF) can cause significant difference in norm of otherwise similar feature vectors\n\nE.g., all other things being equal, and considering just 3 dimensions and 3 words:\n\nno-collision: (1, 1, 1) has norm sqrt(3)<br>\ncollision: (3, 0, 0) has norm sqrt(9) == 3","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"3c5fde99-8e2f-4bf7-ac71-2fa102ecdb35"},{"version":"CommandV1","origId":503877321549064,"guid":"cbea44fb-f3c2-4ea9-aafb-d08ef57d6dc1","subtype":"command","commandType":"auto","position":11.0,"command":"val finalDF = new Normalizer()\n  .setInputCol(\"idfOutput\")\n  .setOutputCol(\"features\")\n  .transform(trimmedTFIDFOutput)","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"df9affbf-ebb4-4787-93f8-9b631b3e6aed"},{"version":"CommandV1","origId":503877321549065,"guid":"2a6c01d6-331d-4e57-96d1-6e4848a69aa3","subtype":"command","commandType":"auto","position":12.0,"command":"%md Create the K-Means Model (note: this takes about 2-3 minutes on CE)","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"db0f77c5-6227-41ef-8e3f-db4474d4c5fe"},{"version":"CommandV1","origId":503877321549066,"guid":"37c20570-4650-4b70-882f-305cd44087a4","subtype":"command","commandType":"auto","position":13.0,"command":"import org.apache.spark.ml.clustering.KMeans\n\nval kmeans = new KMeans().setK(20).setSeed(1L)\nkmeans.setFeaturesCol(\"features\")\n\nval model = kmeans.fit(finalDF)","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"03684026-5a90-4201-9274-4f417a657b9e"},{"version":"CommandV1","origId":503877321549067,"guid":"af95231a-9e50-4870-86d6-33e74b5485a6","subtype":"command","commandType":"auto","position":14.0,"command":"// Evaluate clustering by computing Within Set Sum of Squared Errors.\nval WSSSE = model.computeCost(finalDF)\nprintln(s\"Within Set Sum of Squared Errors = $WSSSE\")","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"e1a669c5-bb2a-43e1-9d93-0ac038e248c2"},{"version":"CommandV1","origId":503877321549068,"guid":"64522825-d2df-4571-82f2-180dbb796e1a","subtype":"command","commandType":"auto","position":15.0,"command":"// Shows the result.\nprintln(\"Cluster Centers: \")\nmodel.clusterCenters.foreach(println)","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"1927adc5-7b5a-46a6-8818-0542a0b84ba3"},{"version":"CommandV1","origId":503877321549069,"guid":"4f4b6309-79cc-436f-a97b-4fa95429f1f9","subtype":"command","commandType":"auto","position":16.0,"command":"finalDF.show(5)","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"c6fb1e4f-68d7-4324-a93a-7b35e8683157"},{"version":"CommandV1","origId":503877321549070,"guid":"74a66229-cffc-4208-8039-87a63eaecb89","subtype":"command","commandType":"auto","position":17.0,"command":"val finalWithClusters = model.transform(finalDF).cache()","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"19a746f2-ec21-4828-85a2-f3017f1ac1b2"},{"version":"CommandV1","origId":503877321549071,"guid":"9d2971a8-950a-4f6e-acbb-5b866cc016d7","subtype":"command","commandType":"auto","position":18.0,"command":"display(finalWithClusters.groupBy('prediction).count)","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"3d367401-3a34-4dc9-95fb-c279e6db4ced"},{"version":"CommandV1","origId":503877321549072,"guid":"fa4bbffe-27bc-46a3-b971-d0b1bad738ee","subtype":"command","commandType":"auto","position":19.0,"command":"%md What's going on with the one huge group?\n\nDo the smaller groups correlate to newsgroups in a sensible way?","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"4de2ce9a-3d6c-4367-b6fe-3b65cacc686f"},{"version":"CommandV1","origId":503877321549073,"guid":"a09b9edf-3d0d-48cb-b4a2-8a8e3bfed4e4","subtype":"command","commandType":"auto","position":20.0,"command":"display(finalWithClusters.filter('prediction === 1).groupBy('label).count)","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"466da270-bae7-4d16-baeb-5ac755720644"},{"version":"CommandV1","origId":503877321549074,"guid":"103cca0d-a636-421b-a3a9-c3f4c65d1888","subtype":"command","commandType":"auto","position":21.0,"command":"display(finalWithClusters.filter('label === \"rec.sport.hockey\").groupBy('prediction).count)","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"c7e30cdb-f378-435a-9563-9c98467863bc"},{"version":"CommandV1","origId":503877321549075,"guid":"bc60f7d6-6ebb-4ad7-be4a-4843623a2ade","subtype":"command","commandType":"auto","position":22.0,"command":"display(finalWithClusters.filter('prediction === 19).groupBy('label).count)","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"a7b5fd33-9e04-4b3e-a846-4343213f6749"},{"version":"CommandV1","origId":503877321549076,"guid":"9466c564-b57d-41d5-a6c2-62b04ab5dab1","subtype":"command","commandType":"auto","position":23.0,"command":"display(finalWithClusters.filter('label === \"comp.sys.ibm.pc.hardware\").groupBy('prediction).count)","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"c5e0e105-4207-496d-9531-d7a1383188a6"},{"version":"CommandV1","origId":503877321549077,"guid":"f4eca62a-8fbf-4814-bcef-54d2fa49e827","subtype":"command","commandType":"auto","position":24.0,"command":"%md ####Bonus Lab to Try Later at the Conference: Now can you build a Pipeline to chain all the components of our previous model together?","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"6a3ed871-147b-4e70-9c05-34fc2a040c5f"}],"dashboards":[],"guid":"d0172cd2-a619-42a0-a3b1-e0726b62ed32","globalVars":{},"iPythonMetadata":null,"inputWidgets":{}}