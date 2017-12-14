package com.selenium.testscripts;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.selenium.pages.Detailpage;
import com.selenium.pages.Kartpage;
import com.selenium.pages.LandingPage;
import com.selenium.util.BaseClass;

public class TS1{
	BaseClass b=new BaseClass();
	@BeforeMethod
	public void openApp(){
		b.openBrowser("firefox");
	}
	
	 
	@Test
	public void addItemtokart(){
		
		LandingPage lp=PageFactory.initElements(b.driver, LandingPage.class);
		Kartpage kp=PageFactory.initElements(b.driver, Kartpage.class);
		Detailpage dp=PageFactory.initElements(b.driver, Detailpage.class);
		
		lp.rubyxpath.click();
		dp.addtokart.click();
		dp.viewkart.click();
		String actual=kp.kartproduct.getText();
		Assert.assertEquals(actual, "Selenium Ruby");
	}
	
	@AfterMethod
	public void teardown(){
		b.closebrowser();
	}
	
}
