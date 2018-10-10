package com.test.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class GenericPageModel{

	private WebDriver driver;
	private String filePath;
	
	public enum LocatorType{
		id,cssSelector,xpath;
	}
	
	public GenericPageModel(String browserName) {
		if (browserName.equalsIgnoreCase("IE")) {
			driver = new InternetExplorerDriver();
		}
		else 
			if (browserName.equalsIgnoreCase("chrome")) {
				driver = new ChromeDriver();
			}
			else 
				if (browserName.equalsIgnoreCase("firefox")) driver = new FirefoxDriver();
				else driver = null;
	}

	public GenericPageModel(String browserName, Object options) {
		this.setWebDriverInstance(browserName, options);
	}
	


	public void setWebDriverInstance(String browserName, Object options) {
		if (browserName.equalsIgnoreCase("IE")) {
			InternetExplorerOptions capabilities = (InternetExplorerOptions)options;
			driver = new InternetExplorerDriver(capabilities);
		}
		else 
			if (browserName.equalsIgnoreCase("chrome")) {
				ChromeOptions capabilities = (ChromeOptions) options;
				driver = new ChromeDriver(capabilities);
			}
			else 
				if (browserName.equalsIgnoreCase("firefox")) {
					FirefoxOptions capabilities = (FirefoxOptions) options;
					driver = new FirefoxDriver(capabilities);
				}
				else driver = null;
	}

	public void setPageModelPath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getPageModelPath() {
		return this.filePath;
	}

	public WebDriver getDriver() {
		return driver;
	}

	@SuppressWarnings("rawtypes")
	public WebElement getElement(String elementName) {

		JSONParser parser = new JSONParser();
		Object obj = null;
		try {
			obj = parser.parse(new FileReader(this.getPageModelPath()));
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		JSONObject jsonObject = (JSONObject) obj;

		HashMap hm = (JSONObject) jsonObject.get(elementName);
		if (hm.containsKey(LocatorType.id.name())) {
			String selector = (String) hm.get(LocatorType.id.name());
			if (!UtilsDataProvider.isNullOrBlank(selector)) {
				WebElement element = driver.findElement(By.id(selector));
				return element;
				} 
			}else if (hm.containsKey(LocatorType.cssSelector.name())) {
					String cssSelector = (String) hm.get(LocatorType.cssSelector.name());
					if (!UtilsDataProvider.isNullOrBlank(cssSelector)) {
						WebElement element = driver.findElement(By.cssSelector(cssSelector));
						return element;
						}
					} else if (hm.containsKey(LocatorType.xpath.name())) {
							String xpathSelector = (String) hm.get(LocatorType.xpath.name());
							if (!UtilsDataProvider.isNullOrBlank(xpathSelector)) {
								WebElement element = driver.findElement(By.xpath(xpathSelector));
								return element;
							}
					}
		return null;
	}

	public List<WebElement> getElements(String elementName) {

		JSONParser parser = new JSONParser();
		Object obj = null;
		try {
			obj = parser.parse(new FileReader(this.getPageModelPath()));
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		JSONObject jsonObject = (JSONObject) obj;

		HashMap hm = (JSONObject) jsonObject.get(elementName);
		if (hm.containsKey(LocatorType.id.name())) {
			String selector = (String) hm.get(LocatorType.id.name());
			if (!UtilsDataProvider.isNullOrBlank(selector)) {
				List<WebElement> lst = driver.findElements(By.id(selector));
				return lst;
				} 
			}else if (hm.containsKey(LocatorType.cssSelector.name())) {
					String cssSelector = (String) hm.get(LocatorType.cssSelector.name());
					if (!UtilsDataProvider.isNullOrBlank(cssSelector)) {
						List<WebElement> lst = driver.findElements(By.cssSelector(cssSelector));
						return lst;
						}
					} else if (hm.containsKey(LocatorType.xpath.name())) {
							String xpathSelector = (String) hm.get(LocatorType.xpath.name());
							if (!UtilsDataProvider.isNullOrBlank(xpathSelector)) {
								List<WebElement> lst = driver.findElements(By.xpath(xpathSelector));
								return lst;
							}
					}
		return null;
	}

	public void clickWithJavaScriptExecutor(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();
		executor.executeScript("arguments[0].click();", element);
	}

}
