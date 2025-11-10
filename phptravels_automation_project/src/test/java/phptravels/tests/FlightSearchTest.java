package phptravels.tests;

import phptravels.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class FlightSearchTest {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        System.out.println("🚀 Browser launched");
    }

    @Test
    public void testFlightSearch() throws Exception {
        new HomePage(driver)
                .open()
                .enterFrom("Delhi")
                .enterTo("Dubai")
                .setDeparture("15-12-2025")
                .clickSearch();

        Thread.sleep(5000);
        System.out.println("✅ Test finished");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
        System.out.println("✅ Browser closed");
    }
}
