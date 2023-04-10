package controllers;

import java.util.ArrayList;

import views.ProjectView;
import views.RequestView;
import models.*;

public class FYPCoordinatorController extends Controller {
	
	
	private FYPCoordinator FYPCoordinatorModel;
	
	private ProjectView projView = new ProjectView();
	private RequestView reqView = new RequestView();
	
	private MainController mainController;
	
	public void run(User user) throws Throwable{

        if(user instanceof FYPCoordinator) {
		    FYPCoordinatorModel = (FYPCoordinator)user;
		}
            
		mainController = new MainController();

		String[] menu = {
                "Change Password ",
                "Create Project ",
				"Modify Project Title " ,
				"View All Projects ",
				"View Pending Requests ",
				"Approve Requests ",
				"View Request History ",
				"Generate Project Details Report (with filters)",
				"Logout"
		};
		
		int choice = 0;
		int confirmation;
		
		while(choice <= menu.length) {
			
			cli.displayTitle("FYPCOORDINATOR FUNCTIONS");
			cli.display(menu);
			
			choice = cli.inputInteger("choice", 1, menu.length);
			
			switch(choice) {
			
				case 1:
	
				    boolean isPasswordChanged = false;
				    int tries = 3;
	
				    while (tries > 0 && !isPasswordChanged) {
	
				        try {
				            String currentPass = cli.inputString("your current password: ");
	
				            if (!currentPass.equals(FYPCoordinatorModel.getPassword())) {
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
				             FYPCoordinatorModel.setPassword(newPass);
				            isPasswordChanged = true;
				            cli.display("Password has been changed successfully!");
	
				        } catch (Exception e) {
				            System.out.println("Error: " + e.getMessage() + ". Please try again.");
				            tries--;
				        }
				    }
					    
					break;
					// Create Project
				case 2:
					
					String supervisorName;
					String projectTitle;
					

					projectTitle = cli.inputString("Enter the title of the new project: ", "Eg. Molecular Genetics Studies");
					supervisorName = cli.inputString("Enter the name of Project Supervisor", "Eg. Chen Ching Bing");
					confirmation = cli.inputInteger("Confirm Choice? Enter: \n 1 to Confirm \n 2 to Cancel", 1, 2);
					
					if (confirmation == 2) {
						break; //break; //fypCoordinatorController.run();
					}
					
					new Project(supervisorName,projectTitle);
//					Project.updateFile(); // Update file
//					Supervior.updateFile(); // Update file
				
					cli.displayTitle("\nProject has been created successfully");
					cli.display("Current Project List");
					cli.display("------------------------------------");
			
					
					for (Project proj : Project.getProjectList()) {
						projView.printProjectInfo(proj.getProjectId());
						cli.display("----------------------------");
					}
					
					Thread.sleep(2000);
					break; //break; //fypCoordinatorController.run(); 
				
				// Modify project title
				case 3:
					String newTitle;
					
					cli.displayTitle("Modify Project Title");
					for (Project proj : Project.getProjectList()) {
						projView.printProjectInfo(proj.getProjectId());
						cli.display("----------------------------");
					}
					
					cli.display("Enter Project ID to change title:");
					choice = cli.inputInteger("choice", 1, Project.getProjectList().size()+1);


					newTitle = cli.inputString("Enter new title:","Eg. Molecular Genetics Studies");
					
					Project.getProject(choice).setProjectTitle(newTitle);
//					Project.updateFile(); // Update file
					
					cli.displayTitle("\nProject Name has been changed successfully");
					
					
					Thread.sleep(2000);
					break; //break; //fypCoordinatorController.run();
				
				// View all projects
				case 4:
					cli.displayTitle("View All Projects");
					for (Project proj : Project.getProjectList()) {
						projView.printProjectInfo(proj.getProjectId());
						cli.display("----------------------------");
					}
					
					Thread.sleep(2000);
					break; //fypCoordinatorController.run();
				
				// View pending requests
				case 5:
					cli.displayTitle("View All Pending Requests");
					
					for (Request req : Request.getRequests()) {
						if (req.getRequestStatus() == RequestStatus_Enum.PENDING) {
							reqView.printRequestInfo(req.getRequestID());
							cli.display("----------------------------");
						}
					}				
					
					Thread.sleep(2000);
					break; //fypCoordinatorController.run();
					
				// Approve requests
				case 6:
					
					String[] requestsMenu = {
							"Approve Project Allocation",
							"Approve Student Transfer " ,
							"Approve Title Change ",
							"Approve Supervisor Change ",
							"Approve Deregistration ",
							"Back"
					};
					
					choice = 0;
					while(choice <= requestsMenu.length) {
						
						cli.displayTitle("Approve Requests");
						cli.display(requestsMenu);
						
						choice = cli.inputInteger("choice", 1, requestsMenu.length);
						
						switch(choice) {
						
						// Approve Project Allocation
						case 1:
							
							cli.displayTitle("Approve Project Allocation");
							ArrayList<Integer> allocationRequestID = new ArrayList<>();
							cli.display("Pending Project Allocation Requests");
							cli.display("------------------------------------");
							for (Request req : Request.getRequests()) {
								if (req.getRequestStatus() == RequestStatus_Enum.PENDING) {
									if (req.getRequestType() == RequestType_Enum.REGISTERPROJECT) {
										reqView.printRequestInfo(req.getRequestID());
										allocationRequestID.add(req.getRequestID());
										cli.display("----------------------------");
									}
								}
							}	
							cli.displayTitle("Approve Project Allocation");
							choice = -1;
							while (!allocationRequestID.contains(choice)) {
								cli.displayTitle("Approve Project Allocation");
								choice = cli.inputInteger("Enter Request ID (Enter 0 to exit): ", 0, Request.getRequests().size());
								if (choice == 0) {
									break; //fypCoordinatorController.run();
								}
							}
							cli.display("Enter 1 to Approve Request \nEnter 2 to Reject Request");
							confirmation = cli.inputInteger("choice", 1, 2);
							if (confirmation == 1) {
								Request.getRequest(choice-1).approve();
								// Update student projectID
								Student.setProjectID(Request.getRequest(choice-1).getProjectID());
								// Update project
								Project.getProject(Request.getRequest(choice-1).getProjectID()-1).setStudent(Request.getRequest(choice-1).getSenderID(),"StudentName","StudentEmail",ProjectStatus_Enum.ALLOCATED);
//								Request.updateFile(); // Update file
//								Student.updateFile(); // Update file
//								Project.updateFile(); // Update file
							}
							else {
								Request.getRequest(choice-1).reject();
								Student.setProjectID(-1);
								Project.getProject(choice).setProjectStatus(ProjectStatus_Enum.AVAILABLE);
//								Request.updateFile(); // Update file
//								Student.updateFile(); // Update file
//								Project.updateFile(); // Update file
							}
							
							
							break; //fypCoordinatorController.run();
							
	//INCOMEPLETE CODE INCOMEPLETE CODE INCOMEPLETE CODE INCOMEPLETE CODE INCOMEPLETE CODE INCOMEPLETE CODE INCOMEPLETE CODE INCOMEPLETE CODE

							// Approve Title Change
							case 2:
								cli.displayTitle("Approve Title Change");
								choice = cli.inputInteger("Enter Request ID: ", 1, Request.getRequests().size());
								cli.display("Pending Title Change Requests");
								cli.display("------------------------------------");
								for (Request req : Request.getRequests()) {
									if (req.getRequestStatus() == RequestStatus_Enum.PENDING) {
										if (req.getRequestType() == RequestType_Enum.CHANGETITLE) {
											reqView.printRequestInfo(req.getRequestID());
											cli.display("----------------------------");
										}
									}
								}	
								

								
								break; //fypCoordinatorController.run();
							// Approve Supervisor Change 
							case 3:
								cli.displayTitle("Approve Supervisor Change");
								choice = cli.inputInteger("Enter Request ID: ", 1, Request.getRequests().size());
								cli.display("Pending Supervisor Change Requests");
								cli.display("------------------------------------");
								for (Request req : Request.getRequests()) {
									if (req.getRequestStatus() == RequestStatus_Enum.PENDING) {
										if (req.getRequestType() == RequestType_Enum.CHANGESUPERVISOR) {
											reqView.printRequestInfo(req.getRequestID());
											cli.display("----------------------------");
										}
									}
								}	
								
								break; //fypCoordinatorController.run();
							// Approve Deregistration 
							case 4:
								cli.displayTitle("Approve Deregistration");
								choice = cli.inputInteger("Enter Request ID: ", 1, Request.getRequests().size());
								cli.display("Pending Deregistration Requests");
								cli.display("------------------------------------");
								for (Request req : Request.getRequests()) {
									if (req.getRequestStatus() == RequestStatus_Enum.PENDING) {
										if (req.getRequestType() == RequestType_Enum.DEREGISTERPROJECT) {
											reqView.printRequestInfo(req.getRequestID());
											cli.display("----------------------------");
										}
									}
								}	
								
								break; //fypCoordinatorController.run();
							// Back	
							case 6:
							
							break; //fypCoordinatorController.run();
							
	// INCOMEPLETE CODE INCOMEPLETE CODE INCOMEPLETE CODE INCOMEPLETE CODE INCOMEPLETE CODE INCOMEPLETE CODE INCOMEPLETE CODE INCOMEPLETE CODE 	
						}
					}

					break; //fypCoordinatorController.run();
							
					
					
					
					Thread.sleep(2000);
					break; //fypCoordinatorController.run();

				// View request history	
				case 7:
					
					cli.displayTitle("View Request History");
					
					for (Request req : Request.getRequests()) {
						reqView.printRequestInfo(req.getRequestID());
						cli.display("----------------------------");
					}
		
					break; //fypCoordinatorController.run();
					
		
				// Generate Project Details Report (with filters)	
				case 8:

					
				case 9:
					cli.display("Logging out...");
					return;
									
				default:
				mainController.run(user);
			}
		}
	}
}


