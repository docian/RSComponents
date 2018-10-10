package com.cucumber.utils.checkout.steps;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.cucumber.utils.TestRunner;
import com.test.checkout.Checkout.DataHeader;
import com.test.utils.BasicActions;
import com.test.utils.GenericPageModel;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinitions implements BasicActions{
	
	private GenericPageModel gpm = TestRunner.gpm;
	
	@Before()
	public void setUp(){
		List<WebElement> btn = gpm.getElements("declineButton");
		if(!btn.isEmpty()) {
			clickOnElement(btn.get(0));
		}
	}
	
	
    @Given("^Go To HomePage$")
    public void Go_To_HomePage() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    	gpm.getDriver().get(TestRunner.url);
    }
 
    @When("^I fill in \"([^\"]*)\" with \"([^\"]*)\"$")
    public void i_fill_in_with(String locator, String value) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    	while(!gpm.getElement(locator).isEnabled()) {};
    	gpm.getElement(locator).sendKeys(value+Keys.ENTER);
    }
 
    @When("^I click on the \"([^\"]*)\" button$")
    public void i_click_on_the_button(String buttonLocator) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    	clickOnElement(gpm.getElement(buttonLocator));
    }
    
    @When("^I click Up quantity \"([^\"]*)\" times$")
    public void upQuantity(String nTimes) {
    	setUp();
    	int n = Integer.parseInt(nTimes);
    	for(int i = 0; i < n; i++)
    		clickOnElement(gpm.getElement("increaseButton"));
    }
    
    @Then("^Verify the basket amount$")
    public void verifyAmount() {
    	Float prevPrice = Float.parseFloat(gpm.getElement("basketAmt").getText().substring(1).replace(",", ""));
		Float price = Float.parseFloat(gpm.getElement("unitPrice").getText().substring(1));
		Integer q = Integer.parseInt(gpm.getElement("amount").getAttribute("value").split(" ")[0]);
		gpm.getDriver().navigate().refresh();
		Float actualPrice =  Float.parseFloat(gpm.getElement("basketAmt").getText().substring(1).replace(",", ""));
		assertEquals(actualPrice.floatValue() - prevPrice.floatValue(), price.floatValue()*q.intValue(),0.001);
    }
 
    @Then("^I should see \"([^\"]*)\" message$")
    public void i_should_see_message(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
 
    @Then("^I should see the \"([^\"]*)\" button$")
    public void i_should_see_the_button(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    
    @Then("^The Item Page Is Showing The Text \"([^\"]*)\" Located By \"([^\"]*)\"$")
    public void checkTheItemPage(String text, String locator) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    	assertEquals(gpm.getElement(locator).getText(), text);
//        throw new PendingException();
    }
    
    @After
    public void tearDown() {
//    	gpm.getDriver().close();
    }

}
