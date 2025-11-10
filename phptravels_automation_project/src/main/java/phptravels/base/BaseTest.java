package phptravels.base;

import phptravels.factory.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.initDriver();
        System.out.println(">>> Driver initialized");
    }

    @AfterMethod
    public void tearDown() {
        System.out.println(">>> Test completed, closing browser...");
        DriverFactory.quitDriver();
    }
}
