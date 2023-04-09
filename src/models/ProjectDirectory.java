package models;
  
import java.io.BufferedReader;  
import java.io.FileReader;  
import java.io.IOException;  
import java.util.ArrayList;
import com.opencsv.CSVReader;

import requests.Request;

import java.util.Scanner;


public class ProjectDirectory {
	
	private static ProjectDirectory projectDirectory = new ProjectDirectory();
	private static ArrayList<Project> projectList;
	int binary = 1;
	private String supervisorName;
	private String projectTitle;
	
	Scanner sc = new Scanner(System.in);

    // change visibility to private after finish
	public ProjectDirectory() {
		projectList = new ArrayList<Project>();
		CSVReader reader = null;  
		try {  
			//parsing a CSV file into CSVReader class constructor  
			reader = new CSVReader(new FileReader(System.getProperty("user.dir") + "\\files\\rollover project.csv"));  
			reader.readNext();
			String [] nextLine;  
			//reads one line at a time  
			while ((nextLine = reader.readNext()) != null) { 
				for (String token: nextLine) {
					if (binary == 1)
						supervisorName = token;
					else if (binary == -1) { 
						projectTitle = token;
						projectList.add(new Project(supervisorName,projectTitle,projectList.size()+1));  
					}
				binary *= -1;
				}
			}  
		}  
		catch (Exception e) {  
			e.printStackTrace();  
		}    
	}

	
	public static ProjectDirectory getInstance() {
		return projectDirectory;
	}
	
    public static void createProject(String supervisorName, String projectTitle){
		projectList.add(new Project(supervisorName, projectTitle, projectList.size()+1));
	}
    //similar function
	public void addProjects(){
		System.out.println("Please enter the Supervisor name");
		supervisorName = sc.nextLine();
		System.out.println("Please enter the Project title");
		projectTitle = sc.nextLine();
		projectList.add(new Project(supervisorName, projectTitle, projectList.size()+1));
	}
    
	public static void createProject(String supervisorName, String projectTitle){
		projectList.add(new Project(supervisorName, projectTitle, projectList.size()+1));
	}
    
    //same function in project class
	public static void getProjectDetails(String supervisorID) {
		for (int i = 0; i < projectList.size(); i++) {
			if ((projectList.get(i).getSupervisorID()).equals(supervisorID)) {
				System.out.printf("supervisor ID : %s \n",projectList.get(i).getSupervisorID()); 
				System.out.printf("supervisor email : %s \n",projectList.get(i).getSupervisorEmail());
				System.out.printf("project ID : %d \n",projectList.get(i).getProjectID());
				System.out.printf("project status : %s \n",projectList.get(i).getProjectStatus());
				if (projectList.get(i).getProjectStatus() == projectStatus_Enum.ALLOCATED) {
					projectList.get(i).getStudentID();
					projectList.get(i).getStudentEmail();
					projectList.get(i).getProjectTitle();
				}
				else System.out.printf("Project title : %s \n",projectList.get(i).getProjectTitle());
				System.out.println();
				System.out.println();
			}
		}
	}

    //duplicate
    public static ArrayList<Project> getProjectDirectory(){
        return projectList;
    }
    
    public static ArrayList<Project> getProjectList(){
		return projectList;
	}

    public static Project getProject(int projectID) {
    	return projectList.get(projectID-1);
    }


}