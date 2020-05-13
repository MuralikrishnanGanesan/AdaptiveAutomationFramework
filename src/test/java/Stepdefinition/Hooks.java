 package Stepdefinition;

import java.io.File;
import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.cucumber.listener.Reporter;

import ParallelExecutionFiles.AndroidDeviceConfiguration;
import ParallelExecutionFiles.AppiumServermanager;
import ParallelExecutionFiles.DeviceAllocationManager;
import ParallelExecutionFiles.WebBrowserConfiguration;
import commonUtils.CommonLibrary;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import helpers.UserConfig;


public class Hooks {


	private CommonLibrary commonlibrary;

	public Hooks(CommonLibrary common) throws ConfigurationException, IOException {
		commonlibrary= common;

	}


	private WebDriver driver;
	public static Scenario scenario;

	@SuppressWarnings("static-access")
	@Before
	public synchronized void beforeScenarioStarted(Scenario scenario) throws Exception {
		this.scenario = scenario;

		System.out.println("Tags: " + scenario.getSourceTagNames());
		System.out.println("####### Scenario: " + scenario.getName() + " Started #######");		


		if(CommonLibrary.config.getString("appType").equalsIgnoreCase("androidNative")){

			DeviceAllocationManager.initializePort(1);

			DeviceAllocationManager.allocateDevice(DeviceAllocationManager.getNextAvailableDeviceId(), "Pass IOS Device if available");

			AppiumServermanager.startAppiumServer();
			
			

		}else if (CommonLibrary.config.getString("breakPoint").equalsIgnoreCase("Desktop")){

			WebBrowserConfiguration.allocateBrowser(WebBrowserConfiguration.getNextAvailableBrowser());

		}

	}


	@After
	public void embedScreenshotOnFail(Scenario scenario) throws ConfigurationException, IOException, InterruptedException {
		this.scenario = scenario;

		driver= commonlibrary.getdriver();
		long ms = System.currentTimeMillis();
		
		if (scenario.isFailed()) {
			
			System.out.println("Tags: " + scenario.getSourceTagNames());
			System.out.println("####### Scenario: " + scenario.getName() + " FAILED #######");

			commonlibrary.AndroidDevice_information();
			try {

				File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				//The below method will save the screen shot in d drive with name "screenshot.png"
				FileUtils.copyFile(scrFile, new File (System.getProperty("user.dir")+"/Screenshots"+"/FailScreenshots"+"/"+ms+"screenshot.png"));
				Reporter.addScreenCaptureFromPath(System.getProperty("user.dir")+"/Screenshots"+"/FailScreenshots"+"/"+ms+"screenshot.png");
				

			} catch (Exception e) {
				scenario.write("Embed Failed " + e.getMessage());
			} 
		} else{

			System.out.println("Tags: " + scenario.getSourceTagNames());
			System.out.println("####### Scenario: " + scenario.getName() + " Completed Successfully #######");

		}

		if(CommonLibrary.config.getString("appType").equalsIgnoreCase("androidNative")){
			
			DeviceAllocationManager.freeDevice();
			driver.quit();
			AppiumServermanager.stopAppiumServer();

		}else if(CommonLibrary.config.getString("breakPoint").equalsIgnoreCase("Desktop")){

			WebBrowserConfiguration.freeBrowser();
			driver.quit();

		}else{

			driver.quit();

		}
	}
}








