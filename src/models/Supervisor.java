package models;

import java.util.ArrayList;
import java.util.HashMap;
import utilities.Database;

/**
 * This class represents a Supervisor who can supervise projects in the system.
 * It extends the User class and contains a list of supervised projects and methods to add and remove them.
 * It also has methods to retrieve and update information about the supervisors from the database.
 * @author Timothy Lee
 * @author Justin Wong
 * @author Jun Hao
 * @author Lee Cheng Yao
 * @author Abhishekh
 * @version 1.0
 * @since 2023-04-16
 */
public class Supervisor extends User implements DatabaseConnector {
	
	/**
	 * File path of the file to store the list of supervisors.
	 */
	private static final String FILEPATH = "src/data/";
	
	/**
	 * File name of the file to store the list of supervisors.
	 */
	private static final String FILENAME = "facultyList.txt";

	/**
	 * List of all the supervisors in the system.
	 */
	private static ArrayList<Supervisor> supervisorsList = new ArrayList<Supervisor>();

	/**
	 * List of all the supervised projects of each supervisor in the system.
	 */
	private ArrayList<Project> supervisedProjectList = new ArrayList<Project>(0);
	
	/**
	 * Database object to perform database operations.
	 */
	private static Database d = new Database();
	
	/**
	 * Supervisor's ID.
	 */
	private String supervisorId;
	
	/**
	 * Supervisor's Name.
	 */
	private String supervisorName;
	
	/**
	 * Supervisor's Email.
	 */
	private String supervisorEmail;
	
	/**
	 * Supervisor's Password.
	 */
	private String supervisorPassword;

	/**
	 * Supervisor's Project ID.
	 */
    private int projectID;

	//  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  
    
    /**
     * Creates a new Supervisor object with the given attributes, and adds it to the list of supervisors.
     * 
     * @param supervisorId The ID of the supervisor.
     * @param supervisorEmail The email address of the supervisor.
     * @param supervisorPassword The password of the supervisor.
     * @param supervisorName The name of the supervisor.
     */
    public Supervisor(String supervisorId, String supervisorEmail, String supervisorPassword, String supervisorName) {
		this.supervisorId = supervisorId.toUpperCase();
		this.supervisorName = supervisorName;
		this.supervisorEmail = supervisorEmail;
		this.supervisorPassword = supervisorPassword;
        this.projectID = -1;
        
        //Prevents duplicates
        for (Supervisor sup : supervisorsList) {
    		if (sup.getId().equals(supervisorId)) {
    			return;
    		}
    			
    	}
		addSupervisor(this);
	}

    /**
     * Sets the name of the supervisor.
     * 
     * @param supervisorName The new name of the supervisor.
     */
	public void setSupervisorName(String supervisorName){
		this.supervisorName = supervisorName;
	}

	/**
	 * Retrieves the name of the supervisor.
	 * 
	 * @return The name of the supervisor.
	 */
	public String getName(){
		return this.supervisorName;
	}
	
	/**
	 * Retrieves the email address of the supervisor.
	 * @return The email address of the supervisor.
	 */
	public String getEmailAddress(){
		return this.supervisorEmail;
	}
	
	/**
	 * Sets the email address of the supervisor.
	 * 
	 * @param supervisorEmail The new email address of the supervisor.
	 */
	public void setEmailAddress(String supervisorEmail){
		this.supervisorEmail =  supervisorEmail;
	}
	
	/**
	 * Retrieves the password of the supervisor.
	 * 
	 * @return The password of the supervisor.
	 */
	public String getPassword(){
		return this.supervisorPassword;
	}
	
	/**
	 * Sets the password of the supervisor.
	 * 
	 * @param supervisorPassword The new password of the supervisor.
	 */
	public void setPassword(String supervisorPassword){
		this.supervisorPassword = supervisorPassword;
	}
	
	/**
	 * Adds a new supervisor to the list of supervisors, and updates the file that stores the list of supervisors.
	 * 
	 * @param s The supervisor object to add
	 */
	public void addSupervisor(Supervisor s) {
		supervisorsList.add(s);
		updateFile();
	}
	
	/**
	 * Retrieves the ID of the supervisor.
	 * 
	 * @return The ID of the supervisor.
	 */
	public String getId() {
		return this.supervisorId;
	}
	
    /**
     * Returns the list of projects supervised by this supervisor.
     * 
     * @return The list of projects supervised by this supervisor
     */
    public ArrayList<Project> getSupervisedProjectList(){
    	return this.supervisedProjectList;
    }
    
