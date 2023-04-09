package views;

import models.Request;


public class RequestView {
	
	public void printRequestInfo(int requestID){
		
		View.cli.display("Request ID: " + requestID);
		View.cli.display("Request Type: " + req.getRequest(requestID-1).getRequestType());
		View.cli.display("Sender ID: " +req.getRequest(requestID-1).getSenderID());
		View.cli.display("Recipient ID: " +req.getRequest(requestID-1).getRecipientID());
		if (req.getRequestType() == CHANGETITLE) {
			View.cli.display("Requesting to change title to: " + req.getNewProjectTitle());
		}
		else if (req.getRequestType() == REGISTERPROJECT) {
			View.cli.display("Requesting to register to projectID: " + req.getProjectID());
		}
		else if (req.getRequestType() == DEREGISTERPROJECT) {
			View.cli.display("Requesting to deregister from projectID: " + req.getProjectID());
		}
		else if (req.getRequestType() == CHANGESUPERVISOR) {
			View.cli.display("For projectID " + req.getProjectID, + " , " req.getSenderID() + "is requesting to change the supervisor to: " + req.getSupervisorName());
		}
		else if (req.getRequestType() == TRANSFERSTUDENT) { //isnt this the same as change supervisor?
			View.cli.display("For projectID " + req.getProjectID, + " , " req.getSenderID() + "is requesting to change the student to new project: " + req.getNewProjectID());
		}
		View.cli.display("Request Status: " +req.getRequest(requestID-1).getRequestStatus().toString());
	}
	
	public void printRequestHistory(String userID){
			
		if (req.getSenderID().equals(userID)) {
			View.cli.display("Request ID: " + req.getRequestID());
			View.cli.display("Request Type: " + req.getRequestType());
			View.cli.display("Sender ID: " + req.getSenderID());
			View.cli.display("Recipient ID: " + req.getRecipientID());
			if (req.getRequestType() == CHANGETITLE) {
				View.cli.display("Requesting to change title to: " + req.getNewProjectTitle());
			}
			else if (req.getRequestType() == REGISTERPROJECT) {
				View.cli.display("Requesting to register to projectID: " + req.getProjectID());
			}
			else if (req.getRequestType() == DEREGISTERPROJECT) {
				View.cli.display("Requesting to deregister from projectID: " + req.getProjectID());
			}
			else if (req.getRequestType() == CHANGESUPERVISOR) {
				View.cli.display("For projectID " + req.getProjectID, + " , " req.getSenderID() + "is requesting to change the supervisor to: " + req.getSupervisorName());
			}
			else if (req.getRequestType() == TRANSFERSTUDENT) { //isnt this the same as change supervisor?
				View.cli.display("For projectID " + req.getProjectID, + " , " req.getSenderID() + "is requesting to change the student to new project: " + req.getNewProjectID());
			}
			View.cli.display("Request Status: " +req.getRequestStatus().toString());
		}
	}
}
