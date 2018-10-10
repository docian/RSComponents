package com.test.utils;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebElement;

public interface BasicActions{
	
	public default void loadPage(GenericPageModel gpm, String url, String pgModelPath) {
		gpm.getDriver().manage().window().fullscreen();
		gpm.setPageModelPath(pgModelPath);
		gpm.getDriver().get(url);		
	}

	public default void fillText(WebElement e, String text) {
		if(e.getTagName().equals("input")&&(e.getAttribute("type").equals("text"))){
			e.sendKeys(text);
			return;
		}
		throw new InvalidArgumentException(e.getTagName()+" element is not fillable");
	}
	
	public default void clickOnElement(WebElement e) {
		e.click();
	}
	
}
