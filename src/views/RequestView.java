package views;

import models.*;


public class RequestView {

	public static void printRequestInfo(int requestID){
		
		View.cli.display("Request ID: " + requestID);
		View.cli.display("Request Type: " + Request.getRequest(requestID-1).getRequestType());
		View.cli.display("Sender ID: " +Request.getRequest(requestID-1).getSenderID());
		View.cli.display("Recipient ID: " +Request.getRequest(requestID-1).getRecipientID());
		if (Request.getRequest(requestID-1).getRequestType() == RequestType_Enum.CHANGETITLE) {
			View.cli.display("Requesting to change title to: " + Request.getRequest(requestID-1).getNewProjectTitle());
		}
		else if (Request.getRequest(requestID-1).getRequestType() == RequestType_Enum.REGISTERPROJECT) {
			View.cli.display("Requesting to register to projectID: " + Request.getRequest(requestID-1).getProjectID());
		}
		else if (Request.getRequest(requestID-1).getRequestType() == RequestType_Enum.DEREGISTERPROJECT) {
			View.cli.display("Requesting to deregister from projectID: " + Request.getRequest(requestID-1).getProjectID());
		}
		else if (Request.getRequest(requestID-1).getRequestType() == RequestType_Enum.CHANGESUPERVISOR) {
			View.cli.display("For projectID " + Request.getRequest(requestID-1).getProjectID() + " , " + Request.getRequest(requestID-1).getSenderID() + "is requesting to change the supervisor to: " + Request.getRequest(requestID-1).getNewSupervisorName());
		}
		View.cli.display("Request Status: " +Request.getRequest(requestID-1).getRequestStatus().toString());
	}
	

	public static void printRequestHistory(String userID){
		for (Request req : Request.getRequests()) {
			int requestID = req.getRequestID();
			if (Request.getRequest(requestID-1).getSenderID().equals(userID)) {
				View.cli.display("Request ID: " + Request.getRequest(requestID-1).getRequestID());
				View.cli.display("Request Type: " + Request.getRequest(requestID-1).getRequestType());
				View.cli.display("Sender ID: " + Request.getRequest(requestID-1).getSenderID());
				View.cli.display("Recipient ID: " + Request.getRequest(requestID-1).getRecipientID());
				if (Request.getRequest(requestID-1).getRequestType() == RequestType_Enum.CHANGETITLE) {
					View.cli.display("Requesting to change title to: " + Request.getRequest(requestID-1).getNewProjectTitle());
				}
				else if (Request.getRequest(requestID-1).getRequestType() == RequestType_Enum.REGISTERPROJECT) {
					View.cli.display("Requesting to register to projectID: " + Request.getRequest(requestID-1).getProjectID());
				}
				else if (Request.getRequest(requestID-1).getRequestType() == RequestType_Enum.DEREGISTERPROJECT) {
					View.cli.display("Requesting to deregister from projectID: " + Request.getRequest(requestID-1).getProjectID());
				}
				else if (Request.getRequest(requestID-1).getRequestType() == RequestType_Enum.CHANGESUPERVISOR) {
					View.cli.display("For projectID " + Request.getRequest(requestID-1).getProjectID() + " , " + Request.getRequest(requestID-1).getSenderID() + "is requesting to change the supervisor to: " + Request.getRequest(requestID-1).getNewSupervisorName());
				}
				
				View.cli.display("Request Status: " +Request.getRequest(requestID-1).getRequestStatus().toString());
		}
		
		}
	}
}
