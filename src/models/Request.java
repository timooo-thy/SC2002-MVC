package models;

import java.util.ArrayList;
import java.util.HashMap;

import utilities.Database;

public class Request {
	
    private String senderID;
    
    private String senderName;
    
    private String senderEmail;
    
    private String recipientID;
    
    private String recipientName;
    
    private String recipientEmail;
    
    private int projectID;
    
    private int newProjectID;
    
    private String newProjectTitle;
    
    private String newSupervisorID;
    
    private String newSupervisorName;
    
    private String newSupervisorEmail;
    
    private RequestType_Enum requestType;
    
    private RequestStatus_Enum requestStatus;
    
    private int requestID;
    
    private static ArrayList<Request> requests = new ArrayList<Request>();
    
    private static final String FILEPATH = "src/data/";	
    
	private static final String FILENAME = "requests.txt";
	
	private static Database d = new Database();
    
    // CHANGETITLE
    public static void RequestChange(String senderID, String senderName, String senderEmail, String recipientID,String recipientName, String recipientEmail, int projectID, String newProjectTitle, RequestType_Enum requestType, RequestStatus_Enum requestStatus, int requestID) {
        Request.senderID = senderID; //check here
        this.senderName = senderName;
        this.senderEmail = senderEmail;
        this.recipientID = recipientID;
        this.recipientName = recipientName;
        this.recipientEmail = recipientEmail;
        this.projectID = projectID;
        this.newProjectTitle = newProjectTitle;
        this.requestType = requestType;
        this.requestStatus = requestStatus;
        this.requestID = requestID;
        this.addRequest(this);
    }

	// REGISTERPROJECT and DEREGISTERPROJECT
    public static void RequestSend(String senderID,String senderName, String senderEmail, String recipientID,String recipientName, String recipientEmail, int projectID,RequestType_Enum requestType, RequestStatus_Enum requestStatus,int requestID) {
        this.senderID = senderID;
        this.senderName = senderName;
        this.senderEmail = senderEmail;
        this.recipientID = recipientID;
        this.recipientName = recipientName;
        this.recipientEmail = recipientEmail;
        this.requestType = requestType;
        this.requestStatus = requestStatus;
        this.requestID = requestID; 
        this.projectID = projectID;
        this.senderName = senderName;
        this.addRequest(this);
    }

    // CHANGESUPERVISOR
    public Request(String senderID, String senderName, String senderEmail, String recipientID,String recipientName, String recipientEmail, int projectID, String newSupervisorID, String newSupervisorName, String newSupervisorEmail, RequestType_Enum requestType, RequestStatus_Enum requestStatus, int requestID) {
        this.senderID = senderID;
        this.senderName = senderName;
        this.senderEmail = senderEmail;
        this.recipientID = recipientID;
        this.recipientName = recipientName;
        this.recipientEmail = recipientEmail;
        this.newSupervisorID = newSupervisorID;
        this.newSupervisorName = newSupervisorName;
        this.newSupervisorEmail = newSupervisorEmail;
        this.requestType = requestType;
        this.requestStatus = requestStatus;
        this.requestID = requestID; 
        this.projectID = projectID;
        this.senderName = senderName;
        this.addRequest(this);
    }
    
    // TRANSFERSTUDENT
    public Request(String senderID, String senderName, String senderEmail, String recipientID,String recipientName, String recipientEmail, int projectID,int newProjectID,RequestType_Enum requestType, RequestStatus_Enum requestStatus, int requestID) {
        this.senderID = senderID;
        this.senderName = senderName;
        this.senderEmail = senderEmail;
        this.recipientID = recipientID;
        this.recipientName = recipientName;
        this.recipientEmail = recipientEmail;
        this.requestType = requestType;
        this.requestStatus = requestStatus;
        this.requestID = requestID; 
        this.projectID = projectID;
        this.newProjectID = newProjectID;
        this.senderName = senderName;
        this.addRequest(this);;
    }
    
    //////////// Class methods //////////////
 
    public void approve() {
    	requestStatus = RequestStatus_Enum.APPROVED;
        return;
    } // Approve request 


    public void reject() {
        requestStatus = RequestStatus_Enum.REJECTED;
        return;
    } // Reject request
    
	public void addRequest(Request r) {
		requests.add(r);
		updateFile();
	}
  

    /////////////Accessors and mutators///////////////
    public String getSenderID() {
		return senderID;
	}

	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getRecipientID() {
		return recipientID;
	}

	public void setRecipientID(String recipientID) {
		this.recipientID = recipientID;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public String getRecipientEmail() {
		return recipientEmail;
	}

	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	public int getNewProjectID() {
		return newProjectID;
	}

	public void setNewProjectID(int newProjectID) {
		this.newProjectID = newProjectID;
	}

	public String getNewProjectTitle() {
		return newProjectTitle;
	}

	public void setNewProjectTitle(String newProjectTitle) {
		this.newProjectTitle = newProjectTitle;
	}

	public String getNewSupervisorID() {
		return newSupervisorID;
	}

	public void setNewSupervisorID(String newSupervisorID) {
		this.newSupervisorID = newSupervisorID;
	}

	public String getNewSupervisorName() {
		return newSupervisorName;
	}

	public void setNewSupervisorName(String newSupervisorName) {
		this.newSupervisorName = newSupervisorName;
	}

	public String getNewSupervisorEmail() {
		return newSupervisorEmail;
	}

	public void setNewSupervisorEmail(String newSupervisorEmail) {
		this.newSupervisorEmail = newSupervisorEmail;
	}

	public RequestType_Enum getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType_Enum requestType) {
		this.requestType = requestType;
	}

	public RequestStatus_Enum getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(RequestStatus_Enum requestStatus) {
		this.requestStatus = requestStatus;
	}

	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}
    
	public static ArrayList<Request> getRequests(){
		return requests;
	}
	
	public static Request getRequest(int i){
		return requests.get(i);
	}

	public static void updateRequests(ArrayList<Request> r){
		requests = r;
	}

	public static void initializeFile() throws Throwable {
		HashMap<String, String[]> map  = d.initializeFile(FILENAME, FILEPATH);
		for (String name : map.keySet()) {
        	String[] values = map.get(name);
        	new Request(); //change 
        }
	}

	public static void updateFile() {	
		d.updateRequestFile(FILENAME,FILEPATH,requests);
	}
}


