package Listeners;

import Base.DriverFactory;
import Base.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TestListener implements ITestListener {
	 private static ExtentReports extent = ExtentManager.getInstance();
	    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	    @Override
	    
	    public void onTestStart(ITestResult result) {
	        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
	        test.set(extentTest);
	    }
	    @Override
	    public void onTestSuccess(ITestResult result) {
	        ExtentTest extentTest = (ExtentTest) result.getAttribute("extentTest");
	        if (extentTest != null) {
	            extentTest.log(Status.PASS, "Test Passed");
	        }
	    }

	    @Override
	    public void onTestFailure(ITestResult result) {
	        ExtentTest extentTest = test.get();

	        if (extentTest != null) {
	            extentTest.log(Status.FAIL, "Test Failed: " + result.getThrowable());

	            try {
	                File screenshotDir = new File("screenshots");
	                if (!screenshotDir.exists()) {
	                    screenshotDir.mkdirs();
	                }

	                File screenshot = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);
	                String fileName = result.getMethod().getMethodName() + "_" + System.currentTimeMillis() + ".png";
	                File dest = new File(screenshotDir, fileName);

	                Files.copy(screenshot.toPath(), dest.toPath());

	                extentTest.addScreenCaptureFromPath(dest.getAbsolutePath());

	            } catch (IOException e) {
	                extentTest.log(Status.WARNING, "Failed to attach screenshot: " + e.getMessage());
	            }

	        } else {
	            System.out.println("ExtentTest was null – test might not have started.");
	        }
	    }


    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
