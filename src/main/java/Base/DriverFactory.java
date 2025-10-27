package Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;


import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	
	public static WebDriver getDriver() {
		return driver.get();
	}
	public static void initDriver(String browser) {
		if(browser.equalsIgnoreCase("chrome")){
			WebDriverManager.chromedriver().setup();
			driver.set(new ChromeDriver());
		}
		else if(browser.equalsIgnoreCase("firefox")) {
		    WebDriverManager.edgedriver().setup();
			driver.set(new EdgeDriver());
			
		}
		getDriver().manage().window().maximize();
	}
	public static void quitDriver() {
		getDriver().quit();
		driver.remove();
	}
	

}
