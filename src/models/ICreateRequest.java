package models;

/**
 * This interface defines the method signature for creating a request.
 */
public interface ICreateRequest {
	
	/*
	 * This method creates a request with the given parameters.
	 * 
	 * @param senderID The ID of the user sending the request.
	 * @param recipientID The ID of the user receiving the request.
	 * @param requesttype The type of request being made.
	 * @param requestdetail1 The first detail of the request.
	 * @param requestdetail2 The second detail of the request.
	 */
	public static void createRequest(String senderID, String recipientID, RequestType_Enum requesttype, String requestdetail1, String requestdetail2) {};
}
