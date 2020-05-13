package ParallelExecutionFiles;

public class BrowserManager {


	    private static ThreadLocal<String> browserID = new ThreadLocal<>();
	    
	   	 

	    public static String getBrowserID() {
	        return browserID.get();
	    }

	    
	    
	    protected static void setDeviceUDID(String UDID) {
	        browserID.set(UDID);
	        
	    }


}
