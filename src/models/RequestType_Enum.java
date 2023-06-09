package models;

/**
 * This enum represents the different types of requests that can be made in the FYP Registration System.
 * The possible types are CHANGETITLE, REGISTERPROJECT, DEREGISTERPROJECT, and CHANGESUPERVISOR.
 * @author Timothy Lee
 * @author Justin Wong
 * @author Jun Hao
 * @author Lee Cheng Yao
 * @author Abhishekh
 * @version 1.0
 * @since 2023-04-16
 */
public enum RequestType_Enum {
	
	/**
	 * Indicates a request to change the title of a project.
	 */
    CHANGETITLE,
    
    /**
     * Indicates a request to register a new project.
     */
    REGISTERPROJECT, 
    
    /**
     * Indicates a request to deregister a project.
     */
    DEREGISTERPROJECT, 
    
    /**
     * Indicates a request to change the supervisor of a project.
    */
    CHANGESUPERVISOR
}


