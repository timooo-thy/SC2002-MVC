  package models;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.HashMap;
import utilities.Database;
import models.Supervisor;

public class Project {
	
	private static final String FILEPATH = "src/data/";
	
	private static final String FILENAME = "projectList.txt";
	
	private static ArrayList<Project> projectList = new ArrayList<Project>();
	
	private static Database d = new Database();
	
	private int projectId;
	
	private String supervisorId;
	
	private String supervisorName;
	
	private String supervisorEmail;
	
	private String studentId;
	
	private String studentName = "-1";
	
	private String studentEmail;
	
	private String projectTitle = "-1";
	
	private String oriProjectTitle;
	
	private  ProjectStatus_Enum projectStatus;
	
	private static final int MAX_PROJECT = 2;
		
	////////////////////////////////////////////////////////////////////////////////////////

	//Constructor 
	public Project() {}
	
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

	////////////////////////////////////////////////////////////////////////////////////////
	
	public static void addProject(Project project){
		projectList.add(project);
		//updateProjectFile();
	}

	public static ArrayList<Project> getProjectList(){
		return projectList;
	}
	
	//project id start from 1
	public static Project getProject(int i){
		return projectList.get(i-1);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////

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

	public String getOriProjectTitle() {
		return this.oriProjectTitle;
	}

	public void setOriProjectTitle(String oriProjectTitle) {
		this.oriProjectTitle = oriProjectTitle;
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

	// value 0 = sup name, value 1 = title, value 2 = student
	public static void initializeProjectFile() throws Throwable {
		HashMap<Integer, Object[]> map  = d.initializeProjectFile(FILENAME, FILEPATH);
		for (int projId : map.keySet()) {
        	Object[] values = map.get(projId);       	
        		if ((ProjectStatus_Enum)values[4] == ProjectStatus_Enum.ALLOCATED) {
					addSupervisedProject((String)values[0], new Project((String)values[0], (String)values[1], (String)values[2], (String)values[3], (ProjectStatus_Enum)values[4]));  
					
					Student.getStudentFromName((String)values[3]).setProjectID(projId); //set student project id if allocated
        		}
        		else new Project((String)values[0],(String) values[1],(ProjectStatus_Enum) values[4]); 
        }
	}

	public static void updateProjectFile() {
		d.updateProjectFile(FILENAME,FILEPATH,projectList);
	}

	///////////////////////////////////////////////////////////////////////////////////////
	
	// Supervised Project
	
	public static void addSupervisedProject(String supervisorName,Project p) {
		Supervisor.getSupervisorFromName(supervisorName).getSupervisedProjectList().add(p);
	}
	
	// 2 is max, if size is 2 cannot allocate project
	public static boolean isAvailable(String supervisorId) { // error here
		return (!(Supervisor.getSupervisorFromId(supervisorId).getSupervisedProjectList().size() == MAX_PROJECT));
	}
	
	///////////////////////////////////////////////////////////////////////////////////////

	// For ProjectFYPCoordinator Interface //
	
	public static void changeSupervisor(int projectId, String replacementSupervisorName) {
		Project tempProj = Project.getProject(projectId);
		Supervisor tempSup = Supervisor.getSupervisorFromName(tempProj.getSupervisorName()); 
		ArrayList<Project> supervisingProjList = Supervisor.getSupervisorFromId(tempProj.getSupervisorId()).getSupervisedProjectList();
		if (supervisingProjList.size() == 2) {
			for (Project proj : Project.getProjectList() ) {
				if (proj.getSupervisorId().equals(tempProj.getSupervisorId()) && proj.getProjectStatus() == ProjectStatus_Enum.UNAVAILABLE)
					proj.setProjectStatus(ProjectStatus_Enum.AVAILABLE);
			}
		}
		
//		for(int i=0;i<tempSup.getSupervisedProjectList().size();i++) {
//			if(tempSup.getSupervisedProjectList().get(i).getProjectId()==projectId) {
//				tempSup.getSupervisedProjectList().remove(i);
//			}
//		}
		
		for (Project proj : tempSup.getSupervisedProjectList()) {
			if (proj.getProjectId()==projectId)
				tempSup.getSupervisedProjectList().remove(proj);
		}
		
		tempProj.setSupervisorName(replacementSupervisorName);
		tempProj.setSupervisorId(Supervisor.getSupervisorNameToId(replacementSupervisorName));
		tempProj.setSupervisorEmail(Supervisor.getSupervisorNameToEmail(replacementSupervisorName));
		Project.addSupervisedProject(replacementSupervisorName, tempProj);
		ArrayList<Project> newSupervisingProjList = Supervisor.getSupervisorFromId(tempProj.getSupervisorId()).getSupervisedProjectList();
		if (newSupervisingProjList.size() == 2) {
			for (Project proj : Project.getProjectList() ) {
				if (proj.getSupervisorId().equals(tempProj.getSupervisorId()) && proj.getProjectStatus() == ProjectStatus_Enum.AVAILABLE)
					proj.setProjectStatus(ProjectStatus_Enum.UNAVAILABLE);
			}
		}
//		View.cli.display("Supervisor has been changed successfully to...");
//		View.cli.display("Supervisor Id:" + tempProj.getSupervisorId());
//		View.cli.display("Supervisor Email:" + tempProj.getSupervisorEmail());
	}
	
	public static void allocateProject(int projectId, String studentId) {
		Project tempProj = getProject(projectId);
		tempProj.setStudentId(studentId);
		tempProj.setStudentEmail(Student.getStudentIdToEmail(studentId));
		tempProj.setStudentName(Student.getStudentIdToName(studentId));		
		tempProj.setProjectStatus(ProjectStatus_Enum.ALLOCATED);
		Project.addSupervisedProject(tempProj.getSupervisorName(),tempProj);
		if ((Supervisor.getSupervisorFromId(tempProj.getSupervisorId()).getSupervisedProjectList().size())==2) {
			for (Project proj : Project.getProjectList() ) {
				if (proj.getSupervisorId().equals(tempProj.getSupervisorId()) && proj.getProjectStatus() == ProjectStatus_Enum.AVAILABLE)
					proj.setProjectStatus(ProjectStatus_Enum.UNAVAILABLE);
			}
		}
	}
	
	public static void deregisterStudent(int projectId) {
		Project tempProj = Project.getProject(projectId);
		Supervisor tempSup = Supervisor.getSupervisorFromId(tempProj.getSupervisorId()); 
		if (Supervisor.getSupervisorFromId(tempProj.getSupervisorId()).getSupervisedProjectList().size() == MAX_PROJECT) {
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
		tempProj.setProjectTitle(tempProj.getOriProjectTitle());
		tempProj.setStudentId(null);
		tempProj.setStudentName("-1");
		tempProj.setStudentEmail(null);
		tempProj.setProjectStatus(ProjectStatus_Enum.AVAILABLE);
	}
	
	public static void changeProjectTitle(int projectId, String tempProjectTitle) {
		Project tempProj = Project.getProject(projectId);
		tempProj.setProjectTitle(tempProjectTitle);
	}
	
	
	public static void selectProject(int projectId) {
		Project tempProj = Project.getProject(projectId);
		tempProj.setProjectStatus(ProjectStatus_Enum.RESERVED);// change project status to reserved
		int count = 0;
		for (Project proj : Project.getProjectList() ) {
			if (proj.getSupervisorId().equals(tempProj.getSupervisorId()) && proj.getProjectStatus() == ProjectStatus_Enum.RESERVED)
				count++;
		}
		if (count == 2) {
			for (Project proj : Project.getProjectList() ) {
				if (proj.getSupervisorId().equals(tempProj.getSupervisorId()) && proj.getProjectStatus() == ProjectStatus_Enum.AVAILABLE)
						proj.setProjectStatus(ProjectStatus_Enum.UNAVAILABLE);
			}
		}
	}
}



