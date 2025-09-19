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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class CheckNewsTest {

    private WebDriver driver;

    private static final String EXPECTED_TITLE = "Читайте также";

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
    public void checkNewsTest(BrowserType browserType) throws InterruptedException {
        setUp(browserType);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://www.gismeteo.ru/");

        WebElement newsButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("/html/body/header/div[1]/div[2]/a[1]")
                )
        );
        newsButton.click();

        WebElement newsTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("/html/body/main/div[1]/section[2]/div/h2")
                )
        );

        String actualTitle = newsTitle.getText().trim();
        assertEquals(EXPECTED_TITLE, actualTitle,
                "Заголовок новостей не соответствует ожидаемому. Ожидалось: " +
                        EXPECTED_TITLE + ", Фактически: " + actualTitle);

        WebElement newsContainer = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("/html/body/main/div[1]/section[2]/div/div")
                )
        );

        List<WebElement> newsItems = newsContainer.findElements(
                By.xpath(".//a[contains(@class, 'article-item')]")
        );

        if (newsItems.isEmpty()) {
            newsItems = newsContainer.findElements(
                    By.xpath(".//*[contains(@class, 'news-item') or contains(@class, 'card') or contains(@class, 'article')]")
            );
        }

        assertFalse(newsItems.isEmpty(), "Список новостей пуст");
        assertTrue(newsItems.size() >= 2, "Слишком мало новостей: " + newsItems.size());

        // проверка на наличие заголовков у новостей
        for (WebElement newsItem : newsItems) {
            try {
                WebElement titleElement = newsItem.findElement(
                        By.xpath(".//*[contains(@class, 'item-title') or contains(@class, 'title')]")
                );
                String newsTitleText = titleElement.getText().trim();
                assertFalse(newsTitleText.isEmpty(),
                        "Найдена новость без заголовка");
            } catch (Exception e) {

            }
        }
    }
}
