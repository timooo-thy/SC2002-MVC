package controllers;

import models.Student;
import models.User;

/**
 * Represents the Student Controller
 */
public class StudentController extends Controller {
	
	private Student studentModel;
	
	private MainController mainController;
	
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
                "6. View Available Projects",
                "7. View Request History",
                "8. Back"
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
					projectStatus_Enum projectStatus;
					if (studentModel.getProjectID() != -1) {
						cli.display("You are already registered for a project.");	
					}
					else if (studentModel.getProjectID() != 0) {
						cli.display("You already have a pending request for registration.");
					}
					else {
						cli.display("Enter the project ID to register for:");
						do {
							projectChoice = cli.inputInteger("Project ID", 1, ProjectDirectory.getProjectDirectory().size());
							projectStatus = ProjectDirectory.getProjectDirectory().get(projectChoice-1).getProjectStatus();
							if (projectStatus == projectStatus_Enum.AVAILABLE) {
								RequestDirectory.createRequest(studentModel.getuserID(),"ASFLI",RequestType_Enum.REGISTERPROJECT, null, null);// send request
								ProjectDirectory.getProject(projectChoice-1).setProjectStatus(projectStatus_Enum.RESERVED);// change project status to reserved
							}
						} while (projectStatus != projectStatus_Enum.AVAILABLE);
						cli.displayTitle("SUCCESS, YOUR REGISTRATION IS NOW PENDING FOR APPROVAL BY THE COORDINATOR");
//						Request.updateFile(); // Update file
//						Project.updateFile(); // Update file
//						Student.updateFile(); // Update file
					}
					Thread.sleep(1000);
					studentController.run(); 
					
				case 4: //Request: Project Title Change
					String newTitle;
					
					cli.displayTitle("Request for Change of Project Title");
					if (studentModel.getProjectID() == -1 || studentModel.getProjectID() == 0) {
						cli.display("You are not registered for any projects.");
						Thread.sleep(3000);
						studentController.run();
					}
					//if student already registered, proceed title change
					cli.display("Your project title is : " + ProjectDirectory.getProject(Student.getProjectID()-1).getProjectTitle());
					newTitle = cli.inputString("What would you like to change it to?","\n");
					RequestDirectory.createRequest(studentModel.getuserID(), ProjectDirectory.getProject(studentModel.getProjectID()-1).getSupervisorID(), RequestType_Enum.CHANGETITLE, newTitle, null); // Create change title request
//					Project.updateFile(); // Update file
					Thread.sleep(3000);
					studentController.run();
				
				case 5: //Request: Project Deregistration
					cli.displayTitle("Deregistering Project");
					cli.display("Request to deregister project: " + ProjectDirectory.getProject(Student.getProjectID()-1).getProjectTitle());
					cli.display("Enter 1 to confirm, 2 to exit. "); 
					choice = cli.inputInteger("choice", 1, 2);
					if (choice == 1) {
						RequestDirectory.createRequest(studentModel.getuserID(), "ASFLI", RequestType_Enum.DEREGISTERPROJECT, null, null); // Create change title request
//						Request.updateFile(); // Update file
					}
					else {
						cli.display("Request Cancelled");
					}
					
					Thread.sleep(3000);
					studentController.run();
				case 6: //View available projects
					cli.displayTitle("View all available Projects");
					for (Project proj : ProjectDirectory.getProjectDirectory()) {
						if (proj.getProjectStatus() == projectStatus_Enum.AVAILABLE) {
							proj.projectDetails();
						}
					}

					Thread.sleep(3000);
					studentController.run();
				
				case 7: //View RequestHistory
					cli.displayTitle("View Request History");
					RequestDirectory.viewRequestHistory(studentModel.getuserID(),UserType_Enum.STUDENT);
//					for (Request req : RequestDirectory.getRequestDirectory()) {
//						req.viewRequest();
//					}

					Thread.sleep(3000);
					studentController.run();
					
				default:
				mainController.run(user);
			}
		}
	}
}


