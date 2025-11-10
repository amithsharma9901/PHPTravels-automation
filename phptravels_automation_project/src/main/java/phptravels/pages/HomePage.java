package phptravels.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait  = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public HomePage open() {
        driver.get("https://phptravels.net/flights");
        System.out.println("✅ Website opened");
        return this;
    }

    // ---------- Helpers ----------
    private void typeAndPickBySuggestion(By input, String city, String cityForContainsXpath) throws InterruptedException {
        WebElement box = wait.until(ExpectedConditions.elementToBeClickable(input));
        box.click();
        box.clear();
        box.sendKeys(city);

        try {
            By sug = By.xpath("//div[contains(@class,'autocomplete-result')]//strong[contains(translate(.,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'"+ cityForContainsXpath.toUpperCase() +"')]");
            WebElement suggestion = new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.elementToBeClickable(sug));
            suggestion.click();
            return;
        } catch (TimeoutException ignored) {}

        box.sendKeys(Keys.ARROW_DOWN);
        Thread.sleep(300);
        box.sendKeys(Keys.ENTER);
        Thread.sleep(300);

        try {
            String val = box.getAttribute("value");
            if (val == null || val.isBlank() || val.length() < 3) {
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input',{bubbles:true})); arguments[0].blur();",
                        box, city
                );
            }
        } catch (Exception ignored) {}
    }

    private void safeJsClick(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    public HomePage enterFrom(String city) {
        try {
            typeAndPickBySuggestion(By.name("from"), city, city);
            System.out.println("✅ FROM: " + city);
        } catch (Exception e) {
            System.out.println("❌ FROM failed: " + e.getMessage());
        }
        return this;
    }

    public HomePage enterTo(String city) {
        try {
            typeAndPickBySuggestion(By.name("to"), city, city);
            System.out.println("✅ TO: " + city);
        } catch (Exception e) {
            System.out.println("❌ TO failed: " + e.getMessage());
        }
        return this;
    }

    public HomePage setDeparture(String date_dd_MM_yyyy) {
        try {
            WebElement dep = wait.until(ExpectedConditions.elementToBeClickable(By.id("departure")));
            dep.click();
            dep.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            dep.sendKeys(date_dd_MM_yyyy);
            dep.sendKeys(Keys.ENTER);
            System.out.println("✅ DATE: " + date_dd_MM_yyyy);
        } catch (Exception e) {
            System.out.println("❌ Date failed: " + e.getMessage());
        }
        return this;
    }

    public HomePage clickSearch() {
        try {
            By[] candidates = new By[] {
                    By.id("flights-search"),
                    By.xpath("//button[@type='submit' and contains(@class,'search_button')]"),
                    By.xpath("//button[contains(.,'Search')]")
            };

            WebElement btn = null;
            for (By c : candidates) {
                try {
                    btn = new WebDriverWait(driver, Duration.ofSeconds(4))
                            .until(ExpectedConditions.elementToBeClickable(c));
                    if (btn != null) break;
                } catch (Exception ignored) {}
            }
            if (btn == null) {
                btn = wait.until(ExpectedConditions.elementToBeClickable(
                        By.cssSelector("form button[type='submit']")));
            }

            safeJsClick(btn);
            System.out.println("✅ Search clicked");
        } catch (Exception e) {
            System.out.println("❌ Search failed: " + e.getMessage());
        }
        return this;
    }
}
