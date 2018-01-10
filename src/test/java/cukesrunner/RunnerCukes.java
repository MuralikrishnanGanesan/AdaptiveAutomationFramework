package cukesrunner;

import org.junit.Test;
import org.testng.Assert;

import ParallelExecutionFiles.ParallelThread;

public class RunnerCukes {

    @Test 
    public void testCukesRunner() throws Exception {
        ParallelThread cucumberParallelThread = new ParallelThread();
        cucumberParallelThread.runner("com.example");
        
    }
}
