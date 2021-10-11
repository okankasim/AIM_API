##Getting Started
- You will need java 8 or newer version
After you cloned and open the project in intellij open the pom.xml file and click the "M" maven icon 
on the top right side of the screen or just open pref. and enable auto import
- You will be able to run once all the dependencies are downloaded 
#To run the test;
 ## 1 
 just clicking ">" run icon on "src/test/java/com/aim_tests/Test.java" file should be enough.
- Or if you want to use a command line to run the tests; you will need to download Maven on your comp. 
and configure it in the project then you can just run 
## 2 
command to run all the test.
--> mvn clean test
## To See Reports
Basically open the target/surefire-reports folder and right click on "emailable-report.html" file open in a browser
## Note; 
First option will be easiest if you are using IntelliJ

## Structure of the project
--- src/test/java/com/
            -- aim_tests
                -Tests.java --> All the positive and negative tests are here
            -- POJO(Serialization/Deserialization)
                - Item.java --> this is needed to set json body for posting
                - SKUItem.java --> This is another Serialization/Deserialization class to use in the tests 
                                    while asserting actual and expected values. it includes the metadata, body and retryAttempts
                - ResponseMetadata.java --> Contains HTTPHeaders and HttpStatusCode and RequestID
            -- ConfigReader.java --> this file reads the configurations in the config.properties file
            -- Utilities.java --> I utilized common methods to prevent redundant code
            -- JsonReader.java --> Reads the data from json file and returns Item clsss object for making post requests
      --Config.properties --> this file would be have more information normally if this was an real project
                              such as user, urls, some common fileds.
      --  pom.xml --> for external libraries(dependencies) 
      
## Note
 There are a lot of issues I found and left some comments on the test methods
such as being able to make a call for a not existing sku, or making a http request to delete an item that is 
already been deleted or delete an item that's not exist at all. And still were getting 200 sucess code.
So those tests are going to fail as I would consider them as bug.

            
            


