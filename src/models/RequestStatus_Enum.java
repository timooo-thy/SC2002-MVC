package models;

/**
 * This enum represents the different possible statuses for a request in the FYP Registration System.
 * The possible statuses are APPROVED, PENDING, and REJECTED.
 */
public enum RequestStatus_Enum {
	
	/*
	 * Indicates that a request has been approved by the relevant authority.
	 */
	APPROVED,
	
	/*
	 * Indicates that a request is still pending a decision by the relevant authority.
	 */
	PENDING,
	
	/*
	 * Indicates that a request has been rejected by the relevant authority.
	 */
	REJECTED
}
