package ParallelExecutionFiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.github.lalyos.jfiglet.FigletFont;

import commonUtils.CommonLibrary;


public class ParallelThread extends CommonLibrary{

	public ParallelThread() throws Exception {
		super();
		androidDevice = new AndroidDeviceConfiguration();

	}
	static int threadCount=0;
	protected int deviceCount = 0;
	protected int browserCount = 0;
	Map<String, String> devices = new HashMap<String, String>();
	Map<String, String> browsers = new HashMap<String, String>();
	private AndroidDeviceConfiguration androidDevice;
	ArrayList<Integer> Ports = new ArrayList<Integer>();
	TestNgXml testngXml = new TestNgXml();



	public void runner(String pack) throws Exception {
		String s = CommonLibrary.config.getString("Mode");
		figlet(s);
		parallelExecution(pack);

	}


	public void parallelExecution(String pack) throws Exception {

		if (config.getString("breakPoint").equalsIgnoreCase("Mobile")){
			if (androidDevice.getDevices() != null ) {
				devices = androidDevice.getDevices();
				deviceCount = devices.size();

				DeviceAllocationManager.getInstance();

			}

			if (deviceCount == 0) {
				figlet("No Devices Connected");
				System.exit(0);
			}
			System.out.println("***************************************************\n");
			System.out.println("Total Number of Devices Detected::" + deviceCount + "\n");
			System.out.println("***************************************************\n");
			System.out.println("starting running tests in threads");
			
			threadCount=deviceCount;

		}

		if (config.getString("breakPoint").equalsIgnoreCase("Desktop")){

			WebBrowserConfiguration.getInstance();
			browserCount= WebBrowserConfiguration.Browser_size;
			System.out.println("***************************************************\n");
			System.out.println("Total Number of Browsers Detected::" + browserCount + "\n");
			System.out.println("***************************************************\n");
			System.out.println("starting running tests in threads");

		}

		if (config.getString("Pageload").equalsIgnoreCase("yes")){

			CommonLibrary.initReportFile();

		}


		if(config.getString("breakPoint").equalsIgnoreCase("Mobile")){        

			if(config.getString("Mode").equalsIgnoreCase("Distributed")){


				testngXml.RuntimeTestngXmlfileDistubuted(deviceCount,pack);

			}else{
				if (deviceCount>=2){
					testngXml.RuntimeTestngXmlfileParallel(deviceCount,pack);
				}else {

					figlet("Device Count < 2");
					System.out.println("***************************************************\n");
					System.out.println("Minimum Number of Devices For Parallel Execution should be  ** 2 ** \n");
					System.out.println("***************************************************\n");


					System.exit(0);
				}
			}

		}

		if(config.getString("breakPoint").equalsIgnoreCase("Desktop")&& config.getString("browser").equalsIgnoreCase("SingleBrowser")){

			if(config.getString("Mode").equalsIgnoreCase("Distributed")){

				testngXml.RuntimeTestngXmlfileDistubuted(config.getInt("parallelCount"),pack);

			}else{

				if (config.getInt("parallelCount")>=2){
					testngXml.RuntimeTestngXmlfileParallel(config.getInt("parallelCount"),pack);
				}else {

					figlet("Parallel Count < 2");
					System.out.println("***************************************************\n");
					System.out.println("Minimum Number of threads For Parallel Execution should be  ** 2 ** \n");
					System.out.println("***************************************************\n");


					System.exit(0);
				}



			}
		}


		if(config.getString("breakPoint").equalsIgnoreCase("Desktop")&& config.getString("browser").equalsIgnoreCase("CrossBrowser")){

			if(config.getString("Mode").equalsIgnoreCase("Distributed")){

				testngXml.RuntimeTestngXmlfileDistubuted(browserCount,pack);

			}else{
				if (browserCount>=2){
					testngXml.RuntimeTestngXmlfileParallel(browserCount,pack);
				}else {

					figlet("Browser Count < 2");
					System.out.println("***************************************************\n");
					System.out.println("Minimum Number of Browser For Parallel Execution should be  ** 2 ** \n");
					System.out.println("***************************************************\n");


					System.exit(0);
				}
			}
		}
	}



	public static void figlet(String text) {
		String asciiArt1 = null;
		try {
			asciiArt1 = FigletFont.convertOneLine(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(asciiArt1);
	}
}
