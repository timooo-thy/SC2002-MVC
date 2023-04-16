package models;

import java.util.ArrayList;
import java.util.HashMap;
import utilities.Database;

/**
 * This class represents a Project in the system.
 * It contains a list of supervised projects and methods to assign them to students or transfer to supervisors.
 * It also has methods to retrieve and update information about the projects from the database.
 * @author Timothy Lee
 * @author Justin Wong
 * @author Jun Hao
 * @author Lee Cheng Yao
 * @author Abhishekh
 * @version 1.0
 * @since 2023-04-16
 */
public class Project {
	
	/**
	 * File path of the file to store the list of projects.
	 */
	private static final String FILEPATH = "src/data/";
	
	/**
	 * File name of the file to store the list of projects.
	 */
	private static final String FILENAME = "projectList.txt";
	
	/**
	 * List of all the projects in the system.
	 */
	private static ArrayList<Project> projectList = new ArrayList<Project>();
	
	/**
	 * Database object to perform database operations.
	 */
	private static Database d = new Database();
	
	/**
	 * Project's ID.
	 */
	private int projectId;
	
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
	 * Student's ID.
	 */
	private String studentId;
	
	/**
	 * Student's Name
	 */
	private String studentName = "-1";
	
	/**
	 * Student's Email
	 */
	private String studentEmail;
	
	/**
	 * Project's Title
	 */
	private String projectTitle = "-1";
	
	/**
	 * Project's Original Title
	 */
	private String oriProjectTitle;
	
	/**
	 * Project's Status
	 */
	private  ProjectStatus_Enum projectStatus;
	
	/**
	 * Maximum size for list of supervised projects
	 */
	public static final int MAX_PROJECT = 2;
		
	//  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  

	/**
     * Creates a new Project object with the given attributes, and adds it to the list of projects.
     * 
     * @param supervisorName The name of the supervisor.
     * @param projectTitle The title of the project.
     * @param projStatus The status of the project.
     */
	public Project(String supervisorName, String projectTitle, ProjectStatus_Enum projStatus) {
		this.supervisorId = Supervisor.getSupervisorNameToId(supervisorName).toUpperCase();
		this.supervisorName = supervisorName;
		this.supervisorEmail = Supervisor.getSupervisorNameToEmail(supervisorName);
        this.projectId = projectList.size()+1;
        this.oriProjectTitle = projectTitle;
        if (this.projectTitle.equals("-1") || this.projectTitle.equals(this.oriProjectTitle)) this.projectTitle = projectTitle;
        this.projectStatus = projStatus;
		addProject(this);
	}

	/**
     * Creates a new Project object with the given attributes, and adds it to the list of projects.
     * 
     * @param supervisorName The name of the supervisor.
     * @param oriProjectTitle The original title of the project. 
     * @param projectTitle The title of the project.
	 * @param studentName The name of the student
     * @param projStatus The status of the project.
     */
	public Project(String supervisorName, String oriProjectTitle, String projectTitle, String studentName, ProjectStatus_Enum projStatus) {
		this.supervisorId = Supervisor.getSupervisorNameToId(supervisorName).toUpperCase();
		this.supervisorName = supervisorName;
		this.supervisorEmail = Supervisor.getSupervisorNameToEmail(supervisorName);
		this.studentName = studentName;
		this.studentId = Student.getStudentNameToId(studentName).toUpperCase();
		this.studentEmail = Student.getStudentNameToEmail(studentName);
        this.projectId = projectList.size()+1;
        this.projectTitle = projectTitle;
        this.oriProjectTitle = oriProjectTitle;
        this.projectStatus = projStatus;
		addProject(this);
	}
	
	/**
	 * Adds a new project to the list of projects.
	 * 
	 * @param project The project object to add.
	 */
	public static void addProject(Project project){
		projectList.add(project);
	}

	/**
     * Returns a list of all projects.
     * 
     * @return A list of all projects
     */
	public static ArrayList<Project> getProjectList(){
		return projectList;
	}
	
	/**
	 * Returns the project at the specified index in the list.
	 * 
	 * @param i The index of the project to return
	 * @return The project at the specified index in the list
	 */
	public static Project getProject(int i){
		return projectList.get(i-1);
	}

	/**
	 * Retrieves the ID of the project.
	 * 
	 * @return The ID of the project.
	 */
	public int getProjectId() {
		return this.projectId;
	}
	
	/**
	 * Retrieves the ID of the supervisor.
	 * 
	 * @return The ID of the supervisor.
	 */
	public String getSupervisorId() {
		return this.supervisorId;
	}

	/**
     * Sets the ID of the supervisor.
     * 
     * @param supervisorId The new ID of the supervisor.
     */
	public void setSupervisorId(String supervisorId) {
		this.supervisorId = supervisorId;
	}

