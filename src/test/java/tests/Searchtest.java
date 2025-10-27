package tests;

import Base.BaseTest;
import Listeners.RetryAnalyzer;
import Utils.ExcelUtils;
import pages.HomePage;
import pages.SearchResultsPage;
import pages.ProductPage;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class Searchtest extends BaseTest {

    @DataProvider(name = "searchData")
    public Object[][] getSearchData() throws Exception {
        String excelPath = System.getProperty("user.dir") + "/testdata/searchData.xlsx";
        return ExcelUtils.getTestData(excelPath, "Sheet1");
    }

    @Test(dataProvider = "searchData", retryAnalyzer = RetryAnalyzer.class)
    public void searchProductTest(String product, String expectedTitle) throws Exception {

        // Step 1: Go to home page and search for product
        HomePage homePage = new HomePage(driver);
        homePage.searchProduct(product);

        // Step 2: Initialize search results page
        SearchResultsPage resultsPage = new SearchResultsPage(driver);

        // Step 3: Verify product search results are displayed
        Assert.assertTrue(resultsPage.isSearchResultDisplayed(),
                "No search results found for: " + product);

        // Step 4: Open the first product in a new tab
        resultsPage.openFirstProductInNewTab();

        // Wait briefly for page load
        Thread.sleep(4000);

        // Step 5: Validate product title (basic verification)
        String actualTitle = driver.getTitle();
        System.out.println("Actual Page Title: " + actualTitle);
        Assert.assertTrue(actualTitle.toLowerCase().contains(product.toLowerCase()),
                "Expected product '" + product + "' not found in page title: " + actualTitle);

        // Step 6: Add product to cart
        ProductPage p = new ProductPage(driver);
        p.addToCart();

        // Step 7: Go to cart page
        p.goToCart();

        // Step 8: Proceed to checkout
        p.proceedToCheckout();
        p.selectAddress();
       
        p.clickDeliverHere();
        p.clickContinue();

        // Step 9: Verify checkout/cart page URL
        String currentUrl = driver.getCurrentUrl().toLowerCase();
        Assert.assertTrue(currentUrl.contains("checkout") || currentUrl.contains("cart"),
                "User did not reach checkout/cart page. Current URL: " + currentUrl);

        // Step 10: Close product tab and return to main tab
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        if (tabs.size() > 1) {
            driver.close();
            driver.switchTo().window(tabs.get(0));
        }

        System.out.println("Completed flow for product: " + product);
    }
}
