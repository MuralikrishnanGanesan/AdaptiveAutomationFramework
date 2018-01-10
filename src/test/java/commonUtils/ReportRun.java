package commonUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;


public class ReportRun {


	public void generateReport1 () throws Exception {
		String s= "target/cucumber-report.json";
		// to create the output directory
		File reportOutputDirectory = new File("target1");
		List<String> jsonFiles = new ArrayList<String>();
		jsonFiles.add(s);

		String jenkinsBasePath = "";
		String buildNumber = "1";
		String projectName = "RTH_POC";
		boolean skippedFails = true;
		boolean pendingFails = false;
		boolean undefinedFails = true;
		boolean missingFails = true;
		boolean runWithJenkins = false;
		boolean parallelTesting = false;

		Configuration configuration = new Configuration(reportOutputDirectory, projectName);
		/* optionally only if you need */
		//configuration.setStatusFlags(skippedFails, pendingFails, undefinedFails, missingFails);
		configuration.setParallelTesting(parallelTesting);
		//configuration.setJenkinsBasePath(jenkinsBasePath);
		configuration.setRunWithJenkins(runWithJenkins);
		configuration.setBuildNumber(buildNumber);
		/**/
		ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
		System.out.println("test");
		reportBuilder.generateReports();
		System.out.println("after test");

	}
	public static void main(String [] args) {

		try {
			ReportRun rr = new ReportRun();

			System.out.println("Report is getting generated "+ rr.toString());
			rr.generateReport1 ();
		}
		catch(Throwable t){

			t.getStackTrace();
		}
		finally {

			System.out.println(" finally");
		}
	}

}
