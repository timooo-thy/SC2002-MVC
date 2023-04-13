package controllers;

import java.util.ArrayList;

import views.FYPCoordinatorView;
import views.ProjectView;
import views.RequestView;
import models.*;
import utilities.Database;

/**
 * This class represents a controller for a FYP Coordinator in the FYP Registration System.
 * It extends the abstract class Controller and overrides its run() method.
 * @author Timothy Lee
 * @author Justin Wong
 * @author Jun Hao
 * @author Lee Cheng Yao
 * @author Abhishekh
 */
public class FYPCoordinatorController extends Controller {
	
	/**
	 * The FYP Coordinator model associated with this controller.
	 */
	private FYPCoordinator FYPCoordinatorModel;
	
	/**
	 * The MainController associated with this FYP Coordinator Controller.
	 */
	private MainController mainController;
	
	/**
	 * This method overrides the abstract method run() in the Controller class.
	 * It takes in a User object as a parameter and throws any exceptions that may occur during execution.
	 * 
	 * @param user The currently logged in FYP Coordinator.
	 * @throws ClassNotFoundException If the specified class cannot be found.
	 */
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
				"View Profile",
				"Logout"
		};
		
		int choice = 0;
		int confirmation;
		
		while(choice <= menu.length) {
			
			cli.displayTitle("FYPCOORDINATOR FUNCTIONS");
		    String newPending = RequestView.checkForNew(FYPCoordinatorModel.getId());
		    menu[5] = "Approve/Reject Title Change Requests " + newPending;
		    
			cli.display(menu);
			
			choice = cli.inputInteger("Choice", 1, menu.length);
			
			switch(choice) {
			
				case 1:
	
				    boolean isPasswordChanged = false;
				    int tries = 3;
	
				    while (tries > 0 && !isPasswordChanged) {
	
				        try {
				            String currentPass = cli.inputString("Your current password");
	
				            if (!currentPass.equals(FYPCoordinatorModel.getPassword())) {
				                tries--;
				                cli.display("Wrong password. You have " + tries + " more tries.");
				                continue;
				            }
	
				            String newPass = cli.inputString("your new password");
	
				            String confirmPass = cli.inputString("password to reconfirm");
	
				            if (!newPass.equals(confirmPass)) {
				                cli.displayTitle("Passwords do not match. Please try again.");
				                continue;
				            }
				             FYPCoordinatorModel.setPassword(newPass);
				            isPasswordChanged = true;
				            cli.display("Password has been changed successfully! Please relogin. ");
	
				        } catch (Exception e) {
				            System.out.println("Error: " + e.getMessage() + ". Please try again.");
				            tries--;
				        }
				    }
				    
				    Database.updateAllData();
				    Thread.sleep(1000);    
					return;
				// Create Project
				case 2:
					
					String supervisorName;				
					String projectTitle;
					cli.displayTitle("Create Project");
					cli.display("Please Enter the Project Title: ");
					projectTitle = cli.inputString("Project Title");
					confirmation = cli.inputInteger("Confirm Choice? Enter: \n 1 to Confirm \n 2 to Cancel", 1, 2);
					
					if (confirmation == 2) {
						break; 
					}
					new Project(FYPCoordinatorModel.getName(),projectTitle,ProjectStatus_Enum.AVAILABLE);

				
					cli.displayTitle("Project has been created successfully");
					cli.display("Current Project List");
					cli.display("------------------------------------");
			
					for (Project proj : Project.getProjectList()) {
						ProjectView.printProjectInfo(proj.getProjectId());
					cli.display("------------------------------------");
					}
					
					Database.updateAllData();
					Thread.sleep(3000);
					break;  
				
				// Modify project title
				case 3:
					String newTitle;
					int projectID;
					
					cli.displayTitle("Modify Project Title");
					for (Project proj : Project.getProjectList()) {
						ProjectView.printProjectInfo(proj.getProjectId());
						cli.display("------------------------------------");
					}
					
					cli.display("Enter Project ID to change title (Enter 0 to exit):");
					projectID = cli.inputInteger("Choice", 0, Project.getProjectList().size());
					
					if (projectID == 0) break;
	
					newTitle = cli.inputString("Enter New Title ","Eg. Molecular Genetics Studies");
					
					if (Project.getProject(projectID).getProjectTitle().equals(Project.getProject(projectID).getOriProjectTitle())) { 
						Project.getProject(projectID).setProjectTitle(newTitle);
					}
					Project.getProject(projectID).setOriProjectTitle(newTitle);
					cli.displayTitle("Project Name has been changed successfully");
					
					Database.updateAllData();
					Thread.sleep(3000);
					break; 
				
				// View all projects
				case 4:
					cli.displayTitle("View All Projects");
					for (Project proj : Project.getProjectList()) {
						ProjectView.printProjectInfo(proj.getProjectId());
						cli.display("------------------------------------");
					}
					
					Database.updateAllData();
					Thread.sleep(3000);
					break; //fypCoordinatorController.run();
				
				// View pending requests
				case 5:
					cli.displayTitle("View All Pending Requests");
					for (Request req : Request.getRequests()) {
						if (req.getRequestStatus() == RequestStatus_Enum.PENDING) {
							RequestView.printRequestInfo(req.getRequestID());
							cli.display("------------------------------------");
						}
					}				
					
					Database.updateAllData();
					Thread.sleep(3000);
					break; //fypCoordinatorController.run();
					
				// Approve requests
				case 6:
					int innerChoice;
					String[] requestsMenu = {
							"Approve Project Allocation",
							"Approve Title Change ",
							"Approve Supervisor Change ",
							"Approve Deregistration ",
							"Back"
					};
					
					innerChoice = 0;
					while(innerChoice <= requestsMenu.length) {
						if(innerChoice==5)
							break;
						cli.displayTitle("Approve Requests Menu");
						cli.display(requestsMenu);
						
						innerChoice = cli.inputInteger("Choice", 1, requestsMenu.length);
						
						switch(innerChoice) {
						
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
										cli.display("------------------------------------");
									}
								}
							}	
							
							int requestID = -1;
							while (!allocationRequestID.contains(requestID)) {
								cli.displayTitle("Approve Project Allocation");
								requestID = cli.inputInteger("Enter Request ID (Enter 0 to exit)", 0, Request.getRequests().size());
								if (requestID == 0) {
									break; 
								}
							}
							// Exit if Coordinator chose to quit
							if (requestID == 0) {
								break; 
							}
							// Else continue with approval
							String studentID = Request.getRequest(requestID).getSenderID();
							String studentName = Request.getRequest(requestID).getSenderName();
							String studentEmail = Request.getRequest(requestID).getSenderEmail();
							projectID = Request.getRequest(requestID).getProjectID();
							
							// Confirmation for approval
							confirmation = RequestView.requestConfirmation();
							
							if (confirmation == 0) break;
							
							else if (confirmation == 1) {
								// Approve Request
								Request.getRequest(requestID).approve();
								// Update student projectID
								Student.getStudentFromID(studentID).setProjectID(projectID);
								
								// Update student's details on project and change status to ALLOCATED
								Project.allocateProject(projectID, studentID);
//								Project.getProject(projectID).setStudent(studentID,studentName,studentEmail);
//								Project.getProject(projectID).setProjectStatus(ProjectStatus_Enum.ALLOCATED);
								cli.displayTitle("Request has been approved");
							}
							else {
								// Reject Request
								Request.getRequest(requestID).reject();
								// Update student projectID
								Student.getStudentFromID(studentID).setProjectID(-1);
								// Make Project available again after rejecting request
								Project.getProject(requestID).setProjectStatus(ProjectStatus_Enum.AVAILABLE);
								cli.displayTitle("Request has been rejected");
							}
							Database.updateAllData();
							Thread.sleep(1000);
							break; //fypCoordinatorController.run();
							
							// Approve Title Change
							case 2:
								cli.displayTitle("Pending Title Change Requests");
								for (Request req : Request.getRequests()) {
									if (req.getRequestStatus() == RequestStatus_Enum.PENDING) {
										if (req.getRequestType() == RequestType_Enum.CHANGETITLE) {
											RequestView.printRequestInfo(req.getRequestID());
											cli.display("------------------------------------");
										}
									}
								}	
								cli.displayTitle("Approve Title Change");
								requestID = RequestView.requestRequestID();
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
									cli.displayTitle("Request has been approved");
								}
								// Reject
								else {
									// Reject Request
									Request.getRequest(choice).reject();
									cli.displayTitle("Request has been rejected");
								}
								Database.updateAllData();
								Thread.sleep(3000);
								break; //fypCoordinatorController.run();
								
							// Approve Supervisor Change 
							case 3:
								cli.displayTitle("Pending Supervisor Change Requests");
								for (Request req : Request.getRequests()) {
									if (req.getRequestStatus() == RequestStatus_Enum.PENDING) {
										if (req.getRequestType() == RequestType_Enum.CHANGESUPERVISOR) {
											RequestView.printRequestInfo(req.getRequestID());
											cli.display("------------------------------------");
										}
									}
								}
								cli.displayTitle("Approve Supervisor Change");
								requestID = cli.inputInteger("Enter Request ID (Enter 0 to exit)", 0, Request.getRequests().size());
								if (requestID == 0) break;

								confirmation = RequestView.requestConfirmation();
								
								projectID = Request.getRequest(requestID).getProjectID();
								String newSupervisorName = Request.getRequest(requestID).getNewSupervisorName();
