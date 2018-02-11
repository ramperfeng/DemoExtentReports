package com.selenium.testscripts;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
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
import com.selenium.extentreports.HandleExtentReports;
import com.selenium.pages.Detailpage;
import com.selenium.pages.Kartpage;
import com.selenium.pages.LandingPage;
import com.selenium.util.BaseClass;

public class TS1{
	BaseClass b=new BaseClass();
	
	
	
	// ADD new feature to this file 
	public WebDriver driver;
	public ExtentReports extent;
	public ExtentTest extentTest;
	@BeforeMethod
	public void openApp(){
		b.openBrowser("firefox");
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
