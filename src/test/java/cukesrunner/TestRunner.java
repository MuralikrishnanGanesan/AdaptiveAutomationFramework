package cukesrunner;

import org.testng.annotations.BeforeClass;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions
(
glue     = {"commonUtils","botanicsStepdefinition","ParallelExecutionFiles","Stepdefinition"},
features = "src/test/resources/features", 
plugin   = { "html:automationReport/cucumber-htmlreport","usage:automationReport/cucumber-usage.json", "json:automationReport/cucumber-report.json",
		     "com.cucumber.listener.ExtentCucumberFormatter:AutomationExtentReport/report.html"},
tags     = { "@tag2,@tag1"}, 
monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {  
	
	public class ExtendReportRunner extends AbstractTestNGCucumberTests {

		@BeforeClass
		public void setup() {
		
// Initiates the extent report and generates the output in the output/Run_<unique timestamp>/report.html file by default.

//		ExtentCucumberFormatter.initiateExtentCucumberFormatter();
//		
//		ExtentCucumberFormatter.loadConfig(new File("src/test/java/extent-config.xml"));
		
	}
		}



}