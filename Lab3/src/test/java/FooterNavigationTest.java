import com.djeno.utils.BrowserType;
import com.djeno.utils.SeleniumSetting;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class FooterNavigationTest {

    private WebDriver driver;

    private static final String EXPECTED_TERMS_TITLE = "Правила пользования сайтом";
    private static final String EXPECTED_TECHNOLOGY_TITLE = "Рекомендательные технологии";
    private static final String EXPECTED_ADS_TITLE = "Рекламная служба Gismeteo.ru";
    private static final String EXPECTED_CONTACTS_TITLE = "Контакты";

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
    public void checkFooterNavigationTest(BrowserType browserType) throws InterruptedException {
        setUp(browserType);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://www.gismeteo.ru/");

        WebElement footer = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("/html/body/footer")
                )
        );

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'end', behavior: 'instant'});", footer);

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");

        wait.until(ExpectedConditions.visibilityOf(footer));

        WebElement termsLink = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("/html/body/footer/div[1]/a[1]")
                )
        );
        termsLink.click();

        WebElement termsTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("/html/body/main/div/section/article/h1")
                )
        );
        String actualTermsTitle = termsTitle.getText().trim();
        assertEquals(EXPECTED_TERMS_TITLE, actualTermsTitle,
                "Заголовок страницы правил не соответствует ожидаемому");

        driver.navigate().back();
        wait.until(ExpectedConditions.urlToBe("https://www.gismeteo.ru/"));

        WebElement technologyLink = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("/html/body/footer/div[1]/a[2]")
                )
        );
        technologyLink.click();

        WebElement technologyTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("/html/body/main/div/section/article/h1")
                )
        );
        String actualTechnologyTitle = technologyTitle.getText().trim();
        assertEquals(EXPECTED_TECHNOLOGY_TITLE, actualTechnologyTitle,
                "Заголовок страницы технологий не соответствует ожидаемому");

        driver.navigate().back();
        wait.until(ExpectedConditions.urlToBe("https://www.gismeteo.ru/"));

        // Переход на страницу "Рекламная служба"
        WebElement adsLink = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("/html/body/footer/div[1]/a[3]")
                )
        );
        adsLink.click();

        WebElement adsTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("/html/body/main/div/section/article/h1")
                )
        );
        String actualAdsTitle = adsTitle.getText().trim();
        assertEquals(EXPECTED_ADS_TITLE, actualAdsTitle,
                "Заголовок страницы рекламной службы не соответствует ожидаемому");

        driver.navigate().back();
        wait.until(ExpectedConditions.urlToBe("https://www.gismeteo.ru/"));

        WebElement contactsLink = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("/html/body/footer/div[1]/a[4]")
                )
        );
        contactsLink.click();

        WebElement contactsTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("/html/body/main/div/section/article/h1")
                )
        );
        String actualContactsTitle = contactsTitle.getText().trim();
        assertEquals(EXPECTED_CONTACTS_TITLE, actualContactsTitle,
                "Заголовок страницы контактов не соответствует ожидаемому");
    }
}
