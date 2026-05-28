package com.openway.periplus.tests;

import com.openway.periplus.config.TestConfig;
import com.openway.periplus.driver.DriverFactory;
import com.openway.periplus.pages.CartPage;
import com.openway.periplus.pages.HomePage;
import com.openway.periplus.pages.LoginPage;
import com.openway.periplus.pages.ProductDetailPage;
import com.openway.periplus.support.TextUtil;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PeriplusCartTest {
    private WebDriver driver;
    private String email;
    private String password;

    @BeforeMethod
    public void setUp() {
        email = TestConfig.requiredEnvironment("PERIPLUS_EMAIL");
        password = TestConfig.requiredEnvironment("PERIPLUS_PASSWORD");
        driver = DriverFactory.createChromeDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(description = "Login, search for a product, add it to cart, and verify cart contents.")
    public void shouldAddSearchedProductToCart() {
        new LoginPage(driver)
                .open()
                .login(email, password);

        HomePage homePage = new HomePage(driver).open();
        ProductDetailPage productPage = homePage
                .search(TestConfig.searchTerm())
                .openFirstProductWithAddToCart();

        String productTitle = productPage.productTitle();
        int previousCartCount = productPage.cartCount();

        boolean addConfirmed = productPage
                .addOneToCart()
                .waitForAddConfirmation(previousCartCount);

        Assert.assertTrue(
                addConfirmed,
                "Expected cart count to increase or success feedback after adding: " + productTitle);

        CartPage cartPage = productPage.openCart();

        Assert.assertFalse(
                cartPage.isEmpty(),
                "Expected the cart to contain at least one product after Add to Cart.");
        Assert.assertTrue(
                cartPage.containsProductTitle(productTitle),
                "Expected cart to contain product title '" + productTitle
                        + "'. Cart text: " + TextUtil.truncate(cartPage.visibleText(), 500));
    }
}
