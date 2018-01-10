package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class LoginPageConstants {
	WebDriver driver;
	
	public LoginPageConstants(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		//PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(id="usernameOrEmail")
	@AndroidFindBy(id="org.wordpress.android:id/nux_username")
	@iOSFindBy(xpath = "org.wordpress.android:id/nux_username")
	public WebElement Username;
	
	@FindBy(xpath="//*[@id='password']")
	@AndroidFindBy(id="org.wordpress.android:id/nux_password")
	@iOSFindBy(xpath = "org.wordpress.android:id/nux_password")
	public WebElement Password;
	
	@FindBy(xpath="//*[@id='primary']/div/main/div/div[1]/div/form/div[1]/div[2]/button")
	@AndroidFindBy(id="org.wordpress.android:id/nux_sign_in_button")
	@iOSFindBy(xpath = "//*[@resource-id='org.wordpress.android:id/nux_sign_in_button']")
	public WebElement Login;
	
	@FindBy(xpath="//*[@id='search-component-1']")
	@AndroidFindBy(xpath="org.wordpress.android:id/my_site_title_label")
	@iOSFindBy(xpath = "org.wordpress.android:id/nux_username")
	public WebElement HomeScreen;
	
	
	public void validLogin(){
		
		Username.sendKeys("muralimurali14");
		Password.sendKeys("m#rali14");
		Login.click();
		
	}
	
public void InvalidLogin(){
		
		Username.sendKeys("muralikrishnan");
		Password.sendKeys("Password");
		Login.click();
		
	}
	
		
	
}
