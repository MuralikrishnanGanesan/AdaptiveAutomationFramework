package ParallelExecutionFiles;

/**
 * Command Prompt - this class contains method to run windows commands
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandPrompt {

    Process p;
    ProcessBuilder builder;

    public String runCommand(String command) throws InterruptedException, IOException {
        p = Runtime.getRuntime().exec(command);
        // get std output
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = "";
        String allLine = "";
        int i = 1;
        while ((line = r.readLine()) != null) {
            allLine = allLine + "" + line + "\n";
            if (line.contains("Console LogLevel: debug") && line.contains("Complete")) {
                break;
            }
            i++;
        }
        return allLine;

    }
    
    public static void main(String[] args) throws Exception {
		CommandPrompt cmd = new CommandPrompt();
		String appium="/usr/bin/local/appium";
		String output = cmd.runCommand(appium);
		System.out.println(output);
	}

}
