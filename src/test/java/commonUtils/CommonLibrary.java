package commonUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.JTextField;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.eclipse.jetty.util.IO;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cucumber.listener.Reporter;

import ParallelExecutionFiles.AndroidDeviceConfiguration;
import ParallelExecutionFiles.BrowserManager;
import ParallelExecutionFiles.DeviceAllocationManager;
import ParallelExecutionFiles.DeviceManager;
import ParallelExecutionFiles.ParallelThread;
import cucumber.api.DataTable;
import helpers.UserConfig;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;



public class CommonLibrary {


	public WebDriver webDriver;
	static WebDriverWait browserWithElementWait = null;
	public static Configuration config = null;
	public static FileWriter reportFile=null;

	
	public CommonLibrary() throws ConfigurationException, IOException {
		ConfigurationFactory factory = new ConfigurationFactory("config/config.xml");
		config = factory.getConfiguration();
	}



	/**
	 * Muralikrishnan G
	 * Method to create CSV file to update the Dom load and Page load of a page
	 * @throws IOException
	 * 
	 */

	public static void initReportFile() throws IOException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy-hh-mm-ss");
			Date date = new Date();
			String reportFileFullPath = UserConfig.reportDir + UserConfig.reportFile + "_" 
					+ sdf.format(date) + ".csv";
			File file = new File(reportFileFullPath);
			file.createNewFile();
			reportFile =  new FileWriter(reportFileFullPath, true);
			reportFile.append("DomLoadTime (sec),PageLoadTime (sec) ,From Page,To Page,ResponseCode,ResponseMessage,ThreadName,"
					+ "DataType,Success,FailureMessage,Bytes,GrpThreads,AllThreads,Latency,IdleTime");
			reportFile.append("\n");
			reportFile.flush();

		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException(e);
		}
	}




	/**
	 * Muralikrishnan G
	 * Method to Close the Report file
	 * @throws IOException
	 * 
	 */

	public static void closeReportFile() throws IOException {
		reportFile.flush();
		reportFile.close();
	}




	/**
	 * Muralikrishnan G
	 * Method to calculate the Page-load time while you navigate from one page to another Page
	 * @param  Webelement
	 * @throws 
	 */

	public  boolean isElementPresentVerifyClickPageLoad(WebElement element) {
		boolean isVerifiedAndClicked = false;
		browserWithElementWait = new WebDriverWait(webDriver,config.getInt("elementWaitInSeconds"));
		String PageName= webDriver.getTitle();
		if(config.getString("Pageload").equalsIgnoreCase("yes")){
			try {
				browserWithElementWait.until(ExpectedConditions.elementToBeClickable(element));
				if (element != null) {
					element.click();
					isVerifiedAndClicked = true;
					JavascriptExecutor js =((JavascriptExecutor) webDriver); 
					Object val = js.executeScript("" +
							"try{window.performance = window.performance || window.mozPerformance || window.msPerformance || window.webkitPerformance || {};" +
							"return(parseInt(window.performance.timing.domContentLoadedEventEnd)-parseInt(window.performance.timing.fetchStart));}catch(e){alert(e);}");
					String s=val.toString();
					System.out.println("Dom Load "+s);

					// Get Page Load
					Object val1 = js.executeScript("" +
							"try{window.performance = window.performance || window.mozPerformance || window.msPerformance || window.webkitPerformance || {};" +
							"return(parseInt(window.performance.timing.domComplete)-parseInt(window.performance.timing.navigationStart));}catch(e){alert(e);}");
					String s1=val1.toString();


					// Casting to double for computes
					double DomLoad = (Double.parseDouble(s)/1000.0);
					double PageLoad = (Double.parseDouble(s1)/1000.0);

					// Casting again for concatenation and return
					String DomLoadString = Double.toString(DomLoad);
					String PageLoadString = Double.toString(PageLoad);

					String LandingPagename = webDriver.getTitle();

					ReportObject ro = new ReportObject(DomLoadString, PageLoadString,PageName,LandingPagename, "Global Brands", 0, 0);
					logPerfMetrics(ro);	
				} 

				else {
					throw new Exception("Object Couldn't be retrieved and clicked");
				}
			} catch (Exception e) {
				element = null;
			}

		}else{
			
			try {
				browserWithElementWait.until(ExpectedConditions.elementToBeClickable(element));

				if (element != null) {

					element.click();
					isVerifiedAndClicked = true;
					screenshotForAllSteps(element);
				} else {
					
					throw new Exception("Object Couldn't be retrieved and clicked");
				}
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
		}
		return isVerifiedAndClicked;

	}




	/**
	 * Muralikrishnan G
	 * Method to Click on given WebElement
	 * @param - Webelement
	 * 
	 */

	public boolean isElementPresentVerifyClick(WebElement element) {
		boolean isVerifiedAndClicked = false;
		highlightElement(element, webDriver);
		browserWithElementWait = new WebDriverWait(webDriver,config.getInt("elementWaitInSeconds"));

		try {
			browserWithElementWait.until(ExpectedConditions.elementToBeClickable(element));

			if (element != null) {

				element.click();
				isVerifiedAndClicked = true;
				screenshotForAllSteps(element);
			} else {
				
				throw new Exception("Object Couldn't be retrieved and clicked");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		return isVerifiedAndClicked;
	}





	/**
	 * Muralikrishnan G
	 * Appending the page load Time and Dom load Time to the CSV File
	 * @param - Report Object
	 * @throws IO Exception
	 * 
	 */

	public static void logPerfMetrics(ReportObject ro1) throws IOException{

		reportFile.append(ro1.toString());
		reportFile.append("\n");
		reportFile.flush();
		
	}




	/**
	 * Muralikrishnan G
	 * Method to Initiate Web Browser , Android Mobile Web browser and Android Application based on the input from Property File
	 * @throws IOException 
	 * @throws InterruptedException
	 * @throws ConfigurationException
	 * 
	 */

	public WebDriver initiateBrowser() throws ConfigurationException, IOException, InterruptedException {


		if (config.getString("breakPoint").equalsIgnoreCase("Desktop")) {

			//	if ("Yes".equalsIgnoreCase(config.getString("fireFox"))) {
			if (BrowserManager.getBrowserID().equalsIgnoreCase("fireFox")) {

				System.setProperty("webdriver.gecko.driver", UserConfig.geckodriver_desktop_location);

				webDriver = new FirefoxDriver();
				webDriver.manage().window().maximize();
				webDriver.manage().deleteAllCookies();
				webDriver.get(config.getString("ApplicationUrl"));
				webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			} 

			//else if ("Yes".equalsIgnoreCase(config.getString("chrome"))) {
			else if (BrowserManager.getBrowserID().equalsIgnoreCase("chrome")) {

				System.setProperty("webdriver.chrome.driver", UserConfig.chromeDriver_Desktop_Location);

				webDriver = new ChromeDriver();
				webDriver.manage().window().maximize();
				webDriver.manage().deleteAllCookies();
				webDriver.get(config.getString("ApplicationUrl"));
				webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);


			} 
			//else if ("Yes".equalsIgnoreCase(config.getString("IE"))) {
			else if (BrowserManager.getBrowserID().equalsIgnoreCase("ie")) {

				System.setProperty("webdriver.ie.driver", UserConfig.IEDriver_Desktop_Location);

				webDriver= new InternetExplorerDriver();
				webDriver.manage().window().maximize();
				webDriver.manage().deleteAllCookies();
				webDriver.get(config.getString("ApplicationUrl"));
				webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				Thread.sleep(3000);

			} else if ("Yes".equalsIgnoreCase(config.getString("safari"))) {
				
			
		} 

			else {

				ParallelThread.figlet("No Browser Found");
				System.exit(0);
			}		


		} else if(config.getString("breakPoint").equalsIgnoreCase("Mobile")) {

			if ("iOS".equalsIgnoreCase(config.getString("operatingSystem"))) {

				initiateBrowser_iOS();

			} else if("android".equalsIgnoreCase(config.getString("operatingSystem"))) {

				initiateBrowser_Android();

			}else if("androidNative".equalsIgnoreCase(config.getString("appType"))) {

				initiateNativeAPP_Android();

			} 
			else {
				
				ParallelThread.figlet("No Mobile App/Operating System Found");
				System.exit(0);
			}
		}
		
		return webDriver;



	}




	/**
	 * Muralikrishnan G
	 * Method to Close the WebBrowser
	 * @throws InterruptedException
	 *  
	 */

	public void closeBrowser() throws InterruptedException{


		webDriver.quit();
	}




	/**
	 * Muralikrishnan G
	 * Method to Initiate Android Web Browser through Chrome Options
	 * @throws IOException
	 * @throws InterruptedException
	 * 
	 */

	public void initiateBrowser_Android() throws IOException, InterruptedException {
		try {
			String adbPath = config.getString("sdk_Location")+ File.separator + "platform-tools";
			//Runtime.getRuntime().exec(UserConfig.projectLocation + File.separator + "Extensions/Killchromedriver.sh" + " start");
			Thread.sleep(1000 * 2);
			Thread.sleep(1000);
			Runtime.getRuntime().exec(adbPath + "/adb" + " start-server");
			Thread.sleep(1000);
			Runtime.getRuntime().exec(System.getProperty("user.dir")+"/MobileDriver/chromedriver");
			Thread.sleep(1000);//UserConfig
			System.out.println("initialising the browser");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("androidPackage", "com.android.chrome");
			options.setExperimentalOption("androidDeviceSerial", UserConfig.deviceId);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			capabilities.setPlatform(Platform.ANDROID);
			capabilities.setCapability("device", "android");
			capabilities.setCapability("app", "chrome");
			webDriver = new RemoteWebDriver(new URL(UserConfig.chromeDriver_location), capabilities);
			webDriver.manage().deleteAllCookies();
			webDriver.get(config.getString("ApplicationUrl"));
			webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} catch(Exception e) {
			e.printStackTrace();
		}

	}




	/**
	 * Muralikrishnan G
	 * Method to Initiate IOS Web Browser
	 * @throws IOException
	 * @throws InterruptedException
	 * 
	 */

	public void initiateBrowser_iOS() throws IOException, InterruptedException {
		try {
			System.out.println("initialising the Ios browser");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(CapabilityType.BROWSER_NAME,"safari");
			capabilities.setCapability("app", "safari");
			capabilities.setCapability("platformName", "iOS");
			capabilities.setCapability("platformVersion", UserConfig.platform_Version);
			capabilities.setCapability("deviceName", "iPhone");
			capabilities.setCapability("device", "iPhone");
			capabilities.setCapability("-U", UserConfig.deviceUDID); 
			capabilities.setCapability("app", UserConfig.safarilauncherLocation); 
			capabilities.setCapability("noReset", true);
			capabilities.setCapability("autoAcceptAlerts", true);
			webDriver=new RemoteWebDriver(new URL(UserConfig.appiumServerURL_iOS), capabilities);
			webDriver.manage().deleteAllCookies();
			webDriver.get(config.getString("applicationURL"));
			webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}




	/**
	 * Muralikrishnan G
	 * Method to Initiate Android Native Application
	 * @throws IOException
	 * @throws InterruptedException
	 * 
	 */

	public void initiateNativeAPP_Android() throws IOException, InterruptedException {
		try {
			System.out.println("initialising the Android Application");
			DesiredCapabilities capabilities = new DesiredCapabilities();	
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "6.0.1");
			capabilities.setCapability("deviceName", "Android");
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,AutomationName.APPIUM);
			capabilities.setCapability(MobileCapabilityType.UDID, DeviceManager.getDeviceUDID());
			capabilities.setCapability("appPackage", config.getString("AppPackage")); 
			capabilities.setCapability("appActivity", config.getString("AppActivity")); 
			capabilities.setCapability("app", UserConfig.app);

			webDriver=new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:"+DeviceAllocationManager.getNextAvailablePortBinding()+"/wd/hub"), capabilities);

			Thread.sleep(15000);

		} catch(Exception e) {
			e.printStackTrace();
		}

	}




	/**
	 *  Methods for Validating the Alt text of an image
	 *  @param - WebElement , Alt Text , Image Name
	 *  
	 */

	public void accessibilityValidation(WebElement element ,String Text, String imgName) {
		try {
			
			String alt = element.getAttribute("alt");
			if(alt.equalsIgnoreCase(Text)) {
				System.out.println(imgName+" Image accessibility expected and actual name are Same");
			}else {
				System.out.println(imgName+" Image accessibility expected and actual name are not Same");
				System.out.println("Accessibility-Actual : "+alt);
				System.out.println("Accessibility-Expected : "+Text);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
		}
	}




	/**
	 *  Methods for Validating the Link text of a WebElement
	 *  @param - WebElement , Expected Text
	 *  @throws Exception
	 *  
	 */

	public void linkText_Validation(WebElement element,String Text) throws Exception {
		//	try {
		highlightElement(element, webDriver);
		String linkText = element.getText();
		if(linkText.equalsIgnoreCase(Text)) {

			log("Link Text expected and actual text are Same: "+ linkText);
			screenshotForAllSteps(element);
		}else {
			log("Link Text expected and actual text are not Same");
			log("Link Text - Actual : "+linkText);
			log("Link Text -Expected : "+Text);
			System.out.println("Link Text expected and actual text are not Same");
			System.out.println("Link Text - Actual : "+linkText);
			System.out.println("Link Text -Expected : "+Text);
			screenshotForAllSteps(element);
			throw new Exception("Text not Match");

		}

	}




	/**
	 * Common Method to Identify a given Element is Present or NOT
	 * @throws Exception
	 * 
	 */

	public boolean isElementPresentVerification(WebElement element) throws Exception {
		boolean isElementPresent = false;
		browserWithElementWait = new WebDriverWait(webDriver,config.getInt("elementWaitInSeconds"));
		highlightElement(element, webDriver);
		try {

			if (element!=null) {
				isElementPresent = true;

				log("Given Element is present: "+element.getText());
				screenshotForAllSteps(element);
			} 
		} catch (Exception e) {
			e.printStackTrace();
			log("Object Couldn't be retrieved and verified"+element);
			
			throw new Exception("Object Couldn't be retrieved and verified");
		}
		return isElementPresent;
	}




	/**
	 *  Methods for Clear and Enter Text
	 *
	 *
	 */

	
	public boolean clearAndEnterText(WebElement element, String Text) {
		boolean isTextEnteredResult = false;
		browserWithElementWait = new WebDriverWait(webDriver,config.getInt("elementWaitInSeconds"));
		highlightElement(element, webDriver);
		try {
			browserWithElementWait.until(ExpectedConditions.elementToBeClickable(element));
			if(element!=null){
				element.clear();
				Thread.sleep(2000);
				element.sendKeys(Text);
				isTextEnteredResult = true;
				screenshotForAllSteps(element);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return isTextEnteredResult;
	}




	/**
	 *  Methods for Highlighting the Selected Element
	 *  @param - WebElement , WebDriver  
	 *  
	 */

	public static void highlightElement(WebElement element,WebDriver webDriver) {
		if (config.getString("operatingSystem").equalsIgnoreCase("Windows")){
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "border: 3px solid Red;");
		
		}
	}
	
	
	
	
	/**
	 *  Methods for Removing the Highlighted Element
	 *  @param - WebElement , WebDriver  
	 *  
	 */
	
	public static void RemovehighlightElement(WebElement element,WebDriver webDriver) {
		
		if (config.getString("operatingSystem").equalsIgnoreCase("Windows")){
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "border: ;");
		
		}
	}




	/**
	 *  Methods for Page refresh
	 *  
	 */

	public void refresh_Page() {
		webDriver.navigate().refresh();
	}




	/**
	 *  Muralikrishnan G
	 *  Method for Taking Screenshot and Store it in a FailureScreenshot Folder
	 *  @param - Screenshot Name 
	 *  @throws Exception
	 *  
	 */

	public void getscreenshot(String screenShotName) throws Exception 
	{
		File scrFile = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
		//The below method will save the screen shot in d drive with name "screenshot.png"
		FileUtils.copyFile(scrFile, new File(UserConfig.projectLocation+"/FailureScreenShot/"+screenShotName+".png"));
	}




	/**
	 * Muralikrishnan G
	 * Method for Taking Screenshot and Embedding it in Cucumber Extend Report
	 * @param - Screenshot Name , Scenario from Hooks
	 * @throws Exception
	 * 
	 */

	public void screenshotForAllSteps(WebElement element) throws Exception 
	{
		if (config.getString("screenshotForAllSteps").equalsIgnoreCase("yes")){
		
			long ms = System.currentTimeMillis(); 
		
		File scrFile = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);		
		FileUtils.copyFile(scrFile, new File (System.getProperty("user.dir")+"/Screenshots"+"/PassScreenshots"+"/"+ms+"screenshot.png"));
		Reporter.addScreenCaptureFromPath(System.getProperty("user.dir")+"/Screenshots"+"/PassScreenshots"+"/"+ms+"screenshot.png");
		
		}
		
		RemovehighlightElement(element, webDriver);
	
	}




	/**
	 *  Methods for Scrolling to a WebElement
	 *  @param - WebElement
	 *  
	 */

	public static void scrollTo(WebDriver driver, WebElement element) {
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView();", element);
	}




	/**
	 *  Methods for Getting  Horizontal data from an excel sheet
	 *  @param - Datatable
	 *  
	 */

	public static Map<String, List<String>> getHorizontalData(DataTable dataTable) {
		Map<String, List<String>> dataMap = null;
		try {
			dataMap = new HashMap<String, List<String>>();
			List<String> headingRow = dataTable.raw().get(0);
			int dataTableRowsCount = dataTable.getGherkinRows().size() - 1;
			ArrayList<String> totalRowCount = new ArrayList<String>();
			totalRowCount.add(Integer.toString(dataTableRowsCount));
			dataMap.put("totalRowCount", totalRowCount);
			for (int i = 0; i < headingRow.size(); i++) {
				List<String> dataList = new ArrayList<String>();
				dataMap.put(headingRow.get(i), dataList);
				for (int j = 1; j <= dataTableRowsCount; j++) {
					List<String> dataRow = dataTable.raw().get(j);
					dataList.add(dataRow.get(i));
				}
			}
		} catch (Exception e) {

		}
		return dataMap;
	}




	/**
	 *  Methods for Fetching Vertical Excel Data 
	 *  @param - Datatable
	 *  
	 */

	public static Map<String, List<String>> getVerticalData(DataTable dataTable) {
		Map<String, List<String>> dataMap = null;
		try {
			int dataTableRowsCount = dataTable.getGherkinRows().size();
			dataMap = new HashMap<String, List<String>>();
			for (int k = 0; k < dataTableRowsCount; k++) {
				List<String> dataRow = dataTable.raw().get(k);
				String key = dataRow.get(0);
				dataRow.remove(0);
				dataMap.put(key, dataRow);
			}
		} catch (Exception e) {

		}
		return dataMap;
	}




	/**
	 *  Methods for Reading the Data from Excel sheet 
	 *  @Param - FileName , SheetName , Row Value , Column Value
	 *  @throws IOException 
	 */

	public static String getXLSTestData (String FileName,String SheetName, String RowId,String column) throws IOException {

		String col1 = null;
		DataFormatter df = new DataFormatter();
		FileInputStream file = new FileInputStream(new File(System.getProperty("user.dir") +"/InputData"+ File.separator +FileName+".xls"));
		HSSFWorkbook book = new HSSFWorkbook(file);
		HSSFSheet sheet = book.getSheet(SheetName);

		int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
		for (int rowIterator = 1; rowIterator<=rowCount;rowIterator++) {
			String row = sheet.getRow(rowIterator).getCell(0).getStringCellValue();
			if (row.equalsIgnoreCase(RowId)) {
				for (int colIterator = 1;colIterator<sheet.getRow(rowIterator).getLastCellNum();colIterator++) {
					String col = sheet.getRow(0).getCell(colIterator).getStringCellValue();
					if (col.equalsIgnoreCase(column)) {
						Cell cellvalue = sheet.getRow(rowIterator).getCell(colIterator);
						col1 = df.formatCellValue(cellvalue);
						break;
					}
				}
			}
		}
		book.close();
		return col1;
	}




	/**
	 * Muralikrishnan G
	 * Method to Log the text to Scenario steps in Extend Report 
	 * 
	 */

	public void log(String message)
	{
		
		Reporter.addStepLog(message);

	}




	/**
	 * Muralikrishnan G
	 * Method to Add Device Informations to Extent Report
	 * @throws InterruptedException
	 * @throws IOException
	 * 
	 */

	public void  AndroidDevice_information() throws InterruptedException, IOException{
		Reporter.addStepLog("************************************************");
		Reporter.addStepLog("Device UDID		: "+DeviceManager.getDeviceUDID());
		Reporter.addStepLog("Device Model		: "+AndroidDeviceConfiguration.getDeviceModel());
		Reporter.addStepLog("Device Brand		: "+AndroidDeviceConfiguration.getDeviceBrand());
		Reporter.addStepLog("Device OS Version	: "+AndroidDeviceConfiguration.getDeviceOS());
		Reporter.addStepLog("************************************************");

	}

	/**
	 * Muralikrishnan G
	 * Method to Return webDriver 
	 * 
	 */

	public WebDriver getdriver() {

		return webDriver;
	}	
	
	
	/**
	 * Muralikrishnan_G 
	 * Method for getting the Element Location Percentage
	 * @param WebElement , WebDriver
	 * 
	 */

	public static void getElementLocationPercentage(WebElement element, WebDriver webDriver) {

		// Getting Mobile Device Coordinates and Mobile element Coordinates
		Dimension size = webDriver.manage().window().getSize();
		
		Point point = element.getLocation();

		// Converting Mobile Position values to String
		JTextField MobileSize = new JTextField();
		MobileSize.setText(size.getWidth() + "X" + size.getHeight());
		String MobileCoordinates = MobileSize.getText();

		// Converting ElementPosition values to String
		JTextField ElementPosition = new JTextField();
		ElementPosition.setText(point.x + "X" + point.y);
		String ElementCoordinates = ElementPosition.getText();

		String MobX = StringUtils.substringBefore(MobileCoordinates, "X");
		String MobY = StringUtils.substringAfter(MobileCoordinates, "X");
		String EleX = StringUtils.substringBefore(ElementCoordinates, "X");
		String EleY = StringUtils.substringAfter(ElementCoordinates, "X");

		// Converting String values to Float
		float MobileX = Float.parseFloat(MobX);
		float MobileY = Float.parseFloat(MobY);
		float ElementX = Float.parseFloat(EleX);
		float ElementY = Float.parseFloat(EleY);

		// Calculating the Percentage of the image location
		float ObjectLocation = ((ElementX * ElementY) / (MobileX * MobileY)) * 100;
		BigDecimal bd = new BigDecimal(Float.toString(ObjectLocation));
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);

		System.out.println("Element Location Percentage" + bd + "%");

	}
	
	
	


}
