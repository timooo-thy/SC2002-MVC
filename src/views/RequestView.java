package views;

import java.util.ArrayList;
import models.*;
import utilities.Database;

/**
 * The RequestView class provides the methods to display the information related to different requests.
 */
public class RequestView {

	/**
	 * Prints the information related to a given request ID such as request ID, request type,
	 * sender and recipient IDs, project ID, supervisor name (if applicable) and status.
	 * 
	 * @param requestID An integer that represents the ID of the request
	 */
	public static void printRequestInfo(int requestID){
		// checks request id type and display it accordingly
		View.cli.display("----------------------------------------------------------------------------");
		View.cli.display("Request ID: " + requestID);
		View.cli.display("Request Type: " + Request.getRequest(requestID).getRequestType());
		View.cli.display("Sender ID: " + Request.getRequest(requestID).getSenderID());
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
	
	/**
	 * Prints the history of requests made by a user, including the request ID, request type,
	 * sender and recipient IDs, project ID, supervisor name (if applicable) and status.
	 * 
	 * @param userID A string that represents the user ID of the sender
	 */
	public static void printRequestHistory(String userID){
		// loops through all requests to match the user id
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
	
	/**
	 * This method prints the history of incoming requests for the given recipient user ID.
	 * 
	 * @param recipientUserID The recipient user ID to retrieve the request history for
	 */
	public static void printIncomingRequestHistory(String recipientUserID){
		// loops through all requests to match the user id
		int requestCount = 0;
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
				requestCount++;
			}
		}
		if (requestCount == 0) {
			View.cli.displayTitle("There are no pending requests");
		}
	}
	
	/**
	 * This method prints the information of all requests 
	 * 
	 */
	
	public static void printAllRequests() {
		ArrayList<Integer> allRequestID = new ArrayList<>();
		View.cli.displayTitle("View All Requests");
		// loops through all requests and display the request info
		for (Request req : Request.getRequests()) {
			RequestView.printRequestInfo(req.getRequestID());
			View.cli.display("------------------------------------");
			allRequestID.add(req.getRequestID());
		}	
		if (allRequestID.size()==0) {
			View.cli.displayTitle("There are no pending requests");
			return;
		}
		return; 
	}
	
	/**
	 * This method requests user to choose a request ID.
	 * 
	 * @return The request ID of the selected request
	 */
	public static int requestRequestID() {
		ArrayList<Integer> allocationRequestID = new ArrayList<>();
		// loops through all pending requests and store in an arraylist
		for (Request req : Request.getRequests()) {
			if (req.getRequestStatus() == RequestStatus_Enum.PENDING) {
				allocationRequestID.add(req.getRequestID());
			}
		}
		int requestID = -1; 
		// request id is checked if its inside the arraylist
		while (!allocationRequestID.contains(requestID)) {
			requestID = View.cli.inputInteger("Enter Request ID (Enter 0 to exit)", 0, Request.getRequests().size());
			if (!allocationRequestID.contains(requestID)) {
				if (requestID == 0) break;
				View.cli.display("Please enter a valid Request ID");
			}
		}
		return requestID;
	}
	
	/**
	 * This method checks for confirmation of requests.
	 * 
	 * @return The user's choice to approve, reject or exit
	 */
	public static int requestConfirmation() {
		int confirmation = -1;
		// selection menu
		String [] confirmationMenu = {
				"Approve",
				"Reject",
				"Back"
		};
		View.cli.display(confirmationMenu);
		confirmation = View.cli.inputInteger("Choice (Enter 0 to exit)", 0, 2);
		return confirmation;
	}
	
	/**
	 * This method checks for new requests.
	 * 
	 * @param recipientUserID The recipient ID to check for new requests
	 * @return (NEW) if there are new requests, else it returns an empty string
	 */
	public static String checkForNew(String recipientUserID) {
		// loops through all pending requests that belongs to the id
		for (Request req : Request.getRequests()) {
			if (req.getRequestStatus() == RequestStatus_Enum.PENDING && req.getRecipientID().equals(recipientUserID)) {
				return "[🍪 ⋆ 🍎  🎀  𝒩𝐸𝒲  🎀  🍎 ⋆ 🍪]";
			}
		}
		return "";
	}
}