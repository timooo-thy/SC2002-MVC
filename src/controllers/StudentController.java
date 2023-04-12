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
import views.StudentView;
/**
 * Represents the Student Controller
 */
public class StudentController extends Controller {
	
	private Student studentModel;
	
	private MainController mainController;
	
	private StudentController studentController;
	
	public void run(User user) throws Throwable {

        if(user instanceof Student) {
		    studentModel = (Student)user; 
		}
            
		mainController = new MainController();

		String[] menu = {
                "Change Password ",
                "Request to Register a Project ",
                "Request Project Title Change ",
                "Request Deregistration of Project ",
                "View Available Projects",
                "View Project Details ",
                "View Request History",
                "View Profile",
				"Logout ",
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
				            String currentPass = cli.inputString("Your current password: ");
	
				            if (!currentPass.equals(studentModel.getPassword())) {
				                tries--;
				                cli.display("Wrong password. You have " + tries + " more tries.");
				                continue;
				            }
	
				            String newPass = cli.inputString("Your new password: ");
	
				            String confirmPass = cli.inputString("Password to reconfirm: ");
	
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
					
				case 2://request:Register Project
					int projectChoice;
					ProjectStatus_Enum projectStatus;
					
					if (studentModel.getProjectID() == -1) {
						ProjectView.projectAvailableInfo();
						cli.display("------------------------------------");
						cli.display("Enter the Project ID to Register for:");
						do {
							projectChoice = cli.inputInteger("Project ID", 1, Project.getProjectList().size()+1);
							projectStatus = Project.getProject(projectChoice).getProjectStatus();
							if (projectStatus == ProjectStatus_Enum.AVAILABLE) {
								new Request(studentModel.getId(), studentModel.getName(), studentModel.getEmailAddress(), "ASFLI", "Li Fang", "ASFLI@ntu.edu.sg", projectChoice, RequestType_Enum.REGISTERPROJECT, RequestStatus_Enum.PENDING, Request.getRequests().size()+1);// send request to register
								Project.selectProject(projectChoice);
								projectStatus = Project.getProject(projectChoice).getProjectStatus();
								studentModel.setProjectID(0);
								cli.displayTitle("Success, your registration is pending for approval by the coordinator");
							}
						} while (projectStatus == ProjectStatus_Enum.AVAILABLE);
						// can do a catch throw exception
						
						if(studentModel.getProjectID()!=0) {
							cli.displayTitle("Project is being reserved by another student");
						}
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
					Thread.sleep(3000);
					break;
					
				case 3: //Request: Project Title Change
					String newTitle;					
					cli.displayTitle("Request for Change of Project Title");
					cli.display("------------------------------------");
					if (studentModel.getProjectID() == -1 || studentModel.getProjectID() == 0) {
						cli.display("You are not registered for any projects.");
					}
					else {
						//if student already registered, proceed title change
						Project allocatedProject = Project.getProject(studentModel.getProjectID());
						cli.display("Your project title is : " + allocatedProject.getProjectTitle());
						newTitle = cli.inputString("your new title to change");
						new Request(studentModel.getId(),studentModel.getName(),studentModel.getEmailAddress(),allocatedProject.getSupervisorId(),allocatedProject.getSupervisorName(),allocatedProject.getSupervisorEmail(),allocatedProject.getProjectId(),newTitle,RequestType_Enum.CHANGETITLE,RequestStatus_Enum.PENDING,Request.getRequests().size()+1);// send request to change title
						cli.displayTitle("SUCCESS, YOUR REQUEST FOR CHANGING TITLE IS NOW PENDING FOR APPROVAL BY THE SUPERVISOR");
						Request.updateRequestFile();
					}
					
						Thread.sleep(3000);
						break;
				
				case 4: //Request: Project Deregistration
					cli.displayTitle("Deregistering Project");
					cli.display("------------------------------------");
					// check project id first
					if (studentModel.getProjectID() == -1 || studentModel.getProjectID() == 0) 
						cli.display("You are not registered for any projects.");
					else {
						cli.display("Request to Deregister Project: " + Project.getProject(studentModel.getProjectID()).getProjectTitle());
						
						String menu_item[] = {
							"Confirm",
							"Back"
						};
						
						cli.display(menu_item);
						choice = cli.inputInteger("choice", 1, menu_item.length);
						if (choice == 1) {
							Project allocatedProject = Project.getProject(studentModel.getProjectID());
						
							new Request(studentModel.getId(),studentModel.getName(),studentModel.getEmailAddress(),"ASFLI", "Li Fang", "ASFLI@ntu.edu.sg",allocatedProject.getProjectId(),RequestType_Enum.DEREGISTERPROJECT,RequestStatus_Enum.PENDING,Request.getRequests().size()+1);// send request to register
							//cli.displayTitle();
							Request.updateRequestFile(); // Update file
							cli.display("Request Sent");
						}
						else {
							cli.display("Request Cancelled");
						}
					}
					Thread.sleep(3000);
					break;
					
				case 5: //View available projects
					cli.displayTitle("View all Available Projects");
					cli.display("------------------------------------");
					if(studentModel.getProjectID() != -1 & studentModel.getProjectID() != 0) {
						cli.display("You are currently allocated to a FYP and do not have access to available project list.");
					}
					
					else {
						ProjectView.projectAvailableInfo();
					}
					
					Thread.sleep(3000);
					break;
					
					
				case 6: //View project details
					if (studentModel.getProjectID() == -1) {
						cli.displayTitle("You have not registered for a project.");
					}
					else if (studentModel.getProjectID() == 0)
						cli.display("Your request to select project is pending. Please be patient!");
					else {
						cli.display("Here are the details of your project: ");
						ProjectView.printProjectInfo(studentModel.getProjectID());
					}
					Thread.sleep(3000);
					break;
				
				
				case 7: //View RequestHistory
					cli.displayTitle("View Request History");
					cli.display("------------------------------------");
					RequestView.printRequestHistory(studentModel.getId());
					Thread.sleep(3000);
					break;
					
				case 8: //View Profile
					cli.displayTitle("View Profile");
					cli.display("------------------------------------");
					StudentView.printStudentRecordInfo(studentModel.getId(), studentModel.getName(), studentModel.getEmailAddress(), studentModel.getPassword());
					Thread.sleep(3000);
					break;
				
				case 9:
					cli.display("Logging out...");
					Thread.sleep(1000);
					return;
				
				default:
					return;
			}
		}
	}
}

