package models;

import java.util.ArrayList;
import java.util.HashMap;
import utilities.Database;

/**
 * This class represents a FYP Coordinator who can supervise everything in the system.
 * It extends the User class and methods to oversee projects and accept requests.
 * It also has methods to retrieve and update information about the fyp coordinators from the database.
 * @author Timothy Lee
 * @author Justin Wong
 * @author Jun Hao
 * @author Lee Cheng Yao
 * @author Abhishekh
 */
public class FYPCoordinator extends User {
	
	/**
	 * File path of the file to store the list of fyp coordinators.
	 */
	private static final String FILEPATH = "src/data/";
	
	/**
	 * File name of the file to store the list of fyp coordinators.
	 */
	private static final String FILENAME = "fypcoordinator.txt";

	/**
	 * List of all the fyp coordinators in the system.
	 */
	private static ArrayList<FYPCoordinator> fypcoordinatorsList = new ArrayList<FYPCoordinator>();

	/**
	 * Database object to perform database operations.
	 */
	private static Database d = new Database();
	
	/**
	 * FYP Coordinator's ID.
	 */
	private String fypcoordinatorId;
	
	/**
	 * FYP Coordinator's Name.
	 */
	private String fypcoordinatorName;
	
	/**
	 * FYP Coordinator's Email.
	 */
	private String fypcoordinatorEmail;
	
	/**
	 * FYP Coordinator's Password.
	 */
	private String fypcoordinatorPassword;

	/**
	 * FYP Coordinator's Project ID.
	 */
    private int projectID;

	//  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  
    
    /**
     * Creates a new FYP Coordinator object with the given attributes, and adds it to the list of fyp coordinators.
     * 
     * @param fypcoordinatorId The ID of the fyp coordinator.
     * @param fypcoordinatorEmail The email address of the fyp coordinator.
     * @param fypcoordinatorPassword The password of the fyp coordinator.
     * @param fypcoordinatorName The name of the fyp coordinator.
     */
    public FYPCoordinator(String fypcoordinatorId, String fypcoordinatorEmail, String fypcoordinatorPassword, String fypcoordinatorName) {
		this.fypcoordinatorId = fypcoordinatorId.toUpperCase();
		this.fypcoordinatorName = fypcoordinatorName;
		this.fypcoordinatorEmail = fypcoordinatorEmail;
		this.fypcoordinatorPassword = fypcoordinatorPassword;
        this.projectID = -1;
		addFYPCoordinator(this);
	}

    /**
     * Sets the name of the fyp coordinator.
     * 
     * @param fypcoordinatorName The new name of the fyp coordinator.
     */
	public void setFYPCoordinatorName(String fypcoordinatorName){
		this.fypcoordinatorName = fypcoordinatorName;
	}

	/**
	 * Retrieves the name of the fyp coordinator.
	 * 
	 * @return The name of the fyp coordinator.
	 */
	public String getName(){
		return this.fypcoordinatorName;
	}
	
	/**
	 * Retrieves the email address of the fyp coordinator.
	 * @return The email address of the fyp coordinator.
	 */
	public String getEmailAddress(){
		return this.fypcoordinatorEmail;
	}
	
	/**
	 * Sets the email address of the fyp coordinator.
	 * 
	 * @param fypcoordinatorEmail The new email address of the fyp coordinator.
	 */
	public void setEmailAddress(String fypcoordinatorEmail){
		this.fypcoordinatorEmail =  fypcoordinatorEmail;
	}
	
	/**
	 * Retrieves the password of the fyp coordinator.
	 * 
	 * @return The password of the fyp coordinator.
	 */
	public String getPassword(){
		return this.fypcoordinatorPassword;
	}
	
	/**
	 * Sets the password of the fyp coordinator.
	 * 
	 * @param fypcoordinatorPassword The new password of the fyp coordinator.
	 */
	public void setPassword(String fypcoordinatorPassword){
		this.fypcoordinatorPassword = fypcoordinatorPassword;
	}
	
	/**
	 * Adds a new fyp coordinator to the list of fyp coordinators, and updates the file that stores the list of fyp coordinators.
	 * 
	 * @param s The fyp coordinator object to add.
	 */
	public void addFYPCoordinator(FYPCoordinator s) {
		fypcoordinatorsList.add(s);
		updateFile();
	}
	
