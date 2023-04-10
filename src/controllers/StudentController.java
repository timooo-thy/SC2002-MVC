package controllers;

import models.Student;
import models.User;
import models.Project;
import models.ProjectStatus_Enum;
import models.Request;
import models.RequestStatus_Enum;
import models.RequestType_Enum;
import views.ProjectView;
import views.RequestView;
/**
 * Represents the Student Controller
 */
public class StudentController extends Controller {
	
	private Student studentModel;
	
	private MainController mainController;
	
	private StudentController studentController;
	
	public void run(User user) throws Throwable{

        if(user instanceof Student) {
		    studentModel = (Student)user; 
		}
            
		mainController = new MainController();

		String[] menu = {
                "1. Change Password ",
				"2. Logout ",
                "3. Request to Register a Project ",
                "4. Request Project Title Change ",
                "5. Request Deregistration of Project ",
                "6. View Available Projects / View Project Details ",
                "7. View Request History",
		};
		
		int choice = 0;
		
		while(choice <= menu.length) {
			
			cli.displayTitle("STUDENT FUNCTIONS");
			cli.display(menu);
			
			choice = cli.inputInteger("choice", 1, menu.length);
			
			switch(choice) {
			
				case 1://change password
	
				    boolean isPasswordChanged = false;
				    int tries = 3;
	
				    while (tries > 0 && !isPasswordChanged) {
	
				        try {
				            String currentPass = cli.inputString("your current password: ");
	
				            if (!currentPass.equals(studentModel.getPassword())) {
				                tries--;
				                cli.display("Wrong password. You have " + tries + " more tries.");
				                continue;
				            }
	
				            String newPass = cli.inputString("your new password: ");
	
				            String confirmPass = cli.inputString("password to reconfirm: ");
	
				            if (!newPass.equals(confirmPass)) {
				                cli.display("Passwords do not match. Please try again.");
				                continue;
				            }
				            studentModel.setPassword(newPass);
				            isPasswordChanged = true;
				            cli.display("Password has been changed successfully!");
	
				        } catch (Exception e) {
				            System.out.println("Error: " + e.getMessage() + ". Please try again.");
				            tries--;
				        }
				    }
					    
					break;

				case 2://logout
					cli.display("Logging out...");
					return;
					
				case 3://request:Register Project
					int projectChoice;
					ProjectStatus_Enum projectStatus;
					
					if (studentModel.getProjectID() == -1) {
						cli.display("Enter the project ID to register for:");
						do {
							projectChoice = cli.inputInteger("Project ID", 1, Project.getProjectList().size()+1);
							projectStatus = Project.getProject(projectChoice).getProjectStatus();
							if (projectStatus == ProjectStatus_Enum.AVAILABLE) {
								new Request(studentModel.getId(),studentModel.getName(),studentModel.getEmailAddress(),/*fypCoordinatorModel.getId(),fypCoordinatorModel.getName(),fypCoordinatorModel.getEmail()*/"ASFLI","ASFLI","ASFLI",projectChoice,RequestType_Enum.REGISTERPROJECT,RequestStatus_Enum.PENDING,Request.getRequests().size());// send request to register
								Project.getProject(projectChoice).setProjectStatus(ProjectStatus_Enum.RESERVED);// change project status to reserved
							}
						} while (projectStatus != ProjectStatus_Enum.AVAILABLE);
						// can do a catch throw exception
						
						cli.displayTitle("SUCCESS, YOUR REGISTRATION IS NOW PENDING FOR APPROVAL BY THE COORDINATOR");
						Request.updateRequestFile(); // Update file
						Project.updateProjectFile(); // Update file
						Student.updateFile(); // Update file	
					}
					else if (studentModel.getProjectID() == 0) {
						cli.display("You already have a pending request for registration.");
					}
					else {
						cli.display("You are already registered for a project.");				
					}
					Thread.sleep(1000);
					break;
					
				case 4: //Request: Project Title Change
					String newTitle;					
					cli.displayTitle("Request for Change of Project Title");
					if (studentModel.getProjectID() == -1 || studentModel.getProjectID() == 0) {
						cli.display("You are not registered for any projects.");
					}
					else {
						//if student already registered, proceed title change
						Project allocatedProject = Project.getProject(studentModel.getProjectID());
						cli.display("Your project title is : " + allocatedProject.getProjectTitle());
						newTitle = cli.inputString("What would you like to change it to?","\n");
						new Request(studentModel.getId(),studentModel.getName(),studentModel.getEmailAddress(),allocatedProject.getSupervisorId(),allocatedProject.getSupervisorName(),allocatedProject.getSupervisorEmail(),allocatedProject.getProjectId(),newTitle,RequestType_Enum.CHANGETITLE,RequestStatus_Enum.PENDING,Request.getRequests().size());// send request to change title
						cli.displayTitle("SUCCESS, YOUR REQUEST FOR CHANGING TITLE IS NOW PENDING FOR APPROVAL BY THE COORDINATOR");
						Request.updateRequestFile();
					}
					
						Thread.sleep(3000);
						break;
				
				case 5: //Request: Project Deregistration
					cli.displayTitle("Deregistering Project");
					cli.display("Request to deregister project: " + Project.getProject(studentModel.getProjectID()).getProjectTitle());
					cli.display("Enter 1 to confirm, 2 to exit. "); 
					choice = cli.inputInteger("choice", 1, 2);
					if (choice == 1) {
						Project allocatedProject = Project.getProject(studentModel.getProjectID());
						new Request(studentModel.getId(),studentModel.getName(), studentModel.getEmailAddress(), allocatedProject.getSupervisorId(), allocatedProject.getSupervisorName(), allocatedProject.getSupervisorEmail(), studentModel.getProjectID(), RequestType_Enum.DEREGISTERPROJECT, RequestStatus_Enum.PENDING, Request.getRequests().size()+1);
						
						//cli.displayTitle();
						Request.updateRequestFile(); // Update file
					}
					else {
						cli.display("Request Cancelled");
					}
					
					Thread.sleep(3000);
					break;
					
				case 6: //View available projects
					ProjectView projectView = new ProjectView();
					if (studentModel.getProjectID() == -1) {
						cli.displayTitle("View all Available Projects");
						projectView.projectAvailableInfo();
					}
					else if (studentModel.getProjectID() == 0)
						cli.display("Your request to select project is pending, please be patient!");
					else {
						cli.display("Here is the detail of your project: ");
						projectView.printProjectInfo(studentModel.getProjectID());
					}
					Thread.sleep(3000);
					break;
				
				case 7: //View RequestHistory
					cli.displayTitle("View Request History");
					RequestView.printRequestHistory(studentModel.getId());
					Thread.sleep(3000);
					break;
				
				default:
				studentController.run(studentModel);
			}
		}
	}
}


