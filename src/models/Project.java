package Project;

import java.util.ArrayList;
import java.util.Scanner;
import User.*;


public class Project /*implements ProjectSupervisor, ProjectStudentPreAllocation, ProjectStudentPostAllocation, ProjectFYPCoordinator*/ {
	
	private int projectID;
	private String supervisorID;
	private String supervisorName;
	private String tempSupervisorID;
	private String tempSupervisorEmail;
	private String supervisorEmail;
	private String studentID;
	private String studentName;
	private String tempStudentID;
	private String tempStudentEmail;
	private String studentEmail;
	private String projectTitle;
	private String tempProjectTitle;
	private projectStatus_Enum projectStatus;

	private SupervisorDatabase supervisorDatabase = new SupervisorDatabase();
	private ArrayList<Supervisor> supervisorList; 
	
	//Constructor 

	public Project(String supervisorName, String project_title,int projectID) {
		setSupervisorName(supervisorName);
		setProjectTitle(project_title);
		setProjectID(projectID);
		setProjectStatus(projectStatus_Enum.AVAILABLE);
		setSupervisorEmail(getSupervisorListEmail(supervisorName));
		setSupervisorID(getSupervisorListID(supervisorName));
		setStudentName(null);
	}
	
	Scanner sc = new Scanner(System.in);
	
	////////////////////////////////////////////////////////////////////////////////////////

	// Get ID and Email based on Name
	public String getSupervisorListEmail(String supervisorName){
		if (supervisorList == null) {
			supervisorList = this.getSupervisorList();
		}
		for (int i = 0; i < supervisorList.size(); i++) {
			if ((this.supervisorList.get(i).getname()).equals(supervisorName)) return supervisorList.get(i).getemail();
		}
		return null;
	}
	
	public String getSupervisorListID(String supervisorName) {
		if (supervisorList == null) {
			supervisorList = this.getSupervisorList();
		}
		for (int i = 0; i < supervisorList.size(); i++) {
			if ((this.supervisorList.get(i).getname()).equals(supervisorName)) return supervisorList.get(i).getuserID();
		}
		return null;
	}
	//////////////////////////////////////////////////////////////////////////////////////

	// Get supervisor List for finding ID and Email
	public ArrayList<Supervisor> getSupervisorList(){
		if (supervisorList == null) SupervisorDatabase.initialiseDatabase();
		return supervisorDatabase.getSupervisorList();
	}
	///////////////////////////////////////////////////////////////////////////////////////

	public int getProjectID() {
		return projectID;
	}
	
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	///////////////////////////////////////////////////////////////////////////////////////

	public String getSupervisorID() {
		return supervisorID;
	}

