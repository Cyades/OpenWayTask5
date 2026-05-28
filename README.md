# OpenWay Internship Test Submission

This repository contains the written test documentation and the Java
Selenium/TestNG automation project for Scenario Option B: testing the
shopping cart functionality of an online store using the Periplus web version as the system under test.

## Requirements and Setup

Install these tools before running the automated test:

- Java JDK 17 or newer
- Apache Maven 3.9 or newer
- Google Chrome

Check Java and Maven with:

```MINGW64
java -version
mvn -version
```

Maven downloads the Java dependencies from `pom.xml`, including Selenium and
TestNG. ChromeDriver does not need to be installed manually because Selenium
Manager resolves it automatically.

The test requires internet access and valid Periplus test account credentials.
Set credentials through environment variables. Do not commit passwords.

Git Bash / MINGW64:

```MINGW64
PERIPLUS_EMAIL="your-email@example.com" PERIPLUS_PASSWORD='your-password' mvn test
```

PowerShell:

```powershell
$env:PERIPLUS_EMAIL="your-email@example.com"
$env:PERIPLUS_PASSWORD="your-password"
mvn test
```

Optional visible Chrome run:

```MINGW64
mvn test -Dperiplus.searchTerm=ikigai -Dheadless=false
```

## 1. Test Case Components

A test case is a structured set of conditions, data, actions, and expected results used to verify a specific test condition. 
A test case should include preconditions, inputs, actions where applicable, expected results, and postconditions.

The test case format used in this submission is:

| Component | Description |
| --- | --- |
| Test Case ID | Unique identifier for traceability. |
| Title | Short name that explains the behavior being tested. |
| Objective | The purpose of the test case. |
| Priority | Business or quality importance: High, Medium, or Low. |
| Preconditions | Conditions that must be true before execution. |
| Test Data | Data used during execution, such as product name or quantity. |
| Steps | Ordered actions performed by the tester or automated script. |
| Expected Result | Correct behavior expected from the system. |
| Actual Result | Result observed during execution. Filled after execution. |
| Status | Pass, Fail, Blocked, or Not Run. |
| Postconditions | Expected system state after the test. |
| Notes | Additional context, risks, or dependencies. |


## 2. Shopping Cart Test Documentation

The following test cases cover the core shopping cart behavior expected from an online bookstore or ecommerce site. The cases focus on cart visibility, adding items, quantity handling, totals, persistence, and checkout entry.

### CART-001: Empty Cart Message

| Component | Detail |
| --- | --- |
| Test Case ID | CART-001 |
| Title | View empty shopping cart |
| Objective | Verify that an empty cart is shown clearly to the user. |
| Priority | High |
| Preconditions | User has no products in the shopping cart. |
| Test Data | None |
| Steps | 1. Open the online store.<br>2. Navigate to the shopping cart page. |
| Expected Result | The cart page displays an empty-cart message and no product rows. |
| Actual Result | Not executed. |
| Status | Not Run |
| Postconditions | Cart remains empty. |
| Notes | This validates the default cart state. |

### CART-002: Add One Product from Product Detail Page

| Component | Detail |
| --- | --- |
| Test Case ID | CART-002 |
| Title | Add one product to cart |
| Objective | Verify that one selected product can be added to the cart. |
| Priority | High |
| Preconditions | Product is available and has stock. User can access product detail page. |
| Test Data | Product keyword: `ikigai`; quantity: `1` |
| Steps | 1. Open the online store.<br>2. Search for a product.<br>3. Open a product detail page.<br>4. Click Add to Cart.<br>5. Open the shopping cart page. |
| Expected Result | The selected product appears in the cart with quantity `1`. |
| Actual Result | Not executed. |
| Status | Not Run |
| Postconditions | Cart contains one product. |
| Notes | This case is automated in the Selenium test. |

### CART-003: Add One Product from Search Results

| Component | Detail |
| --- | --- |
| Test Case ID | CART-003 |
| Title | Add product directly from search results |
| Objective | Verify that product cards can add items to the cart. |
| Priority | High |
| Preconditions | Search results contain at least one available product. |
| Test Data | Product keyword: `ikigai`; quantity: `1` |
| Steps | 1. Search for a product.<br>2. Click Add to Cart on a search result item.<br>3. Open the shopping cart. |
| Expected Result | The product from the search result is added to the cart. |
| Actual Result | Not executed. |
| Status | Not Run |
| Postconditions | Cart contains the selected product. |
| Notes | Useful for validating quick-add behavior. |

### CART-004: Cart Count Updates After Add

