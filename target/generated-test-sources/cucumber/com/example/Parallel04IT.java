package com.example;

import java.io.File;
import java.lang.management.ManagementFactory;
import org.testng.annotations.Test;
import org.junit.runner.RunWith;
import org.testng.annotations.AfterClass;
import com.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;
@RunWith(Cucumber.class)
@CucumberOptions(
        strict = true,
        features = {"C:/Users/muralikrishnan_g/Parallel testing/AdaptiveAutomationFramework/src/test/resources/Features/ParallelDemoFeature/WordpressDemo.feature:41"},
        plugin = {"com.cucumber.listener.ExtentCucumberFormatter:C:/Users/muralikrishnan_g/Parallel testing/AdaptiveAutomationFramework/Report/4.html"},
        monochrome = true,
        glue = {"Stepdefinition", "commonUtils"})
@Test        
public class Parallel04IT extends AbstractTestNGCucumberTests{

@org.testng.annotations.BeforeClass
public static void BeforeClass(){

long threadId = Thread.currentThread().getId();
String processName = ManagementFactory.getRuntimeMXBean().getName();
System.out.println("Started in thread: " + threadId + ", in JVM: " + processName);

		

}
   
    @AfterClass
    public static void afterClass(){
      Reporter.loadXMLConfig(new File("src/test/resources/extent-config.xml"));
      Reporter.setSystemInfo("user", System.getProperty("user.name"));
      Reporter.setSystemInfo("os", System.getProperty("os.name"));
      Reporter.setSystemInfo("Android Version", "6.1");
	  Reporter.setSystemInfo("Device UDID ", "123456789");
	  Reporter.setSystemInfo("Device Model", "Samsung");
      Reporter.setTestRunnerOutput("Appium Parallel Test Report");
      
      
     
    }
}
