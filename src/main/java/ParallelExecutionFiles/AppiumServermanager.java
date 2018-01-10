package ParallelExecutionFiles;

import java.io.File;
import java.io.IOException;

import helpers.UserConfig;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AppiumServermanager {
	
	private static ThreadLocal<AppiumDriverLocalService> appiumDriverLocalService = new ThreadLocal<AppiumDriverLocalService>();
	
	
	private static AppiumDriverLocalService getServer() {
        return appiumDriverLocalService.get();
    }
	
	private static void setServer(AppiumDriverLocalService server) {
        appiumDriverLocalService.set(server);
    }
	
	public static void startAppiumServer() throws Exception {
		
		StartAppiumAndroidserver();
    
	}
	
	 private static void destroyAppiumNode() {
	        getServer().stop();
	        if (getServer().isRunning()) {
	            System.out.println("AppiumServer didn't shut... Trying to quit again....");
	            getServer().stop();
	        }
	    }
	 
	 public static void stopAppiumServer() throws IOException  {
	        
		 destroyAppiumNode();
	        
	    }
	
	
	public static void StartAppiumAndroidserver() throws Exception{
		
		System.out.println(
                "**************************************************************************\n");
        System.out.println("Starting Appium Server to handle Android Device: "
                + DeviceManager.getDeviceUDID() + "\n");
        System.out.println(
                "**************************************************************************\n");
		
		AppiumDriverLocalService appiumDriverLocalService;
        int chromePort = AvailablePorts.getPort();
        int bootstrapPort = AvailablePorts.getPort();
        int selendroidPort = AvailablePorts.getPort();
        AppiumServiceBuilder builder =
                new AppiumServiceBuilder().withAppiumJS(new File(UserConfig.Appium_Js_path))
                        .withArgument(GeneralServerFlag.LOG_LEVEL, "info")
                        .withArgument(AndroidServerFlag.CHROME_DRIVER_PORT,
                                Integer.toString(chromePort))
                        .withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER,
                                Integer.toString(bootstrapPort))
                        .withIPAddress("127.0.0.1")
                        .withArgument(AndroidServerFlag.SUPPRESS_ADB_KILL_SERVER)
                        .withArgument(AndroidServerFlag.SELENDROID_PORT,
                                Integer.toString(selendroidPort))
                        .usingPort(DeviceAllocationManager.getNextAvailablePort());
        /* and so on */
        ;
        appiumDriverLocalService = builder.build();
        appiumDriverLocalService.start();
        setServer(appiumDriverLocalService);
		
		
        Thread.sleep(7000);
					
}

	
	
	
	
	
}