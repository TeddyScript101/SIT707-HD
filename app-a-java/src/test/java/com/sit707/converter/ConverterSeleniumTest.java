package com.sit707.converter;

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
 * ChromeOptions configured for headless mode (required by GCP Cloud Build).
 * Covers all conversion types added in the Refactor Stage.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConverterSeleniumTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @Before
    public void setUp() {
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
        if (driver != null) driver.quit();
    }

    private void selectDirection(String value) {
        new Select(driver.findElement(By.id("direction"))).selectByValue(value);
    }

    private void submitValue(String value) {
        WebElement field = driver.findElement(By.id("value"));
        field.clear();
        field.sendKeys(value);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    private String bodyText() {
        try {
            return driver.findElement(By.tagName("body")).getText();
        } catch (org.openqa.selenium.NoSuchElementException | org.openqa.selenium.StaleElementReferenceException e) {
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            return driver.findElement(By.tagName("body")).getText();
        }
    }

    // ---- Page load -------------------------------------------------------

    @Test
    public void ui_homePage_titleContainsUnitConverter() {
        driver.get("http://localhost:" + port + "/");
        Assert.assertTrue(driver.getTitle().contains("Unit Converter"));
    }

    // ---- Length: Meters <-> Feet -----------------------------------------

    @Test
    public void ui_metersToFeet_1m_shows3_28084() {
        driver.get("http://localhost:" + port + "/");
        submitValue("1");
        Assert.assertTrue(bodyText().contains("3.28084"));
    }

    @Test
    public void ui_feetToMeters_1ft_shows0_30480() {
        driver.get("http://localhost:" + port + "/");
        selectDirection("fToM");
        submitValue("1");
        Assert.assertTrue(bodyText().contains("0.30480"));
    }

    // ---- Length: Kilometers <-> Miles ------------------------------------

    @Test
    public void ui_kmToMiles_1km_shows0_62137() {
        driver.get("http://localhost:" + port + "/");
        selectDirection("kmToMi");
        submitValue("1");
        Assert.assertTrue(bodyText().contains("0.62137"));
    }

    @Test
    public void ui_milesToKm_1mi_shows1_60934() {
        driver.get("http://localhost:" + port + "/");
        selectDirection("miToKm");
        submitValue("1");
        Assert.assertTrue(bodyText().contains("1.60934"));
    }

    // ---- Length: Centimeters <-> Inches ----------------------------------

    @Test
    public void ui_cmToInches_2_54cm_shows1_00000() {
        driver.get("http://localhost:" + port + "/");
        selectDirection("cmToIn");
        submitValue("2.54");
        Assert.assertTrue(bodyText().contains("1.00000"));
    }

    @Test
    public void ui_inchesToCm_1in_shows2_54000() {
        driver.get("http://localhost:" + port + "/");
        selectDirection("inToCm");
        submitValue("1");
        Assert.assertTrue(bodyText().contains("2.54000"));
    }

    // ---- Weight: Kilograms <-> Pounds ------------------------------------

    @Test
    public void ui_kgToPounds_1kg_shows2_20462() {
        driver.get("http://localhost:" + port + "/");
        selectDirection("kgToLb");
        submitValue("1");
        Assert.assertTrue(bodyText().contains("2.20462"));
    }

    @Test
    public void ui_poundsToKg_1lb_shows0_45359() {
        driver.get("http://localhost:" + port + "/");
        selectDirection("lbToKg");
        submitValue("1");
        Assert.assertTrue(bodyText().contains("0.45359"));
    }

    // ---- Temperature: Celsius <-> Fahrenheit -----------------------------

    @Test
    public void ui_celsiusToFahrenheit_0c_shows32() {
        driver.get("http://localhost:" + port + "/");
        selectDirection("cToF");
        submitValue("0");
        Assert.assertTrue(bodyText().contains("32.00000"));
    }

    @Test
    public void ui_fahrenheitToCelsius_32f_shows0() {
        driver.get("http://localhost:" + port + "/");
        selectDirection("fToC");
        submitValue("32");
        Assert.assertTrue(bodyText().contains("0.00000"));
    }

    @Test
    public void ui_celsiusToFahrenheit_negativeTempIsValid_minus10c() {
        driver.get("http://localhost:" + port + "/");
        selectDirection("cToF");
        submitValue("-10");
        Assert.assertTrue(bodyText().contains("14.00000"));
    }

    // ---- Error conditions ------------------------------------------------

    @Test
    public void ui_nonNumericInput_displaysErrorMessage() {
        driver.get("http://localhost:" + port + "/");
        submitValue("abc");
        String body = bodyText();
        Assert.assertTrue(body.contains("Error") || body.contains("Invalid"));
    }

    @Test
    public void ui_negativeLength_displaysErrorMessage() {
        driver.get("http://localhost:" + port + "/");
        submitValue("-5");
        String body = bodyText();
        Assert.assertTrue(body.contains("Error") || body.contains("non-negative"));
    }
}
