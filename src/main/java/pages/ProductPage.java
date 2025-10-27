package pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "//button[normalize-space()='Add to cart' or normalize-space()='ADD TO CART']")
    private WebElement addToCartButton;

    @FindBy(xpath = "//a[contains(@href,'/viewcart') or contains(.,'Cart')]")
    private WebElement goToCartButton;

    @FindBy(xpath = "//button[contains(.,'Place Order') or contains(.,'Proceed to Checkout')]")
    private WebElement proceedToCheckoutButton;

    @FindBy(xpath =  "//label[contains(@for, 'CNTCT')][2]")
    private WebElement addressSelect;
    
    @FindBy(xpath = "//button[contains(.,'Deliver Here') or contains(.,'DELIVER HERE')]")
    private WebElement deliverHereButton;

    @FindBy(xpath = "//button[contains(.,'Continue') or contains(.,'CONTINUE')]")
    private WebElement continueButton;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // Scroll and click Add to Cart
    public void addToCart() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            // Close popup overlay if it appears
            try {
                WebElement closePopup = driver.findElement(By.xpath("//button[contains(text(),'✕')]"));
                closePopup.click();
                System.out.println("Closed login popup overlay");
                Thread.sleep(1000);
            } catch (Exception ignored) {}

            // Scroll till button visible
            WebElement addButton = null;
            for (int i = 0; i < 8; i++) {
                try {
                    addButton = driver.findElement(By.xpath("//button[contains(.,'Add to cart') or contains(.,'ADD TO CART')]"));
                    if (addButton.isDisplayed()) {
                        js.executeScript("arguments[0].scrollIntoView(true);", addButton);
                        Thread.sleep(1000);
                        break;
                    }
                } catch (Exception e) {
                    js.executeScript("window.scrollBy(0, 600);");
                    Thread.sleep(800);
                }
            }

            if (addButton == null) {
                throw new NoSuchElementException("Add to Cart button not found even after scrolling.");
            }

            //Perform actual Selenium click (not JS click)
            wait.until(ExpectedConditions.elementToBeClickable(addButton));
            new org.openqa.selenium.interactions.Actions(driver)
                    .moveToElement(addButton)
                    .click()
                    .perform();

            System.out.println("Performed real click on Add to Cart button.");
        
            /*wait.until(ExpectedConditions.elementToBeClickable(addButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", addButton);
            Thread.sleep(1000);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addButton);
            System.out.println("JS click performed on Add to Cart button.");*/

            
            System.out.println("Product added to cart successfully!");
        } catch (Exception e) {
            System.out.println("Add to cart failed (react event not triggered): " + e.getMessage());
        }
    }


    // Go to Cart
    public void goToCart() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(goToCartButton)).click();
            System.out.println("Navigated to cart page");
        } catch (Exception e) {
            System.out.println("Cart button not clickable, performing JS click.");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", goToCartButton);
        }
    }

    // 3. Proceed to Checkout
    public void proceedToCheckout() {
        try {
            WebElement checkoutBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//button[contains(.,'Place Order') or contains(.,'Proceed to Checkout')]")));
            
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", checkoutBtn);
            Thread.sleep(1000);
            wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn));

            try {
                checkoutBtn.click();
                System.out.println("Clicked on Place Order button.");
            } catch (ElementClickInterceptedException e) {
                System.out.println("Place Order button intercepted, using JS click.");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkoutBtn);
            }

            System.out.println("Proceeded to checkout page");
        } catch (Exception e) {
            System.out.println("Proceed to Checkout failed: " + e.getMessage());
        }
    }

    // 4. Select Address
    public void selectAddress() {
        try {
            WebElement addressOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//label[contains(@for,'CNTCT') or contains(@for,'address')])[1]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", addressOption);
            Thread.sleep(500);
            addressOption.click();
            System.out.println("Address selected successfully");
        } catch (Exception e) {
            System.out.println("Address selection failed: " + e.getMessage());
        }
    }

    //  Click Deliver Here
    public void clickDeliverHere() {
        try {
            WebElement deliverHere = wait.until(ExpectedConditions.visibilityOf(deliverHereButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", deliverHere);
            Thread.sleep(700);
            wait.until(ExpectedConditions.elementToBeClickable(deliverHere)).click();
            System.out.println("Clicked on Deliver Here button!");
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deliverHereButton);
            System.out.println("Deliver Here clicked via JS fallback");
        } catch (Exception e) {
            System.out.println("Deliver Here button not clickable: " + e.getMessage());
        }
    }

    //Click Continue (handle optional popup)
    public void clickContinue() {
        try {
            WebElement continueBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//button[contains(.,'Continue') or contains(.,'CONTINUE')]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", continueBtn);
            Thread.sleep(700);

            try {
                continueBtn.click();
                System.out.println("Clicked Continue to proceed (stopping before payment).");
            } catch (ElementClickInterceptedException e) {
                System.out.println("Continue button intercepted, trying JS click.");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", continueBtn);
            }

            handlePopupIfPresent();
        } catch (Exception e) {
            System.out.println("Continue button not visible: " + e.getMessage());
        }
    }

    // Handle Popups (Delivery or Alert)
    private void handlePopupIfPresent() {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
            System.out.println("Popup alert accepted successfully!");
        } catch (NoAlertPresentException ignored) {}

        // Sometimes a modal pops up inside the page
        try {
            WebElement popupAccept = driver.findElement(By.xpath("//button[contains(.,'OK') or contains(.,'Yes') or contains(.,'Accept')]"));
            if (popupAccept.isDisplayed()) {
                popupAccept.click();
                System.out.println("Accepted on-screen popup/modal");
            }
        } catch (Exception ignored) {}
    }
}
