package ParallelExecutionFiles;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class TestNgXml {
	
	public void RuntimeTestngXmlfileDistubuted(int Devicecount,String pack){
	XmlSuite suite = new XmlSuite();
    suite.setName("Cucumber Automation");
    suite.setParallel("classes");
    suite.setThreadCount(Devicecount);
    
    XmlTest test = new XmlTest(suite);
    test.setName("Automation");
    List<XmlPackage> packages = new ArrayList<XmlPackage>();
    packages.add(new XmlPackage(pack));
    test.setXmlPackages(packages) ;


    
    List<XmlSuite> suites = new ArrayList<XmlSuite>();
    suites.add(suite);
    TestNG tng = new TestNG();
    tng.setXmlSuites(suites);
    tng.run();
    
	}
	
	public void RuntimeTestngXmlfileParallel(int Devicecount, String pack){
		XmlSuite suite = new XmlSuite();
	    suite.setName("Cucumber Automation");
	    suite.setParallel("tests");
	    suite.setThreadCount(Devicecount);
	  	   
	    List<XmlPackage> allPackages = new ArrayList<>();
        XmlPackage eachPackage = new XmlPackage();
        eachPackage.setName(pack);
        allPackages.add(eachPackage);
	    
        for(int i=1;i<=Devicecount;i++){
	    XmlTest test = new XmlTest(suite);
	    test.setName("Automation"+i);
	    test.setXmlPackages(allPackages) ;
        }
 
	    List<XmlSuite> suites = new ArrayList<XmlSuite>();
	    suites.add(suite);
	    TestNG tng = new TestNG();
	    tng.setXmlSuites(suites);
	    tng.run();
	    
		}

}
