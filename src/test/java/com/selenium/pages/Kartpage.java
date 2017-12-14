package com.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.selenium.config.OR;

public class Kartpage {
	WebDriver driver;
	@FindBy(xpath=OR.kartproduct)
	public WebElement kartproduct;
	
	public Kartpage(WebDriver dr){
		driver =dr;
	}
	

}
