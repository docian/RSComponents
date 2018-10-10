package com.cucumber.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.test.utils.GenericPageModel;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;

@CucumberOptions(
        features = "src/com/cucumber/features",
        glue = {"com.cucumber.utils.checkout.steps"},
        monochrome=true,
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-pretty",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "rerun:target/cucumber-reports/rerun.txt"
        })

public class TestRunner extends AbstractTestNGCucumberTests{
	private TestNGCucumberRunner testNGCucumberRunner;
	public static String browserName = "Chrome";
	public static String url = "https://uk.rs-online.com/web/";
	public static String path = "data/locator_files/HomePage.json";
	public static GenericPageModel gpm;
	
	@Parameters({"browserName","url","path"})
    @BeforeClass(alwaysRun = true)
    public void setUpClass(String browserName, String url, String path) throws Exception {  
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
        TestRunner.browserName =  browserName;
        TestRunner.url =  url; 
        TestRunner.path = path;
    	gpm = new GenericPageModel(TestRunner.browserName);
    	gpm.getDriver().get(TestRunner.url);
    	gpm.getDriver().manage().window().maximize();
    	gpm.setPageModelPath(TestRunner.path);
    }
  
    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
    	gpm.getDriver().close();
        testNGCucumberRunner.finish();
    }	
}
