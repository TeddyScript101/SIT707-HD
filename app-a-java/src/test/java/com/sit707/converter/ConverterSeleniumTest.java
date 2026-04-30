package com.sit707.converter;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Selenium UI tests - run against the live Spring Boot server.
 * ChromeOptions are configured for headless mode (required by GCP Cloud Build).
 *
 * Excluded from the default surefire run; invoked explicitly in Cloud Build
 * via: mvn test -Dtest=ConverterSeleniumTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConverterSeleniumTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1280,800");
        driver = new ChromeDriver(options);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void ui_homePage_titleContainsUnitConverter() {
        driver.get("http://localhost:" + port + "/");
        Assert.assertTrue(driver.getTitle().contains("Unit Converter"));
    }

    @Test
    public void ui_metersToFeet_1MeterShowsCorrectResult() {
        driver.get("http://localhost:" + port + "/");
        driver.findElement(By.id("value")).sendKeys("1");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        String body = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue("Expected 3.28084 in result", body.contains("3.28084"));
    }

    @Test
    public void ui_feetToMeters_selectionConvertsCorrectly() {
        driver.get("http://localhost:" + port + "/");
        Select direction = new Select(driver.findElement(By.id("direction")));
        direction.selectByValue("fToM");
        driver.findElement(By.id("value")).sendKeys("1");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        String body = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue("Expected 0.30480 in result", body.contains("0.30480"));
    }

    @Test
    public void ui_nonNumericInput_displaysErrorMessage() {
        driver.get("http://localhost:" + port + "/");
        WebElement valueField = driver.findElement(By.id("value"));
        valueField.sendKeys("abc");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        String body = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue("Expected error message", body.contains("Error") || body.contains("Invalid"));
    }
}
