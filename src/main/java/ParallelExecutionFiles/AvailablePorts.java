package ParallelExecutionFiles;

import java.net.ServerSocket;

public class AvailablePorts {

    /* Muralikrishnan G
     * Generates Random ports
     * Used during starting appium server
     */
	public static synchronized int getPort() throws Exception {
        ServerSocket socket = new ServerSocket(0);
        socket.setReuseAddress(true);
        int port = socket.getLocalPort();
        socket.close();
//		Random rand = new Random();
//		int port = rand.nextInt(66666 - 4700 +  1) + 4700;
        return port;
    }
	

}
