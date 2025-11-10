package phptravels.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class WaitUtils {
 private WebDriver driver;
 private WebDriverWait wait;
 public WaitUtils(WebDriver driver){
   this.driver=driver;
   wait=new WebDriverWait(driver, Duration.ofSeconds(20));
 }
 public WebElement waitClickable(By loc){return wait.until(ExpectedConditions.elementToBeClickable(loc));}
 public void click(By loc){waitClickable(loc).click();}
 public void sendKeys(By loc,String txt){wait.until(ExpectedConditions.visibilityOfElementLocated(loc)).sendKeys(txt);}
 public void waitResults(){try{Thread.sleep(4000);}catch(Exception e){}}
}
