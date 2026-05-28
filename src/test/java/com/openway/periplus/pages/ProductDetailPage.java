package com.openway.periplus.pages;

import com.openway.periplus.support.TextUtil;
import java.util.Locale;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

public class ProductDetailPage extends BasePage {
    private final By productTitle = By.cssSelector(".row-product-detail h2, h2");
    private final By addToCartButton = By.cssSelector("button.btn-add-to-cart");
    private final By quantityInput = By.cssSelector("input[id^='qty_']");
    private final By cartCount = By.id("cart_total");
    private final By cartLink = By.cssSelector("#show-your-cart a[href*='checkout/cart'], a[href*='checkout/cart']");
    private final By notificationAreas = By.cssSelector("#notification-modal-header, #notification, .success");

    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    public ProductDetailPage waitForLoaded() {
        waitForPageToSettle();
        visible(productTitle);
        return this;
    }

    public boolean hasAddToCartButton() {
        return driver.findElements(addToCartButton).stream().anyMatch(this::isDisplayed);
    }

    public String productTitle() {
        return TextUtil.normalize(visible(productTitle).getText());
    }

    public int cartCount() {
        return TextUtil.firstInteger(visible(cartCount).getText());
    }

    public ProductDetailPage addOneToCart() {
        setQuantityToOneIfPresent();
        clickable(addToCartButton).click();
        return this;
    }

    public boolean waitForAddConfirmation(int previousCartCount) {
        try {
            wait.until(currentDriver -> currentCartCount() > previousCartCount || hasSuccessNotification());
            return true;
        } catch (TimeoutException exception) {
            return false;
        }
    }

    public CartPage openCart() {
        clickable(cartLink).click();
        return new CartPage(driver).waitForLoaded();
    }

    private int currentCartCount() {
        return TextUtil.firstInteger(driver.findElement(cartCount).getText());
    }

    private void setQuantityToOneIfPresent() {
        driver.findElements(quantityInput).stream()
                .filter(this::isDisplayed)
                .findFirst()
                .ifPresent(input -> {
                    input.click();
                    input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                    input.sendKeys("1");
                });
    }

    private boolean hasSuccessNotification() {
        return driver.findElements(notificationAreas).stream()
                .filter(this::isDisplayed)
                .map(element -> TextUtil.normalize(element.getText()).toLowerCase(Locale.ROOT))
                .anyMatch(text -> text.contains("success add to cart")
                        || text.contains("successfully added")
                        || text.equals("success"));
    }
}
