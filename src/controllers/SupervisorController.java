package controllers;

import java.util.ArrayList;

import models.Project;
import models.Request;
import models.RequestStatus_Enum;
import models.Supervisor;
import models.User;
import models.RequestType_Enum;
import views.ProjectView;
import views.RequestView;


public class SupervisorController extends Controller {
	
	
	private Supervisor supervisorModel;
	
	private MainController mainController;
	
	public void run(User user) throws Throwable{

        if(user instanceof Supervisor) {
		    supervisorModel = (Supervisor)user;
		}
            
		mainController = new MainController();

		String[] menu = {
                "1. Change Password ",
				"2. Add a new project " ,
				"3. View project created by you ",
				"4. View superivsed project ",
				"5. Modify project title upon request ",
				"6. Request FYP coordinator to change supervisor in charged ",
                "7. View pending request ",
                "8. View request history and status ",
				"9. Logout "
		};
		
		int choice = 0;
		
		while(choice <= menu.length) {
			
			cli.displayTitle("SUPERVISOR FUNCTIONS");
			cli.display(menu);
			
			choice = cli.inputInteger("choice", 1, menu.length);
			
			switch(choice) {
			
				case 1:
					//Change Password
				    boolean isPasswordChanged = false;
				    int tries = 3;
	
				    while (tries > 0 && !isPasswordChanged) {
	
				        try {
				            String currentPass = cli.inputString("your current password: ");
	
				            if (!currentPass.equals(supervisorModel.getPassword())) {
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
				             supervisorModel.setPassword(newPass);
				            isPasswordChanged = true;
				            cli.display("Password has been changed successfully!");
	
				        } catch (Exception e) {
				            System.out.println("Error: " + e.getMessage() + ". Please try again.");
				            tries--;
				        }
				    }
					    
					break;
					
				case 2:
					//Add a new project
					String projectTitle;
					cli.display("Please enter the Project title");
					projectTitle = cli.inputString("project title");
					//Project.addProject(new Project(supervisorModel.getName(), projectTitle));
					
					cli.displayTitle("\nPROJECT HAS BEEN ADDED SUCCESSFULLY");
					//Project.updateFile();
					Thread.sleep(3000);
					break;
					
				case 3:
					//View project submitted by you
					cli.displayTitle("View project created by you");
					for (Project proj : Project.getProjectList()) {
						if (proj.getSupervisorName() == supervisorModel.getName()) 
							ProjectView.printProjectInfo(proj.getProjectId());
					}
					
					cli.displayTitle("\nPROJECT LIST HAS BEEN PRINTED");
					Thread.sleep(3000);
					break;
				
				case 4:
					//View supervised project
					cli.displayTitle("View supervised project");
					if (supervisorModel.getSupervisedProjectList() == null) {
						cli.display("Currectly not supervising any project");
						Thread.sleep(3000);
						break;
					}
					else {
						for (Project proj : supervisorModel.getSupervisedProjectList()) {
							ProjectView.printProjectInfo(proj.getProjectId());
						}
						Thread.sleep(3000);
						break;
					}
					
				case 5:
					//Modify project title upon request
					cli.displayTitle("Changing project title upon student request");
					cli.displayTitle("Approve Title Change");
					choice = cli.inputInteger("Enter Request ID: ", 1, Request.getRequests().size());
					cli.display("Pending Title Change Requests");
					cli.display("------------------------------------");
					for (Request req : Request.getRequests()) {
						if (req.getRequestStatus() == RequestStatus_Enum.PENDING) {
							if (req.getRequestType() == RequestType_Enum.CHANGETITLE) {
								RequestView.printRequestInfo(req.getRequestID());
								cli.display("----------------------------");
							}
						}
					}	
					
					cli.displayTitle("\nPROJECT TITLE HAS BEEN UPDATED");
					Thread.sleep(3000);
					break;
					
				case 6:
					//Request FYP coordinator to change supervisor in charged
					cli.displayTitle("Sending request to change supervisor in charged");
					
					cli.display("Enter project ID");
					int projectID = cli.inputInteger("project ID");      
				    cli.display("Enter the replacement supervisorID: ");
				    String newSupervisorID = cli.inputString("supervisor ID");
				    new Request(supervisorModel.getId(),supervisorModel.getName(),supervisorModel.getEmailAddress(), "ASFLI", "ASFLI", "ASFLI",projectID,newSupervisorID,RequestType_Enum.CHANGESUPERVISOR,RequestStatus_Enum.PENDING,Request.getRequests().size());
					
				    // Request.updateFile(); // Update file
					cli.displayTitle("\nREQUEST HAS BEEN SENT");
					Thread.sleep(3000);
					break;
					
				case 7:
					//View pending request
					cli.displayTitle("View pending request");
					//RequestDirectory.getIncomingRequests(UserType_Enum.SUPERVISOR);
					for (Request req : Request.getRequests()) {
						if (req.getRequestType() == RequestType_Enum.CHANGETITLE && req.getRequestStatus() == RequestStatus_Enum.PENDING) {
							RequestView.printRequestInfo(req.getRequestID());
							cli.display("----------------------------");
						}
					}	
					Thread.sleep(3000);
					break;
					
				case 8:
					//View request history and status
					cli.displayTitle("View Request History");
					//RequestDirectory.viewRequestHistory(supervisorModel.getuserID(),UserType_Enum.SUPERVISOR);
						RequestView.printRequestHistory(supervisorModel.getId());
						cli.display("----------------------------");
						Thread.sleep(3000);
					break;
					
				case 9:
					cli.display("Logging out...");
					Thread.sleep(3000);
					return;
									
				default:
				mainController.run(supervisorModel);
			}
		}
	}
}


