**Run the application

> On command prompt:
	>> Run the JAR file -->
		 java -jar target/demo-0.0.1-SNAPSHOT.jar



> To Run in IDE
	>> Use any IDE like STS, Eclipse etc.
	>> Import the Maven project
	>> Run the application as Spring Boot App

Logging output is displayed. The service should be up and running within a few seconds.

===================================================================================================
RUN Unit Tests

> On command prompt:
	>> Got to project root directory
	>> Run the tests as -->
		mvnw test
> To Run in IDE
	>> Run the application as JUnit Test
====================================================================================================
Test the service:

Use any of the tools to test web services like Postman, SOap UI etc
Your service will be accessible on http://localhost:8080/ad

API details
1. POST http://localhost:8080/ad
This operation is used to save Ad campaign data
	
	Headers:
	Content-Type:application/json

	Requet Payload:
	{
"partner_id": "abc",
"duration": 60,
"ad_content":"ad content"
}

2. GET http://localhost:8080/ad/abc
This operation is used to retrieve Ad Campaign data

======================================================================================================

	