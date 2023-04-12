 package views;

import java.util.ArrayList;

import models.*;

public class RequestView {

	public static void printRequestInfo(int requestID){
		View.cli.display("----------------------------------------------------------------------------");
		View.cli.display("Request ID: " + requestID);
		View.cli.display("Request Type: " + Request.getRequest(requestID).getRequestType());
		View.cli.display("Sender ID: " +Request.getRequest(requestID).getSenderID());
		View.cli.display("Recipient ID: " +Request.getRequest(requestID).getRecipientID());
		
		if (Request.getRequest(requestID).getRequestType() == RequestType_Enum.CHANGETITLE) {
			View.cli.display("Requesting to Change Title to: " + Request.getRequest(requestID).getNewProjectTitle());
		}
		
		else if (Request.getRequest(requestID).getRequestType() == RequestType_Enum.REGISTERPROJECT) {
			View.cli.display("Requesting to Register to Project ID: " + Request.getRequest(requestID).getProjectID());
		}
		
		else if (Request.getRequest(requestID).getRequestType() == RequestType_Enum.DEREGISTERPROJECT) {
			View.cli.display("Requesting to Deregister from Project ID: " + Request.getRequest(requestID).getProjectID());
		}
		
		else if (Request.getRequest(requestID).getRequestType() == RequestType_Enum.CHANGESUPERVISOR) {
			View.cli.display("For Project ID " + Request.getRequest(requestID).getProjectID() + ", " + Request.getRequest(requestID).getSenderID() + " is requesting to change the supervisor to: " + Request.getRequest(requestID).getNewSupervisorName());
		}
		
		View.cli.display("Request Status: " + Request.getRequest(requestID).getRequestStatus().toString());
	}
	

	public static void printRequestHistory(String userID){
		for (Request req : Request.getRequests()) {
			int requestID = req.getRequestID();
			if (Request.getRequest(requestID).getSenderID().equals(userID)) {
				View.cli.display("----------------------------------------------------------------------------");
				View.cli.display("Request ID: " + Request.getRequest(requestID).getRequestID());
				View.cli.display("Request Type: " + Request.getRequest(requestID).getRequestType());
				View.cli.display("Sender ID: " + Request.getRequest(requestID).getSenderID());
				View.cli.display("Recipient ID: " + Request.getRequest(requestID).getRecipientID());
				if (Request.getRequest(requestID).getRequestType() == RequestType_Enum.CHANGETITLE) {
					View.cli.display("Requesting to Change Title to: " + Request.getRequest(requestID).getNewProjectTitle());
				}
				else if (Request.getRequest(requestID).getRequestType() == RequestType_Enum.REGISTERPROJECT) {
					View.cli.display("Requesting to Register to Project ID: " + Request.getRequest(requestID).getProjectID());
				}
				else if (Request.getRequest(requestID).getRequestType() == RequestType_Enum.DEREGISTERPROJECT) {
					View.cli.display("Requesting to Deregister from Project ID: " + Request.getRequest(requestID).getProjectID());
				}
				else if (Request.getRequest(requestID).getRequestType() == RequestType_Enum.CHANGESUPERVISOR) {
					View.cli.display("For Project ID " + Request.getRequest(requestID).getProjectID() + " , " + Request.getRequest(requestID).getSenderID() + " is requesting to change the supervisor to: " + Request.getRequest(requestID).getNewSupervisorName());
				}
				
				View.cli.display("Request Status: " + Request.getRequest(requestID).getRequestStatus().toString());
		}
		
		}
	}
	
	public static void printIncomingRequestHistory(String recipientUserID){
		for (Request req : Request.getRequests()) {
			int requestID = req.getRequestID();
			if (Request.getRequest(requestID).getRecipientID().equals(recipientUserID)) {
				View.cli.display("----------------------------------------------------------------------------");
				View.cli.display("Request ID: " + Request.getRequest(requestID).getRequestID());
				View.cli.display("Request Type: " + Request.getRequest(requestID).getRequestType());
				View.cli.display("Sender ID: " + Request.getRequest(requestID).getSenderID());
				View.cli.display("Recipient ID: " + Request.getRequest(requestID).getRecipientID());
				if (Request.getRequest(requestID).getRequestType() == RequestType_Enum.CHANGETITLE) {
					View.cli.display("Requesting to Change Title to: " + Request.getRequest(requestID).getNewProjectTitle());
				}
				else if (Request.getRequest(requestID).getRequestType() == RequestType_Enum.REGISTERPROJECT) {
					View.cli.display("Requesting to Register to Project ID: " + Request.getRequest(requestID).getProjectID());
				}
				else if (Request.getRequest(requestID).getRequestType() == RequestType_Enum.DEREGISTERPROJECT) {
					View.cli.display("Requesting to Deregister from Project ID: " + Request.getRequest(requestID).getProjectID());
				}
				else if (Request.getRequest(requestID).getRequestType() == RequestType_Enum.CHANGESUPERVISOR) {
					View.cli.display("For Project ID " + Request.getRequest(requestID).getProjectID() + " , " + Request.getRequest(requestID).getSenderID() + " is requesting to change the supervisor to: " + Request.getRequest(requestID).getNewSupervisorName());
				}
				
				View.cli.display("Request Status: " + Request.getRequest(requestID).getRequestStatus().toString());
			}
		}
	}
	
	public static int requestRequestID() {
		// Initialise Pending requests list
		ArrayList<Integer> allocationRequestID = new ArrayList<>();
		for (Request req : Request.getRequests()) {
			if (req.getRequestStatus() == RequestStatus_Enum.PENDING) {
				allocationRequestID.add(req.getRequestID());
			}
		}
		int requestID = -1; 
		while (!allocationRequestID.contains(requestID)) {
			requestID = View.cli.inputInteger("Enter Request ID (Enter 0 to exit)", 0, Request.getRequests().size());
		}
		return requestID;
	}
	public static int requestConfirmation() {
		// Initialise Pending requests list
		int confirmation = -1;
		View.cli.display("Enter 1 to Approve Request \nEnter 2 to Reject Request");
		confirmation = View.cli.inputInteger("choice (Enter 0 to exit)", 0, 2);
		
		return confirmation;
	}
	
	public static String checkForNew(String recipientUserID) {
		for (Request req : Request.getRequests()) {
			if (req.getRequestStatus() == RequestStatus_Enum.PENDING && req.getRecipientID().equals(recipientUserID)) {
				return "(NEW)";
			}
		}
		return "";
	}
}