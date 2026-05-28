package com.openway.periplus.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

public class SearchResultsPage extends BasePage {
    private final By productLinks = By.cssSelector(
            ".single-product .product-content h3 a[href*='/p/'], "
                    + ".single-product .product-img a[href*='/p/']");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public SearchResultsPage waitForResults() {
        waitForPageToSettle();
        wait.until(currentDriver -> currentDriver.getCurrentUrl().contains("/product/Search"));
        wait.until(currentDriver -> !currentDriver.findElements(productLinks).isEmpty());
        return this;
    }

    public ProductDetailPage openFirstProductWithAddToCart() {
        List<String> hrefs = wait.until(currentDriver -> {
            List<String> values = currentDriver.findElements(productLinks).stream()
                    .map(element -> element.getAttribute("href"))
                    .filter(href -> href != null && href.contains("/p/"))
                    .distinct()
                    .limit(8)
                    .toList();
            return values.isEmpty() ? null : values;
        });

        for (String href : hrefs) {
            driver.get(href);
            try {
                ProductDetailPage page = new ProductDetailPage(driver).waitForLoaded();
                if (page.hasAddToCartButton()) {
                    return page;
                }
            } catch (TimeoutException ignored) {
                // Try the next search result if this product page is not usable.
            }
        }

        throw new NoSuchElementException("No product detail page with an Add to Cart button was found.");
    }
}
