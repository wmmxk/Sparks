{"version":"NotebookV1","origId":503877321548413,"name":"01-Demo-Spark-Session","language":"scala","commands":[{"version":"CommandV1","origId":503877321548415,"guid":"28b972dd-a86a-4737-8473-234253ac4258","subtype":"command","commandType":"auto","position":1.0,"command":"%md\n# Demo 2. SparkSession - the new entry point","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"9683eea1-5636-4caa-9d53-08b3355a0aab"},{"version":"CommandV1","origId":503877321548416,"guid":"bd09ed77-c416-4f33-82aa-2f50190323c1","subtype":"command","commandType":"auto","position":2.0,"command":"import org.apache.spark.sql.Dataset\n\nimplicit class DatasetDisplay(ds: Dataset[_]) {\n  def display(): Unit = {\n    com.databricks.backend.daemon.driver.EnhancedRDDFunctions.display(ds)\n  }\n}","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"81bedae9-9d0a-4104-b02d-8c79bec99a15"},{"version":"CommandV1","origId":503877321548417,"guid":"25dad604-ecf6-41df-97bf-16243e992979","subtype":"command","commandType":"auto","position":3.0,"command":"%md\n### Unified entry point for reading data","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"98b9a8cd-127c-4227-80d5-939844512d31"},{"version":"CommandV1","origId":503877321548418,"guid":"0f55a4e3-74b7-4721-80c8-5246f6be8a45","subtype":"command","commandType":"auto","position":4.0,"command":"spark","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"aad9c271-2d51-44a4-9a35-5615f35acf7b"},{"version":"CommandV1","origId":503877321548419,"guid":"2a6f2c7d-b4a9-48f0-bb3a-f1302f4a973e","subtype":"command","commandType":"auto","position":5.0,"command":"val df = spark.read.option(\"header\", \"true\").option(\"inferSchema\", \"true\").csv(\"/databricks-datasets/samples/population-vs-price/data_geo.csv\")","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"eaa687b3-caac-4af6-825c-9325d2ec762f"},{"version":"CommandV1","origId":503877321548420,"guid":"15d3369e-8879-47aa-9835-660a79541348","subtype":"command","commandType":"auto","position":6.0,"command":"%md\n### Working with config options","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"d70302c7-9ac7-488c-a343-954498c44080"},{"version":"CommandV1","origId":503877321548421,"guid":"8708871f-1f1d-48d4-a766-d0cf496ef7ae","subtype":"command","commandType":"auto","position":7.0,"command":"// The configs are mutable and can be used to toggle optimizer behavior.\n// Configs are also automatically propagated to Hadoop Configuration during I/O.\nspark.conf.set(\"spark.some.config\", \"abcd\")","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"27a60060-c3e3-4c5b-973c-c0175e409a88"},{"version":"CommandV1","origId":503877321548422,"guid":"90882ef4-a015-4f65-8f1c-bd4722564274","subtype":"command","commandType":"auto","position":8.0,"command":"spark.conf.get(\"spark.some.config\")","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"6991c1c5-d13b-4ada-b11a-16eacbbfece3"},{"version":"CommandV1","origId":503877321548423,"guid":"783453ed-7ba8-4826-b091-9a339bda79cd","subtype":"command","commandType":"auto","position":9.0,"command":"spark.sql(\"select \\\"${spark.some.config}\\\"\").display()","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"757564c1-1c91-411f-b1cc-81d155906788"},{"version":"CommandV1","origId":503877321548424,"guid":"8495ff28-d21f-4821-af08-8d878caef757","subtype":"command","commandType":"auto","position":10.0,"command":"%md\n### Running SQL over data","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"f4bcb9c3-2344-4563-9b5c-ee7818f220f0"},{"version":"CommandV1","origId":503877321548425,"guid":"784b3563-4910-4a30-ab4f-9525c27dde51","subtype":"command","commandType":"auto","position":11.0,"command":"df.withColumnRenamed(\"2014 rank\", \"rank\")\n  .withColumnRenamed(\"State Code\", \"stateCode\")\n  .withColumnRenamed(\"2014 Population Estimate\", \"popEstimate\")\n  .withColumnRenamed(\"2015 median sales price\", \"medianSalePrice\")\n  .write\n  .saveAsTable(\"geodata\")","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"b8a7cf96-4526-46e7-bbb2-2351e15679f4"},{"version":"CommandV1","origId":503877321548426,"guid":"33c8638f-b151-4504-aa7e-1387444fe554","subtype":"command","commandType":"auto","position":12.0,"command":"spark.sql(\"select * from geodata\").display()","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"603aaa6e-6df0-40c1-a22a-e3e35ff688cf"},{"version":"CommandV1","origId":503877321548427,"guid":"ac4ea531-e11b-4990-924a-a9eb13652f0d","subtype":"command","commandType":"auto","position":13.0,"command":"%md\n### Working with metadata directly","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"f5aa96dc-36db-4c5e-a435-d440e0547b28"},{"version":"CommandV1","origId":503877321548428,"guid":"b8e97e59-2cc7-41b1-a09d-05d510bcbb29","subtype":"command","commandType":"auto","position":14.0,"command":"spark.catalog.listTables().display()","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"2dc51e1d-2051-4514-bfea-28d8cbdf698c"},{"version":"CommandV1","origId":503877321548429,"guid":"fd5cc547-0e4f-426c-bb77-4a2257210bed","subtype":"command","commandType":"auto","position":15.0,"command":"spark.catalog.listColumns(\"geodata\").display()","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"69b4e22a-c0a5-44c4-aca0-3f2ae2548a1c"},{"version":"CommandV1","origId":503877321548430,"guid":"630a0c69-6f3f-4159-bc10-fe1f6acbf970","subtype":"command","commandType":"auto","position":16.0,"command":"%md\n### Also have access to the underlying SparkContext","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"8098f29e-7025-45ff-b052-24541af499df"},{"version":"CommandV1","origId":503877321548431,"guid":"487a22ad-e665-4913-9b22-6b2d40e0eda1","subtype":"command","commandType":"auto","position":17.0,"command":"spark.sparkContext","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"c190dc45-9e05-4995-b69e-f4bbd322a6dd"},{"version":"CommandV1","origId":503877321548432,"guid":"d6d6e2bc-8e79-46f9-a106-45026d0c05aa","subtype":"command","commandType":"auto","position":18.0,"command":"","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0.0,"submitTime":0.0,"finishTime":0.0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"nuid":"a6637995-336d-4cc9-864a-1fbf63f98e1b"}],"dashboards":[],"guid":"b3c9ca93-acd5-4090-9e70-a33cd3dc3d61","globalVars":{},"iPythonMetadata":null,"inputWidgets":{}}