package commonUtils;

import com.github.mkolisnyk.cucumber.reporting.CucumberResultsOverview;
import com.github.mkolisnyk.cucumber.reporting.CucumberUsageReporting;

public class CucumberReport {

	public void generateCucumberReport () throws Exception {
	CucumberResultsOverview results= new CucumberResultsOverview();
	
	results.setOutputDirectory("automationReport");
	//results.setOutputName("cucumber-results");
	results.setSourceFile("./automationReport/cucumber-report.json");
	results.setOutputDirectory("automationReport");
	results.setOutputName("cucumber-results");
	
	CucumberUsageReporting report = new CucumberUsageReporting();
	report.setOutputDirectory("automationReport");
	report.setJsonUsageFile("./automationReport/cucumber-usage.json");
	report.executeReport();
	
	try {
		
	
	//results.executeFeaturesOverviewReport();
	
	}catch (Exception e){
	e.printStackTrace();
	}
	}
	public static void main(String[] args) {
		try {
			CucumberReport CR = new CucumberReport();

			System.out.println("Report is getting generated "+ CR.toString());
			CR.generateCucumberReport();
			}
		catch(Throwable t){

			t.getStackTrace();
		}
		finally {

			System.out.println("check report in target finally");
		}
	}
	}

