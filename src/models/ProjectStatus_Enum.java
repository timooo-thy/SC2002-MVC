package models;

/**
 * This enum represents the different possible statuses for a project in the FYP Registration System.
 * The possible statuses are AVAILABLE, RESERVED, UNAVAILABLE, and ALLOCATED.
 * @author Timothy Lee
 * @author Justin Wong
 * @author Jun Hao
 * @author Lee Cheng Yao
 * @author Abhishekh
 */
public enum ProjectStatus_Enum {
	
	/**
	Indicates that a project is available for selection by a student.
	 */
	AVAILABLE,
	
	/**
	 * Indicates that a project has been reserved by a student but not yet allocated to them.
	 */
	RESERVED,
	
	/**
	 *Indicates that a project is unavailable for selection by students.
	 */
	UNAVAILABLE,
	
	/**
	 * Indicates that a project has been allocated to a student.
	 */
	ALLOCATED
}