| Component | Detail |
| --- | --- |
| Test Case ID | CART-004 |
| Title | Cart counter increases after adding product |
| Objective | Verify that the cart icon count reflects added products. |
| Priority | High |
| Preconditions | User has a visible cart icon or cart counter. |
| Test Data | Product quantity: `1` |
| Steps | 1. Record current cart count.<br>2. Add one available product to cart.<br>3. Observe the cart count. |
| Expected Result | Cart count increases by one item or shows a non-zero count. |
| Actual Result | Not executed. |
| Status | Not Run |
| Postconditions | Cart count matches cart contents. |
| Notes | Dynamic UI feedback should be verified with explicit waits in automation. |

### CART-005: Product Details Match Cart Item

| Component | Detail |
| --- | --- |
| Test Case ID | CART-005 |
| Title | Product information is correct in cart |
| Objective | Verify that the product added to cart has the correct details. |
| Priority | High |
| Preconditions | A product has been added to the cart. |
| Test Data | Product title, price, quantity |
| Steps | 1. Capture product title and price before adding.<br>2. Add the product to the cart.<br>3. Open the cart page.<br>4. Compare cart details with the captured product details. |
| Expected Result | Cart displays the same product title, price, and quantity. |
| Actual Result | Not executed. |
| Status | Not Run |
| Postconditions | Cart contains accurate product data. |
| Notes | Exact price comparison may need normalization for currency formatting. |

### CART-006: Increase Product Quantity

| Component | Detail |
| --- | --- |
| Test Case ID | CART-006 |
| Title | Increase product quantity in cart |
| Objective | Verify that quantity can be increased for an existing cart item. |
| Priority | High |
| Preconditions | Cart contains one available product with quantity `1`. |
| Test Data | New quantity: `2` |
| Steps | 1. Open the cart page.<br>2. Increase product quantity to `2`.<br>3. Apply or wait for the update. |
| Expected Result | Quantity changes to `2` and subtotal is recalculated. |
| Actual Result | Not executed. |
| Status | Not Run |
| Postconditions | Cart contains the same product with updated quantity. |
| Notes | System should not exceed available stock. |

### CART-007: Decrease Product Quantity

| Component | Detail |
| --- | --- |
| Test Case ID | CART-007 |
| Title | Decrease product quantity in cart |
| Objective | Verify that quantity can be decreased for an existing cart item. |
| Priority | High |
| Preconditions | Cart contains one product with quantity greater than `1`. |
| Test Data | Starting quantity: `2`; new quantity: `1` |
| Steps | 1. Open the cart page.<br>2. Decrease product quantity from `2` to `1`.<br>3. Apply or wait for the update. |
| Expected Result | Quantity changes to `1` and subtotal is recalculated. |
| Actual Result | Not executed. |
| Status | Not Run |
| Postconditions | Cart contains the same product with updated quantity. |
| Notes | Quantity should not become negative. |

### CART-008: Reject Invalid Quantity

| Component | Detail |
| --- | --- |
| Test Case ID | CART-008 |
| Title | Reject invalid quantity value |
| Objective | Verify that the cart prevents invalid quantity input. |
| Priority | High |
| Preconditions | Cart contains one product. |
| Test Data | Invalid quantities: `0`, `-1`, `abc`, blank |
| Steps | 1. Open the cart page.<br>2. Replace item quantity with invalid input.<br>3. Apply the update. |
| Expected Result | System rejects the invalid value, keeps a valid quantity, and shows an error or validation message. |
| Actual Result | Not executed. |
| Status | Not Run |
| Postconditions | Cart remains in a valid state. |
| Notes | Error messages should clearly identify the input problem. |

### CART-009: Stock Limit Handling

| Component | Detail |
| --- | --- |
| Test Case ID | CART-009 |
| Title | Prevent quantity above available stock |
| Objective | Verify that users cannot add more units than available stock. |
| Priority | High |
| Preconditions | Product has a known available stock limit. |
| Test Data | Quantity greater than available stock |
| Steps | 1. Open a product detail page.<br>2. Enter a quantity higher than available stock.<br>3. Click Add to Cart. |
| Expected Result | System prevents the action or limits the quantity and shows a stock-related message. |
| Actual Result | Not executed. |
| Status | Not Run |
| Postconditions | Cart quantity does not exceed stock. |
| Notes | Important for inventory accuracy. |

### CART-010: Remove Product from Cart

| Component | Detail |
| --- | --- |
| Test Case ID | CART-010 |
| Title | Remove product from cart |
| Objective | Verify that a user can remove a cart item. |
| Priority | High |
| Preconditions | Cart contains at least one product. |
| Test Data | Existing cart product |
| Steps | 1. Open the cart page.<br>2. Click remove or delete for the product.<br>3. Confirm removal if prompted. |
| Expected Result | Product is removed and cart total is recalculated. If no items remain, empty-cart message is displayed. |
| Actual Result | Not executed. |
| Status | Not Run |
| Postconditions | Product is no longer in the cart. |
| Notes | Prevents users from being stuck with unwanted items. |

