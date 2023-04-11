package controllers;

import java.util.ArrayList;

import views.ProjectView;
import views.RequestView;
import models.*;

public class FYPCoordinatorController extends Controller {
	
	private FYPCoordinator FYPCoordinatorModel;
	
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
					
					//new Project(supervisorName, projectTitle);
//					Project.updateProjectFile(); // Update file
//					Supervior.updateFile(); // Update file
				
					cli.displayTitle("\nProject has been created successfully");
					cli.display("Current Project List");
					cli.display("------------------------------------");
			
					
					for (Project proj : Project.getProjectList()) {
						ProjectView.printProjectInfo(proj.getProjectId());
						cli.display("----------------------------");
					}
					
					Thread.sleep(3000);
					break; //break; //fypCoordinatorController.run(); 
				
				// Modify project title
				case 3:
					String newTitle;
					
					cli.displayTitle("Modify Project Title");
					for (Project proj : Project.getProjectList()) {
						ProjectView.printProjectInfo(proj.getProjectId());
						cli.display("----------------------------");
					}
					
					cli.display("Enter Project ID to change title:");
					choice = cli.inputInteger("choice", 1, Project.getProjectList().size()+1);


					newTitle = cli.inputString("Enter new title:","Eg. Molecular Genetics Studies");
					
					Project.getProject(choice).setProjectTitle(newTitle);
//					Project.updateProjectFile(); // Update file
					
					cli.displayTitle("\nProject Name has been changed successfully");
					
					
					Thread.sleep(3000);
					break; //break; //fypCoordinatorController.run();
				
				// View all projects
				case 4:
					cli.displayTitle("View All Projects");
					for (Project proj : Project.getProjectList()) {
						ProjectView.printProjectInfo(proj.getProjectId());
						cli.display("----------------------------");
					}
					
					Thread.sleep(3000);
					break; //fypCoordinatorController.run();
				
				// View pending requests
				case 5:
					cli.displayTitle("View All Pending Requests");
					
					for (Request req : Request.getRequests()) {
						if (req.getRequestStatus() == RequestStatus_Enum.PENDING) {
							RequestView.printRequestInfo(req.getRequestID());
							cli.display("----------------------------");
						}
					}				
					
					Thread.sleep(3000);
					break; //fypCoordinatorController.run();
					
				// Approve requests
				case 6:
					
					String[] requestsMenu = {
							"Approve Project Allocation",
							"Approve Title Change ",
							"Approve Supervisor Change ",
							"Approve Deregistration ",
							"Back"
					};
					
