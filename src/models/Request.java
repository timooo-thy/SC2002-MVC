package models;

import java.util.ArrayList;
import java.util.HashMap;

import utilities.Database;

/**
 * This class represents a Request in the system.
 * It contains a list of requests and methods to approve or reject request.
 * It also has methods to retrieve and update information about the requests from the database.
 * @author Timothy Lee
 * @author Justin Wong
 * @author Jun Hao
 * @author Lee Cheng Yao
 * @author Abhishekh
 */
public class Request {
	
	/**
	 * Sender's ID.
	 */
    private String senderID = "-1";
    
	/**
	 * Sender's Name.
	 */
    private String senderName = "-1";
    
	/**
	 * Sender's Email.
	 */
    private String senderEmail = "-1";
    
	/**
	 * Recipent's ID.
	 */
    private String recipientID = "-1";
    
	/**
	 * Recipent's Name.
	 */
    private String recipientName = "-1";
    
	/**
	 * Recipent's Email.
	 */
    private String recipientEmail = "-1";
    
	/**
	 * Project's ID.
	 */
    private int projectID = -1;
    
	/**
	 * New Project's ID.
	 */
    private int newProjectID = -1;
    
	/**
	 * New Project's Title.
	 */
    private String newProjectTitle = "-1";
    
	/**
	 * New Supervisor's ID.
	 */
    private String newSupervisorID = "-1";
    
	/**
	 * New Supervisor's Name.
	 */
    private String newSupervisorName = "-1";
    
	/**
	 * New Supervisor's Email.
	 */
    private String newSupervisorEmail = "-1";
    
	/**
	 * Request's Type.
	 */
    private RequestType_Enum requestType;
    
	/**
	 * Request's Status.
	 */
    private RequestStatus_Enum requestStatus;
    
	/**
	 * Request's ID.
	 */
    private int requestID = -1;
    
	/**
	 * List of all the requests in the system.
	 */
    private static ArrayList<Request> requests = new ArrayList<Request>();
    
	/**
	 * File path of the file to store the list of requests.
	 */
    private static final String FILEPATH = "src/data/";	
    
	/**
	 * File name of the file to store the list of requests.
	 */
	private static final String FILENAME = "requestList.txt";
	
	/**
	 * Database object to perform database operations.
	 */
	private static Database d = new Database();
    
