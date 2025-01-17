import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.AfterScenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebTestImplementation {
    private WebDriver driver;
    private WebDriverWait wait;

    @Step("Open <browser> browser")
    public void openBrowser(String browser) {
        try {
            switch (browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    driver = new ChromeDriver(chromeOptions);
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--remote-allow-origins=*");
                    edgeOptions.addArguments("--no-sandbox");
                    edgeOptions.addArguments("--disable-dev-shm-usage");
                    driver = new EdgeDriver(edgeOptions);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }
            driver.manage().window().maximize();
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        } catch (Exception e) {
            System.err.println("Failed to initialize browser: " + e.getMessage());
            throw e;
        }
    }

    @Step("Navigate to Baidu homepage")
    public void navigateToBaidu() {
        try {
            driver.get("https://www.baidu.com");
            // Wait for the search box to be present to ensure page is loaded
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("kw")));
        } catch (Exception e) {
            System.err.println("Failed to navigate to Baidu homepage: " + e.getMessage());
            throw e;
        }
    }

    @Step("Search for <query> in Baidu")
    public void searchInBaidu(String query) {
        try {
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("kw")));
            searchBox.clear();
            searchBox.sendKeys(query);
            
            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("su")));
            searchButton.click();
        } catch (Exception e) {
            System.err.println("Failed to perform search: " + e.getMessage());
            throw e;
        }
    }

    @Step("Verify search results are displayed")
    public void verifySearchResults() {
        try {
            // Wait for the search results container
            WebElement searchResults = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("#content_left .result-op, #content_left .result")
            ));
            Assert.assertTrue("Search results should be displayed", searchResults.isDisplayed());
        } catch (Exception e) {
            System.err.println("Failed to verify search results: " + e.getMessage());
            throw e;
        }
    }

    @AfterScenario
    public void closeBrowser() {
        try {
            if (driver != null) {
                driver.quit();
                driver = null;
            }
        } catch (Exception e) {
            System.err.println("Error closing browser: " + e.getMessage());
        }
    }
}