					choice = 0;
					while(choice <= requestsMenu.length) {
						if(choice==5)
							break;
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
										RequestView.printRequestInfo(req.getRequestID());
										allocationRequestID.add(req.getRequestID());
										cli.display("----------------------------");
									}
								}
							}	
							
							choice = -1;
							while (!allocationRequestID.contains(choice)) {
								cli.displayTitle("Approve Project Allocation");
								choice = cli.inputInteger("Enter Request ID (Enter 0 to exit): ", 0, Request.getRequests().size());
								if (choice == 0) {
									break; 
								}
							}
							// Exit if Coordinator chose to quit
							if (choice == 0) {
								break; 
							}
							// Else continue with approval
							String studentID = Request.getRequest(choice).getSenderID();
							String studentName = Request.getRequest(choice).getSenderName();
							String studentEmail = Request.getRequest(choice).getSenderEmail();
							int projectID = Request.getRequest(choice).getProjectID();
							
							// Confirmation for approval
							confirmation = RequestView.requestConfirmation();
							
							if (confirmation == 0) break;
							else if (confirmation == 1) {
								// Approve Request
								Request.getRequest(choice).approve();
								// Update student projectID
								Student.getStudentFromID(studentID).setProjectID(projectID);
								
								// Update student's details on project and change status to ALLOCATED
								Project.allocateProject(projectID, studentID);
//								Project.getProject(projectID).setStudent(studentID,studentName,studentEmail);
//								Project.getProject(projectID).setProjectStatus(ProjectStatus_Enum.ALLOCATED);
								
								Request.updateRequestFile(); // Update file
//								Student.updateFile(); // Update file
//								Project.updateProjectFile(); // Update file
							}
							else {
								// Reject Request
								Request.getRequest(choice).reject();
								// Update student projectID
								Student.getStudentFromID(studentID).setProjectID(-1);
								// Make Project available again after rejecting request
								Project.getProject(choice).setProjectStatus(ProjectStatus_Enum.AVAILABLE);
//								Request.updateRequestFile(); // Update file
//								Student.updateFile(); // Update file
//								Project.updateProjectFile(); // Update file
							}
							
							break; //fypCoordinatorController.run();
							
	//INCOMEPLETE CODE INCOMEPLETE CODE INCOMEPLETE CODE INCOMEPLETE CODE INCOMEPLETE CODE INCOMEPLETE CODE INCOMEPLETE CODE INCOMEPLETE CODE

							// Approve Title Change
							case 2:
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
								cli.displayTitle("Approve Title Change");
								int requestID = RequestView.requestRequestID();
								if (requestID == 0) break;

								confirmation = RequestView.requestConfirmation();
								
								projectID = Request.getRequest(requestID).getProjectID();
								String newProjectTitle = Request.getRequest(requestID).getNewProjectTitle();
								
								// Approve
								if (confirmation == 1) {
									// Approve Request
									Request.getRequest(choice).approve();
									// Update project title
									Project.changeProjectTitle(projectID, newProjectTitle);
									//Project.getProject(projectID).setProjectTitle(newProjectTitle);
								}
								// Reject
								else {
									// Reject Request
									Request.getRequest(choice).reject();
								}
								
								
								
								break; //fypCoordinatorController.run();
							// Approve Supervisor Change 
							case 3:
								cli.display("Pending Supervisor Change Requests");
								cli.display("------------------------------------");
								for (Request req : Request.getRequests()) {
									if (req.getRequestStatus() == RequestStatus_Enum.PENDING) {
										if (req.getRequestType() == RequestType_Enum.CHANGESUPERVISOR) {
											RequestView.printRequestInfo(req.getRequestID());
											cli.display("----------------------------");
										}
									}
								}
								cli.displayTitle("Approve Supervisor Change");
								
								requestID = cli.inputInteger("Enter Request ID: ", 1, Request.getRequests().size());
								if (requestID == 0) break;

								confirmation = RequestView.requestConfirmation();
								
								projectID = Request.getRequest(requestID).getProjectID();
								String newSupervisorID = Request.getRequest(requestID).getNewSupervisorID();