//								String newSupervisorName = Request.getRequest(requestID).getNewSupervisorName();
//								String newSupervisorEmail = Request.getRequest(requestID).getNewSupervisorEmail();
								

								// Approve
								if (confirmation == 1) {
									// Approve Request
									Request.getRequest(requestID).approve();
									// Update project new supervisor
//									Project.getProject(projectID).setSupervisorId(newSupervisorID);
//									Project.getProject(projectID).setSupervisorName(newSupervisorName);
//									Project.getProject(projectID).setSupervisorEmail(newSupervisorEmail);
									Project.changeSupervisor(projectID, newSupervisorName);
									cli.displayTitle("Request has been approved");
									
								}
								// Reject
								else {
									// Reject Request
									Request.getRequest(requestID).reject();
									cli.displayTitle("Request has been rejected");
								}
								Database.updateAllData();
								Thread.sleep(3000);
								break; //fypCoordinatorController.run();
								
							// Approve Deregistration 
							case 4:
								cli.displayTitle("Pending Deregistration Requests");
								for (Request req : Request.getRequests()) {
									if (req.getRequestStatus() == RequestStatus_Enum.PENDING) {
										if (req.getRequestType() == RequestType_Enum.DEREGISTERPROJECT) {
											RequestView.printRequestInfo(req.getRequestID());
											cli.display("------------------------------------");
										}
									}
								}	
								cli.displayTitle("Approve Deregistration");
								requestID = cli.inputInteger("Enter Request ID (Enter 0 to exit)", 0, Request.getRequests().size());
								confirmation = RequestView.requestConfirmation();
								
								projectID = Request.getRequest(requestID).getProjectID();
								studentID = Request.getRequest(requestID).getSenderID();
								
								if (confirmation == 0) break;
								if (confirmation == 1) {
									Request.getRequest(requestID).approve();
									// Deregister student from project
									Project.deregisterStudent(projectID);
									//set as -2, means can never register again
									Student.getStudentFromID(studentID).setProjectID(-2);
									cli.displayTitle("Request has been approved");
								}
								else {
									Request.getRequest(requestID).reject();
									cli.displayTitle("Request has been rejected");
								}
								
								Database.updateAllData();
								Thread.sleep(1000);
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
						cli.display("------------------------------------");
					}
					
					Database.updateAllData();
					Thread.sleep(3000);
					break; //fypCoordinatorController.run();
					
		
				// Generate Project Details Report (with filters)	
				case 8:
					cli.displayTitle("Generate Project Details Report(with filters)");
					String[] projectMenu = {
							"Filter by Supervisor ID",
							"Filter by Project Status",
							"Back"
					};
					int projectMenuChoice = -1;
					while(projectMenuChoice <= projectMenu.length) {
						if(projectMenuChoice==3)
							break;
						cli.display(projectMenu);
						
						projectMenuChoice = cli.inputInteger("Choice", 1, projectMenu.length);
						switch(projectMenuChoice) {
						case 1:
							String tempSupervisorId = "-1";
							Supervisor tempSupervisor = null;
							while (!Supervisor.getSupervisorsList().contains(tempSupervisor)) {
								tempSupervisorId =  cli.inputString("Please Enter Supervisor ID (Enter 0 to exit)").toUpperCase();
								tempSupervisor = Supervisor.getSupervisorFromId(tempSupervisorId);
								if (tempSupervisorId.equals("0")) break;
							}
							if (tempSupervisorId.equals("0")) break;
							String [] supervisorProj = {
									"View All Projects by Chosen Supervisor",
									"View Supervised Projects by Chosen Supervisor",
									"Back"
							};
							cli.display(supervisorProj);
							int viewChoice = cli.inputInteger("Choice");
							
							switch (viewChoice) {
							case 1: 
								// View All Projects by Chosen Supervisor
								cli.displayTitle("Generating All Project Details... ");
								for (Project proj : Project.getProjectList()) {
									if (proj.getSupervisorId().equals(tempSupervisorId)) {
										ProjectView.printProjectInfo(proj.getProjectId());
										cli.display("------------------------------------");
									}
								}
								
								Thread.sleep(3000);
								break;
							case 2:
								// View Supervised Projects by Chosen Supervisor
								cli.displayTitle("Generating Supervised Project Details... ");
								for (Project proj : Project.getProjectList()) {
									if (proj.getSupervisorId().equals(tempSupervisorId) && proj.getProjectStatus() == ProjectStatus_Enum.ALLOCATED) {
										ProjectView.printProjectInfo(proj.getProjectId());
										cli.display("------------------------------------");
									}
								}
								
								Thread.sleep(3000);
								break;
							}
							
							Database.updateAllData();
							break;
							
						case 2:
							cli.displayTitle("Choose the Project Status to View:");
							String [] projStatus = { 
									"Available",
									"Reserved",
									"Allocated",
									"Unavailable",
									"Back"
							};
							cli.display(projStatus);
							innerChoice = cli.inputInteger("Choice", 1, projStatus.length);

							if (innerChoice == 1) {
								cli.displayTitle("Generating project details for all available projects...");
								for (Project proj : Project.getProjectList()) {
									if (proj.getProjectStatus()==(ProjectStatus_Enum.AVAILABLE)) {
										ProjectView.printProjectInfo(proj.getProjectId());
										cli.display("------------------------------------");
									}
								}
								
								Database.updateAllData();
								Thread.sleep(3000);
								break;
							}
							if (innerChoice == 2){
								cli.displayTitle("Generating project details for all reserved projects...");
								for (Project proj : Project.getProjectList()) {
									if (proj.getProjectStatus()==(ProjectStatus_Enum.RESERVED)) {
										ProjectView.printProjectInfo(proj.getProjectId());
										cli.display("------------------------------------");
									}
								}
								
								Database.updateAllData();
								Thread.sleep(3000);
								break;
							}
							if (innerChoice == 3){
								cli.displayTitle("Generating project details for all allocated projects...");
								for (Project proj : Project.getProjectList()) {
									if (proj.getProjectStatus()==(ProjectStatus_Enum.ALLOCATED)) {
										ProjectView.printProjectInfo(proj.getProjectId());
										cli.display("------------------------------------");
									}
								}

								Database.updateAllData();
								Thread.sleep(3000);
								break;
							}
							if (innerChoice == 4){
								cli.displayTitle("Generating project details for all unavailable projects...");
								for (Project proj : Project.getProjectList()) {
									if (proj.getProjectStatus()==(ProjectStatus_Enum.UNAVAILABLE)) {
										ProjectView.printProjectInfo(proj.getProjectId());
										cli.display("------------------------------------");
									}
								}
								
								Database.updateAllData();
								Thread.sleep(3000);
								break;
							}
							if (innerChoice == 5) break;
						}
					}
					break;
					
				case 9: //View Profile
					cli.displayTitle("View Profile");
					FYPCoordinatorView.printFYPCoordinatorRecordInfo(FYPCoordinatorModel.getId(), FYPCoordinatorModel.getName(), FYPCoordinatorModel.getEmailAddress(), FYPCoordinatorModel.getPassword());
					Thread.sleep(3000);
					break;
					
				case 10:
					cli.displayTitle("Logging out...");
					
					Database.updateAllData();
					Thread.sleep(1000);
					return;
									
				default:
					return;
			}
		}
	}
}


