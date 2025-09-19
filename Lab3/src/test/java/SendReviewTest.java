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

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SendReviewTest {

    private WebDriver driver;

    private static final String EXPECTED_MESSAGE = "Ваше сообщение принято.Мы скоро вам ответим.";

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
    public void checkFooterNavigationTest(BrowserType browserType) throws InterruptedException {
        setUp(browserType);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://www.gismeteo.ru/");

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        Thread.sleep(1000);

        WebElement reviewButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("/html/body/footer/div[1]/button")
                )
        );
        reviewButton.click();

        Thread.sleep(5000);

        WebElement headerInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input[@id='header']")
                )
        );

        headerInput.sendKeys("Заголовок тестового отзыва");

        WebElement descriptionInput = driver.findElement(
                By.xpath("//*[@id='description']")
        );
        descriptionInput.sendKeys("Описание тестового отзыва для проверки функционала");

        WebElement emailInput = driver.findElement(
                By.xpath("//*[@id='user_email']")
        );
        emailInput.sendKeys("aaeef@mail.ru");

        try {
            WebElement recaptcha = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//*[@id='recaptcha-anchor']/div[1]")
                    )
            );
            recaptcha.click();
        } catch (Exception e) {
            System.out.println("Recaptcha не найдена или не кликабельна: " + e.getMessage());
        }

        Thread.sleep(3000);

        WebElement submitButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[@id='widget']/div[4]/div/div/div/button")
                )
        );
        submitButton.click();

        WebElement successMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id='widget']/div[4]/div/div/div")
                )
        );

        String actualMessage = successMessage.getText().trim();
        assertEquals(EXPECTED_MESSAGE, actualMessage,
                "Сообщение об успехе не соответствует ожидаемому. Фактическое: " + actualMessage);
    }
}