//								String newSupervisorName = Request.getRequest(requestID).getNewSupervisorName();
//								String newSupervisorEmail = Request.getRequest(requestID).getNewSupervisorEmail();
								

								// Approve
								if (confirmation == 1) {
									// Approve Request
									Request.getRequest(choice).approve();
									// Update project new supervisor
//									Project.getProject(projectID).setSupervisorId(newSupervisorID);
//									Project.getProject(projectID).setSupervisorName(newSupervisorName);
//									Project.getProject(projectID).setSupervisorEmail(newSupervisorEmail);
									Project.changeSupervisor(projectID, newSupervisorID);
									
									
								}
								// Reject
								else {
									// Reject Request
									Request.getRequest(choice).reject();
								}
								
								break; //fypCoordinatorController.run();
							// Approve Deregistration 
							case 4:
								cli.display("Pending Deregistration Requests");
								cli.display("------------------------------------");
								for (Request req : Request.getRequests()) {
									if (req.getRequestStatus() == RequestStatus_Enum.PENDING) {
										if (req.getRequestType() == RequestType_Enum.DEREGISTERPROJECT) {
											RequestView.printRequestInfo(req.getRequestID());
											cli.display("----------------------------");
										}
									}
								}	
								cli.displayTitle("Approve Deregistration");
								requestID = cli.inputInteger("Enter Request ID: ", 1, Request.getRequests().size());
								confirmation = RequestView.requestConfirmation();
								
								projectID = Request.getRequest(requestID).getProjectID();
								studentID = Request.getRequest(requestID).getSenderID();
								
								if (confirmation == 0) break;
								if (confirmation == 1) {
									Request.getRequest(requestID).approve();
									// Deregister student from project
									Project.deregisterStudent(projectID);
									// Update projectID field on Student 
									Student.getStudentFromID(studentID).setProjectID(-1);
								}
								else {
									Request.getRequest(requestID).reject();
								}
								
								Thread.sleep(3000);
								break; //fypCoordinatorController.run();
							// Back	
							case 5:
								break; //fypCoordinatorController.run();
							
						}
					}		
					
					
					break; //fypCoordinatorController.run();

				// View request history	
				case 7:
					
					cli.displayTitle("View Request History");
					
					for (Request req : Request.getRequests()) {
						RequestView.printRequestInfo(req.getRequestID());
						cli.display("----------------------------");
					}
		
					break; //fypCoordinatorController.run();
					
		
				// Generate Project Details Report (with filters)	
				case 8:
					cli.displayTitle("Generate Project Details Report(with filters)");
					
					String[] projectMenu = {
							"filter with supervisor ID",
							"filter with project status ",
							"Back"
					};
					int innerChoice = -1;
					while(innerChoice <= projectMenu.length) {
						if(innerChoice==3)
							break;
						cli.display(projectMenu);
						
						innerChoice = cli.inputInteger("choice", 1, projectMenu.length);
						switch(innerChoice) {
						case 1:
							String tempSupervisorId = cli.inputString("Please enter supervisor ID");
							cli.displayTitle("Generating project details ");
							for (Project proj : Project.getProjectList()) {
								if (proj.getSupervisorId().equals(tempSupervisorId)) {
									ProjectView.printProjectInfo(proj.getProjectId());
								}
							}
							cli.display("----------------------------");
							Thread.sleep(3000);
							break;
						case 2:
							cli.display("Please choose project status: ");
							cli.display("   1: AVAILABLE");
							cli.display("   2: REVERSED");
							cli.display("   3: ALLOCATED");
							cli.display("   4: UNAVAILABLE");
							cli.display("   5: BACK");
							innerChoice = cli.inputInteger("choice", 1, 5);
//							do {
								if (innerChoice == 1) {
									cli.displayTitle("Generating project details for project status AVAILABLE");
									for (Project proj : Project.getProjectList()) {
										if (proj.getProjectStatus()==(ProjectStatus_Enum.AVAILABLE)) {
											ProjectView.printProjectInfo(proj.getProjectId());
										}
									}
									Thread.sleep(3000);
									break;
								}
								if (innerChoice == 2){
									cli.displayTitle("Generating project details for project status RESERVED");
									for (Project proj : Project.getProjectList()) {
										if (proj.getProjectStatus()==(ProjectStatus_Enum.RESERVED)) {
											ProjectView.printProjectInfo(proj.getProjectId());
										}
									}
									Thread.sleep(3000);
									break;
								}
								if (innerChoice == 3){
									cli.displayTitle("Generating project details for project status ALLOCATED");
									for (Project proj : Project.getProjectList()) {
										if (proj.getProjectStatus()==(ProjectStatus_Enum.ALLOCATED)) {
											ProjectView.printProjectInfo(proj.getProjectId());
										}
									}
									Thread.sleep(3000);
									break;
								}
								if (innerChoice == 4){
									cli.displayTitle("Generating project details for project status UNAVAILABLE");
									for (Project proj : Project.getProjectList()) {
										if (proj.getProjectStatus()==(ProjectStatus_Enum.UNAVAILABLE)) {
											ProjectView.printProjectInfo(proj.getProjectId());
										}
									}
									Thread.sleep(3000);
									break;
								}
								if (innerChoice == 5)
									break;
//							}while (innerChoice < 6 && innerChoice > 0);
						}
					}
					break;
					
				case 9:
					cli.display("Logging out...");
					return;
									
				default:
					return;
			}
		}
	}
}


