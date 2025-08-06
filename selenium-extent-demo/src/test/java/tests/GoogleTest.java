package tests;

import com.aventstack.extentreports.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import utils.ExtentManager;

public class GoogleTest {

    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeSuite
    public void setupReport() {
        extent = ExtentManager.getInstance();
    }

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();  
    }

    @Test
    public void googleSearchTest() throws InterruptedException {
        test = extent.createTest("Google Search Test");

        logToExtentAndBrowserStack("Launching Chrome browser", Status.INFO);
        driver.get("https://www.google.com");
        logToExtentAndBrowserStack("Navigated to Google", Status.PASS);

        Thread.sleep(2000);

        driver.findElement(By.name("q")).sendKeys("BrowserStack");
        logToExtentAndBrowserStack("Entered 'BrowserStack' in the search field", Status.INFO);

        Thread.sleep(1000);

        driver.findElement(By.name("q")).submit();
        logToExtentAndBrowserStack("Submitted the search and the test is done", Status.PASS);

        Thread.sleep(5000);
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
        logToExtentAndBrowserStack("Closed the browser", Status.INFO);
    }

    @AfterSuite
    public void flushReport() {
        extent.flush();
    }

    private void logToExtentAndBrowserStack(String message, Status status) {
        test.log(status, message);
        try {
            if (driver instanceof JavascriptExecutor) {
                ((JavascriptExecutor) driver).executeScript(
                    "browserstack_executor: {\"action\": \"annotate\", \"arguments\": {\"data\":\"" + message + "\", \"level\":\"" + status.toString().toLowerCase() + "\"}}"
                );
            }
        } catch (Exception e) {
            test.log(Status.WARNING, "Failed to send log to BrowserStack: " + e.getMessage());
        }
    }
}
