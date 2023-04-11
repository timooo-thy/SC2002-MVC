package controllers;

import java.util.ArrayList;

import models.Project;
import models.ProjectStatus_Enum;
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
				"View Project created",
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
				            String currentPass = cli.inputString("Your current password: ");
	
				            String newPass = cli.inputString("Your new password: ");
	
				            String confirmPass = cli.inputString("Password to reconfirm: ");
	
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
					String Menu = {
							"Create a project",
							"Back"
					};
					
					choice = 0;
					
					while (choice<=Menu.length) {
						cli.displayTitle("Create New Project Menu");
						cli.display("------------------------------------");
						cli.display(Menu);
						choice = cli.inputInteger("choice",1,Menu.length);
						
						switch (choice) {
							case 1:
								cli.displayTitle("Create a Project");
								String projectTitle;
								cli.display("Please enter the Project Title: ");
								projectTitle = cli.inputString("Project Title: ");
								new Project(supervisorModel.getName(),projectTitle,ProjectStatus_Enum.AVAILABLE);
								
								cli.displayTitle("Project has been added successfully");
								Project.updateProjectFile();
								Thread.sleep(3000);
								break;
							case 2:
								break;
							default:
								break;						
						}
					}
					
				
				case 3:
					//Modify own project title
					
					String Menu = {
							"Modify own project title",
							"Back"
					};
					
					choice = 0;
					
					while (choice<=Menu.length) {
						cli.displayTitle("Modify Own Project Title Menu");
						cli.display("------------------------------------");
						cli.display(Menu);
						choice = cli.inputInteger("choice",1,Menu.length);
						
						switch (choice) {
							case 1:
								cli.displayTitle("Modify Own Project Title");
							     ArrayList<Integer> allocationProjectID = new ArrayList<>();
							     
							     for (Project proj : Project.getProjectList()) {
							      if (proj.getSupervisorId().equals(supervisorModel.getId())) {
							       ProjectView.printProjectInfo(proj.getProjectId());
							       allocationProjectID.add(proj.getProjectId());
							       cli.display("----------------------------");
							      }
							     }
							     
							     if(allocationProjectID.size()==0) {
							      break;
							     }
							     
							     int id = cli.inputInteger("Choose Project ID to modify Project Title");
							     String newtitle = cli.inputString("new Project Title");
							     Project.changeProjectTitle(id, newtitle);
							     cli.displayTitle("\nPROJECT TITLE HAS BEEN UPDATED");
							     Project.updateProjectFile();
							     Thread.sleep(3000);
							     break;
							case 2:
								break;
							default:
								break;
						}
					}
				
				case 4:
					// View supervised project
					cli.displayTitle("View Supervised Projects");
					if (supervisorModel.getSupervisedProjectList().size() == 0) {
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
					//View project Created
					cli.displayTitle("View Projects Created");
					for (Project proj : Project.getProjectList()) {
						if (proj.getSupervisorId().equals(supervisorModel.getId())) {
							ProjectView.printProjectInfo(proj.getProjectId());
						}
					}
					Thread.sleep(3000);
					break;
					
				case 6:
					//approve/reject title change requests
					String Menu = {
							"Approve/Reject a request",
							"Back"
					};
					
					choice = 0;
					
					while (choice<=Menu.length) {
						cli.displayTitle("Approve/Reject Title change Requests Menu");
						cli.display("------------------------------------");
						cli.display(Menu);
						choice = cli.inputInteger("choice",1,Menu.length);
						
						switch(choice) {
							case 1:
								cli.displayTitle("Approve/reject title change requests");
								choice = cli.inputInteger("Enter request ID: ", 1, Request.getRequests().size());
								cli.display("Pending title change requests");
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
										int selection = cli.inputInteger("Select request", 1, count);
										int choice2 = cli.inputInteger("(1) Approve\n(2)Reject\n(3)Back ", 1, 3);
										if (choice2==1) {
											Project.changeProjectTitle(Request.getRequest(selection).getProjectID(),Request.getRequest(selection).getNewProjectTitle());
											Request.getRequest(selection).setRequestStatus(RequestStatus_Enum.APPROVED);
											cli.displayTitle("Request approved");
											cli.displayTitle("Project title has been updated");
											Thread.sleep(3000);
											break;

										}
										else if (choice2==2) {
											Request.getRequest(selection).setRequestStatus(RequestStatus_Enum.REJECTED);
											cli.displayTitle("Request rejected");
											Thread.sleep(3000);
											break;
										}
										else if (choice2==3) {
											cli.displayTitle("Returning to main page...");
											Thread.sleep(3000);
											break;
										}
									}
							case 2:
								break;
							default:
								break;
								
						}
						
					}
					
					
				case 7:
					//Request FYP coordinator to change supervisor in charge
					String Menu = {
							"Make a request",
							"Back"
					};
					
					choice = 0;
					
					while (choice<=Menu.length) {
						cli.displayTitle("Request to Change Supervisor in Charge Menu");
						cli.display("------------------------------------");
						cli.display(Menu);
						
						choice = cli.inputInteger("choice",1,Menu.length);
						
						switch (choice) {
							case 1:
								cli.displayTitle("Request to change supervisor in charge");
								int projectID = cli.inputInteger("Enter project ID");      
							    String newSupervisorID = cli.inputString("Enter the Replacement Supervisor ID");
							    new Request(supervisorModel.getId(),supervisorModel.getName(),supervisorModel.getEmailAddress(), "ASFLI", "Li Fang", "ASFLI@ntu.edu.sg",projectID,newSupervisorID,Supervisor.getSupervisorIdToName(newSupervisorID),Supervisor.getSupervisorIdToEmail(newSupervisorID),RequestType_Enum.CHANGESUPERVISOR,RequestStatus_Enum.PENDING,Request.getRequests().size()+1);
							    // Request.updateFile(); // Update file
								cli.displayTitle("Request has been sent");
								Thread.sleep(3000);
								break;
							case 2:
								break;
							default:
								break;
						}
					}
					
				case 8: 
					//View Incoming and Outgoing Request History and Status
					String[] historyMenu = {
							"View incoming requests history and status",
							"View outgoing requests history and status",
							"Back"
					};
					
					choice = 0;
					
					while (choice<=historyMenu.length) {
						cli.displayTitle("View Incoming and Outgoing Request History and Status");
						cli.display(historyMenu);
						
						choice = cli.inputInteger("choice", 1, historyMenu.length);
					
					
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
						case 3:
							break;
						default: 
							break;
					}
				}
					
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

