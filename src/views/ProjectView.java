package views;

import java.sql.ClientInfoStatus;

import models.Project;
import models.ProjectStatus_Enum;

public class ProjectView {

	public void printProjectInfo(int projectID){
		View.cli.display("Welcome to the menu!");
		View.cli.display("Project ID: " + projectID);
		View.cli.display("Project Title: " + Project.getProject(projectID).getProjectTitle());
		View.cli.display("Supervisor ID: " +Project.getProject(projectID).getSupervisorId());
		View.cli.display("Supervisor Name: " +Project.getProject(projectID).getSupervisorName());
		View.cli.display("Supervisor Email: " +Project.getProject(projectID).getSupervisorEmail());		
		View.cli.display("Project status: " +Project.getProject(projectID).getProjectStatus());
		if (Project.getProject(projectID-1).getProjectStatus() == ProjectStatus_Enum.ALLOCATED) {
			View.cli.display("Student ID: " +Project.getProject(projectID).getStudentId());
			View.cli.display("Student Name: " +Project.getProject(projectID).getStudentName());
			View.cli.display("Student Email: " +Project.getProject(projectID).getStudentEmail());		
		}
	}
	
	public void projectInfo() {
		for (int i = 0; i < Project.getProjectList().size(); i++) {
			if (Project.getProjectList().get(i).getProjectStatus() == ProjectStatus_Enum.AVAILABLE) {
				View.cli.display("Project Id:" + Project.getProjectList().get(i).getProjectId());
				View.cli.display("Project Title:" + Project.getProjectList().get(i).getProjectTitle());
				View.cli.display("Supervisor Id:" + Project.getProjectList().get(i).getSupervisorName());
				View.cli.display("Supervisor Email:" + Project.getProjectList().get(i).getSupervisorEmail());
			}
		}
	}
}
