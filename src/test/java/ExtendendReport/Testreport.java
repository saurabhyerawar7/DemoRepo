package ExtendendReport;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Testreport {
	static WebDriver driver;
	public ExtentReports extent;
	public ExtentTest extentTest;

	@BeforeTest
	public void setextend() {
		extent = new ExtentReports(System.getProperty("jbk1.dir") + "test-output/ExtentReport.html",true);
		extent.addSystemInfo("Host Name", "Saurabh win");
		extent.addSystemInfo("Enviroment", "QA");

	}

	@AfterTest
	public void endReport() {
		extent.flush();
		extent.close(); 
	}
	
	public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
	String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	TakesScreenshot ts= (TakesScreenshot)driver;
	File soure=ts.getScreenshotAs(OutputType.FILE);
	String destination= System.getProperty("user.dir")+"/FaildTestScreenshot/"+screenshotName+dateName+".png";
	File finaldestinatin=new File(destination);
	FileUtils.copyFile(soure, finaldestinatin);
	
	return destination;
		
	}

	@BeforeMethod
	public void setup() {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\Saurabh\\Desktop\\java&selenium\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("file:///C:/Users/Saurabh/Desktop/java&selenium/Offline%20Website%20_%20new/index.html");

	}

	@Test
	public void test() {
		extentTest=extent.startTest("test");
		
		String title = driver.getTitle();
		assertEquals(title, "AdminLTE 2 | Log in1");

	}
	@Test
	public void  username()
	{
		extentTest=extent.startTest("test");
	WebElement wb= driver.findElement(By.xpath("//*[@id=\"email\"]"));
	String ps=wb.getAttribute("Placeholder");
	assertEquals(ps, "Email1");
	
	}

	@AfterMethod
	public void tearown(ITestResult result) throws IOException {
		if(result.getStatus()==ITestResult.FAILURE) {
			extentTest.log(LogStatus.FAIL, "TestCase is faild"+result.getName());
			extentTest.log(LogStatus.FAIL, "TestCase is faild"+result.getThrowable());
			String screenshotPath = Testreport.getScreenshot(driver, result.getName());
			extentTest.log(LogStatus.FAIL,extentTest.addScreenCapture(screenshotPath));
		}
		else if(result.getStatus()==ITestResult.SKIP)
		{
			extentTest.log(LogStatus.SKIP,"skip TestCase is"+result.getName());
		}
		else if(result.getStatus()==ITestResult.SUCCESS) {
			extentTest.log(LogStatus.PASS, "Test Pass"+result.getName());
		}
		extent.endTest(extentTest);
		driver.quit();
	}

}
