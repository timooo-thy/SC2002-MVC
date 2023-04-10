package models;

import java.util.ArrayList;
import java.util.HashMap;
import utilities.Database;
import models.Supervisor;

public class Project {
	
	private static final String FILEPATH = "src/data/";
	
	private static final String FILENAME = "rollover project.txt";
	
	private static ArrayList<Project> projectList = new ArrayList<Project>();
	
	private static Database d = new Database();
	
	private int projectId;
	
	private String supervisorId;
	
	private String supervisorName;
	
	private String supervisorEmail;

	//private String tempSupervisorId;
	
	//private String tempSupervisorEmail;
	
	private String studentId;
	
	private String studentName;
	
	private String studentEmail;

	//private String tempStudentId;
	
	//private String tempStudentEmail;
	
	private String projectTitle;
	
	//private String tempProjectTitle;
	
	private  ProjectStatus_Enum projectStatus;
	
	//private String newSupervisorName;
	
	//private  String newProjectTitle;
		
	////////////////////////////////////////////////////////////////////////////////////////

	//Constructor 
	public Project() {}
	
	public Project(String supervisorName, String projectTitle, ProjectStatus_Enum projStatus) {
		this.supervisorId = getSupervisorNameToId(supervisorName).toUpperCase();
		this.supervisorName = supervisorName;
		this.supervisorEmail = getSupervisorNameToEmail(supervisorName);
        this.projectId = projectList.size()+1;
        this.projectTitle = projectTitle;
        this.projectStatus = projStatus;
		addProject(this);
	}
		
	////////////////////////////////////////////////////////////////////////////////////////
	
	public static void addProject(Project project){
		projectList.add(project);
		updateProjectFile();
	}
	
	public static ArrayList<Project> getProjectList(){
		return projectList;
	}
	
