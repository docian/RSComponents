package com.test.checkout;

import static org.testng.Assert.assertEquals;

import java.awt.RenderingHints.Key;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.ITestOrConfiguration;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.test.utils.BasicActions;
import com.test.utils.GenericPageModel;
import com.test.utils.UtilsDataProvider;

public class Checkout implements BasicActions{
	
	GenericPageModel gpm;
	String url;
	
	public enum DataHeader{
		TestID(0),TestCase(1),RSStockReference(2),ManufacturerReference(3),Locator(4),OutcomeLocator(5),Description(6);
		private final int col;
		private DataHeader(int col) {
			this.col = col;
		}	
	}
	
	public enum BasketHeader{
		TestID(0),TestCase(1),RSStockReference(2),ManufacturerReference(3),Locator(4),UnitPriceLocator(5),Amount(6);
		private final int col;
		private BasketHeader(int col) {
			this.col = col;
		}	
	}	
	
	@Parameters({"browser","url","path","dataPath"})
	@BeforeClass
	public void setup(String browser, String url, String path, String dataPath, ITestContext itc) {
		//Open the browser and load the page model
		gpm = new GenericPageModel(browser);
		this.url = url;
		loadPage(gpm, url, path);
		itc.setAttribute("dataPath", dataPath);
	}
	
	@BeforeMethod
	public void cancelPopUp() {
		List<WebElement> btn = gpm.getElements("declineButton");
		if(!btn.isEmpty()) {
			clickOnElement(btn.get(0));
		}
	}
	
	@Test(dataProvider="productById", enabled=false)
	public void getProductByRSSStockRef(String[] row) {
		fillText(gpm.getElement(row[DataHeader.Locator.col]), row[DataHeader.RSStockReference.col]+Keys.ENTER);
		assertEquals(gpm.getElement(row[DataHeader.OutcomeLocator.col]).getText(), row[DataHeader.Description.col]);
	}
	
	@Test(dataProvider="priceComputation", enabled = false)
	public void testPriceComputation(String[] row) {
		fillText(gpm.getElement(row[DataHeader.Locator.col]), row[DataHeader.RSStockReference.col]+Keys.ENTER);
		Float price = Float.parseFloat(gpm.getElement("unitPrice").getText().substring(1));
		Integer q = Integer.parseInt(gpm.getElement("units").getText().split(" ")[0]);
		Float actualPrice =  Float.parseFloat(gpm.getElement("indicativePrice").getText().substring(1).replace(",", ""));
		assertEquals(actualPrice.floatValue(), price.floatValue()*q.intValue());
	}
	
	@Test(dataProvider="addToBasket")
	public void testAddToBasket(String[] row){
		fillText(gpm.getElement(row[DataHeader.Locator.col]), row[DataHeader.RSStockReference.col]+Keys.ENTER);	
		Float price = Float.parseFloat(gpm.getElement("unitPrice").getText().substring(1));
		Integer q = Integer.parseInt(gpm.getElement("units").getText().split(" ")[0]);		
	}
	
	@AfterMethod
	public void navigateToHomePage() {
		if(gpm.getElements("searchTerm").isEmpty())
			gpm.getDriver().get(url);
	}
	
	@AfterClass
	public void tearDown() {
		//Close the browser
		gpm.getDriver().quit();
	}
	
	@DataProvider(name="productById")
	public String[][] issueData(ITestContext its) {
		return UtilsDataProvider.issueData((String)its.getAttribute("dataPath"), "ProductById");
	}
	
	@DataProvider(name="priceComputation")
	public String[][] issueDataPriceComputation(ITestContext its) {
		return UtilsDataProvider.issueData((String)its.getAttribute("dataPath"), "PriceComputation");
	}
	
	@DataProvider(name="addToBasket")
	public String[][] issueDataAddToBasket(ITestContext its){
		return UtilsDataProvider.issueData((String)its.getAttribute("dataPath"), "AddToBasket");
	}

}
