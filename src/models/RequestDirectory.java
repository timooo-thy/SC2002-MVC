package requests;

import User.*;

import java.util.ArrayList;

import Project.ProjectDirectory;

public class RequestDirectory implements IApproveRequest {
	//private static requestsDirectory requests = new requestsDirectory();
    private static RequestDirectory requestdirectory = new RequestDirectory();
    private static ArrayList<Request> requests;
    
    // Constructor *NOTE: making it private so it cannot be instantiated*
    public RequestDirectory(){
        requests = new ArrayList<Request>();
    }

    // returns the request
    public Request selectRequest(int requestID){
    	try {
    		return requests.get(requestID-1);
    	} 
    	catch(Exception e) {
    		return null;
    	}
    } 

    //only supervisor and fyp coordinator receive request
    public void getIncomingRequests(UserType_Enum usertype){ //parameter comes from the person requesting to get request getRequestDetails
        int count=0;
        if (usertype == UserType_Enum.SUPERVISOR){
            for (int i=0;i<requests.size();i++){
                if (requests.get(i).getRequestStatus() == RequestStatus_Enum.PENDING && requests.get(i).getRequestType() == RequestType_Enum.CHANGETITLE){ //supervisor only sees pending req that wants a change in title
                    System.out.println((i+1) + ". The student requesting is " + requests.get(i).getSenderID());  
                    System.out.println((i+1) + ". The student is requesting to change title to " + requests.get(i).getRequestDetail1());      
                    System.out.println((i+1) + ". The request status is " + requests.get(i).getRequestStatus());            
                    count++;
                }
            }
            if (count==0){
                System.out.println("You have no pending requests");    
            }
        }
        /* FYPCOORDINATOR get 3 type of request: 1.Student want FYP to approve student allocation to project 2. Student wants FYP to deregister project 3. Supervisor wants FYP to transfer a student to a replacement supervisor*/
        else if (usertype == UserType_Enum.FYPCOORDINATOR){
            for (int i=0;i<requests.size();i++){
                if (requests.get(i).getRequestStatus() == RequestStatus_Enum.PENDING && requests.get(i).getRequestType() == RequestType_Enum.REGISTERPROJECT){
                    System.out.println((i+1) + ". The student requesting is " + requests.get(i).getSenderID());  
                    System.out.println((i+1) + ". The student is requesting to register to project " + requests.get(i).getRequestDetail1());      
                    System.out.println((i+1) + ". The request status is " + requests.get(i).getRequestStatus());   
                    count++;
                }
                else if (requests.get(i).getRequestStatus() == RequestStatus_Enum.PENDING && requests.get(i).getRequestType() == RequestType_Enum.DEREGISTERPROJECT){
                    System.out.println((i+1) + ". The student requesting is " + requests.get(i).getSenderID());  
                    System.out.println((i+1) + ". The student is requesting to deregister from project " + requests.get(i).getRequestDetail1());      
                    System.out.println((i+1) + ". The request status is " + requests.get(i).getRequestStatus());   
                    count++;
                }
                else if (requests.get(i).getRequestStatus() == RequestStatus_Enum.PENDING && requests.get(i).getRequestType() == RequestType_Enum.CHANGESUPERVISOR){
                    System.out.println((i+1) + ". The supervisor requesting is " + requests.get(i).getSenderID());  
                    System.out.println((i+1) + ". The supervisor is requesting to transfer project ID" + requests.get(i).getRequestDetail1() + " to the supervisor " + requests.get(i).getRequestDetail2());      
                    System.out.println((i+1) + ". The request status is " + requests.get(i).getRequestStatus()); 
                    count++;

                }
            }
            if (count==0){
                System.out.println("You have no pending requests");    
            }
        }
        else {
        	System.out.println("You have no pending requests");   
        }
        
    }
    
    
    public static void viewRequestHistory(String userID, UserType_Enum usertype) {
        int count=0;
        System.out.println("Request ID \t Status \t Type");
        for (int i = 0; i < requests.size(); i++) { 
            if (usertype == UserType_Enum.STUDENT && requests.get(i).getSenderID() == userID) {
                System.out.printf("%d \t %s \t %s\n",i+1,(requests.get(i).getRequestStatus()),(requests.get(i).getRequestType()));
                count++;
            }
            else if (usertype == UserType_Enum.SUPERVISOR && (requests.get(i).getSenderID() == userID || requests.get(i).getRecipientID() == userID)) {
                System.out.printf("%d \t %s \t %s \n",i+1,(requests.get(i).getRequestStatus()),(requests.get(i).getRequestType()));
                count++;
            }
            else if (usertype == UserType_Enum.FYPCOORDINATOR) {
                System.out.printf("%d \t %s \t %s\n",i+1,(requests.get(i).getRequestStatus()),(requests.get(i).getRequestType()));
                count++;
            }

            if (count==0){
                System.out.println("You have not sent any requests before.");
            }
        }
    }
    


    //////////////// Implementing Requests Interfaces ////////////////
    
	@Override
	public void approveRequest(int requestID) {
		// TODO Auto-generated method stub
		requests.get(requestID -1).approve();
	}

	@Override
	public void rejectRequest(int requestID) {
		// TODO Auto-generated method stub
		requests.get(requestID -1).reject();
		
	}


    //////// Accessors and Mutators
    public static void createRequest(String senderID,String recipientID,RequestType_Enum requesttype, String requestdetail1, String requestdetail2){
        Request newRequest = new Request(senderID,recipientID,requesttype,requestdetail1,requestdetail2,RequestStatus_Enum.PENDING,requests.size());
        requests.add(newRequest);
    } // Create new request


    // return instance of this directory so users can ONLY ACCESS ONE INSTANCE AND NOT MANY
    public static ArrayList<Request> getRequestDirectory(){
        return requests;
    }
    
    public static Request getRequest(int requestID) {
    	return requests.get(requestID-1);
    }
    
    }
