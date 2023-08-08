# pingSafe-api-test

All the integration test for API's will be automated in this repo using Java, Rest assured, Testng

To start with the project
* Install maven
* install Java20
* clone the repository 
* In Application.properties file change the baseUrl and path to the DB file
* run mvn clean install


* Our testing framework is a combination of TestNG and rest-assured using Java20. 
* We are using maven as our build managing tool. 
* Using this framework we can easily and quickly automate API's and can cover all the test case scenarios even with very limited knowledge. 
* We are using TestNG's rich execution and validation features to test at the service level as well as for all the integration flows. 
* These tests can be run in any environment by simply changing the environment variable.
* The framework will also support extensive integration flows and will help a lot in end-to-end testing. E.g. testing complete customer flow. 
* We can also run specific test cases based on our requirements like smoke, sanity, regression.<TODO>
* We are going to categorize all the test cases while automating. We can also run service-based testing with a combination of different groups. 
* This will be very helpful in our CICD. We will also be able to easily integrate the suite with our CICD pipeline in the future.
* Right now the framework is very basic and written to move forward quickly but it's extensible to support all our futuristic needs.

# Details of the framework
* To start with the project you first need to set the environment proprties(Application.properties file for now).
* This can be set in 2 ways - By passing as system paramters / we can set the same in applicatiom.properties file
* Tests are triggered using testng.xml.
* Once we initiate the test, the test classes will be invoked to execute using testng. 
* Test classes will get the data from the respective dataProvider classes and will invoke the test. All the data will be composed in the dataprovider and will be passed to the test class using testng.
* To call the api, the test class will rely on its respective helper class. Helper class will take care of  calling it’s super class after compiling the request and will serve the test.
* Requests class will be responsible for composing the request using restAssured, calling the request, extracting the response and return to the caller. 
* Test reports can be found in test-Output folder <In futrue allure report would be integrated for more detailed report>