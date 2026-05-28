package com.openway.periplus.driver;

import com.openway.periplus.config.TestConfig;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public final class DriverFactory {
    private DriverFactory() {
    }

    public static WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");

        if (TestConfig.headless()) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1440,1000");
        }

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
        if (!TestConfig.headless()) {
            driver.manage().window().maximize();
        }
        return driver;
    }
}
