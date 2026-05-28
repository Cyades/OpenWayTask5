package com.openway.periplus.pages;

import com.openway.periplus.support.TextUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private final By searchInput = By.id("filter_name_desktop");
    private final By cartCount = By.id("cart_total");
    private final By cartLink = By.cssSelector("#show-your-cart a[href*='checkout/cart'], a[href*='checkout/cart']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(baseUrl + "/");
        return waitForLoaded();
    }

    public HomePage waitForLoaded() {
        waitForPageToSettle();
        visible(searchInput);
        return this;
    }

    public SearchResultsPage search(String keyword) {
        var input = clickable(searchInput);
        input.click();
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(keyword);
        input.sendKeys(Keys.ENTER);
        return new SearchResultsPage(driver).waitForResults();
    }

    public int cartCount() {
        return TextUtil.firstInteger(visible(cartCount).getText());
    }

    public CartPage openCart() {
        clickable(cartLink).click();
        return new CartPage(driver).waitForLoaded();
    }
}
