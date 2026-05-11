package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");

        String isGitHubActions = System.getenv("CI");

        if (isGitHubActions != null) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
            System.out.println("Running in GitHub Actions headless mode");
        }

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        System.out.println("Browser opened only one time");
    }

    @BeforeMethod
    public void openHomePage() {
        driver.get("https://automationexercise.com/");
        System.out.println("Home page opened for test");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        System.out.println("Browser closed after all tests");
    }
}