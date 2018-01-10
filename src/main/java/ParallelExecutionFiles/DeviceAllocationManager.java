package ParallelExecutionFiles;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  DeviceAllocationManager - Handles device initialisation, allocation and de-allocattion
 */
public class DeviceAllocationManager {
    private ArrayList<String> devices = new ArrayList<String>();
    private static ConcurrentHashMap<String, Boolean> deviceMapping =
            new ConcurrentHashMap<String, Boolean>();  
    private static ConcurrentHashMap<Integer, String> Mapping =
            new ConcurrentHashMap<Integer, String>();
    private static DeviceAllocationManager instance;
    private static AndroidDeviceConfiguration androidDevice = new AndroidDeviceConfiguration();

    
    private DeviceAllocationManager() throws Exception {
        initializeDevices();
   
    }

    public static DeviceAllocationManager getInstance() throws Exception {
        if (instance == null) {
            instance = new DeviceAllocationManager();
        }
        return instance;
    }

    private void initializeDevices() throws Exception {
        
    
                getAndroidDeviceSerial();
            
            for (String device : devices) {
                deviceMapping.put(device, true);
            }
            System.out.println(deviceMapping);
         
    }
    
    
    
    public static void initializePort(int Threadcount) throws Exception {
        
                    
    for (int i=1 ;i<=Threadcount;i++) {
        Mapping.put(AvailablePorts.getPort(), "Initialize");
    }
    System.out.println(Mapping);
 
}


    private void getAndroidDeviceSerial() throws Exception {
        if (androidDevice.getDeviceSerial() != null) {
            System.out.println("Adding Android devices");
            if (AndroidDeviceConfiguration.validDeviceIds.size() > 0) {
                System.out.println("Adding Devices from DeviceList Provided");
                devices.addAll(AndroidDeviceConfiguration.validDeviceIds);
            } else {
                devices.addAll(AndroidDeviceConfiguration.deviceSerial);
            }
        }
    }

    public ArrayList<String> getDevices() {
        return devices;
    }

    public static synchronized String getNextAvailableDeviceId() {
        ConcurrentHashMap.KeySetView<String, Boolean> devices = deviceMapping.keySet();
    
        for (String device : devices) {
                   
            if (deviceMapping.get(device)) {
                deviceMapping.put(device, false);
                System.out.println(deviceMapping);
                System.out.println(device);
                return device;
            }
            
            System.out.println(deviceMapping);
        }
        return null;
    }
       
    
    public static synchronized int getNextAvailablePort() {
        ConcurrentHashMap.KeySetView<Integer, String> Ports = Mapping.keySet();
    
        for (int Port : Ports) {
                   
            if (Mapping.get(Port).equals("Initialize")) {
                Mapping.put(Port, "Ready");
                System.out.println(Port);
                System.out.println(Mapping);
                return Port;
            }
            
            System.out.println(Mapping);
        }
        return 0;
    }
    
    
    public static synchronized int getNextAvailablePortBinding() {
        ConcurrentHashMap.KeySetView<Integer, String> Ports = Mapping.keySet();
    
        for (int Port : Ports) {
                   
            if (Mapping.get(Port).equals("Ready")) {
                Mapping.put(Port, "Completed");
                System.out.println(Port);
                System.out.println(Mapping);
                return Port;
            }
            
            System.out.println(Mapping);
        }
        return 0;
    }
    
    
    

    public static void freeDevice() {
        deviceMapping.put(DeviceManager.getDeviceUDID(), true);
    }

    public static void allocateDevice(String device, String deviceUDID) {
        if (device==null) {
            DeviceManager.setDeviceUDID(deviceUDID);
        } else {
            DeviceManager.setDeviceUDID(device);
        }
    }
}