	/**Creates a new Request object with the given attributes, and adds it to the list of projects.
	 * This constructor is used when user wants to change title
	 * 
	 * @param senderID The ID of the sender
	 * @param senderName The name of the sender
	 * @param senderEmail The email of the sender
	 * @param recipentID The ID of the recipent
	 * @param recipentName The name of the recipent
	 * @param recipentEmail The email of the recipent
	 * @param projectID  The ID of the project
	 * @param newProjectTitle  The new title of the project
	 * @param requestType  The type of the request
	 * @param requestStatus  The status of the request
	 * @param requestID The ID of the request
	 */
    public Request(String senderID, String senderName, String senderEmail, String recipientID,String recipientName, String recipientEmail, int projectID, String newProjectTitle, RequestType_Enum requestType, RequestStatus_Enum requestStatus, int requestID) {
        this.senderID = senderID; 
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

	/**Creates a new Request object with the given attributes, and adds it to the list of projects.
	 * This constructor is used when user wants to register or deregister project
	 * 
	 * @param senderID The ID of the sender
	 * @param senderName The name of the sender
	 * @param senderEmail The email of the sender
	 * @param recipentID The ID of the recipent
	 * @param recipentName The name of the recipent
	 * @param recipentEmail The email of the recipent
	 * @param projectID  The ID of the project
	 * @param requestType  The type of the request
	 * @param requestStatus  The status of the request
	 * @param requestID The ID of the request
	 */
    public Request(String senderID,String senderName, String senderEmail, String recipientID, String recipientName, String recipientEmail, int projectID, RequestType_Enum requestType, RequestStatus_Enum requestStatus, int requestID) {
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

	/**Creates a new Request object with the given attributes, and adds it to the list of projects.
	 * This constructor is used when user wants to change supervisor
	 * 
	 * @param senderID The ID of the sender
	 * @param senderName The name of the sender
	 * @param senderEmail The email of the sender
	 * @param recipentID The ID of the recipent
	 * @param recipentName The name of the recipent
	 * @param recipentEmail The email of the recipent
	 * @param projectID  The ID of the project
	 * @param newSupervisorID The ID of the new supervisor
	 * @param newSupervisorName The name of the new supervisor
	 * @param newSupervisorEmail The email of the new supervisor
	 * @param requestType  The type of the request
	 * @param requestStatus  The status of the request
	 * @param requestID The ID of the request
	 */
    public Request(String senderID, String senderName, String senderEmail, String recipientID, String recipientName, String recipientEmail, int projectID, String newSupervisorID, String newSupervisorName, String newSupervisorEmail, RequestType_Enum requestType, RequestStatus_Enum requestStatus, int requestID) {
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
	
    // public Request(String senderID, String senderName, String senderEmail, String recipientID,String recipientName, String recipientEmail, int projectID, int newProjectID, RequestType_Enum requestType, RequestStatus_Enum requestStatus, int requestID) {
    //     this.senderID = senderID;
    //     this.senderName = senderName;
    //     this.senderEmail = senderEmail;
    //     this.recipientID = recipientID;
    //     this.recipientName = recipientName;
    //     this.recipientEmail = recipientEmail;
    //     this.requestType = requestType;
    //     this.requestStatus = requestStatus;
    //     this.requestID = requestID; 
    //     this.projectID = projectID;
    //     this.newProjectID = newProjectID;
    //     this.senderName = senderName;
    //     this.addRequest(this);;
    // }
    
    //////////// Class methods //////////////
	
	/**
	 * Set the request status to APPROVED
	 */
    public void approve() {
    	requestStatus = RequestStatus_Enum.APPROVED;
        return;
    }

	/**
	 * Set the request status to REJECTED
	 */
    public void reject() {
        requestStatus = RequestStatus_Enum.REJECTED;
        return;
    } // Reject request
    
	/**
	 * Adds a new request to the list of requests.
	 * 
	 * @param r The request object to add.
	 */
	public void addRequest(Request r) {
		requests.add(r);
		updateRequestFile();
	}
  

    /////////////Accessors and mutators///////////////
	/**
	 * Retrieves the ID of the sender.
	 * 
	 * @return The ID of the sender.
	 */
    public String getSenderID() {
		return senderID;
	}

	/**
     * Sets the ID of the sender.
     * 
     * @param senderID The new ID of the sender.
     */
	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}

	/**
	 * Retrieves the name of the sender.
	 * 
	 * @return The name of the sender.
	 */
	public String getSenderName() {
		return senderName;
	}

	/**
     * Sets the name of the sender.
     * 
     * @param senderName The new name of the sender.
     */
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	/**
	 * Retrieves the email of the sender.
	 * 
	 * @return The email of the sender.
	 */
	public String getSenderEmail() {
		return senderEmail;
	}

	/**
     * Sets the email of the sender.
     * 
     * @param senderEmail The new email of the sender.
     */
	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	/**
	 * Retrieves the ID of the recipent.
	 * 
	 * @return The ID of the recipent.
	 */
	public String getRecipientID() {
		return recipientID;
	}

	/**
     * Sets the ID of the recipent.
     * 
     * @param recipentID The new ID of the recipent.
     */
	public void setRecipientID(String recipientID) {
		this.recipientID = recipientID;
	}

	/**
	 * Retrieves the name of the recipent.
	 * 
	 * @return The name of the recipent.
	 */
	public String getRecipientName() {
		return recipientName;
	}

	/**
     * Sets the name of the recipent.
     * 
     * @param recipentName The new name of the recipent.
     */
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	/**
	 * Retrieves the email of the recipent.
	 * 
	 * @return The email of the recipent.
	 */
	public String getRecipientEmail() {
		return recipientEmail;
	}

	/**
     * Sets the email of the recipent.
     * 
     * @param recipentEmail The new email of the recipent.
     */
	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}

	/**
	 * Retrieves the ID of the project.
	 * 
	 * @return The ID of the project.
	 */
	public int getProjectID() {
		return projectID;
	}

	
	// public void setProjectID(int projectID) {
	// 	this.projectID = projectID;
	// }

	/**
	 * Retrieves the ID of the new project.
	 * 
	 * @return The ID of the new project.
	 */
	public int getNewProjectID() {
		return newProjectID;
	}

	/**
     * Sets the ID of the new project.
     * 
     * @param newProjectID The new ID of the new project.
     */
	public void setNewProjectID(int newProjectID) {
		this.newProjectID = newProjectID;
	}

	/**
	 * Retrieves the title of the new project.
	 * 
	 * @return The title of the new project.
	 */
	public String getNewProjectTitle() {
		return newProjectTitle;
	}

	/**
     * Sets the title of the new project.
     * 
     * @param newProjectTitle The new title of the new project.
     */
	public void setNewProjectTitle(String newProjectTitle) {
		this.newProjectTitle = newProjectTitle;
	}

	/**
	 * Retrieves the ID of the new supervisor.
	 * 
	 * @return The ID of the new supervisor.
	 */
	public String getNewSupervisorID() {
		return newSupervisorID;
	}

	/**
     * Sets the ID of the new supervisor.
     * 
     * @param newSupervisorID The new ID of the new supervisor.
     */
	public void setNewSupervisorID(String newSupervisorID) {
		this.newSupervisorID = newSupervisorID;
	}

	/**
	 * Retrieves the name of the new supervisor.
	 * 
	 * @return The name of the new supervisor.
	 */
	public String getNewSupervisorName() {
		return newSupervisorName;
	}

	/**
     * Sets the name of the new supervisor.
     * 
     * @param newSupervisorName The new name of the new supervisor.
     */
	public void setNewSupervisorName(String newSupervisorName) {
		this.newSupervisorName = newSupervisorName;
	}

	/**
	 * Retrieves the email of the new supervisor.
	 * 
	 * @return The email of the new supervisor.
	 */
	public String getNewSupervisorEmail() {
		return newSupervisorEmail;
	}

	/**
     * Sets the email of the new supervisor.
     * 
     * @param newSupervisorEmail The new email of the new supervisor.
     */
	public void setNewSupervisorEmail(String newSupervisorEmail) {
		this.newSupervisorEmail = newSupervisorEmail;
	}

	/**
	 * Retrieves the type of the request.
	 * 
	 * @return The type of the request.
	 */
	public RequestType_Enum getRequestType() {
		return requestType;
	}

	/**
     * Sets the type of the request.
     * 
     * @param requestType The new type of the request.
     */
	public void setRequestType(RequestType_Enum requestType) {
		this.requestType = requestType;
	}

	/**
	 * Retrieves the status of the request.
	 * 
	 * @return The status of the request.
	 */
	public RequestStatus_Enum getRequestStatus() {
		return requestStatus;
	}

	/**
     * Sets the status of the request.
     * 
     * @param requestStatus The new status of the request.
     */
	public void setRequestStatus(RequestStatus_Enum requestStatus) {
		this.requestStatus = requestStatus;
	}
	
	/**
	 * Retrieves the ID of the request.
	 * 
	 * @return The ID of the request.
	 */
	public int getRequestID() {
		return requestID;
	}

	/**
     * Sets the ID of the request.
     * 
     * @param requestID The new ID of the request.
     */
	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}
    
	
	/////////// STATIC METHODS ///////////////
	/**
     * Returns a list of all requests.
     * 
     * @return A list of all requests
     */
	public static ArrayList<Request> getRequests() {
		return requests;
	}
	
