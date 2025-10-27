



/*package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchResultsPage {
    WebDriver driver;
    WebDriverWait wait;

    // Common clickable locator for any Flipkart product
    @FindBy(xpath = "(//a[contains(@href,'/p/') or contains(@href,'/product/')])[1]")
    private WebElement firstProductLink;

    // Add to cart button (common locator)
    @FindBy(xpath = "//button[contains(.,'ADD TO CART')]")
    private WebElement addToCartButton;

    // Go to cart button
    @FindBy(xpath = "//a[contains(@href,'/viewcart') or contains(.,'Cart')]")
    private WebElement goToCartButton;

    // Checkout or Place Order button
    @FindBy(xpath = "//button[contains(.,'Place Order') or contains(.,'Proceed to Checkout')]")
    private WebElement proceedToCheckoutButton;
    
    @FindBy(xpath = "//label[contains(@for, 'CNTCT')][2]")
    private WebElement AddressSelect;

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // Check if results are displayed
    public boolean isSearchResultDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(firstProductLink));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Open first product in new tab
    public void openFirstProductInNewTab() {
        try {
            wait.until(ExpectedConditions.visibilityOf(firstProductLink));
            String productUrl = firstProductLink.getAttribute("href");

            if (productUrl == null || productUrl.isEmpty()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstProductLink);
                System.out.println("Clicked first product directly (no href).");
            } else {
                ((JavascriptExecutor) driver).executeScript("window.open(arguments[0], '_blank');", productUrl);
                System.out.println("Opened product in new tab: " + productUrl);
            }

            // Switch to new tab
            List<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(tabs.size() - 1));
            System.out.println("Switched to new tab successfully.");
        } catch (Exception e) {
            throw new RuntimeException("Unable to open first product: " + e.getMessage());
        }
    }

    // Scroll to Add to Cart and click it
    public void addToCart() {
        try {
            // Wait a bit for product page to load fully
            Thread.sleep(3000);

            // Scroll down slowly to reveal the Add to Cart button
            JavascriptExecutor js = (JavascriptExecutor) driver;
            for (int i = 0; i < 5; i++) {
                js.executeScript("window.scrollBy(0, 400);");
                Thread.sleep(1000);
                try {
                    WebElement addButton = driver.findElement(By.xpath("//button[normalize-space()='Add to cart' or normalize-space()='ADD TO CART']"));
                    if (addButton.isDisplayed()) {
                        js.executeScript("arguments[0].scrollIntoView(true);", addButton);
                        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
                        System.out.println("Product added to cart successfully!");
                        return;
                    }
                } catch (NoSuchElementException e) {
                    // keep scrolling until button appears
                }
            }
            throw new NoSuchElementException("Add to Cart button not found after scrolling.");
        } catch (Exception e) {
            System.out.println("Add to cart failed: " + e.getMessage());
        }
    }


    // Go to cart page
    public void goToCart() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(goToCartButton)).click();
            System.out.println("Navigated to cart page");
        } catch (Exception e) {
            System.out.println("Cart button not found, trying fallback click.");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", goToCartButton);
        }
    }

    // Proceed to checkout
    public void proceedToCheckout() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(proceedToCheckoutButton)).click();
            System.out.println("Proceeded to checkout page");
        } catch (Exception e) {
            System.out.println("Checkout button not found: " + e.getMessage());
        }
    }
    
    public void AddressSelection() {
    	try {
    		wait.until(ExpectedConditions.elementToBeClickable(AddressSelect)).click();
    		System.out.println("Address selected");
    	}
    	catch(Exception ex){
    		System.out.println("Radio button not found");
    		
    	}
    }
}*/


package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchResultsPage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "(//a[contains(@href,'/p/') or contains(@href,'/product/')])[1]")
    private WebElement firstProductLink;

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // Check if results are visible
    public boolean isSearchResultDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(firstProductLink));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Open first product in a new tab
    public void openFirstProductInNewTab() {
        try {
            wait.until(ExpectedConditions.visibilityOf(firstProductLink));
            String productUrl = firstProductLink.getAttribute("href");

            if (productUrl == null || productUrl.isEmpty()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstProductLink);
                System.out.println("Clicked first product directly (no href).");
            } else {
                ((JavascriptExecutor) driver).executeScript("window.open(arguments[0], '_blank');", productUrl);
                System.out.println("Opened product in new tab: " + productUrl);
            }

            // Switch to new tab
            List<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(tabs.size() - 1));
            System.out.println("Switched to new tab successfully.");
        } catch (Exception e) {
            throw new RuntimeException("Unable to open first product: " + e.getMessage());
        }
    }
}

