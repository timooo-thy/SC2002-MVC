package models;

import java.util.ArrayList;
import java.util.HashMap;

import utilities.Database;

public class FYPCoordinator extends User{
	
	private static final String FILEPATH = "src/data/";
	
	private static final String FILENAME = "fypcoordinator.txt";

	private static ArrayList<FYPCoordinator> fypcoordinatorsList = new ArrayList<FYPCoordinator>();

	private static Database d = new Database();
	
	private String fypcoordinatorId;
	
	private String fypcoordinatorName;
	
	private String fypcoordinatorEmail;
	
	private String fypcoordinatorPassword;

    private int projectID;

    public FYPCoordinator(String fypcoordinatorId, String fypcoordinatorEmail, String fypcoordinatorPassword, String fypcoordinatorName) {
		this.fypcoordinatorId = fypcoordinatorId.toUpperCase();
		this.fypcoordinatorName = fypcoordinatorName;
		this.fypcoordinatorEmail = fypcoordinatorEmail;
		this.fypcoordinatorPassword = fypcoordinatorPassword;
        this.projectID = -1;
		addFYPCoordinator(this);
	}

	public void setFYPCoordinatorName(String fypcoordinatorName){
		this.fypcoordinatorName = fypcoordinatorName;
	}

	public String getName(){
		return this.fypcoordinatorName;
	}
	
	public String getEmailAddress(){
		return this.fypcoordinatorEmail;
	}
	
	public void setEmailAddress(String fypcoordinatorEmail){
		this.fypcoordinatorEmail =  fypcoordinatorEmail;
	}
	
	public String getPassword(){
		return this.fypcoordinatorPassword;
	}
	
	public void setPassword(String fypcoordinatorPassword){
		this.fypcoordinatorPassword = fypcoordinatorPassword;
	}
	
	public void addFYPCoordinator(FYPCoordinator s) {
		fypcoordinatorsList.add(s);
		//updateFile();
	}
	
	public String getId() {
		return this.fypcoordinatorId;
	}

    public int getProjectID() {
		return projectID;
	}

    /*public setProjectID(int projectID) {
        this.projectID=projectID;
    }*/
	
	public static boolean duplicateFYPCoordinatorId(String fypcoordinatorId) {

		for (FYPCoordinator s : fypcoordinatorsList) {

			if (s.getId().equalsIgnoreCase(fypcoordinatorId)) {
				return true;
			}
		}

		return false;
	}
	
	public static int getTotalNumberOfFYPCoordinators(){
		return fypcoordinatorsList.size();
	}
	
	public static ArrayList<FYPCoordinator> getFYPCoordinatorsList(){
		return fypcoordinatorsList;
	}
	
	public static FYPCoordinator getFYPCoordinator(int i){
		return fypcoordinatorsList.get(i);
	}
	
	public static void removeFYPCoordinator(FYPCoordinator s){
		fypcoordinatorsList.remove(s);
	}
	
	public static ArrayList<String> getFYPCoordinatorMenu(){
		
		ArrayList<String> fypcoordinatorMenu = new ArrayList<String>();
		
		for (FYPCoordinator s : fypcoordinatorsList) {

			fypcoordinatorMenu.add(s.getName() + " ("
					+ s.getId() + ")");
		}
		
		return fypcoordinatorMenu;
	}

	public static void updateFYPCoordinatorsList(ArrayList<FYPCoordinator> s){
		fypcoordinatorsList = s;
	}

	public static void initializeFile() throws Throwable {
		HashMap<String, String[]> map  = d.initializeFile(FILENAME, FILEPATH);
		for (String name : map.keySet()) {
        	String[] values = map.get(name);
        	new FYPCoordinator(values[0], values[1], values[2], name); 
        }
	}

	public static void updateFile() {
		ArrayList<User> usersList = new ArrayList<>(fypcoordinatorsList);
		
		d.updateFile(FILENAME,FILEPATH,usersList);
	}

}