	public void setSupervisorID(String supervisorID) {
		this.supervisorID = supervisorID;
	}
	///////////////////////////////////////////////////////////////////////////////////////

	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}
	
	public String getSupervisorName() {
		return supervisorName;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////

	public String getSupervisorEmail() {
		return supervisorEmail;
	}

	public void setSupervisorEmail(String supervisorEmail) {
		this.supervisorEmail = supervisorEmail;
	}

	///////////////////////////////////////////////////////////////////////////////////////

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	public String getStudentName() {
		return studentName;
	}

	///////////////////////////////////////////////////////////////////////////////////////

	public String getStudentEmail() {
		return studentEmail;
	}

	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}

	///////////////////////////////////////////////////////////////////////////////////////

	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////

	public projectStatus_Enum getProjectStatus() {
		return this.projectStatus;
	}

	public void setProjectStatus(projectStatus_Enum projectStatus) {
		this.projectStatus = projectStatus;
	}

	///////////////////////////////////////////////////////////////////////////////////////


    public void setStudent(String studentID,String studentName,String studentEmail, projectStatus_Enum projectStatus) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.projectStatus = projectStatus_Enum.ALLOCATED;
    }

    // public void allocateProject(int projectID, String studentID) {
	// 	projectList.get(projectID).setStudentID(studentID);
	// 	projectList.get(projectID).setStudentEmail(getStudentIDToEmail(studentID));
	// 	projectList.get(projectID).setStudentName(getStudentIDToName(studentID));		
	// 	projectList.get(projectID).setProjectStatus(projectStatus_Enum.ALLOCATED);
	// 	System.out.println("Student has been changed successfully to...");
	// 	System.out.println("Student ID:" + projectList.get(projectID).getStudentID());
	// 	System.out.println("Student Email:" + projectList.get(projectID).getStudentEmail());
	// }

	// For ProjectFYPCoordinator Interface //
	public void changeSupervisor() {
		System.out.println("Please enter the new Supervisor ID");
		tempSupervisorID = sc.nextLine();
		System.out.println("Please enter the new Supervisor Email");
		tempSupervisorEmail = sc.nextLine();
		setSupervisorID(tempSupervisorID);
		setSupervisorEmail(tempSupervisorEmail);
		System.out.print("Supervisor has been changed successfully to...");
		System.out.print("Supervisor ID:" + getSupervisorID());
		System.out.print("Supervisor Email:" + getSupervisorEmail());
	}

	// public void changeSupervisor(int projectID, String replacementSupervisorID) {
	// 	projectList.get(projectID).setSupervisorID(replacementSupervisorID);;
	// 	projectList.get(projectID).setSupervisorName(getSupervisorIDToName(replacementSupervisorID));
	// 	projectList.get(projectID).setSupervisorEmail(getSupervisorIDToEmail(replacementSupervisorID));
	// 	System.out.print("Supervisor has been changed successfully to...");
	// 	System.out.print("Supervisor ID:" + getSupervisorID());
	// 	System.out.print("Supervisor Email:" + getSupervisorEmail());
	// }

	public void changeStudent() {
		System.out.println("Please enter the new Student ID");
		tempStudentID = sc.nextLine();
		System.out.println("Please enter the new Student Email");
		tempStudentEmail = sc.nextLine();
		setStudentID(tempStudentID);
		setStudentEmail(tempStudentEmail);
		System.out.print("Student has been changed successfully to...");
		System.out.print("Student ID:" + getStudentID());
		System.out.print("Student Email:" + getStudentEmail());
	}
	
	public void deregisterStudent() {
		setStudentID(null);
		setStudentName(null);
		setStudentEmail(null);
		setProjectStatus(projectStatus_Enum.AVAILABLE);
	}

	// public void deregisterStudent(int projectID) {
	// 	projectList.get(projectID).setStudentID(null);
	// 	projectList.get(projectID).setStudentName(null);
	// 	projectList.get(projectID).setStudentEmail(null);
	// 	projectList.get(projectID).setProjectStatus(projectStatus_Enum.AVAILABLE);
	// }

	///////////////////////////////////////////////////////////////////////////////////////

	// For ProjectSupervisor Interface //
	public void viewProject() {
		System.out.println("\n");
		System.out.println("Project ID:" + getProjectID());
		System.out.println("Project Title:" + getProjectTitle());
		System.out.println("\n");
	}
	
	public static void changeProjectTitle(int projectID, String tempProjectTitle) {
         Project tempProject = ProjectDirectory.getProject(projectID-1);
         tempProject.setProjectTitle(tempProjectTitle);
         System.out.print("Project Title has been changed successfully to...");
         System.out.print("Project Title:" + tempProject.getProjectTitle());
	}

    //   EDITED
    // public static void getProjectDetails(String supervisorID) {
	// 	for (int i = 0; i < projectList.size(); i++) {
	// 		if ((projectList.get(i).getSupervisorID()).equals(supervisorID)) {
	// 			System.out.printf("supervisor ID : %s \n",projectList.get(i).getSupervisorID()); 
	// 			System.out.printf("supervisor email : %s \n",projectList.get(i).getSupervisorEmail());
	// 			System.out.printf("project status : %s \n",projectList.get(i).getProjectStatus());
	// 			if (projectList.get(i).getProjectStatus() == projectStatus_Enum.ALLOCATED) {
	// 				System.out.printf("student name : %s \n",projectList.get(i).getStudentName());
	// 				System.out.printf("student ID : %s \n",projectList.get(i).getStudentID());
	// 				System.out.printf("student Email : %s \n",projectList.get(i).getStudentEmail());
	// 				System.out.printf("project ID : %d \n",projectList.get(i).getProjectID());
	// 				System.out.printf("Project title : %s \n",projectList.get(i).getProjectTitle());
	// 			}
	// 			else {
	// 				System.out.printf("project ID : %d \n",projectList.get(i).getProjectID());
	// 				System.out.printf("Project title : %s \n",projectList.get(i).getProjectTitle());
	// 			}
	// 			System.out.println();
	// 			System.out.println();
	// 		}
	// 	}
	// }

    ///////////////////////////////////////////////////////////////////////////////////////

	// For ProjectStudentPreAllocation Interface //

	public void projectInfo() {
		System.out.println("Project ID:" + getProjectID());
		System.out.println("Project Title:" + getProjectTitle());
		System.out.println("Supervisor ID:" + getSupervisorID());
		System.out.println("Supervisor Email:" + getSupervisorEmail());
	}

    // EDITED
	// 	public void projectInfo() {
	// 	for (int i = 0; i < projectList.size(); i++) {
	// 		if (projectList.get(i).projectStatus == projectStatus_Enum.AVAILABLE) {
	// 			System.out.println("Project ID:" + projectList.get(i).getProjectID());
	// 			System.out.println("Project Title:" + projectList.get(i).getProjectTitle());
	// 			System.out.println("Supervisor ID:" + projectList.get(i).getSupervisorName());
	// 			System.out.println("Supervisor Email:" + projectList.get(i).getSupervisorEmail());
	// 		}
	// 	}
	// }

        public void chooseProject(){

        }

	///////////////////////////////////////////////////////////////////////////////////////

	// For ProjectStudentPostAllocation Interface //
	public void projectDetails() {
		System.out.println("Project ID:" + getProjectID());
		System.out.println("Project Title:" + getProjectTitle());
		System.out.println("Supervisor ID:" + getSupervisorID());
		System.out.println("Supervisor Email:" + getSupervisorEmail());
		System.out.println("Student ID:" + getStudentID());
		System.out.println("Student Email:" + getStudentEmail());
	}

    // EDITED
    // public void projectDetails() {
	// 	System.out.println("Project ID:" + getProjectID());
	// 	System.out.println("Project Title:" + getProjectTitle());
	// 	System.out.println("Supervisor ID:" + getSupervisorID());
	// 	System.out.println("Supervisor Email:" + getSupervisorEmail());
	// 	System.out.println("Student ID:" + getStudentID());
	// 	System.out.println("Student Email:" + getStudentEmail());
	// }

}