### CART-011: Add Multiple Different Products

| Component | Detail |
| --- | --- |
| Test Case ID | CART-011 |
| Title | Add multiple different products |
| Objective | Verify that the cart supports more than one unique product. |
| Priority | Medium |
| Preconditions | At least two products are available. |
| Test Data | Product A quantity `1`; Product B quantity `1` |
| Steps | 1. Add Product A to the cart.<br>2. Search for Product B.<br>3. Add Product B to the cart.<br>4. Open the cart page. |
| Expected Result | Cart displays both products as separate items with correct quantities. |
| Actual Result | Not executed. |
| Status | Not Run |
| Postconditions | Cart contains multiple products. |
| Notes | Validates cart line-item handling. |

### CART-012: Cart Persists After Refresh

| Component | Detail |
| --- | --- |
| Test Case ID | CART-012 |
| Title | Cart contents persist after page refresh |
| Objective | Verify that cart state is not lost during normal navigation. |
| Priority | Medium |
| Preconditions | Cart contains at least one product. |
| Test Data | Existing cart product |
| Steps | 1. Open the cart page.<br>2. Refresh the page.<br>3. Observe cart contents. |
| Expected Result | Product remains in the cart after refresh. |
| Actual Result | Not executed. |
| Status | Not Run |
| Postconditions | Cart state is preserved. |
| Notes | Persistence may depend on session or account state. |

### CART-013: Continue Shopping Keeps Cart State

| Component | Detail |
| --- | --- |
| Test Case ID | CART-013 |
| Title | Continue shopping without losing cart contents |
| Objective | Verify that leaving the cart does not clear selected items. |
| Priority | Medium |
| Preconditions | Cart contains at least one product. |
| Test Data | Existing cart product |
| Steps | 1. Open the cart page.<br>2. Navigate back to the store or search page.<br>3. Return to the cart page. |
| Expected Result | Previously added product remains in the cart. |
| Actual Result | Not executed. |
| Status | Not Run |
| Postconditions | Cart state is preserved. |
| Notes | Important for realistic browsing behavior. |

### CART-014: Navigate to Checkout from Populated Cart

| Component | Detail |
| --- | --- |
| Test Case ID | CART-014 |
| Title | Proceed to checkout from cart |
| Objective | Verify that a user can start checkout from a valid cart. |
| Priority | High |
| Preconditions | User is logged in and cart contains at least one available product. |
| Test Data | Existing cart product |
| Steps | 1. Open the cart page.<br>2. Click checkout or proceed to checkout. |
| Expected Result | User is redirected to the checkout flow without losing cart contents. |
| Actual Result | Not executed. |
| Status | Not Run |
| Postconditions | Checkout flow is opened. |
| Notes | Payment and order submission are out of scope for this task. |

## 3. Automated Test Scenario

The automation covers the required flow:

1. Open Google Chrome in a new maximized window.
2. Navigate to `https://www.periplus.com/`.
3. Enter login email and password.
4. Search for one product.
5. Add one product to the cart.
6. Verify that the product has been successfully added to the cart.

Run instructions and credential setup are described in the Requirements and
Setup section above.

## 4. Automation Design Notes

- Selenium Page Objects are used so locators and page-specific actions are kept separate from test assertions.
- Explicit waits are used for dynamic Periplus behavior. Implicit waits are avoided to prevent unpredictable wait timing when combined with explicit waits.
- Selenium Manager is used through Selenium 4, so ChromeDriver does not need to be committed or manually configured in this project.
- TestNG annotations organize browser setup, teardown, and the cart test.
- Maven Surefire runs the TestNG test during `mvn test`.
- Checkout and payment submission are intentionally out of scope.

## 5. References

- Selenium, "Page object models": https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/
- Selenium, "Waiting Strategies": https://www.selenium.dev/documentation/webdriver/waits/
- Selenium, "Selenium Manager": https://www.selenium.dev/documentation/selenium_manager/
- TestNG, "Annotations": https://testng.org/annotations.html
- Maven Surefire Plugin, "Using TestNG": https://maven.apache.org/surefire/maven-surefire-plugin/examples/testng.html
- W3C WAI, "Understanding Success Criterion 3.3.1: Error Identification": https://www.w3.org/WAI/WCAG21/Understanding/error-identification
- Baymard Institute, "Checkout Optimization: 5 Ways to Minimize Form Fields in Checkout": https://baymard.com/blog/checkout-flow-average-form-fields
- Periplus : https://www.periplus.com/
