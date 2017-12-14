package com.selenium.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.selenium.config.OR;

public class Detailpage {
	
	@FindBy(xpath=OR.addtokart)
	public WebElement addtokart;
	
	@FindBy(xpath=OR.viewkart)
	public WebElement viewkart;

}
