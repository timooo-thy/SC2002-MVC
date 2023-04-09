package views;

import models.RequestDirectory;


public class RequestView {
	public void printRequestInfo(int requestID){
		
		System.out.println("Request ID: " + requestID);
		System.out.println("Request Type: " + RequestDirectory.getRequest(requestID-1).getRequestType());
		System.out.println("Sender ID: " +RequestDirectory.getRequest(requestID-1).getSenderID());
		System.out.println("Recipient ID: " +RequestDirectory.getRequest(requestID-1).getRecipientID());
		System.out.println("Additional Detail: " +RequestDirectory.getRequest(requestID-1).getRequestDetail1());
		System.out.println("Additional Detail: " +RequestDirectory.getRequest(requestID-1).getRequestDetail2());
		System.out.println("Request Status: " +RequestDirectory.getRequest(requestID-1).getRequestStatus().toString());
	}

}
