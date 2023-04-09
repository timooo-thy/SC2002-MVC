package requests;

public interface IApproveRequest {
	public abstract void approveRequest(int requestID);
    public abstract void rejectRequest(int requestID);
}
