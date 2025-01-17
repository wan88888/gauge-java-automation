import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.AfterScenario;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.After;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class MobileTestImplementation {
    private AndroidDriver driver;
    private WebDriverWait wait;

    @Step("Launch SwagLabs mobile app")
    public void launchApp() {
        try {
            System.out.println("Initializing mobile driver...");
            UiAutomator2Options options = new UiAutomator2Options()
                    .setDeviceName("ZL5227R9TD")
                    .setAutomationName("UiAutomator2")
                    .setPlatformName("Android")
                    .setPlatformVersion("10")
                    .setNoReset(true)
                    .setAppPackage("com.swaglabsmobileapp")
                    .setAppActivity("com.swaglabsmobileapp.MainActivity");
            
            if (driver != null) {
                System.out.println("Existing driver found, quitting...");
                driver.quit();
            }
            
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            System.out.println("Mobile driver initialized successfully");
        } catch (Exception e) {
            System.err.println("Error launching mobile app: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to launch mobile app", e);
        }
    }

    @Step("Enter username <username>")
    public void enterUsername(String username) {
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(
            AppiumBy.accessibilityId("test-Username")));
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    @Step("Enter password <password>")
    public void enterPassword(String password) {
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(
            AppiumBy.accessibilityId("test-Password")));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    @Step("Click login button")
    public void clickLoginButton() {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
            AppiumBy.accessibilityId("test-LOGIN")));
        loginButton.click();
    }

    @Step("Verify successful login to products page")
    public void verifySuccessfulLoginToProductsPage() {
        WebElement productsTitle = wait.until(ExpectedConditions.presenceOfElementLocated(
            AppiumBy.accessibilityId("test-PRODUCTS")));
        Assert.assertTrue("Products page should be displayed", productsTitle.isDisplayed());
    }

    @AfterScenario
    public void closeApp() {
        try {
            if (driver != null) {
                System.out.println("Closing mobile app...");
                driver.quit();
                driver = null;
                System.out.println("Mobile app closed successfully");
            } else {
                System.out.println("Driver is already null, no need to close");
            }
        } catch (Exception e) {
            System.err.println("Error closing mobile app: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
