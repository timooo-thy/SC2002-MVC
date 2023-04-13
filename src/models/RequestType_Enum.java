package models;

/**
 * This enum represents the different types of requests that can be made in the FYP Registration System.
 * The possible types are CHANGETITLE, REGISTERPROJECT, DEREGISTERPROJECT, and CHANGESUPERVISOR.
 */
public enum RequestType_Enum {
	
	/*
	 * Indicates a request to change the title of a project.
	 */
    CHANGETITLE,
    
    /*
     * Indicates a request to register a new project.
     */
    REGISTERPROJECT, 
    
    /*
     * Indicates a request to deregister a project.
     */
    DEREGISTERPROJECT, 
    
    /*
     * Indicates a request to change the supervisor of a project.
    */
    CHANGESUPERVISOR
}


