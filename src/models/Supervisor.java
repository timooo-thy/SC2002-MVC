package models;

import java.util.ArrayList;
import java.util.HashMap;
import utilities.Database;

/**
 * This class represents a Supervisor who can supervise projects in the system.
 * It extends the User class and contains a list of supervised projects and methods to add and remove them.
 * It also has methods to retrieve and update information about the supervisors from the database.
 */
public class Supervisor extends User {
	
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

    /**
     * Creates a new Supervisor object with the given attributes, and adds it to the list of supervisors.
     * 
     * @param supervisorId The ID of the supervisor.
     * @param supervisorEmail The email address of the supervisor.
     * @param supervisorPassword The password of the supervisor.
     * @param supervisorName The name of the supervisor.
     * @param projectID The ID of the project that the supervisor is assigned to.
     */
    public Supervisor(String supervisorId, String supervisorEmail, String supervisorPassword, String supervisorName) {
		this.supervisorId = supervisorId.toUpperCase();
		this.supervisorName = supervisorName;
		this.supervisorEmail = supervisorEmail;
		this.supervisorPassword = supervisorPassword;
        this.projectID = -1;
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
	 * @param s The supervisor object to add.
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
	 * Retrieves the ID of the project that the supervisor is assigned to.
	 * 
	 * @return The ID of the project that the supervisor is assigned to.
	 */
    public int getProjectID() {
		return projectID;
	}

    /**
     * Sets the ID of the project that the supervisor is assigned to.
     * 
     * @param projectID The new project ID.
     */
    public void setProjectID(int projectID) {
        this.projectID=projectID;
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
 		for (Supervisor sup : Supervisor.getSupervisorsList()) {
 			if (sup.getId().equals(supervisorId)) {
 				return sup.getName();
 			}
 		}
 		return null;
 	}
 	
 	/**
 	 * Checks if a supervisor with the given ID already exists in the list of supervisors.
 	 * 
 	 * @param supervisorId The ID of the supervisor to check for duplicates
 	 * @return True if a supervisor with the given ID already exists, false otherwise
 	 */
	public static boolean duplicateSupervisorId(String supervisorId) {
		for (Supervisor s : supervisorsList) {
			if (s.getId().equalsIgnoreCase(supervisorId)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the total number of supervisors in the list.
	 * 
	 * @return The total number of supervisors in the list
	 */
	public static int getTotalNumberOfSupervisors(){
		return supervisorsList.size();
	}
	
	/**
	 * Returns a list of all supervisors.
	 * 
	 * @return A list of all supervisors
	 */
	public static ArrayList<Supervisor> getSupervisorsList(){
		return supervisorsList;
	}
	
	/**
	 * Returns the supervisor at the specified index in the list.
	 * 
	 * @param i The index of the supervisor to return
	 * @return The supervisor at the specified index in the list
	 */
	public static Supervisor getSupervisor(int i){
		return supervisorsList.get(i);
	}
	
	/**
	 * Removes the specified supervisor from the list.
	 * 
	 * @param s The supervisor to remove from the list
	 */
	public static void removeSupervisor(Supervisor s){
		supervisorsList.remove(s);
	}
	
	/**
	 * Generates a menu of supervisors with their names and IDs.
	 * 
	 * @return An ArrayList of strings that represent the student menu.
	 */
	public static ArrayList<String> getSupervisorMenu(){
		ArrayList<String> supervisorMenu = new ArrayList<String>();
		for (Supervisor s : supervisorsList) {
			supervisorMenu.add(s.getName() + " ("
					+ s.getId() + ")");
		}
		return supervisorMenu;
	}

	/**
	 * Sets the list of supervisors to the specified list.
	 * 
	 * @param s The new list of supervisors
	 */
	public static void updateSupervisorsList(ArrayList<Supervisor> s){
		supervisorsList = s;
	}

	/**
	 * Initializes the list of supervisors from the data in the supervisor data file.
	 * 
	 * @throws Throwable If there is an error reading the supervisor data file
	 */
	public static void initializeFile() throws Throwable {
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
		ArrayList<User> usersList = new ArrayList<>(supervisorsList);
		d.updateFile(FILENAME,FILEPATH,usersList);
	}

}
