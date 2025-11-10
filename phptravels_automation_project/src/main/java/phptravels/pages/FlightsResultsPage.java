package phptravels.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class FlightsResultsPage {

    private WebDriver driver;
    private By priceText = By.cssSelector("strong.text-primary");

    public FlightsResultsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void printCheapestTwo() {
        List<WebElement> prices = driver.findElements(priceText);

        if (prices.size() < 2) {
            System.out.println("❌ Not enough flights found");
            return;
        }

        System.out.println("✅ Cheapest Flight: " + prices.get(0).getText());
        System.out.println("✅ Second Cheapest Flight: " + prices.get(1).getText());
    }
}
