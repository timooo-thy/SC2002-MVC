package views;

import Request;


public class RequestView {
	public void printRequestInfo(int requestID){
		
		System.out.println("Request ID: " + requestID);
		System.out.println("Request Type: " + req.getRequest(requestID-1).getRequestType());
		System.out.println("Sender ID: " +req.getRequest(requestID-1).getSenderID());
		System.out.println("Recipient ID: " +req.getRequest(requestID-1).getRecipientID());
		if (req.getRequestType() == CHANGETITLE) {
			System.out.println("Requesting to change title to: " + req.getNewProjectTitle());
		}
		else if (req.getRequestType() == REGISTERPROJECT) {
			System.out.println("Requesting to register to projectID: " + req.getProjectID());
		}
		else if (req.getRequestType() == DEREGISTERPROJECT) {
			System.out.println("Requesting to deregister from projectID: " + req.getProjectID());
		}
		else if (req.getRequestType() == CHANGESUPERVISOR) {
			System.out.println("For projectID " + req.getProjectID, + " , " req.getSenderID() + "is requesting to change the supervisor to: " + req.getSupervisorName());
		}
		else if (req.getRequestType() == TRANSFERSTUDENT) { //isnt this the same as change supervisor?
			System.out.println("For projectID " + req.getProjectID, + " , " req.getSenderID() + "is requesting to change the student to new project: " + req.getNewProjectID());
		}
		System.out.println("Request Status: " +req.getRequest(requestID-1).getRequestStatus().toString());
	}
	
public void printRequestHistory(String userID){
		
	if (req.getSenderID().equals(userID)) {
		System.out.println("Request ID: " + req.getRequestID());
		System.out.println("Request Type: " + req.getRequestType());
		System.out.println("Sender ID: " + req.getSenderID());
		System.out.println("Recipient ID: " + req.getRecipientID());
		if (req.getRequestType() == CHANGETITLE) {
			System.out.println("Requesting to change title to: " + req.getNewProjectTitle());
		}
		else if (req.getRequestType() == REGISTERPROJECT) {
			System.out.println("Requesting to register to projectID: " + req.getProjectID());
		}
		else if (req.getRequestType() == DEREGISTERPROJECT) {
			System.out.println("Requesting to deregister from projectID: " + req.getProjectID());
		}
		else if (req.getRequestType() == CHANGESUPERVISOR) {
			System.out.println("For projectID " + req.getProjectID, + " , " req.getSenderID() + "is requesting to change the supervisor to: " + req.getSupervisorName());
		}
		else if (req.getRequestType() == TRANSFERSTUDENT) { //isnt this the same as change supervisor?
			System.out.println("For projectID " + req.getProjectID, + " , " req.getSenderID() + "is requesting to change the student to new project: " + req.getNewProjectID());
		}
		System.out.println("Request Status: " +req.getRequestStatus().toString());
	}
}
