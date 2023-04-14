package controllers;

import models.Student;
import models.User;
import utilities.Database;
import models.Project;
import models.ProjectStatus_Enum;
import models.Request;
import models.RequestStatus_Enum;
import models.RequestType_Enum;
import views.ProjectView;
import views.RequestView;
import views.StudentView;

/**
 * This class represents a controller for a Student in the FYP Registration System.
 * It extends the abstract class Controller and overrides its run() method.
 * @author Timothy Lee
 * @author Justin Wong
 * @author Jun Hao
 * @author Lee Cheng Yao
 * @author Abhishekh
 */
public class StudentController extends Controller {
	
	/**
	 * The Student model associated with this controller.
	 */
	private Student studentModel;
	
	/**
	 * The MainController associated with this Student Controller.
	 */
	private MainController mainController;
	
	/**
	 * This method overrides the abstract method run() in the Controller class.
	 * It takes in a User object as a parameter and throws any exceptions that may occur during execution.
	 * 
	 * @param user The currently logged in Student
	 * @throws ClassNotFoundException If the specified class cannot be found
	 */
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
			/*
			 * Student main page
			 */
			cli.displayTitle("STUDENT FUNCTIONS");
			cli.display(menu);
			
			choice = cli.inputInteger("Choice", 1, menu.length);
			
