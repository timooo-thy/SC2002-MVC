package models;

import java.util.ArrayList;
import java.util.HashMap;

import utilities.Database;

public class Supervisor extends User {
	
	private static final String FILEPATH = "src/data/";
	
	private static final String FILENAME = "facultylist.txt";

	private static ArrayList<Supervisor> supervisorsList = new ArrayList<Supervisor>();

	private static ArrayList<Project> supervisedProjectList = new ArrayList<Project>();
	
	private static Database d = new Database();
	
	private String supervisorId;
	
	private String supervisorName;
	
	private String supervisorEmail;
	
	private String supervisorPassword;

    private int projectID;

    public Supervisor(String supervisorId, String supervisorEmail, String supervisorPassword, String supervisorName) {
		this.supervisorId = supervisorId.toUpperCase();
		this.supervisorName = supervisorName;
		this.supervisorEmail = supervisorEmail;
		this.supervisorPassword = supervisorPassword;
        this.projectID = -1;
		addSupervisor(this);
	}

	public void setSupervisorName(String supervisorName){
		this.supervisorName = supervisorName;
	}

	public String getName(){
		return this.supervisorName;
	}
	
	public String getEmailAddress(){
		return this.supervisorEmail;
	}
	
	public void setEmailAddress(String supervisorEmail){
		this.supervisorEmail =  supervisorEmail;
	}
	
	public String getPassword(){
		return this.supervisorPassword;
	}
	
	public void setPassword(String supervisorPassword){
		this.supervisorPassword = supervisorPassword;
	}
	
	public void addSupervisor(Supervisor s) {
		supervisorsList.add(s);
		updateFile();
	}
	
	public String getId() {
		return this.supervisorId;
	}

    public int getProjectID() {
		return projectID;
	}

    /*public setProjectID(int projectID) {
        this.projectID=projectID;
    }*/
	
    public ArrayList<Project> getSupervisedProjectList(){
    	if (supervisedProjectList == null) return null;
    	return this.supervisedProjectList;
    }
    
    public ArrayList<Project> getSupervisedProjectList(String supervisorId){
    	for (Supervisor sup : Supervisor.getSupervisorsList())
    		if (sup.getId() == supervisorId)
    			return getSupervisedProjectList();
    	return null;
    }
    
    public static Supervisor getSupervisorFromId(String supervisorId) {
    	for (Supervisor sup : supervisorsList) {
    		if (sup.getId() == supervisorId) return sup;
    	}
    	return null;
    }
    
    public static Supervisor getSupervisorFromName(String supervisorName) {
    	for (Supervisor sup : supervisorsList) {
    		if (sup.getName() == supervisorName) return sup;
    	}
    	return null;
    }
    
	public static boolean duplicateSupervisorId(String supervisorId) {

		for (Supervisor s : supervisorsList) {

			if (s.getId().equalsIgnoreCase(supervisorId)) {
				return true;
			}
		}

		return false;
	}
	
	public static int getTotalNumberOfSupervisors(){
		return supervisorsList.size();
	}
	
	public static ArrayList<Supervisor> getSupervisorsList(){
		return supervisorsList;
	}
	
	public static Supervisor getSupervisor(int i){
		return supervisorsList.get(i);
	}
	
	public static void removeSupervisor(Supervisor s){
		supervisorsList.remove(s);
	}
	
	public static ArrayList<String> getSupervisorMenu(){
		
		ArrayList<String> supervisorMenu = new ArrayList<String>();
		
		for (Supervisor s : supervisorsList) {

			supervisorMenu.add(s.getName() + " ("
					+ s.getId() + ")");
		}
		
		return supervisorMenu;
	}

	public static void updateSupervisorsList(ArrayList<Supervisor> s){
		supervisorsList = s;
	}

	public static void initializeFile() throws Throwable {
		HashMap<String, String[]> map  = d.initializeFile(FILENAME, FILEPATH);
		for (String name : map.keySet()) {
        	String[] values = map.get(name);
        	new Supervisor(values[0], values[1], values[2], name); 
        }
	}

	public static void updateFile() {
		ArrayList<User> usersList = new ArrayList<>(supervisorsList);
		
		d.updateFile(FILENAME,FILEPATH,usersList);
	}

}