	/**
	 * Retrieves the ID of the fyp coordinator.
	 * 
	 * @return The ID of the fyp coordinator.
	 */
	public String getId() {
		return this.fypcoordinatorId;
	}

	/**
	 * Retrieves the ID of the project that the fyp coordinator is assigned to.
	 * 
	 * @return The ID of the project that the fyp coordinator is assigned to.
	 */
    public int getProjectID() {
		return projectID;
	}

    /**
     * Sets the ID of the project that the fyp coordinator is assigned to.
     * 
     * @param projectID The new project ID.
     */
    public void setProjectID(int projectID) {
        this.projectID=projectID;
    }
	
    /**
 	 * Checks if a fyp coordinator with the given ID already exists in the list of fyp coordinators.
 	 * 
 	 * @param fypcoordinatorId The ID of the fyp coordinator to check for duplicates
 	 * @return True if a fyp coordinator with the given ID already exists, false otherwise
 	 */
	public static boolean duplicateFYPCoordinatorId(String fypcoordinatorId) {
		// loops through the list and return the object if id is matched
		for (FYPCoordinator s : fypcoordinatorsList) {
			if (s.getId().equalsIgnoreCase(fypcoordinatorId)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the total number of fyp coordinators in the list.
	 * 
	 * @return The total number of fyp coordinators in the list
	 */
	public static int getTotalNumberOfFYPCoordinators(){
		return fypcoordinatorsList.size();
	}
	
	/**
	 * Returns a list of all fyp coordinators.
	 * 
	 * @return A list of all fyp coordinators
	 */
	public static ArrayList<FYPCoordinator> getFYPCoordinatorsList(){
		return fypcoordinatorsList;
	}
	
	/**
	 * Returns the fyp coordinator at the specified index in the list.
	 * 
	 * @param i The index of the fyp coordinator to return
	 * @return The fyp coordinator at the specified index in the list
	 */
	public static FYPCoordinator getFYPCoordinator(int i){
		return fypcoordinatorsList.get(i);
	}
	
	/**
	 * Removes the specified fyp coordinator from the list.
	 * 
	 * @param s The fyp coordinator to remove from the list
	 */
	public static void removeFYPCoordinator(FYPCoordinator s){
		fypcoordinatorsList.remove(s);
	}
	
	/**
	 * Generates a menu of fyp coordinators with their names and IDs.
	 * 
	 * @return An ArrayList of strings that represent the fyp coordinators menu.
	 */
	public static ArrayList<String> getFYPCoordinatorMenu(){
		ArrayList<String> fypcoordinatorMenu = new ArrayList<String>();
		// loops through the list and add it into the menu
		for (FYPCoordinator s : fypcoordinatorsList) {
			fypcoordinatorMenu.add(s.getName() + " ("
					+ s.getId() + ")");
		}
		return fypcoordinatorMenu;
	}

	/**
	 * Sets the list of fyp coordinators to the specified list.
	 * 
	 * @param s The new list of fyp coordinators
	 */
	public static void updateFYPCoordinatorsList(ArrayList<FYPCoordinator> s){
		fypcoordinatorsList = s;
	}

	//  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  
	
	/**
	 * Initializes the list of fyp coordinators from the data in the fyp coordinator data file.
	 * 
	 * @throws Throwable If there is an error reading the fyp coordinator data file
	 */
	public static void initializeFile() throws Throwable {
		// uses hashmap to create fyp coordinator object
		HashMap<String, String[]> map  = d.initializeFile(FILENAME, FILEPATH);
		for (String name : map.keySet()) {
        	String[] values = map.get(name);
        	new FYPCoordinator(values[0], values[1], values[2], name); 
        }
	}
	/**
	 * Updates the fyp coordinator data file with the current list of fyp coordinators.
	 */
	public static void updateFile() {
		// upcast to user arraylist before updating the file
		ArrayList<User> usersList = new ArrayList<>(fypcoordinatorsList);
		d.updateFile(FILENAME,FILEPATH,usersList);
	}

}
