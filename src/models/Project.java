package models;

import java.util.ArrayList;
import java.util.HashMap;
import utilities.Database;
import java.util.Scanner;
//import models.Supervisor;

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

	private static ArrayList<Supervisor> supervisorList; 

	private static ArrayList<Student> studentList;
		
	Scanner sc = new Scanner(System.in);

	////////////////////////////////////////////////////////////////////////////////////////

	//Constructor 
	public Project() {}
	
	public Project(String supervisorName, String projectTitle) {
		this.supervisorId = getSupervisorNameToId(supervisorName).toUpperCase();
		this.supervisorName = supervisorName;
		this.supervisorEmail = getSupervisorNameToEmail(supervisorName);
        this.projectId = projectList.size()+1;
        this.projectTitle = projectTitle;
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
		for (int i = 0; i < supervisorList.size(); i++) {
			if ((supervisorList.get(i).getName()).equals(supervisorName)) return supervisorList.get(i).getEmailAddress();
		}
		return null;
	}
	
	public String getSupervisorNameToId(String supervisorName) {
		for (int i = 0; i < supervisorList.size(); i++) {
			if ((supervisorList.get(i).getName()).equals(supervisorName)) return supervisorList.get(i).getId();
		}
		return null;
	}
	
	public String getSupervisorIdToEmail(String supervisorId){
		for (int i = 0; i < supervisorList.size(); i++) {
			if ((supervisorList.get(i).getId()).equals(supervisorId)) return supervisorList.get(i).getEmailAddress();
		}
		return null;
	}
	
	public String getSupervisorIdToName(String supervisorId) {
		for (int i = 0; i < supervisorList.size(); i++) {
			if ((supervisorList.get(i).getId()).equals(supervisorId)) return supervisorList.get(i).getName();
		}
		return null;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////
	
	// Get Student Id and Email based on Name
	public String getStudentNameToEmail(String studentName){
		for (int i = 0; i < studentList.size(); i++) {
			if ((studentList.get(i).getName()).equals(studentName)) return studentList.get(i).getEmailAddress();
		}
		return null;
	}
	
	public String getStudentNameToId(String studentName) {
		for (int i = 0; i < studentList.size(); i++) {
		if ((studentList.get(i).getName()).equals(studentName)) return studentList.get(i).getId();
		}
		return null;
	}
	
	public String getStudentIdToEmail(String studentId){
		for (int i = 0; i < studentList.size(); i++) {
			if ((studentList.get(i).getId()).equals(studentId)) return studentList.get(i).getEmailAddress();
		}
		return null;
	}
	
	public String getStudentIdToName(String studentId) {
		for (int i = 0; i < studentList.size(); i++) {
		if ((studentList.get(i).getId()).equals(studentId)) return studentList.get(i).getName();
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
		HashMap<String, String> map  = d.initializeProjectFile(FILENAME, FILEPATH);
		for (String name : map.keySet()) {
        	String title = map.get(name);
        	new Project(name, title); 
        }
	}

	public static void updateProjectFile() {
		ArrayList<Project> newProjectList = new ArrayList<>(projectList);
		
		d.updateProjectFile(FILENAME,FILEPATH,newProjectList);
	}

	// For ProjectFYPCoordinator Interface //
	
	public void changeSupervisor(int projectId, String replacementSupervisorId) {
		projectList.get(projectId).setSupervisorId(replacementSupervisorId);;
		projectList.get(projectId).setSupervisorName(getSupervisorIdToName(replacementSupervisorId));
		projectList.get(projectId).setSupervisorEmail(getSupervisorIdToEmail(replacementSupervisorId));
		System.out.print("Supervisor has been changed successfully to...");
		System.out.print("Supervisor Id:" + getSupervisorId());
		System.out.print("Supervisor Email:" + getSupervisorEmail());
		// do something to supervisor
	}
	
	public void allocateProject(int projectId, String studentId) {
		projectList.get(projectId).setStudentId(studentId);
		projectList.get(projectId).setStudentEmail(getStudentIdToEmail(studentId));
		projectList.get(projectId).setStudentName(getStudentIdToName(studentId));		
		projectList.get(projectId).setProjectStatus(ProjectStatus_Enum.ALLOCATED);
		System.out.println("Student has been changed successfully to...");
		System.out.println("Student Id:" + projectList.get(projectId).getStudentId());
		System.out.println("Student Email:" + projectList.get(projectId).getStudentEmail());
		// do something to supervisor
	}
	
	public void deregisterStudent(int projectId/*, String studentId*/) {
		projectList.get(projectId).setStudentId(null);
		projectList.get(projectId).setStudentName(null);
		projectList.get(projectId).setStudentEmail(null);
		projectList.get(projectId).setProjectStatus(ProjectStatus_Enum.AVAILABLE);
		// do something in supervisor
	}
	
//	public void viewAllProject() {
//		for (int i = 0; i < projectList.size(); i++) {
//			System.out.printf("supervisor name : %s \n",projectList.get(i).getSupervisorName());
//			System.out.printf("supervisor Id : %s \n",projectList.get(i).getSupervisorId()); 
//			System.out.printf("supervisor email : %s \n",projectList.get(i).getSupervisorEmail());
//			if (projectList.get(i).getProjectStatus() == projectStatus_Enum.ALLOCATED) {
//				System.out.printf("student name : %s \n",projectList.get(i).getStudentName());
//				System.out.printf("student Id : %s \n",projectList.get(i).getStudentId());
//				System.out.printf("student Email : %s \n",projectList.get(i).getStudentEmail());
//			}
//			System.out.printf("project Id : %d \n",projectList.get(i).getProjectId());
//			System.out.printf("Project title : %s \n",projectList.get(i).getProjectTitle());
//			System.out.printf("project status : %s \n",projectList.get(i).getProjectStatus());
//			System.out.println();
//		}
//	}
	
//	public voId getFilteredProjectDetails() {
//		// filter based on sup name, availability, 
//	}
	
	///////////////////////////////////////////////////////////////////////////////////////

	// For ProjectSupervisor Interface //

//	public static void getProjectDetails(String supervisorId) {
//		for (int i = 0; i < projectList.size(); i++) {
//			if ((projectList.get(i).getSupervisorId()).equals(supervisorId)) {
//				System.out.printf("supervisor Id : %s \n",projectList.get(i).getSupervisorId()); 
//				System.out.printf("supervisor email : %s \n",projectList.get(i).getSupervisorEmail());
//				System.out.printf("project status : %s \n",projectList.get(i).getProjectStatus());
//				if (projectList.get(i).getProjectStatus() == ProjectStatus_Enum.ALLOCATED) {
//					System.out.printf("student name : %s \n",projectList.get(i).getStudentName());
//					System.out.printf("student Id : %s \n",projectList.get(i).getStudentId());
//					System.out.printf("student Email : %s \n",projectList.get(i).getStudentEmail());
//					System.out.printf("project Id : %d \n",projectList.get(i).getProjectId());
//					System.out.printf("Project title : %s \n",projectList.get(i).getProjectTitle());
//				}
//				else {
//					System.out.printf("project Id : %d \n",projectList.get(i).getProjectId());
//					System.out.printf("Project title : %s \n",projectList.get(i).getProjectTitle());
//				}
//				System.out.println();
//			}
//		}
//	}
	
	public static void createProject() {
		
	}
	
	public static void changeProjectTitle(int projectId, String tempProjectTitle) {
		Project tempProject = Project.getProject(projectId);
		tempProject.setProjectTitle(tempProjectTitle);
		System.out.println("Project Title has been changed successfully to...");
		System.out.println("Project Title:" + tempProject.getProjectTitle());
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////

	// For ProjectStudentPreAllocation Interface //
//	public void projectInfo() {
//		for (int i = 0; i < projectList.size(); i++) {
//			if (projectList.get(i).projectStatus == projectStatus_Enum.AVAILABLE) {
//				System.out.println("Project Id:" + projectList.get(i).getProjectId());
//				System.out.println("Project Title:" + projectList.get(i).getProjectTitle());
//				System.out.println("Supervisor Id:" + projectList.get(i).getSupervisorName());
//				System.out.println("Supervisor Email:" + projectList.get(i).getSupervisorEmail());
//			}
//		}
//	}
	
	public void selectProject() {
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////

	// For ProjectStudentPostAllocation Interface //
	
//	public void projectDetails() {
//		System.out.println("Project Id:" + getProjectId());
//		System.out.println("Project Title:" + getProjectTitle());
//		System.out.println("Supervisor Name:" + getSupervisorName());
//		System.out.println("Supervisor Id:" + getSupervisorId());
//		System.out.println("Supervisor Email:" + getSupervisorEmail());
//		System.out.println("Student Id:" + getStudentId());
//		System.out.println("Student Email:" + getStudentEmail());
//		System.out.println();
//	}

}

