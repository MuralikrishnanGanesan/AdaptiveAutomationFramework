package Stepdefinition;

import org.openqa.selenium.WebDriver;
import PageObjects.HomePageConstants;
import PageObjects.LoginPageConstants;
import commonUtils.CommonLibrary;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoginPageStepdefinition {
	
	private CommonLibrary commonlibrary;

	public LoginPageStepdefinition(CommonLibrary common)  {
		this.commonlibrary=common;

	}

	WebDriver driver;
	LoginPageConstants lp;
	@Given("^I launch the Wordpress application or Wordpress Url$")
	public void i_launch_the_Wordpress_application_or_wordpress_url() throws Throwable {
		commonlibrary.initiateBrowser();
		driver= commonlibrary.getdriver();
		System.out.println("Webdriver :"+driver);
		lp = new LoginPageConstants(driver);
		
	}

	@When("^I Enter valid login credentials$")
	public void i_enter_valid_login_credentials() throws Throwable {	
			
			
			commonlibrary.clearAndEnterText(lp.Username, "muralimurali14");
			commonlibrary.isElementPresentVerifyClick(lp.Login);
			commonlibrary.clearAndEnterText(lp.Password,"m#rali14");
			commonlibrary.isElementPresentVerifyClick(lp.Login);
			
		
	}
	@When("^I Enter invalid login credentials$")
	public void i_enter_Invalid_login_credentials() throws Throwable {
		
			commonlibrary.clearAndEnterText(lp.Username, "Tester");
			commonlibrary.isElementPresentVerifyClick(lp.Login);
			commonlibrary.clearAndEnterText(lp.Password,"Tester1");
			commonlibrary.isElementPresentVerifyClick(lp.Login);

		
	}

	
}
