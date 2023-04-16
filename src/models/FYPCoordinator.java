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
 * @version 1.0
 * @since 2023-04-16
 */
public class FYPCoordinator extends Supervisor implements DatabaseConnector {
	
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
    	super(fypcoordinatorId, fypcoordinatorName, fypcoordinatorEmail, fypcoordinatorPassword);
		this.fypcoordinatorId = fypcoordinatorId.toUpperCase();
		this.fypcoordinatorName = fypcoordinatorName;
		this.fypcoordinatorEmail = fypcoordinatorEmail;
		this.fypcoordinatorPassword = fypcoordinatorPassword;
        this.projectID = -1;
        Supervisor.updateFile();
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
	 * Returns a list of all fyp coordinators.
	 * 
	 * @return A list of all fyp coordinators
	 */
	public static ArrayList<FYPCoordinator> getFYPCoordinatorsList(){
		return fypcoordinatorsList;
	}

	/**
	 * Deregister student from the project
	 * 
	 * @param projectId Id of the project to be deregistered
	 */
	public void deregisterStudent(int projectId) {
		Project tempProj = Project.getProject(projectId);
		Supervisor tempSup = Supervisor.getSupervisorFromId(tempProj.getSupervisorId()); 
		if (Supervisor.getSupervisorFromId(tempProj.getSupervisorId()).getSupervisedProjectList().size() <= Project.MAX_PROJECT) {
			//  loop throughs project list to set them to available if supervisor has less than max projects after deregistering
			for (Project proj : Project.getProjectList() ) {
				if (proj.getSupervisorId().equals(tempProj.getSupervisorId()) && proj.getProjectStatus() == ProjectStatus_Enum.UNAVAILABLE)
					proj.setProjectStatus(ProjectStatus_Enum.AVAILABLE);
			}
		}
		//  loop through the project list and remove the project based of project id
		for(int i=0;i<tempSup.getSupervisedProjectList().size();i++) {
			if(tempSup.getSupervisedProjectList().get(i).getProjectId()==projectId) {
				tempSup.getSupervisedProjectList().remove(i);
			}
		}
		tempProj.setProjectTitle(tempProj.getOriProjectTitle());
		tempProj.setStudentId(null);
		tempProj.setStudentName("-1");
		tempProj.setStudentEmail(null);
		tempProj.setProjectStatus(ProjectStatus_Enum.AVAILABLE);
	}
	
	/**
	 * Change supervisor of a project.
	 * 
	 * @param projectId The Id of project supervisor to be changed
	 * @param replacementSupervisorName The name of supervisor to receive the project
	 */
	public void changeSupervisor(int projectId, String replacementSupervisorName) {
		Project tempProj = Project.getProject(projectId);
		Supervisor tempSup = Supervisor.getSupervisorFromName(tempProj.getSupervisorName()); 
		ArrayList<Project> supervisingProjList = Supervisor.getSupervisorFromId(tempProj.getSupervisorId()).getSupervisedProjectList();
		if (supervisingProjList.size() <= Project.MAX_PROJECT) {
			for (Project proj : Project.getProjectList() ) {
				if (proj.getSupervisorId().equals(tempProj.getSupervisorId()) && proj.getProjectStatus() == ProjectStatus_Enum.UNAVAILABLE)
					proj.setProjectStatus(ProjectStatus_Enum.AVAILABLE);
			}
		}
		
		for(int i=0;i<tempSup.getSupervisedProjectList().size();i++) {
			if(tempSup.getSupervisedProjectList().get(i).getProjectId()==projectId) {
				tempSup.getSupervisedProjectList().remove(i);
			}
		}
		
		tempProj.setSupervisorName(replacementSupervisorName);
		tempProj.setSupervisorId(Supervisor.getSupervisorNameToId(replacementSupervisorName));
		tempProj.setSupervisorEmail(Supervisor.getSupervisorNameToEmail(replacementSupervisorName));
		Project.addSupervisedProject(replacementSupervisorName, tempProj);
		ArrayList<Project> newSupervisingProjList = Supervisor.getSupervisorFromId(tempProj.getSupervisorId()).getSupervisedProjectList();
		if (newSupervisingProjList.size() >= Project.MAX_PROJECT) {
			for (Project proj : Project.getProjectList() ) {
				if (proj.getSupervisorId().equals(tempProj.getSupervisorId()) && proj.getProjectStatus() == ProjectStatus_Enum.AVAILABLE)
					proj.setProjectStatus(ProjectStatus_Enum.UNAVAILABLE);
			}
		}
	}
	
	/**
	 * Allocate project to the student.
	 * 
	 * @param projectId The id of project to be allocated
	 * @param studentId The id of student to register the project
	 */
	public void allocateProject(int projectId, String studentId) {
		Project tempProj = Project.getProject(projectId);
		tempProj.setStudentId(studentId);
		tempProj.setStudentEmail(Student.getStudentIdToEmail(studentId));
		tempProj.setStudentName(Student.getStudentIdToName(studentId));		
		tempProj.setProjectStatus(ProjectStatus_Enum.ALLOCATED);
		Project.addSupervisedProject(tempProj.getSupervisorName(),tempProj);
		if ((Supervisor.getSupervisorFromId(tempProj.getSupervisorId()).getSupervisedProjectList().size()) >= Project.MAX_PROJECT) {
			//  loop throughs project list to set them to unavailable if supervisor has max projects
			for (Project proj : Project.getProjectList() ) {
				if (proj.getSupervisorId().equals(tempProj.getSupervisorId()) && proj.getProjectStatus() == ProjectStatus_Enum.AVAILABLE)
					proj.setProjectStatus(ProjectStatus_Enum.UNAVAILABLE);
			}
		}
	}

	//  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  
	
	/**
	 * Initialises the list of fyp coordinators from the data in the fyp coordinator data file.
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