    /**
     * Returns the list of projects supervised by the supervisor with the given ID.
     * 
     * @param supervisorId The ID of the supervisor whose projects are being retrieved
     * @return The list of projects supervised by the supervisor with the given ID
     */

    public static ArrayList<Project> getSupervisedProjectList(String supervisorId){
    	//loops through the list to find the projects supervised by a certain supervisor
    	for (Supervisor sup : Supervisor.getSupervisorsList())
    		if (sup.getId().equals(supervisorId))
    			return sup.getSupervisedProjectList();
    	return null;
    }

    /**
     * Returns the Supervisor object with the given ID.
     * 
     * @param supervisorId the ID of the Supervisor being retrieved
     * @return the Supervisor object with the given ID
     */
    public static Supervisor getSupervisorFromId(String supervisorId) {
    	//loops through the list to find supervisor object with id
    	for (Supervisor sup : supervisorsList) {
    		if (sup.getId().equals(supervisorId)) 
    			return sup;
    	}
    	return null;
    }
    
    /**
     * Returns the supervisor object with the given name.
     * 
     * @param supervisorName The name of the Supervisor being retrieved
     * @return The supervisor object with the given name
     */
    public static Supervisor getSupervisorFromName(String supervisorName) {
    	//loops through the list to find supervisor object with name
    	for (Supervisor sup : supervisorsList) {
    		if (sup.getName().equals(supervisorName)) 
    			return sup;
    	}
    	return null;
    }
    
    /**
     * Returns the email address of the supervisor with the given name.
     * 
     * @param supervisorName The name of the supervisor whose email address is being retrieved
     * @return The email address of the supervisor with the given name
     */
 	public static String getSupervisorNameToEmail(String supervisorName){
 		//loops through the list to find supervisor email with name
 		for (Supervisor sup : Supervisor.getSupervisorsList()) {
 			if (sup.getName().equals(supervisorName)) {
 				return sup.getEmailAddress();
 			}
 		}
 		return null;
 	}
 	
 	/**
 	 * Returns the ID of the supervisor with the given name.
 	 * 
 	 * @param supervisorName The name of the supervisor
 	 * @return The ID of the supervisor with the given name, or null if no such supervisor exists
 	 */
 	public static String getSupervisorNameToId(String supervisorName) {
 		//loops through the list to find supervisor id with name
 		for (Supervisor sup : Supervisor.getSupervisorsList()) {
 			if (sup.getName().equals(supervisorName)) {
 				return sup.getId();
 			}
 		}
 		return null;
 	}
 	
 	/**
 	 * Returns the email address of the supervisor with the given ID.
 	 * 
 	 * @param supervisorId The ID of the supervisor
 	 * @return The email address of the supervisor with the given ID, or null if no such supervisor exists
 	 */
 	public static String getSupervisorIdToEmail(String supervisorId){
 		//loops through the list to find supervisor email with id
 		for (Supervisor sup : Supervisor.getSupervisorsList()) {
 			if (sup.getId().equals(supervisorId)) {
 				return sup.getEmailAddress();
 			}
 		}
 		return null;
 	}
 	
 	/**
 	 * Returns the name of the supervisor with the given ID.
 	 * 
 	 * @param supervisorId The ID of the supervisor
 	 * @return The name of the supervisor with the given ID, or null if no such supervisor exists
 	 */
 	public static String getSupervisorIdToName(String supervisorId) {
 		//loops through the list to find supervisor name with id
 		for (Supervisor sup : Supervisor.getSupervisorsList()) {
 			if (sup.getId().equals(supervisorId)) {
 				return sup.getName();
 			}
 		}
 		return null;
 	}
 	
	/**
	 * Returns a list of all supervisors.
	 * 
	 * @return A list of all supervisors
	 */
	public static ArrayList<Supervisor> getSupervisorsList(){
		return supervisorsList;
	}

	//  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  
	
	/**
	 * Initialises the list of supervisors from the data in the supervisor data file.
	 * 
	 * @throws Throwable If there is an error reading the supervisor data file
	 */
	public static void initializeFile() throws Throwable {
		//uses hashmap to create supervisor objects
		HashMap<String, String[]> map  = d.initializeFile(FILENAME, FILEPATH);
		for (String name : map.keySet()) {
        	String[] values = map.get(name);
        	new Supervisor(values[0], values[1], values[2], name); 
        }
	}

	/**
	 * Updates the supervisor data file with the current list of supervisors.
	 */
	public static void updateFile() {
		//upcast to user arraylist before updating the file
		ArrayList<User> usersList = new ArrayList<>(supervisorsList);
		d.updateFile(FILENAME,FILEPATH,usersList);
	}

}
