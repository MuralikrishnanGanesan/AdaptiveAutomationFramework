package commonUtils;

public class ReportObject {
	
	private String DomLoadTime; 	//test start time
	private String PageLoadTime; 	//page load time 
	private String label; 			//From test page name
	private String label2;			//To test Page name
	private int responseCode;		//200
	private String responseMessage;	//OK
	private String threadName;		//TestSuite name
	private String dataType;		//text
	private String success;			//true
	private String failureMessage;	//empty string
	private int bytes;				//page requent size
	private int grpThreads;			//1
	private int allThreads;			//1
	private int latency;			//page latency
	private int IdleTime;			//0
	
	public ReportObject() {
		initDefaultValues();
	}
	
	public ReportObject(String DomLoadTime, String PageLoadTime, String label,String label2,
			String threadName, int bytes, int latency) {
		super();
		this.DomLoadTime = DomLoadTime;
		this.PageLoadTime = PageLoadTime;
		this.label = label;
		this.label2 = label2;
		this.threadName = threadName;
		this.bytes = bytes;
		this.latency = latency;
		initDefaultValues();
	}
	
	private void initDefaultValues() {
		setResponseCode(200);
		setResponseMessage("OK");
		setDataType("text");
		setSuccess("true");
		setFailureMessage("");
		setGrpThreads(1);
		setAllThreads(1);
		setIdleTime(0);
	}
	
	public ReportObject(String DomLoadTime, String PageLoadTime, String label,String label2,
			int responseCode, String responseMessage, String threadName,
			String dataType, String success, String failureMessage, int bytes,
			int grpThreads, int allThreads, int latency, int idleTime) {
		super();
		this.DomLoadTime = DomLoadTime;
		this.PageLoadTime = PageLoadTime;
		this.label = label;
		this.label2 = label2;
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
		this.threadName = threadName;
		this.dataType = dataType;
		this.success = success;
		this.failureMessage = failureMessage;
		this.bytes = bytes;
		this.grpThreads = grpThreads;
		this.allThreads = allThreads;
		this.latency = latency;
		IdleTime = idleTime;
	}
	
	public String getDomLoadTime() {
		return DomLoadTime;
	}
	
	public void setDomLoadTime(String DomLoadTime) {
		this.DomLoadTime = DomLoadTime;
	}
	
	public String getPageLoadTime() {
		return PageLoadTime;
	}
	
	public void setPageLoadTime(String PageLoadTime) {
		this.PageLoadTime = PageLoadTime;
	}
	
	public String getLabel() {
		return label;
	}
	public String getLabel2() {
		return label2;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public int getResponseCode() {
		return responseCode;
	}
	
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	
	public String getResponseMessage() {
		return responseMessage;
	}
	
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	public String getThreadName() {
		return threadName;
	}
	
	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
	
	public String getDataType() {
		return dataType;
	}
	
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public String getSuccess() {
		return success;
	}
	
	public void setSuccess(String success) {
		this.success = success;
	}
	
	public String getFailureMessage() {
		return failureMessage;
	}
	
	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}
	
	public int getBytes() {
		return bytes;
	}
	
	public void setBytes(int bytes) {
		this.bytes = bytes;
	}
	
	public int getGrpThreads() {
		return grpThreads;
	}
	
	public void setGrpThreads(int grpThreads) {
		this.grpThreads = grpThreads;
	}
	
	public int getAllThreads() {
		return allThreads;
	}
	
	public void setAllThreads(int allThreads) {
		this.allThreads = allThreads;
	}
	
	public int getLatency() {
		return latency;
	}
	
	public void setLatency(int latency) {
		this.latency = latency;
	}
	
	public int getIdleTime() {
		return IdleTime;
	}
	
	public void setIdleTime(int idleTime) {
		IdleTime = idleTime;
	}
	
	@Override
	public String toString() {
		return "" + DomLoadTime + "," + PageLoadTime + "," + label + ","+ label2 + ","
				+ responseCode + "," + responseMessage + "," + threadName + ","
				+ dataType + "," + success + "," + failureMessage + "," + bytes
				+ "," + grpThreads + "," + allThreads + "," + latency + ","
				+ IdleTime + "";
	}
	
}
