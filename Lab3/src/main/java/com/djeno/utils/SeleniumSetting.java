package com.djeno.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


public abstract class SeleniumSetting {

    public static WebDriver createDriver(BrowserType browserType) {
        switch (browserType) {
            case CHROME:
                return createChromeDriver();
            case FIREFOX:
                return createFirefoxDriver();
            default:
                throw new IllegalArgumentException("Unsupported browser type: " + browserType);
        }
    }

    private static ChromeDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        return new ChromeDriver(options);
    }

    private static FirefoxDriver createFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--start-maximized");
        return new FirefoxDriver(options);
    }
}
