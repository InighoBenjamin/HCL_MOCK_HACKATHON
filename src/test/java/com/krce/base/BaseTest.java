package com.krce.base;

import com.krce.utils.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {

    public WebDriver driver;
    public WebDriverWait wait;

    @BeforeClass
    public void setup() {

        String url = ConfigReader.getValue("baseUrl");
        int timeout = Integer.parseInt(ConfigReader.getValue("timeout"));

        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

        driver.get(url);
    }

    @BeforeMethod
    public void openLoginPage() {
        driver.get(ConfigReader.getValue("baseUrl"));
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}