	//project id start from 1
	public static Project getProject(int i){
		return projectList.get(i-1);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////

	// Get Supervisor Id and Email based on Name
	public String getSupervisorNameToEmail(String supervisorName){
		for (int i = 0; i < Supervisor.getSupervisorsList().size(); i++) {
			if ((Supervisor.getSupervisorsList().get(i).getName()).equals(supervisorName)) {
				return Supervisor.getSupervisorsList().get(i).getEmailAddress();
			}
		}
		return null;
	}
	
	public String getSupervisorNameToId(String supervisorName) {
		for (int i = 0; i < Supervisor.getSupervisorsList().size(); i++) {
			if ((Supervisor.getSupervisorsList().get(i).getName()).equals(supervisorName)) {
				return Supervisor.getSupervisorsList().get(i).getId();
			}
		}
		return null;
	}
	
	public String getSupervisorIdToEmail(String supervisorId){
		for (int i = 0; i < Supervisor.getSupervisorsList().size(); i++) {
			if ((Supervisor.getSupervisorsList().get(i).getId()).equals(supervisorId)) {
				return Supervisor.getSupervisorsList().get(i).getEmailAddress();
			}
		}
		return null;
	}
	
	public String getSupervisorIdToName(String supervisorId) {
		for (int i = 0; i < Supervisor.getSupervisorsList().size(); i++) {
			if ((Supervisor.getSupervisorsList().get(i).getId()).equals(supervisorId)) {
				return Supervisor.getSupervisorsList().get(i).getName();
			}
		}
		return null;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////
	
	// Get Student Id and Email based on Name
	public String getStudentNameToEmail(String studentName){
		for (int i = 0; i < Student.getStudentsList().size(); i++) {
			if ((Student.getStudentsList().get(i).getName()).equals(studentName)) {
				return Student.getStudentsList().get(i).getEmailAddress();
			}
		}
		return null;
	}
	
	public String getStudentNameToId(String studentName) {
		for (int i = 0; i < Student.getStudentsList().size(); i++) {
			if ((Student.getStudentsList().get(i).getName()).equals(studentName)) {
				return Student.getStudentsList().get(i).getId();
			}
		}
		return null;
	}
	
	public String getStudentIdToEmail(String studentId){
		for (int i = 0; i < Student.getStudentsList().size(); i++) {
			if ((Student.getStudentsList().get(i).getId()).equals(studentId)) {
				return Student.getStudentsList().get(i).getEmailAddress();
			}
		}
		return null;
	}
	
	public String getStudentIdToName(String studentId) {
		for (int i = 0; i < Student.getStudentsList().size(); i++) {
			if ((Student.getStudentsList().get(i).getId()).equals(studentId)) {
				return Student.getStudentsList().get(i).getName();
			}
		}
		return null;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////

	public int getProjectId() {
		return this.projectId;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////

	public String getSupervisorId() {
		return this.supervisorId;
	}

	public void setSupervisorId(String supervisorId) {
		this.supervisorId = supervisorId;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////

	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}
	
	public String getSupervisorName() {
		return this.supervisorName;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////

	public String getSupervisorEmail() {
		return this.supervisorEmail;
	}

	public void setSupervisorEmail(String supervisorEmail) {
		this.supervisorEmail = supervisorEmail;
	}

	///////////////////////////////////////////////////////////////////////////////////////

	public String getStudentId() {
		return this.studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	public String getStudentName() {
		return this.studentName;
	}

	///////////////////////////////////////////////////////////////////////////////////////

	public String getStudentEmail() {
		return this.studentEmail;
	}

	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}

	///////////////////////////////////////////////////////////////////////////////////////

	public String getProjectTitle() {
		return this.projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////

	public ProjectStatus_Enum getProjectStatus() {
		return this.projectStatus;
	}

	public void setProjectStatus(ProjectStatus_Enum projectStatus) {
		this.projectStatus = projectStatus;
	}

	///////////////////////////////////////////////////////////////////////////////////////
	
	public static void updateProjectList(ArrayList<Project> p){
		projectList = p;
	}

	public static void initializeProjectFile() throws Throwable {
		HashMap<Integer, Object[]> map  = d.initializeProjectFile(FILENAME, FILEPATH);
		for (int projId : map.keySet()) {
        	Object[] values = map.get(projId);       	
        		new Project((String)values[1],(String) values[2],(ProjectStatus_Enum) values[4]); 
        		if ((ProjectStatus_Enum)values[4] != ProjectStatus_Enum.ALLOCATED) {
        			
        		}
        }
	}

	public static void updateProjectFile() {
		d.updateProjectFile(FILENAME,FILEPATH,projectList);
	}

	// For ProjectFYPCoordinator Interface //
	
	public void changeSupervisor(int projectId, String replacementSupervisorId) {
		projectList.get(projectId-1).setSupervisorId(replacementSupervisorId);;
		projectList.get(projectId-1).setSupervisorName(getSupervisorIdToName(replacementSupervisorId));
		projectList.get(projectId-1).setSupervisorEmail(getSupervisorIdToEmail(replacementSupervisorId));
		System.out.print("Supervisor has been changed successfully to...");
		System.out.print("Supervisor Id:" + getSupervisorId());
		System.out.print("Supervisor Email:" + getSupervisorEmail());
		// do something to supervisor
	}
	
	public void allocateProject(int projectId, String studentId) {
		projectList.get(projectId-1).setStudentId(studentId);
		projectList.get(projectId-1).setStudentEmail(getStudentIdToEmail(studentId));
		projectList.get(projectId-1).setStudentName(getStudentIdToName(studentId));		
		projectList.get(projectId-1).setProjectStatus(ProjectStatus_Enum.ALLOCATED);
		System.out.println("Student has been changed successfully to...");
		System.out.println("Student Id:" + projectList.get(projectId).getStudentId());
		System.out.println("Student Email:" + projectList.get(projectId).getStudentEmail());
		// do something to supervisor
	}
	
	public void deregisterStudent(int projectId) {
		Project tempProj = projectList.get(projectId-1);
		Supervisor tempSup = Supervisor.getSuperVisor(tempProj.getSupervisorId()); 
		tempProj.setStudentId(null);
		tempProj.setStudentName(null);
		tempProj.setStudentEmail(null);
		tempProj.setProjectStatus(ProjectStatus_Enum.AVAILABLE);
		// do something in supervisor
		for (Project proj : tempSup.getSupervisedProjectList()) {
			//if (proj.getProjectId() == projectId) tempSup.getS
		}
			
	}
	
	public static void createProject() {
		
	}
	
	public static void changeProjectTitle(int projectId, String tempProjectTitle) {
		Project tempProject = Project.getProject(projectId);
		tempProject.setProjectTitle(tempProjectTitle);
		System.out.println("Project Title has been changed successfully to...");
		System.out.println("Project Title:" + tempProject.getProjectTitle());
	}
	
	
	public void selectProject() {
		
	}
	
}

