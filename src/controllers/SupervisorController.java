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

		
		String[] menu = { //change to similar structure to fypcoordinatorcontroller
//                "Change Password ",
//				"Add a new project " , case1
//				"View project created by you ", case2
//				"View superivsed project ", case3
//				"Modify project title upon request ", APPROVE REQ case 4
//				"Request FYP coordinator to change supervisor in charged ", SEND REQ case 5
//               "View pending request ", case 6
//                "View request history", case 7
//				"Logout " case 8
				"Change Password",//done
				"Create Project",//done
				"Modify Own Project Title", //done
				"View Supervised Projects", //done
				"Approve/Reject Title Change Requests (if any)", //done
				"Request to Change Supervisor", //done
				"View Incoming and Outgoing Request History and Status", //doing
				"Logout" //done
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
					cli.display("Please enter the Project title: ");
					projectTitle = cli.inputString("Project title: ");
					//Project.addProject(new Project(supervisorModel.getName(), projectTitle));
					
					cli.displayTitle("\nPROJECT HAS BEEN ADDED SUCCESSFULLY");
					//Project.updateFile();
					Thread.sleep(3000);
					break;
					
				
				case 3:
					//Modify own project title
					cli.displayTitle("Modify Own Project Title");
					if (supervisorModel.getSupervisedProjectList() == null) {
						cli.display("Currectly not supervising any project, unable to modify own project title");
						Thread.sleep(3000);
						break;
					}
					else {
						int count=0;
						for (Project proj : supervisorModel.getSupervisedProjectList()) {
							ProjectView.printProjectInfo(proj.getProjectId());
							count++;
						}
						int id = cli.inputInteger("Choose projectId to modify project title", 1, count);
						String newtitle = cli.inputString("Enter new project title");
						Project.changeProjectTitle(id, newtitle);
						cli.displayTitle("\nPROJECT TITLE HAS BEEN UPDATED");
						Thread.sleep(3000);
						break;
					}
				
				case 4:
					//View supervised project/view projects created
					cli.displayTitle("View supervised projects");
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
					//approve/reject title change requests
					cli.displayTitle("Approve Title Change Requests");
					choice = cli.inputInteger("Enter Request ID: ", 1, Request.getRequests().size());
					cli.display("Pending Title Change Requests");
					cli.display("------------------------------------");
					int count=0;
					for (Request req : Request.getRequests()) {
						if (req.getRequestStatus() == RequestStatus_Enum.PENDING && req.getRequestType() == RequestType_Enum.CHANGETITLE) {
							RequestView.printRequestInfo(req.getRequestID());
							cli.display("----------------------------");
							count++;
						}
					}
						if (count==0) {
							cli.display("There are no pending requests");
							Thread.sleep(3000);
							break;
						}
						else {
							int selection = cli.inputInteger("Select request: ", 1, count);
							int choice2 = cli.inputInteger("(1) Approve\n(2)Reject\n(3)Back ", 1, 3);
							if (choice2==1) {
								Project.changeProjectTitle(Request.getRequest(selection).getProjectID(),Request.getRequest(selection).getNewProjectTitle());
								Request.getRequest(selection).setRequestStatus(RequestStatus_Enum.APPROVED);
								cli.displayTitle("Request Approved");
								cli.displayTitle("Project Title has been updated");
								Thread.sleep(3000);
								break;

							}
							else if (choice2==2) {
								Request.getRequest(selection).setRequestStatus(RequestStatus_Enum.REJECTED);
								cli.displayTitle("Request Rejected");
								Thread.sleep(3000);
								break;
							}
							else if (choice2==3) {
								cli.displayTitle("Returning to main page...");
								Thread.sleep(3000);
								break;
							}
						}
					
				case 6:
					//Request FYP coordinator to change supervisor in charge
					cli.displayTitle("Request to change supervisor in charge");
					
					int projectID = cli.inputInteger("Enter project ID");      
				    String newSupervisorID = cli.inputString("Enter the replacement supervisor ID");
				    new Request(supervisorModel.getId(),supervisorModel.getName(),supervisorModel.getEmailAddress(), "ASFLI", "Li Fang", "ASFLI@ntu.edu.sg",projectID,newSupervisorID,Project.getSupervisorIdToName(newSupervisorID),Project.getSupervisorIdToEmail(newSupervisorID),RequestType_Enum.CHANGESUPERVISOR,RequestStatus_Enum.PENDING,Request.getRequests().size());
				    // Request.updateFile(); // Update file
					cli.displayTitle("REQUEST HAS BEEN SENT");
					Thread.sleep(3000);
					break;
					
				case 7: 
					//View Incoming and Outgoing Request History and Status
					String[] historyMenu = {
							"View incoming requests history and status",
							"View outgoing requests history and status",
							"Back to main menu"
					};
					
					choice = 0;
					
					while (choice<=historyMenu.length) {
						if (choice==3) {
							break;
						}
						cli.displayTitle("View Incoming and Outgoing Request History and Status");
						cli.display(historyMenu);
						
						choice = cli.inputInteger("choice",1,historyMenu.length);
					}
					
					switch(choice) {
					case 1:
						cli.displayTitle("View Incoming Request History"); //pass in ???
						RequestView.printIncomingRequestHistory(supervisorModel.getId());	
						Thread.sleep(3000);
						break;
					case 2:
						cli.displayTitle("View Outgoing Request History");
						RequestView.printRequestHistory(supervisorModel.getId());
						Thread.sleep(3000);
						break;
					default: 
						return;
					}
					
				case 8:
					cli.display("Logging out...");
					Thread.sleep(3000);
					return;
									
				default:
					return;
			}
		}
	}
}

