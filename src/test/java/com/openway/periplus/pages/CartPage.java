package com.openway.periplus.pages;

import com.openway.periplus.support.TextUtil;
import java.util.Locale;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {
    private final By heading = By.xpath("//h1[contains(., 'Cart')]");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public CartPage waitForLoaded() {
        waitForPageToSettle();
        wait.until(currentDriver -> currentDriver.getCurrentUrl().contains("/checkout/cart")
                || !currentDriver.findElements(heading).isEmpty());
        return this;
    }

    public boolean isEmpty() {
        return bodyText().toLowerCase(Locale.ROOT).contains("your shopping cart is empty");
    }

    public boolean containsProductTitle(String productTitle) {
        return TextUtil.containsMeaningfulTitle(bodyText(), productTitle);
    }

    public String visibleText() {
        return bodyText();
    }
}
