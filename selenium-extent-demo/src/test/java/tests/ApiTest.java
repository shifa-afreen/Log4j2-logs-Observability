package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;

import org.testng.annotations.*;

import utils.ExtentManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApiTest {

    private static final Logger logger = LogManager.getLogger(ApiTest.class);

    ExtentReports extent;
    ExtentTest test;

    @BeforeSuite
    public void setupReport() {
        logger.info("Initializing Extent Report.");
        extent = ExtentManager.getInstance();
    }

    @Test
    public void getPostTest() {
        test = extent.createTest("GET /posts/1 API Test");
        logger.info("Starting API Test: GET /posts/1");

        try {
            logger.info("Sending GET request to https://jsonplaceholder.typicode.com/posts/1");
            Response response = RestAssured.get("https://jsonplaceholder.typicode.com/posts/1");

            int statusCode = response.getStatusCode();
            logger.info("Status Code received: " + statusCode);
            test.log(Status.INFO, "Status Code: " + statusCode);

            if (statusCode == 200) {
                logger.info("Status code 200 OK.");
                test.log(Status.PASS, "Correct status code received: 200");
            } else {
                logger.error("Unexpected status code: " + statusCode);
                test.log(Status.FAIL, "Expected status code 200 but got: " + statusCode);
            }

            JsonPath jsonPath = response.jsonPath();
            String title = jsonPath.getString("title");
            logger.info("Title in response: " + title);
            test.log(Status.INFO, "Title in response: " + title);

            if (title != null && !title.isEmpty()) {
                logger.info("Title is present in the response.");
                test.log(Status.PASS, "Title is present in the response");
            } else {
                logger.error("Title is missing in the response.");
                test.log(Status.FAIL, "Title is missing in the response");
            }

        } catch (Exception e) {
            logger.error("Exception occurred: ", e);
            test.log(Status.FAIL, "Exception occurred: " + e.getMessage());
        }
    }

    @AfterSuite
    public void flushReport() {
        logger.info("Flushing Extent Report.");
        extent.flush();
    }
}
