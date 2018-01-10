package ParallelExecutionFiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AndroidDeviceConfiguration {

	private static CommandPrompt cmd = new CommandPrompt();
	private Map<String, String> devices = new HashMap<String, String>();
	public static ArrayList<String> deviceSerial = new ArrayList<String>();
	ArrayList<String> deviceModel = new ArrayList<String>();
	public static List<String> validDeviceIds = new ArrayList<String>();

	/**
	 * This method start adb server
	 */
	public void startADB() throws Exception {
		String output = cmd.runCommand("adb start-server");
		String[] lines = output.split("\n");
		if (lines[0].contains("internal or external command")) {
			System.out.println("Please set ANDROID_HOME in your system variables");
		}
	}

	/**
	 * This method stop adb server
	 */
	public void stopADB() throws Exception {
		cmd.runCommand("adb kill-server");
	}


	/**
	 * Muralikrishnan G
	 * This method to get the connected device count
	 *
	 */
	public Map<String, String> getDevices() throws Exception {

		startADB(); // start adb service
		String output = cmd.runCommand("adb devices");
		String[] lines = output.split("\n");

		if (lines.length <= 1) {
			System.out.println("No Android Device Connected");
			stopADB();
			return null;
		} else {
			for (int i = 1; i < lines.length; i++) {
				lines[i] = lines[i].replaceAll("\\s+", "");

				if (lines[i].contains("device")) {
					lines[i] = lines[i].replaceAll("device", "");
					String deviceID = lines[i];

					devices.put("deviceID" + i, deviceID);
				}
			} 

		}
		return devices;
	}

	public ArrayList<String> getDeviceSerial() throws Exception {

		startADB(); // start adb service
		String output = cmd.runCommand("adb devices");
		String[] lines = output.split("\n");

		if (lines.length <= 1) {
			System.out.println("No Android Device Connected");
			return null;
		} else {
			for (int i = 1; i < lines.length; i++) {
				lines[i] = lines[i].replaceAll("\\s+", "");

				if (lines[i].contains("device")) {
					lines[i] = lines[i].replaceAll("device", "");
					String deviceID = lines[i];
					if (validDeviceIds.size() > 0) {
						if (validDeviceIds.contains(deviceID)) {
							deviceSerial.add(deviceID);
							System.out.println("Adding device with user specified: " + deviceID);
						}

					} else {
						System.out.println("Adding all android devices: " + deviceID);
						deviceSerial.add(deviceID);
					}
				}
			}
			return deviceSerial;
		}
	}

	/**
	 * 
	 * This method gets the device model name
	 *
	 */
	public static String getDeviceModel() throws InterruptedException {
		String deviceModelName = null;
	
		try {
			deviceModelName =
					cmd.runCommand("adb -s " + DeviceManager.getDeviceUDID()
					+ " shell getprop ro.product.model");

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//deviceModel = deviceModelName.concat("_" + brand).replace("\n", "");

		return deviceModelName;

	}
	
	
	/**
	 * 
	 * This method gets the device Brand name
	 *@throws InterruptedException
	 *
	 */
	public static String getDeviceBrand() throws InterruptedException {
		String brand = null;
		
		try {
			
			brand = cmd.runCommand("adb -s " + DeviceManager.getDeviceUDID()
			+ " shell getprop ro.product.brand");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return brand;

	}
	
	

	/**
	 * 
	 * This method gets the device OS API Level
	 *@throws InterruptedException
	 *
	 */
	public static String getDeviceOS()throws InterruptedException {
		String deviceOSLevel = null;
		try {
			deviceOSLevel =
					cmd.runCommand("adb -s " + DeviceManager.getDeviceUDID()
							+ " shell getprop ro.build.version.release")
					.replace("\n", "");
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deviceOSLevel;

	}
	


	/**
	 * This method will close the running app
	 *
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void closeRunningApp(String deviceID, String app_package)
			throws InterruptedException, IOException {
		
		cmd.runCommand("adb -s " + deviceID + " shell am force-stop "
				+ app_package);
	}

	/**
	 * This method clears the app data only for android
	 *
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void clearAppData(String deviceID, String app_package)
			throws InterruptedException, IOException {
		// adb -s 192.168.56.101:5555 com.android2.calculator3
		cmd.runCommand("adb -s " + deviceID + " shell pm clear "
				+ app_package);
	}

	/**
	 * This method removes apk from the devices attached
	 *
	 * @param app_package
	 * @throws Exception
	 */

	public void removeApkFromDevices(String app_package)
			throws Exception {
		cmd.runCommand("adb -s " + DeviceManager.getDeviceUDID() + " uninstall " + app_package);
	}

	
}
