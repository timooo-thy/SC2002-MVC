package views;

import models.Project;
import models.ProjectStatus_Enum;

/**
 * The ProjectView class provides methods for displaying information about projects in a console-based user interface.
 */
public class ProjectView {

	/**
	 * Prints information about a single project.
	 * 
	 * @param projectID the ID of the project to display
	 */
	public static void printProjectInfo(int projectID){
		View.cli.display("----------------------------------------------------------------------------");
		View.cli.display("Project ID: " + projectID);
		View.cli.display("Project Title: " + Project.getProject(projectID).getProjectTitle());
		View.cli.display("Supervisor ID: " + Project.getProject(projectID).getSupervisorId());
		View.cli.display("Supervisor Name: " + Project.getProject(projectID).getSupervisorName());
		View.cli.display("Supervisor Email: " + Project.getProject(projectID).getSupervisorEmail());		
		View.cli.display("Project status: " + Project.getProject(projectID).getProjectStatus());
		if (Project.getProject(projectID).getProjectStatus() == ProjectStatus_Enum.ALLOCATED) {
			View.cli.display("Student ID: " + Project.getProject(projectID).getStudentId());
			View.cli.display("Student Name: " + Project.getProject(projectID).getStudentName());
			View.cli.display("Student Email: " + Project.getProject(projectID).getStudentEmail());		
		}
	}
	
	/**
	 * Prints information about all available projects.
	 */
	public static void projectAvailableInfo() {
		for (int i = 0; i < Project.getProjectList().size(); i++) {
			if (Project.getProjectList().get(i).getProjectStatus() == ProjectStatus_Enum.AVAILABLE) {
				View.cli.display("----------------------------------------------------------------------------");
				View.cli.display("Project ID: " + Project.getProjectList().get(i).getProjectId());
				View.cli.display("Project Title: " + Project.getProjectList().get(i).getProjectTitle());
				View.cli.display("Supervisor ID: " + Project.getProjectList().get(i).getSupervisorName());
				View.cli.display("Supervisor Email: " + Project.getProjectList().get(i).getSupervisorEmail());
			}
		}
	}
}
