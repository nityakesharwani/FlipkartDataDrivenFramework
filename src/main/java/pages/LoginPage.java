package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;



public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;

    
    @FindBy(xpath = "//input[@class='_18u87m _3WuvDp']")
    private WebElement mobileNumberInput;

   
    @FindBy(xpath = "//button[contains(text(),'Request OTP')]")
    private WebElement requestOtpButton;

    // Locator for the close (X) button of the popup
    @FindBy(xpath = "//button[text()='✕' or text()='X' or contains(@class,'_2doB4z')]")
    private WebElement popupCloseButton;
    
    @FindBy(xpath = "//span[contains(text(),'Login')]")
    WebElement homeLoginButton;
    
    @FindBy(xpath = "//input[contains(@class,'r4vIwl BV+Dqf')]")
    WebElement homeMobileNo;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    
    private boolean isVisible(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**Handles both popup and manual login UI */
    public void openLoginForm() {
        try {
            // Close popup if visible
            if (isVisible(popupCloseButton)) {
                popupCloseButton.click();
                System.out.println("Closed auto popup.");
            }

            // Case 1: Popup login
            if (isVisible(mobileNumberInput)) {
                System.out.println("Popup login detected.");
                return;
            }

            // Case 2: Manual login
            if (isVisible(homeLoginButton)) {
                homeLoginButton.click();
                System.out.println("Clicked manual Login button on homepage.");
                wait.until(ExpectedConditions.visibilityOf(homeMobileNo));
                System.out.println("Manual login form visible now.");
            } else {
                System.out.println("No login option found.");
            }

        } catch (Exception e) {
            throw new RuntimeException("Unable to open login form: " + e.getMessage());
        }
    }

    /** Enter mobile number automatically in whichever input is visible */
    public void enterMobileNumber(String mobileNumber) {
        try {
            if (isVisible(mobileNumberInput)) {
            	mobileNumberInput.sendKeys(mobileNumber);
                System.out.println("Entered mobile number in popup login.");
            } else if (isVisible(homeMobileNo)) {
            	homeMobileNo.sendKeys(mobileNumber);
                System.out.println("Entered mobile number in manual login.");
            } else {
                throw new RuntimeException("No mobile input found on screen.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to enter mobile number: " + e.getMessage());
        }
    }

    /** Click Request OTP button */
    public void clickRequestOtp() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(requestOtpButton)).click();
            System.out.println("Clicked on Request OTP button.");
        } catch (Exception e) {
            throw new RuntimeException("Request OTP button not found: " + e.getMessage());
        }
    }
}