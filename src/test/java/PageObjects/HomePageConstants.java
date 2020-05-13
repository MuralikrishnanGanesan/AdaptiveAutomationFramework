package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSBy;
//import io.appium.java_client.pagefactory.iOSFindBy;

public class HomePageConstants {
	
	
WebDriver driver;
	
	public HomePageConstants(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		
	}

	@FindBy(xpath="//*[@id='search-component-1']")
	@AndroidFindBy(xpath="org.wordpress.android:id/my_site_title_label")
	@iOSBy(xpath = "org.wordpress.android:id/nux_username")
	public WebElement Search_Bar;
	
	@FindBy(xpath="//*[@id='primary']/main/div/div/div[3]/div[1]/div/div[3]/div[2]/div[1]/div[2]/div[1]/a[2]/div")
	@AndroidFindBy(id="org.wordpress.android:id/nux_password")
	//@iOSFindBy(xpath = "org.wordpress.android:id/nux_password")
	public WebElement First_Search_Result;
	
	
	
}
