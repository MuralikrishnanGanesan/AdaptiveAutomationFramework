package ParallelExecutionFiles;

import java.io.IOException;

/**
 * Device Manager - Handles all device related information's e.g UDID
 */
public class DeviceManager {

    private static ThreadLocal<String> deviceUDID = new ThreadLocal<>();
    
    private AndroidDeviceConfiguration androidDeviceConfiguration;

    public DeviceManager() throws IOException {
        
		androidDeviceConfiguration = new AndroidDeviceConfiguration();
    }

    public static String getDeviceUDID() {
        return deviceUDID.get();
    }

    protected static void setDeviceUDID(String UDID) {
        deviceUDID.set(UDID);
        
    }

   
    }

 
