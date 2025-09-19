import com.djeno.utils.BrowserType;
import com.djeno.utils.SeleniumSetting;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ChangeMeasurementTest {

    private WebDriver driver;

    private static final String EXPECTED_TEMPERATURE = "°F";
    private static final String EXPECTED_SPEED = "км/ч";

    public void setUp(BrowserType type) {
        driver = SeleniumSetting.createDriver(type);
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @ParameterizedTest
    @EnumSource(BrowserType.class)
    public void changeMeasurementTest(BrowserType browserType) throws InterruptedException {
        setUp(browserType);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://www.gismeteo.ru/");

        WebElement settingsButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("/html/body/header/div[1]/div[3]/div/button")
                )
        );
        settingsButton.click();

        WebElement tempSelect = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div[1]/div[1]/div/select")
                )
        );
        tempSelect.click();

        WebElement fahrenheitOption = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div[1]/div[1]/div/select/option[2]")
                )
        );
        fahrenheitOption.click();

        WebElement speedSelect = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div[1]/div[2]/div/select")
                )
        );
        speedSelect.click();

        WebElement kmhOption = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div[1]/div[2]/div/select/option[2]")
                )
        );
        kmhOption.click();

        // /html/body/div[2]/div/div[2]/div/div[1]/button
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ESCAPE).perform();

        //Thread.sleep(2000);

        WebElement temperatureElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("/html/body/main/div[1]/section[1]/div[1]/div/div[2]/div[3]/p/span/temperature-value")
                )
        );
        String actualTemperature = temperatureElement.getText().trim();
        assertTrue(actualTemperature.contains(EXPECTED_TEMPERATURE),
                "Температура не отображается в Фаренгейтах. Фактическое значение: " + actualTemperature);

        WebElement speedElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("/html/body/main/div[1]/section[1]/div[1]/div/div[2]/div[4]/p/span/speed-value")
                )
        );
        String actualSpeed = speedElement.getText().trim();
        assertTrue(actualSpeed.contains(EXPECTED_SPEED),
                "Скорость не отображается в км/ч. Фактическое значение: " + actualSpeed);
    }
}
