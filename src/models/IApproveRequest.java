package models;

/**
 * The IApproveRequest interface defines methods to approve or reject a request.
 */
public interface IApproveRequest {
	
	/**
	 * Approves a request with the given request ID.
	 * 
	 * @param requestID The ID of the request to be approved.
	 */
	public abstract void approveRequest(int requestID);
	
	/**
	 * Rejects a request with the given request ID.
	 * 
	 * @param requestID The ID of the request to be rejected.
	 */
    public abstract void rejectRequest(int requestID);
}
