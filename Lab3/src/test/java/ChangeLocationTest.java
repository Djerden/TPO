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


public class ChangeLocationTest {

    private WebDriver driver;

    private static final String LOCATION = "Москва";
    private static final String EXPECTED_HEADER = "Погода в Москве";

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
    public void changeLocationTest(BrowserType browserType) throws InterruptedException {
        setUp(browserType);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://www.gismeteo.ru/");

        WebElement searchInput = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='text']"))
        );
        searchInput.click();
        searchInput.sendKeys(LOCATION);
        searchInput.sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        WebElement firstSuggestion = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("/html/body/header/div[2]/div/div[1]/div[3]/div/ul/li[1]/a")
                )
        );
        firstSuggestion.click();

        WebElement header = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("/html/body/main/div[1]/section[1]/div/div/div[2]/h1")
                )
        );

        String actualHeader = header.getText().trim();
        assertEquals(EXPECTED_HEADER, actualHeader,
                "Заголовок страницы не соответствует ожидаемому");
    }
}