	/**
     * Sets the name of the supervisor.
     * 
     * @param supervisorName The new name of the supervisor.
     */
	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}
	
	/**
	 * Retrieves the name of the supervisor.
	 * 
	 * @return The name of the supervisor.
	 */
	public String getSupervisorName() {
		return this.supervisorName;
	}

	/**
	 * Retrieves the email address of the supervisor.
	 * 
	 * @return The email address of the supervisor.
	 */
	public String getSupervisorEmail() {
		return this.supervisorEmail;
	}

	/**
	 * Sets the email address of the supervisor.
	 * 
	 * @param supervisorEmail The new email address of the supervisor.
	 */
	public void setSupervisorEmail(String supervisorEmail) {
		this.supervisorEmail = supervisorEmail;
	}

	/**
	 * Retrieves the ID of the student.
	 * 
	 * @return The ID of the student.
	 */
	public String getStudentId() {
		return this.studentId;
	}

	/**
     * Sets the ID of the student.
     * 
     * @param studentId The new ID of the student.
     */
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	/**
     * Sets the name of the student.
     * 
     * @param studentName The new name of the student.
     */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	/**
	 * Retrieves the name of the student.
	 * 
	 * @return The name of the student.
	 */
	public String getStudentName() {
		return this.studentName;
	}

	/**
	 * Retrieves the email address of the student.
	 * 
	 * @return The email address of the student.
	 */
	public String getStudentEmail() {
		return this.studentEmail;
	}

	/**
	 * Sets the email address of the student.
	 * 
	 * @param studentEmail The new email address of the student.
	 */
	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}

	/**
	 * Retrieves the title of the project.
	 * 
	 * @return The title of the project.
	 */
	public String getProjectTitle() {
		return this.projectTitle;
	}

	/**
     * Sets the title of the project.
     * 
     * @param projectTitle The new title of the project.
     */
	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	/**
	 * Retrieves the original title of the project.
	 * 
	 * @return The original title of the project.
	 */
	public String getOriProjectTitle() {
		return this.oriProjectTitle;
	}

	/**
     * Sets the original title of the project.
     * 
     * @param oriProjectTitle The new original title of the project.
     */
	public void setOriProjectTitle(String oriProjectTitle) {
		this.oriProjectTitle = oriProjectTitle;
	}

	/**
	 * Retrieves the status of the project.
	 * 
	 * @return The status  of the project.
	 */
	public ProjectStatus_Enum getProjectStatus() {
		return this.projectStatus;
	}

	/**
     * Sets the title of the project.
     * 
     * @param projectStatus The new title of the project.
     */
	public void setProjectStatus(ProjectStatus_Enum projectStatus) {
		this.projectStatus = projectStatus;
	}
	
	/**
	 * Adds a project to the list of supervised project.
	 * 
	 * @param supervisorName The name of supervisor
	 * @param p The project object to add
	 */
	public static void addSupervisedProject(String supervisorName,Project p) {
		Supervisor.getSupervisorFromName(supervisorName).getSupervisedProjectList().add(p);
	}
	
	/**
	 * Change title of the project
	 * 
	 * @param projectId Id of the project to be modified
	 * @param tempProjectTitle New title of the project to be updated 
	 */
	public static void changeProjectTitle(int projectId, String tempProjectTitle) {
		Project tempProj = Project.getProject(projectId);
		tempProj.setProjectTitle(tempProjectTitle);
	}
	
	/**
	 * Select the project to register
	 * 
	 * @param projectId Id of the project to be registered
	 */
	public static void selectProject(int projectId) {
		Project tempProj = Project.getProject(projectId);
		//  change project status to reserved
		tempProj.setProjectStatus(ProjectStatus_Enum.RESERVED);
		int count = 0;
		//  loop through project list and count number of reserved projects
		for (Project proj : Project.getProjectList() ) {
			if (proj.getSupervisorId().equals(tempProj.getSupervisorId()) && proj.getProjectStatus() == ProjectStatus_Enum.RESERVED)
				count++;
		}
		if (count == MAX_PROJECT) {
			//  loop through project list and set project status to unavailable if supervisor has max projects 
			for (Project proj : Project.getProjectList() ) {
				if (proj.getSupervisorId().equals(tempProj.getSupervisorId()) && proj.getProjectStatus() == ProjectStatus_Enum.AVAILABLE)
						proj.setProjectStatus(ProjectStatus_Enum.UNAVAILABLE);
			}
		}
	}
	
	//  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  //  
	
	/**
	 * Initializes the list of projects from the data in the project data file.
	 * 
	 * @throws Throwable If there is an error reading the project data file
	 */
	public static void initializeProjectFile() throws Throwable {
		//  hashmap to create project objects 
		HashMap<Integer, Object[]> map  = d.initializeProjectFile(FILENAME, FILEPATH);
		for (int projId : map.keySet()) {
        	Object[] values = map.get(projId);       	
        		//  if status is allocated, add project to supervisor and student
        		if ((ProjectStatus_Enum)values[4] == ProjectStatus_Enum.ALLOCATED) {
					addSupervisedProject((String)values[0], new Project((String)values[0], (String)values[1], (String)values[2], (String)values[3], (ProjectStatus_Enum)values[4]));  
					
					Student.getStudentFromName((String)values[3]).setProjectID(projId); //  set student project id if allocated
        		}
        		else new Project((String)values[0],(String) values[1],(ProjectStatus_Enum) values[4]); 
        }
	}

	/**
	 * Updates the project data file with the current list of projects.
	 */
	public static void updateProjectFile() {
		d.updateProjectFile(FILENAME,FILEPATH,projectList);
	}
}
