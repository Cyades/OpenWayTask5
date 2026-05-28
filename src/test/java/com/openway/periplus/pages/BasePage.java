package com.openway.periplus.pages;

import com.openway.periplus.config.TestConfig;
import com.openway.periplus.support.TextUtil;
import java.time.Duration;
import java.util.Objects;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final String baseUrl;

    protected BasePage(WebDriver driver) {
        this.driver = Objects.requireNonNull(driver, "driver");
        this.wait = new WebDriverWait(driver, TestConfig.timeout());
        this.baseUrl = TestConfig.baseUrl();
    }

    protected WebElement visible(By locator) {
        return wait.until(currentDriver -> currentDriver.findElements(locator).stream()
                .filter(this::isDisplayed)
                .findFirst()
                .orElse(null));
    }

    protected WebElement clickable(By locator) {
        return wait.until(currentDriver -> currentDriver.findElements(locator).stream()
                .filter(element -> isDisplayed(element) && element.isEnabled())
                .findFirst()
                .orElse(null));
    }

    protected void waitForPageToSettle() {
        wait.until(currentDriver -> "complete".equals(
                ((JavascriptExecutor) currentDriver).executeScript("return document.readyState")));

        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(currentDriver -> currentDriver.findElements(By.cssSelector(".preloader")).stream()
                            .noneMatch(this::isDisplayed));
        } catch (RuntimeException ignored) {
            // The site can leave the preloader node in the DOM. Continue once the document is ready.
        }
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (StaleElementReferenceException ignored) {
            return false;
        }
    }

    protected String bodyText() {
        return TextUtil.normalize(driver.findElement(By.tagName("body")).getText());
    }
}
