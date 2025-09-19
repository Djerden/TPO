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

import static org.junit.jupiter.api.Assertions.*;


public class ChangeDataSetTest {

    private WebDriver driver;

    private static final String EXPECTED_FEELS_LIKE = "Температура по ощущению";
    private static final String EXPECTED_POLLEN = "Пыльца берёзы, баллы";
    private static final String EXPECTED_PRESSURE = "Давление";
    private static final String EXPECTED_HUMIDITY = "Относительная влажность";

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
    public void changeDataSetTest(BrowserType browserType) throws InterruptedException {
        setUp(browserType);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://www.gismeteo.ru/");

        WebElement firstButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("/html/body/main/div[1]/section[1]/div[1]/div/a")
                )
        );
        firstButton.click();

        WebElement dataSetButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("/html/body/main/div[1]/section[3]/div[2]/button[1]")
                )
        );
        dataSetButton.click();

        WebElement feelsLikeOption = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(.,'Температура по ощущению')]")
                )
        );
        feelsLikeOption.click();

        WebElement pollenOption = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(.,'Пыльца берёзы')]")
                )
        );
        pollenOption.click();

        WebElement pressureOption = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(.,'Давление')]")
                )
        );
        pressureOption.click();

        WebElement humidityOption = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(.,'Относительная влажность')]")
                )
        );
        humidityOption.click();

        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ESCAPE).perform();

        WebElement feelsLikeElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("/html/body/main/div[1]/section[3]/div[1]/div/div/div[5]/p/span")
                )
        );
        String actualFeelsLike = feelsLikeElement.getText().trim();
        assertTrue(actualFeelsLike.contains(EXPECTED_FEELS_LIKE),
                "Температура по ощущению не содержит ожидаемого значения. Ожидалось: " + EXPECTED_FEELS_LIKE + ", Фактически: " + actualFeelsLike);

        WebElement pollenElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("/html/body/main/div[1]/section[3]/div[1]/div/div/div[7]/p/span")
                )
        );
        String actualPollen = pollenElement.getText().trim();
        assertTrue(actualPollen.contains(EXPECTED_POLLEN),
                "Пыльца березы не содержит ожидаемого значения. Ожидалось: " + EXPECTED_POLLEN + ", Фактически: " + actualPollen);

        WebElement pressureElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("/html/body/main/div[1]/section[3]/div[1]/div/div/div[12]/p/span")
                )
        );
        String actualPressure = pressureElement.getText().trim();
        assertTrue(actualPressure.contains(EXPECTED_PRESSURE),
                "Давление не содержит ожидаемого значения. Ожидалось: " + EXPECTED_PRESSURE + ", Фактически: " + actualPressure);

        WebElement humidityElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("/html/body/main/div[1]/section[3]/div[1]/div/div/div[13]/p/span")
                )
        );
        String actualHumidity = humidityElement.getText().trim();
        assertTrue(actualHumidity.contains(EXPECTED_HUMIDITY),
                "Влажность не содержит ожидаемого значения. Ожидалось: " + EXPECTED_HUMIDITY + ", Фактически: " + actualHumidity);
    }
}
