Observability Log4j2 API 

This repo is a sample TestNG-based API test automation framework that demonstrates:

- Logging API test execution details to the BrowserStack Observability dashboard using Log4j2.
- Generating  test reports using ExtentReports.
- Executing a simple `GET` API test using REST-assured.

Getting Started

 1. Clone the Repo 

```bash
git clone https://github.com/shifa-afreen/Log4j2-logs-Observability.git
cd Log4j2-logs-Observability
code .
````

2. Set Up BrowserStack Credentials

Before running the tests, you must update your BrowserStack credentials in the `browserstack.yml` file located in the root folder.

Update the following fields:

```yaml
username: <your_browserstack_username>
access_key: <your_browserstack_access_key>
```

Key Files

 1. ApiTest.java

Located at `src/test/java/tests/ApiTest.java`, this file:

* Initializes ExtentReports in `@BeforeSuite`
* Sends a `GET` request to `https://jsonplaceholder.typicode.com/posts/1`
* Logs details using Log4j2
* Writes test outcomes to ExtentReports

 2. log4j2.xml

Located at `src/test/resources/log4j2.xml`. This config enables logs to be sent to the Observability dashboard.



3.  pom.xml — Required Dependencies

You must include the following in your `pom.xml` for logging and reporting:


4. Running the API Test : mvn clean test -Dtest=ApiTest




