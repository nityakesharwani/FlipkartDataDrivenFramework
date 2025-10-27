package Base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import Utils.CookieUtils;

public class BaseTest {
	protected WebDriver driver;
	
	@BeforeMethod
	public void setup() {
		DriverFactory.initDriver("chrome");
		driver= DriverFactory.getDriver();
		driver.get("https://www.flipkart.com/");
		
		 CookieUtils.loadCookies(driver);
	     driver.navigate().refresh();
	     System.out.println("Cookies loaded and page refreshed.");
	}
	
	@AfterMethod
	public void tearDown(){
		DriverFactory.quitDriver();
		
	}

}
