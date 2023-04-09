package views;

import models.Project;
import models.ProjectStatus_Enum;

public class ProjectView {


	public void printProjectInfo(int projectID){
		
		System.out.println("Project ID: " + projectID);
		System.out.println("Project Title: " + Project.getProject(projectID).getProjectTitle());
		System.out.println("Supervisor ID: " +Project.getProject(projectID).getSupervisorId());
		System.out.println("Supervisor Name: " +Project.getProject(projectID).getSupervisorName());
		System.out.println("Supervisor Email: " +Project.getProject(projectID).getSupervisorEmail());		
		System.out.println("Project status: " +Project.getProject(projectID).getProjectStatus());
		if (Project.getProject(projectID-1).getProjectStatus() == ProjectStatus_Enum.ALLOCATED) {
			System.out.println("Student ID: " +Project.getProject(projectID).getStudentId());
			System.out.println("Student Name: " +Project.getProject(projectID).getStudentName());
			System.out.println("Student Email: " +Project.getProject(projectID).getStudentEmail());		
		}
	}

}
