import com.djeno.utils.BrowserType;
import com.djeno.utils.SeleniumSetting;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class CheckClickOnAdTest {

    private WebDriver driver;

    private static final String BASE_URL = "https://www.gismeteo.ru/";

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

    @Disabled
    @ParameterizedTest
    @EnumSource(BrowserType.class)
    public void checkClickOnAdTest(BrowserType browserType) throws InterruptedException {
        setUp(browserType);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get(BASE_URL);
        String originalUrl = driver.getCurrentUrl();

        WebElement firstAdLink = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[@id='media-right']//a[contains(@class, 'yrw-url')][1]")
                )
        );

        assertTrue(firstAdLink.isDisplayed(), "Рекламная ссылка не отображается");

        firstAdLink.click();

        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(originalUrl)));

        String currentUrl = driver.getCurrentUrl();
        assertNotEquals(BASE_URL, currentUrl,
                "После клика по рекламе URL не изменился. Текущий URL: " + currentUrl);

        assertNotNull(currentUrl, "URL новой вкладки null");
        assertFalse(currentUrl.isEmpty(), "URL новой вкладки пустой");
        assertTrue(currentUrl.startsWith("http"),
                "Новый URL не начинается с http: " + currentUrl);

        driver.close();

        Set<String> windows = driver.getWindowHandles();
        if (!windows.isEmpty()) {
            driver.switchTo().window(windows.iterator().next());
        }

        String finalUrl = driver.getCurrentUrl();
        assertTrue(finalUrl.startsWith("http"),
                "После закрытия рекламы URL невалиден: " + finalUrl);
    }
}