	/**
	 * Returns the request at the specified index in the list.
	 * 
	 * @param i The index of the request to return
	 * @return The request at the specified index in the list
	 */
	public static Request getRequest(int i) {
		return requests.get(i-1);
	}
	
	/**
	 * Returns the request object with the given student ID.
	 * 
	 * @param studentID The ID of the student
	 * @return The request object
	 */
	public static Request getRequestFromStudentId(String studentID) {
		for (Request req : getRequests()) {
			if (req.getSenderID().equals(studentID) & req.getRequestType()==RequestType_Enum.CHANGETITLE & req.getRequestStatus()==RequestStatus_Enum.PENDING) {
				return req;
			}
		}
		return null;
	}

	/**
	 * Sets the list of requests to the specified list.
	 * 
	 * @param r The new list of requests
	 */
	public static void updateRequests(ArrayList<Request> r){
		requests = r;
	}

	/**
	 * Initializes the list of requests from the data in the request data file.
	 * 
	 * @throws Throwable If there is an error reading the request data file
	 */
	public static void initializeRequestFile() throws Throwable {
		HashMap<Integer, Object[]> map  = d.initializeRequestFile(FILENAME, FILEPATH);
		for (int requestID : map.keySet()) {
        	Object[] values = map.get(requestID);
        	
        	if((RequestType_Enum)values[12]==RequestType_Enum.CHANGETITLE) {
        		new Request((String)values[0],(String)values[1], (String)values[2], (String)values[3], (String)values[4], (String)values[5], (int)values[6], (String)values[8], (RequestType_Enum)values[12], (RequestStatus_Enum)values[13], requestID);
        	}
        	
        	else if((RequestType_Enum)values[12]==RequestType_Enum.REGISTERPROJECT) {
        		new Request((String)values[0],(String)values[1], (String)values[2], (String)values[3], (String)values[4], (String)values[5], (int)values[6], (RequestType_Enum)values[12], (RequestStatus_Enum)values[13], requestID);
        		if((RequestStatus_Enum)values[13]==RequestStatus_Enum.PENDING) {
        			Student.getStudentFromID((String)values[0]).setProjectID(0);
        		}
        	}
        	
        	else if((RequestType_Enum)values[12]==RequestType_Enum.DEREGISTERPROJECT) {
        		new Request((String)values[0],(String)values[1], (String)values[2], (String)values[3], (String)values[4], (String)values[5], (int)values[6], (RequestType_Enum)values[12], (RequestStatus_Enum)values[13], requestID);
        	}
        	
        	else if((RequestType_Enum)values[12]==RequestType_Enum.CHANGESUPERVISOR) {
        		new Request((String)values[0],(String)values[1], (String)values[2], (String)values[3], (String)values[4], (String)values[5], (int)values[6], (String)values[9], (String)values[10], (String)values[11], (RequestType_Enum)values[12], (RequestStatus_Enum)values[13], requestID);
        	}
        }
	}

	/**
	 * Updates the request data file with the current list of requests.
	 */
	public static void updateRequestFile() {	
		d.updateRequestFile(FILENAME,FILEPATH,requests);
	}
}


