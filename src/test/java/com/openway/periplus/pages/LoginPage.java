package com.openway.periplus.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final By emailInput = By.cssSelector("input[name='email']");
    private final By passwordInput = By.cssSelector("input[name='password']");
    private final By loginButton = By.id("button-login");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        driver.get(baseUrl + "/account/Login");
        waitForPageToSettle();
        visible(emailInput);
        return this;
    }

    public HomePage login(String email, String password) {
        clickable(emailInput).sendKeys(email);
        clickable(passwordInput).sendKeys(password);
        clickable(loginButton).click();

        wait.until(currentDriver -> !currentDriver.getCurrentUrl().contains("/account/Login"));
        return new HomePage(driver).waitForLoaded();
    }
}
