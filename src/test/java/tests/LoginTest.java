package tests;

import org.testng.annotations.Test;
import Base.BaseTest;
import pages.LoginPage;
import Utils.CookieUtils;

public class LoginTest extends BaseTest {

  
    @Test
    public void loginToFlipkart() {
        try {
            LoginPage loginPage = new LoginPage(driver);

            System.out.println("Attempting login...");

            // If user is already logged in (cookies loaded), skip login
            if (!driver.getPageSource().contains("Login")) {
                System.out.println("Already logged in with cookies.");
                return;
            }

            loginPage.openLoginForm();
            loginPage.enterMobileNumber("your no.");
            loginPage.clickRequestOtp();

            System.out.println("Please enter OTP manually within 25 seconds...");
            Thread.sleep(25000); // wait manually for OTP entry

            // After OTP entry, save cookies
            CookieUtils.saveCookies(driver);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
