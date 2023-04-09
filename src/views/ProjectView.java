package Views;

import Project.ProjectDirectory;

public class ProjectView {


	public void printProjectInfo(int projectID){
		
		System.out.println("Project ID: " + projectID);
		System.out.println("Project Title: " + ProjectDirectory.getProject(projectID-1).getProjectTitle());
		System.out.println("Supervisor ID: " +ProjectDirectory.getProject(projectID-1).getSupervisorID());
		System.out.println("Supervisor Name: " +ProjectDirectory.getProject(projectID-1).getSupervisorName());
		System.out.println("Supervisor Email: " +ProjectDirectory.getProject(projectID-1).getSupervisorEmail());		
		System.out.println("Project status: " +ProjectDirectory.getProject(projectID-1).getProjectStatus());
		if (ProjectDirectory.getProject(projectID-1).getProjectStatus() == projectStatus_Enum.ALLOCATED) {
			System.out.println("Student ID: " +ProjectDirectory.getProject(projectID-1).getStudentID());
			System.out.println("Student Name: " +ProjectDirectory.getProject(projectID-1).getStudentName());
			System.out.println("Student Email: " +ProjectDirectory.getProject(projectID-1).getStudentEmail());		
		}
	}

}
