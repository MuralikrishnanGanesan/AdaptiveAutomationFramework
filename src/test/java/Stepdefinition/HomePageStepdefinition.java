package Stepdefinition;

import org.openqa.selenium.WebDriver;

import PageObjects.HomePageConstants;
import PageObjects.LoginPageConstants;
import commonUtils.CommonLibrary;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

public class HomePageStepdefinition {
	
	
	private CommonLibrary commonlibrary;

	public HomePageStepdefinition(CommonLibrary common)  {
		this.commonlibrary=common;

	}
	
	
	WebDriver driver;
	HomePageConstants hp;
	
	@Then("^I should land on homescreen$")
	public void i_should_land_on_homescreen() throws Throwable {
		
		driver = commonlibrary.getdriver();
		hp = new HomePageConstants(driver);
		Thread.sleep(3000);
		commonlibrary.isElementPresentVerification(hp.Search_Bar);

	}
	
	 @Then("^I Select the First item in the search list$")
	    public void i_select_the_first_item_in_the_search_list() throws Throwable {
	       
		 commonlibrary.isElementPresentVerifyClickPageLoad(hp.First_Search_Result);
		 
	    }

	    @And("^I Search for \"([^\"]*)\"$")
	    public void i_search_for_something(String strArg1) throws Throwable {
	        
	    	commonlibrary.clearAndEnterText(hp.Search_Bar, strArg1);
	    	
	    }

}
