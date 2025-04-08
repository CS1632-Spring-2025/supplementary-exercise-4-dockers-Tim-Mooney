package edu.pitt.cs;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConnectTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless");
    driver = new ChromeDriver(options);
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
    
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void tEST1LINKS() {
    driver.get("http://localhost:8080");
    js.executeScript("document.cookie = \"1=false\";document.cookie = \"2=false\";document.cookie = \"3=false\";");
    driver.manage().window().setSize(new Dimension(1211, 812));
    vars.put("resetbutton", driver.findElement(By.xpath("//a[contains(@href, \'/reset\')]")).getText());
    assertEquals(vars.get("resetbutton").toString(), "Reset");
  }
  @Test
  public void tEST2RESET() {
    driver.get("http://localhost:8080");
    js.executeScript("document.cookie = \"1=true\";document.cookie = \"2=true\";document.cookie = \"3=true\";");
    driver.manage().window().setSize(new Dimension(1211, 812));
    driver.findElement(By.linkText("Reset")).click();
    vars.put("c1", driver.findElement(By.xpath("//div/ul/li")).getText());
    assertEquals(vars.get("c1").toString(), "ID 1. Jennyanydots");
    vars.put("c2", driver.findElement(By.xpath("//div/ul/li[2]")).getText());
    assertEquals(vars.get("c2").toString(), "ID 2. Old Deuteronomy");
    vars.put("c3", driver.findElement(By.xpath("//div/ul/li[3]")).getText());
    assertEquals(vars.get("c3").toString(), "ID 3. Mistoffelees");
  }
  @Test
  public void tEST3CATALOG() {
    driver.get("http://localhost:8080");
    js.executeScript("document.cookie = \"1=false\";document.cookie = \"2=false\";document.cookie = \"3=false\";");
    driver.manage().window().setSize(new Dimension(812, 812));
    driver.findElement(By.linkText("Catalog")).click();
    {
      List<WebElement> elements = driver.findElements(By.xpath("//li[2]/img[@src=\"/images/cat2.jpg\"]"));
      assert(elements.size() > 0);
    }
  }
  @Test
  public void tEST4LISTING() {
    driver.get("http://localhost:8080");
    js.executeScript("document.cookie = \"1=false\";document.cookie = \"2=false\";document.cookie = \"3=false\";");
    driver.manage().window().setSize(new Dimension(812, 812));
    driver.findElement(By.linkText("Catalog")).click();
    {
      List<WebElement> elements = driver.findElements(By.xpath("//div[@id=\'listing\']/ul/li[3]"));
      assert(elements.size() > 0);
    }
    {
      List<WebElement> elements = driver.findElements(By.xpath("//div[@id=\'listing\']/ul/li[4]"));
      assert(elements.size() == 0);
    }
    vars.put("cat3", driver.findElement(By.xpath("//div/ul/li[3]")).getText());
    assertEquals(vars.get("cat3").toString(), "ID 3. Mistoffelees");
  }
  @Test
  public void tEST5RENTACAT() {
    driver.get("http://localhost:8080");
    js.executeScript("document.cookie = \"1=false\";document.cookie = \"2=false\";document.cookie = \"3=false\";");
    driver.manage().window().setSize(new Dimension(812, 812));
    driver.findElement(By.linkText("Rent-A-Cat")).click();
    {
      List<WebElement> elements = driver.findElements(By.xpath("//button[@onclick=\'rentSubmit()\']"));
      assert(elements.size() > 0);
    }
    {
      List<WebElement> elements = driver.findElements(By.xpath("//button[@onclick=\'returnSubmit()\']"));
      assert(elements.size() > 0);
    }
  }
  @Test
  public void tEST6RENT() {
    driver.get("http://localhost:8080");
    js.executeScript("document.cookie = \"1=false\";document.cookie = \"2=false\";document.cookie = \"3=false\";");
    driver.manage().window().setSize(new Dimension(812, 812));
    driver.findElement(By.linkText("Rent-A-Cat")).click();
    driver.findElement(By.xpath("//input[@id=\'rentID\']")).click();
    driver.findElement(By.xpath("//input[@id=\'rentID\']")).sendKeys("1");
    driver.findElement(By.xpath("//button[@onclick=\'rentSubmit()\']")).click();
    vars.put("cat1", driver.findElement(By.xpath("//div/ul/li")).getText());
    assertEquals(vars.get("cat1").toString(), "Rented out");
    vars.put("cat2", driver.findElement(By.xpath("//div/ul/li[2]")).getText());
    assertEquals(vars.get("cat2").toString(), "ID 2. Old Deuteronomy");
    vars.put("cat3", driver.findElement(By.xpath("//div/ul/li[3]")).getText());
    assertEquals(vars.get("cat3").toString(), "ID 3. Mistoffelees");
    {
      List<WebElement> elements = driver.findElements(By.id("rentResult"));
      assert(elements.size() > 0);
    }
  }
  @Test
  public void tEST7RETURN() {
    driver.get("http://localhost:8080");
    js.executeScript("document.cookie = \"1=false\";document.cookie = \"2=true\";document.cookie = \"3=false\";");
    driver.manage().window().setSize(new Dimension(812, 812));
    driver.findElement(By.linkText("Rent-A-Cat")).click();
    vars.put("cat2original", driver.findElement(By.cssSelector("#cat-id2")).getText());
    assertEquals(vars.get("cat2original").toString(), "Rented out");
    driver.findElement(By.id("returnID")).click();
    driver.findElement(By.id("returnID")).sendKeys("2");
    driver.findElement(By.xpath("//button[@onclick=\'returnSubmit()\']")).click();
    vars.put("cat1", driver.findElement(By.cssSelector("#cat-id1")).getText());
    assertEquals(vars.get("cat1").toString(), "ID 1. Jennyanydots");
    vars.put("cat2", driver.findElement(By.cssSelector("#cat-id2")).getText());
    assertEquals(vars.get("cat2").toString(), "ID 2. Old Deuteronomy");
    vars.put("cat3", driver.findElement(By.cssSelector("#cat-id3")).getText());
    assertEquals(vars.get("cat3").toString(), "ID 3. Mistoffelees");
    vars.put("returnresult", driver.findElement(By.id("returnResult")).getText());
    assertEquals(vars.get("returnresult").toString(), "Success!");
  }
  @Test
  public void tEST8FEEDACAT() {
    driver.get("http://localhost:8080");
    js.executeScript("document.cookie = \"1=false\";document.cookie = \"2=false\";document.cookie = \"3=false\";");
    driver.manage().window().setSize(new Dimension(812, 812));
    driver.findElement(By.linkText("Feed-A-Cat")).click();
    vars.put("feedbutton", driver.findElement(By.cssSelector(".btn")).getText());
    assertEquals(vars.get("feedbutton").toString(), "Feed");
  }
  @Test
  public void tEST9FEED() {
    driver.get("http://localhost:8080");
    js.executeScript("document.cookie = \"1=false\";document.cookie = \"2=false\";document.cookie = \"3=false\";");
    driver.manage().window().setSize(new Dimension(812, 812));
    driver.findElement(By.linkText("Feed-A-Cat")).click();
    driver.findElement(By.id("catnips")).click();
    driver.findElement(By.id("catnips")).sendKeys("6");
    driver.findElement(By.cssSelector(".btn")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
      wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\'feedResult\' and text()=\'Nom, nom, nom.\']")));
    }
    vars.put("feedret", driver.findElement(By.id("feedResult")).getText());
    assertEquals(vars.get("feedret").toString(), "Nom, nom, nom.");
  }
  @Test
  public void tEST10GREETACAT() {
    driver.get("http://localhost:8080");
    js.executeScript("document.cookie = \"1=false\";document.cookie = \"2=false\";document.cookie = \"3=false\";");
    driver.manage().window().setSize(new Dimension(812, 812));
    driver.findElement(By.linkText("Greet-A-Cat")).click();
    vars.put("meows", driver.findElement(By.cssSelector("#greeting > h4")).getText());
    assertEquals(vars.get("meows").toString(), "Meow!Meow!Meow!");
  }
  @Test
  public void tEST11GREETACATWITHNAME() {
    driver.get("http://localhost:8080greet-a-cat/Jennyanydots");
    js.executeScript("document.cookie = \"1=false\";document.cookie = \"2=false\";document.cookie = \"3=false\";");
    driver.manage().window().setSize(new Dimension(812, 812));
    vars.put("meows", driver.findElement(By.cssSelector("#greeting > h4")).getText());
    assertEquals(vars.get("meows").toString(), "Meow! from Jennyanydots.");
  }
}
