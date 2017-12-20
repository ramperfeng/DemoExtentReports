package com.selenium.extentreports;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.selenium.pages.Detailpage;
import com.selenium.pages.Kartpage;
import com.selenium.pages.LandingPage;
import com.selenium.util.BaseClass;

public class HandleExtentReports {
	BaseClass b=new BaseClass();
	
	public WebDriver driver;
	public ExtentReports extent;
	public ExtentTest extentTest;
	
	
	@BeforeMethod
	public void launchAPP()
	{
		driver = new FirefoxDriver();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://www.freecrm.com/");
		
	}

	
	@BeforeTest
	public void setExtent()
	{
		extent = new ExtentReports(System.getProperty("user.dir")+"/test-output/ExtentReport.html",true);
	}
	
	
	
	@AfterTest
	public void endReport()
	{
		extent.flush();
		extent.close();
	}
	
	
	public static String getScreenShot(WebDriver driver,String screenshotname) throws IOException
	{
		String dateName= new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts=(TakesScreenshot)driver;
		File source= ts.getScreenshotAs(OutputType.FILE);
		String destination=System.getProperty("user.dir")+"/FailedTestsScreenshots/"+screenshotname+dateName+".png";
		File finalDestination= new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}
	
	
	
	@Test
	public void freecrmtest()
	{
		extentTest= extent.startTest("freecrmtest");
		String title=driver.getTitle();
		Assert.assertEquals(title, "#1  prsfert Free CRM for Any Business: Online Customer Relationship Software");
		
	}
	@Test
	public void freecrmlogo()
	{
		extentTest= extent.startTest("freecrmlogo");
		boolean b=driver.findElement(By.xpath("//img[@class='img-responsive']")).isDisplayed();
		Assert.assertTrue(b);
	}
	//@Test
	public void addItemtokart(){
		extentTest= extent.startTest("addItemtokartTest");
		LandingPage lp=PageFactory.initElements(b.driver, LandingPage.class);
		Kartpage kp=PageFactory.initElements(b.driver, Kartpage.class);
		Detailpage dp=PageFactory.initElements(b.driver, Detailpage.class);
		
		lp.rubyxpath.click();
		dp.addtokart.click();
		dp.viewkart.click();
		String actual=kp.kartproduct.getText();
		Assert.assertEquals(actual, "SElterty");
	}
	
	@AfterMethod
	public void teardown(ITestResult result) throws IOException
	{
		
		if(result.getStatus()==ITestResult.FAILURE)
		{
			extentTest.log(LogStatus.FAIL, "Test case Failed is "+result.getName());
			extentTest.log(LogStatus.FAIL, "Test case Failed is "+result.getThrowable());
			
			String screenshotpath=HandleExtentReports.getScreenShot(driver, result.getName());
			extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(screenshotpath) );
			//extentTest.log(LogStatus.FAIL, extentTest.addScreencast(screenshotpath) );
			
			
		}
		else if(result.getStatus()==ITestResult.SUCCESS)
		{
			extentTest.log(LogStatus.PASS, "Test case passed  is "+result.getName());
		}
		extent.endTest(extentTest);
		driver.quit();
	}
}
