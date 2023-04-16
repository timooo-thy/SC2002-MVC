package views;

import models.Project;
import models.ProjectStatus_Enum;

/**
 * The ProjectView class provides methods for displaying information about projects in a console-based user interface.
 * @author Timothy Lee
 * @author Justin Wong
 * @author Jun Hao
 * @author Lee Cheng Yao
 * @author Abhishekh
 * @version 1.0
 * @since 2023-04-16
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
		View.cli.display("Original Project Title: " + Project.getProject(projectID).getOriProjectTitle());
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
				View.cli.display("Supervisor ID: " + Project.getProjectList().get(i).getSupervisorId());
				View.cli.display("Supervisor Name: " + Project.getProjectList().get(i).getSupervisorName());
				View.cli.display("Supervisor Email: " + Project.getProjectList().get(i).getSupervisorEmail());
			}
		}
	}
	
	/**
	 * Prints information about all projects.
	 * 
	 * @param detailed Boolean field (True/False) to generate detailed project report or not
	 */
	public static void projectAllInfo(boolean detailed) {
		if (detailed) {
			for (int i = 0; i < Project.getProjectList().size(); i++) {
				printProjectInfo(i+1);
			}
		}
		else {
			for (int i = 0; i < Project.getProjectList().size(); i++) {
				View.cli.display("----------------------------------------------------------------------------");
				View.cli.display("Project ID: " + Project.getProjectList().get(i).getProjectId());
				View.cli.display("Project Title: " + Project.getProjectList().get(i).getProjectTitle());
				View.cli.display("Supervisor ID: " + Project.getProjectList().get(i).getSupervisorId());
				View.cli.display("Supervisor Name: " + Project.getProjectList().get(i).getSupervisorName());
				View.cli.display("Supervisor Email: " + Project.getProjectList().get(i).getSupervisorEmail());
			}
		}	
	}
	
	/**
	 * Prints information about all projects based on Status.
	 */
	public static void printProjectsByStatus() {
		View.cli.displayTitle("Choose the Project Status to View");
		String [] projStatus = { 
				"Available",
				"Reserved",
				"Allocated",
				"Unavailable",
				"Back"
		};
		View.cli.display(projStatus);
		int choice = View.cli.inputInteger("Choice", 1, projStatus.length);
		int projectCount = 0;
		if (choice == 1) {
			projectCount = 0;
			View.cli.displayTitle("Generating project details for all available projects...");
			for (Project proj : Project.getProjectList()) {
				if (proj.getProjectStatus()==(ProjectStatus_Enum.AVAILABLE)) {
					ProjectView.printProjectInfo(proj.getProjectId());
					View.cli.display("------------------------------------");
					projectCount++;
				}
			}
			if (projectCount == 0) {
				View.cli.displayTitle("There are no available projects");
			}
			return;
		}
		if (choice == 2){
			View.cli.displayTitle("Generating project details for all reserved projects...");
			for (Project proj : Project.getProjectList()) {
				if (proj.getProjectStatus()==(ProjectStatus_Enum.RESERVED)) {
					ProjectView.printProjectInfo(proj.getProjectId());
					View.cli.display("------------------------------------");
					projectCount++;
				}
			}
			if (projectCount == 0) {
				View.cli.displayTitle("There are no reserved projects");
			}
			return;
		}
		if (choice == 3){
			View.cli.displayTitle("Generating project details for all allocated projects...");
			for (Project proj : Project.getProjectList()) {
				if (proj.getProjectStatus()==(ProjectStatus_Enum.ALLOCATED)) {
					ProjectView.printProjectInfo(proj.getProjectId());
					View.cli.display("------------------------------------");
					projectCount++;
				}
			}
			if (projectCount == 0) {
				View.cli.displayTitle("There are no allocated projects");
			}
			return;
		}
		if (choice == 4){
			View.cli.displayTitle("Generating project details for all unavailable projects...");
			for (Project proj : Project.getProjectList()) {
				if (proj.getProjectStatus()==(ProjectStatus_Enum.UNAVAILABLE)) {
					ProjectView.printProjectInfo(proj.getProjectId());
					View.cli.display("------------------------------------");
					projectCount++;
				}
			}
			if (projectCount == 0) {
				View.cli.displayTitle("There are no unavailable projects");
			}
			return;
		}
		if (choice == 5) return;
	}
		
}
	
