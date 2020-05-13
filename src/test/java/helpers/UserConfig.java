package helpers;

import java.io.File;

import ParallelExecutionFiles.DeviceManager;

public class UserConfig  {


	public static final String projectLocation = "";

	/*
	 * iOS 
	 */
	public static final String deviceUDID =  "";
	public static final String platform_Version =  "";
	public static final String deviceName =  "";
	public static final String safarilauncherLocation =  "/Applications/Appium/Contents/Resources/node_modules/appium/build/SafariLauncher/SafariLauncher.app";
	public static final String appiumServerURL_iOS =  "http://127.0.0.1:4723/wd/hub";

	/*
	 * Android
	 */
	public static final String sdk_location =  "C:/Software/sdk";
	public static final String deviceId =DeviceManager.getDeviceUDID();
	public static final String chromeDriver_location ="http://localhost:9515";
	public static final String Appium_Js_path="/usr/local/lib/node_modules/appium/build/lib/main.js";
	public static String app = System.getProperty("user.dir")+"/ApkFile"+ File.separator+"WordPress.apk";
	public static String Node = "/usr/local/bin/node";
	
	
	public static String AppPackage ="org.wordpress.android";
	public static String AppActivity = "org.wordpress.android.ui.accounts.SignInActivity";
	public static String Video_Location = System.getProperty("user.dir")+"/FailureVideos";
	
	

	/*
	 * Drivers
	 * 
	 */

	public static final String chromeDriver_Desktop_Location =  System.getProperty("user.dir")+"/drivers"+"/chromedriver.exe";
	public static final String geckodriver_desktop_location = System.getProperty("user.dir")+"/drivers"+"/geckodriver.exe";
	public static final String IEDriver_Desktop_Location = System.getProperty("user.dir")+"/drivers"+"/IEDriverServer.exe";
	
	
	public static String reportDir=System.getProperty("user.dir")+File.separator+"CSVFile"+File.separator;
	public static String reportFile = "Parallel_Pageload_Report";
	
}
