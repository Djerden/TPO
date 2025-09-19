import com.djeno.utils.BrowserType;
import com.djeno.utils.SeleniumSetting;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ChangeMapTest {

    private WebDriver driver;

    private static final String EXPECTED_SIBERIA_TITLE = "осадки в Сибири";
    private static final String EXPECTED_FAR_EAST_TITLE = "осадки на Дальнем Востоке";

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
    public void changeMapTest(BrowserType browserType) throws InterruptedException {
        setUp(browserType);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://www.gismeteo.ru/");

        WebElement mapsButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("/html/body/header/div[1]/div[2]/a[2]")
                )
        );
        mapsButton.click();

        wait.until(ExpectedConditions.urlContains("maps"));

        WebElement regionButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("/html/body/header/div[2]/div/div[1]/div/button")
                )
        );
        regionButton.click();

        WebElement siberiaButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("/html/body/header/div[2]/div/div[1]/div/div/button[2]")
                )
        );
        siberiaButton.click();

        WebElement mapTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("/html/body/main/div/section/div[1]/h1")
                )
        );

        String actualSiberiaTitle = mapTitle.getText().trim();
        assertTrue(actualSiberiaTitle.contains(EXPECTED_SIBERIA_TITLE),
                "Заголовок карты Сибири не соответствует ожидаемому. Фактический: " + actualSiberiaTitle);

        regionButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("/html/body/header/div[2]/div/div[1]/div/button")
                )
        );
        regionButton.click();

        WebElement farEastButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("/html/body/header/div[2]/div/div[1]/div/div/button[3]")
                )
        );
        farEastButton.click();

        mapTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("/html/body/main/div/section/div[1]/h1")
                )
        );

        String actualFarEastTitle = mapTitle.getText().trim();
        assertTrue(actualFarEastTitle.contains(EXPECTED_FAR_EAST_TITLE),
                "Заголовок карты Дальнего Востока не соответствует ожидаемому. Фактический: " + actualFarEastTitle);
    }
}
