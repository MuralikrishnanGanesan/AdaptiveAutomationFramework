package ParallelExecutionFiles;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import commonUtils.CommonLibrary;

public class WebBrowserConfiguration {
	
	private static ConcurrentHashMap<String, Boolean> browserMapping =
            new ConcurrentHashMap<String, Boolean>();
	private static ArrayList<String> Browsers = new ArrayList<String>();
	private static WebBrowserConfiguration instance;
	static int Browser_size;
	private WebBrowserConfiguration() throws Exception {
		InitializeBrowsers();
        
    }
	
	public static WebBrowserConfiguration getInstance() throws Exception {
        if (instance == null) {
            instance = new WebBrowserConfiguration();
        }
        return instance;
    }
	
	public void InitializeBrowsers(){
		System.out.println("Initializing the Browser");
		getBrowsers();
		
		for(String Browser : Browsers){
			browserMapping.put(Browser, true);
			
		}
		System.out.println(browserMapping);
		
		Browser_size = Browsers.size();	
			
		}
		
	
	
	public void getBrowsers(){
		
		if (CommonLibrary.config.getString("chrome").equalsIgnoreCase("yes")){
			
			Browsers.add("chrome");
			System.out.println(Browsers);
		}
		if (CommonLibrary.config.getString("fireFox").equalsIgnoreCase("yes")){
			
			Browsers.add("firefox");
			System.out.println(Browsers);
		}
		
		if (CommonLibrary.config.getString("ie").equalsIgnoreCase("yes")){
			
			Browsers.add("ie");
			System.out.println(Browsers);
		}
		if (CommonLibrary.config.getString("safari").equalsIgnoreCase("yes")){
			
			Browsers.add("safari");
			System.out.println(Browsers);
		}
		
		
	}
	
	
	 public static synchronized String getNextAvailableBrowser() {
	        ConcurrentHashMap.KeySetView<String, Boolean> browsers = browserMapping.keySet();
	    
	        for (String browser : browsers) {
	                   
	            if (browserMapping.get(browser)) {
	            	browserMapping.put(browser, false);
	                System.out.println(browserMapping);
	                System.out.println(browser);
	                return browser;
	            }
	            
	            System.out.println(browserMapping);
	        }
	        return null;
	    }
	 
	 
	 public static void freeBrowser() {
	        browserMapping.put(BrowserManager.getBrowserID(), true);
	    }
	 
	 
	 public static void allocateBrowser(String browser) {
		 if (browser==null) {
	            BrowserManager.setDeviceUDID("chrome");
	        } else {
	        	BrowserManager.setDeviceUDID(browser);
	        }
	    }
	
	

}
