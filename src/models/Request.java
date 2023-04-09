package models;

import requests.RequestStatus_Enum;
import requests.RequestType_Enum;

public class Request {
    private String senderID;
    private String recipientID;
    private RequestType_Enum requesttype;
    private String requestdetail1;
    private String requestdetail2;
    private RequestStatus_Enum requeststatus;
    private int requestID;


    ////////////// Constructor //////////////
    public Request(String senderID,String recipientID,RequestType_Enum requesttype, String requestdetail1, String requestdetail2, RequestStatus_Enum requeststatus,int requestID) {
        this.senderID = senderID;
        this.recipientID = recipientID;
        this.requesttype = requesttype;
        this.requeststatus = requeststatus;
        this.requestID = requestID; 
        this.requestdetail1 = requestdetail1;
        this.requestdetail2 = requestdetail2;
    }

    //////////// Class methods //////////////
 
    public void approve() {
    	requeststatus = RequestStatus_Enum.APPROVED;
        return;
    } // Approve request 


    public void reject() {
        requeststatus = RequestStatus_Enum.REJECTED;
        return;
    } // Reject request
    
  

    /////////////Accessors and mutators///////////////
    // gets
    public String getSenderID() {
        return this.senderID;
    }
    public String getRecipientID() {
        return this.recipientID;
    }
    public RequestType_Enum getRequestType() {
        return this.requesttype;
    }
    public RequestStatus_Enum getRequestStatus() {
        return this.requeststatus;
    }
    public int getRequestID() {
        return this.requestID;
    }
    public String getRequestDetail1() {
        return this.requestdetail1;
    }
    public String getRequestDetail2() {
        return this.requestdetail2;
    }
    //sets
    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }
    public void setRecipientID(String recipientID) {
        this.recipientID = recipientID;
    }
    public void setRequestType(RequestType_Enum requesttype) {
        this.requesttype = requesttype;
    }
    public void setRequestStatus(RequestStatus_Enum requeststatus) {
        this.requeststatus = requeststatus;
    }
    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }
    public void setRequestDetail1(String requestdetail1) {
        this.requestdetail1 = requestdetail1;
    }
    public void setRequestDetail2(String requestdetail2) {
        this.requestdetail2 = requestdetail2;
    }


}