			switch(choice) {
			
				case 1://change password
				/**
				 * Case 1 enables user to change password. If attempt to key current password is wrong 3 times, attempt is failed
				 * If successful, password is changed and user will have to log in again.
				 */
				    boolean isPasswordChanged = false;
				    int tries = 3;
	
				    while (tries > 0 && !isPasswordChanged) {
	
				        try {
				            String currentPass = cli.inputString("your current password");
	
				            if (!currentPass.equals(studentModel.getPassword())) {
				                tries--;
				                cli.display("Wrong password. You have " + tries + " more tries.");
				                continue;
				            }
	
				            String newPass = cli.inputString("your new password");
	
				            String confirmPass = cli.inputString("password to reconfirm");
	
				            if (!newPass.equals(confirmPass)) {
				                cli.display("Passwords do not match. Please try again.");
				                continue;
				            }
				            studentModel.setPassword(newPass);
				            isPasswordChanged = true;
				            cli.display("Password has been changed successfully! Please relogin. ");
	
				        } catch (Exception e) {
				            cli.display("Error: " + e.getMessage() + ". Please try again.");
				            tries--;
				        }
				    }
				    cli.display("Logging out...");
				    Database.updateAllData();
				    Thread.sleep(1500);    
					return;
					
				case 2://request:Register Project
					/*
					 * Case 2 enables user to register for a project. 
					 * User is first prompted to enter a project ID.
					 * If project is available the request will be sent.
					 * If project already have a request, the user cannot send  the request.
					 */
					int projectChoice;
					ProjectStatus_Enum projectStatus;
					cli.displayTitle("Register Project");
					if (studentModel.getProjectID() == -1) {
						ProjectView.projectAvailableInfo();
						do {
							projectChoice = cli.inputInteger("Enter the Project ID to register for (Enter 0 to exit)", 0, Project.getProjectList().size());
							if (projectChoice==0) break;
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
						if (projectChoice==0) {
							cli.displayTitle("Cancelling Request...");
							Thread.sleep(1500);
							break;
						}
						
						if(studentModel.getProjectID()!=0) {
							cli.displayTitle("Project is being reserved by another student");
						}
						Database.updateAllData();
					}
					
					else if (studentModel.getProjectID() == 0) {
						cli.displayTitle("You already have a pending request for registration");
					}
					
					else if (studentModel.getProjectID() == -2) {
						cli.displayTitle("You are not allowed to register for a project as you have deregistered your FYP");
					}
					
					else {
						cli.displayTitle("You are already registered for a project");				
					}
					Thread.sleep(1500);
					break;
					
				case 3: //Request: Project Title Change
					/*
					 * Case 3 enables user to request for a project title change.
					 * User is prompted to enter a new title.
					 */
					String newTitle;					
					cli.displayTitle("Request for Change of Project Title");
					if (studentModel.getProjectID() == -1 || studentModel.getProjectID() == 0 || studentModel.getProjectID() == -2) {
						cli.displayTitle("You are not registered for any project");
					}
					else {
						//if student already registered, proceed title change
						Project allocatedProject = Project.getProject(studentModel.getProjectID());
						cli.display("Your project title is : " + allocatedProject.getProjectTitle());
						newTitle = cli.inputString("Your new title to change (Enter 0 to exit)");
						Thread.sleep(1500);
						if (newTitle.equals("0")) {
							cli.displayTitle("Cancelling Project Title Change Request...");
							Thread.sleep(1500);
							break;
						}
						//creates a new request
						new Request(studentModel.getId(),studentModel.getName(),studentModel.getEmailAddress(),allocatedProject.getSupervisorId(),allocatedProject.getSupervisorName(),allocatedProject.getSupervisorEmail(),allocatedProject.getProjectId(),newTitle,RequestType_Enum.CHANGETITLE,RequestStatus_Enum.PENDING,Request.getRequests().size()+1);// send request to change title
						cli.displayTitle("Success, your request for changing title is now pending for approval by the supervisor");
						Database.updateAllData();
					}
						Thread.sleep(1500);
						break;
				
				case 4: //Request: Project Deregistration
					/*
					 * Case 4 enables user to request for a project deregistration.
					 * If request becomes approved, user will not be able to utilise most of the above functions as they are no longer registerd for FYP.
					 */
					cli.displayTitle("Deregistering Project");
					// check project id first
					cli.display("------------------------------------");
					if (studentModel.getProjectID() == -1 || studentModel.getProjectID() == 0 || studentModel.getProjectID() == -2) 
						cli.displayTitle("You are not registered for any projects");
					else {
						cli.display("Request to Deregister Project: " + Project.getProject(studentModel.getProjectID()).getProjectTitle());
						
						String menu_item[] = {
							"Confirm",
							"Back"
						};
						
						cli.display(menu_item);
						choice = cli.inputInteger("Choice", 1, menu_item.length);
						if (choice == 1) {
							Project allocatedProject = Project.getProject(studentModel.getProjectID());
							//creates a new request
							new Request(studentModel.getId(),studentModel.getName(),studentModel.getEmailAddress(),"ASFLI", "Li Fang", "ASFLI@ntu.edu.sg",allocatedProject.getProjectId(),RequestType_Enum.DEREGISTERPROJECT,RequestStatus_Enum.PENDING,Request.getRequests().size()+1);// send request to register
							//cli.displayTitle();
							Database.updateAllData();
							cli.displayTitle("Request to deregister project has been sent");
						}
						else {
							cli.displayTitle("Request to deregister project has been cancelled");
						}
					}
					Thread.sleep(1500);
					break;
					
				case 5: //View available projects
					/*
					 * User will be able to view all available projects
					 */
					cli.displayTitle("View all Available Projects");
					if (studentModel.getProjectID() == -2) {
						cli.displayTitle("You are not allowed to make selection again as you deregistered your FYP");
					}
					else if(studentModel.getProjectID() != -1 & studentModel.getProjectID() != 0) {
						cli.displayTitle("You are currently allocated to a FYP and do not have access to available project list");
					}
					
					else {
						ProjectView.projectAvailableInfo();
						Thread.sleep(1500);
						break;
					}
					
					Thread.sleep(1500);
					break;
					
					
				case 6: //View project details
					/*
					 * User is able to view their project details.
					 * If they have no projects or still waiting for allocation approval, the project details will not be displayed.
					 */
					if (studentModel.getProjectID() == -1 || studentModel.getProjectID() == -2) {
						cli.displayTitle("You have not registered for a project.");
					}
					else if (studentModel.getProjectID() == 0)
						cli.displayTitle("Your request to select project is pending. Please be patient!");
					else {
						cli.displayTitle("Details of your project: ");
						ProjectView.printProjectInfo(studentModel.getProjectID());
						Thread.sleep(1500);
						break;
					}
					Thread.sleep(1500);
					break;
				
				
				case 7: //View RequestHistory
					/*
					 * User is able to view his request history
					 */
					cli.displayTitle("View Request History");
					RequestView.printRequestHistory(studentModel.getId());
					Thread.sleep(1500);
					break;
					
				case 8: //View Profile
					/*
					 * Case 8 enables users to view their current profile information
					 */
					cli.displayTitle("View Profile");
					StudentView.printStudentRecordInfo(studentModel.getId(), studentModel.getName(), studentModel.getEmailAddress(), studentModel.getPassword());
					Thread.sleep(1500);
					break;
				
				case 9:
					cli.displayTitle("Logging out...");
					Thread.sleep(1500);
					return;
				
				default:
					return;
			}
		}
	}
}

