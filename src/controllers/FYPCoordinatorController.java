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
			
		
		String newPending = RequestView.checkForNew(FYPCoordinatorModel.getId());
		String[] menu = {
                "Change Password ",
                "Create/Update/View Projects",
                "View Pending Requests " + newPending,
                "View Project Details Report (with filters)",
                "View Request History",
                "View Profile",
                "Logout"
		};
		
		int choice = 0;
		int confirmation;
		
		while(choice <= menu.length) {
			/**
			 * FYP Coordinator main page
			 */
			cli.displayTitle("FYPCOORDINATOR FUNCTIONS");
			newPending = RequestView.checkForNew(FYPCoordinatorModel.getId());
		    
			cli.display(menu);
			
			choice = cli.inputInteger("Choice", 1, menu.length);
			
			switch(choice) {
			
			case 1:
				/**
				 * Case 1 enables user to change password. If attempt to key current password is wrong 3 times, attempt is failed
				 * If successful, password is changed and user will have to log in again.
				 */
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
			
			// Create/Update/View Projects
			case 2:
				/**
				 *	Case 2 enables user to Create,Update and View project.  
				 *	Creating a project will add a project into the project list.
				 *	Updating a project brings user to another menu where they are able to modify project title or change supervisor
				 */
				String [] projectsSubMenu = {
						"Create Project",
						"Update Project",
						"View All Projects",
						"Back"
				};
				
				int projectsSubMenuChoice = -1;

				
				
				while(projectsSubMenuChoice <= projectsSubMenu.length) {
					if(projectsSubMenuChoice==4) break;
					
					cli.displayTitle("Projects Menu");
					cli.display(projectsSubMenu);
					
					projectsSubMenuChoice = cli.inputInteger("Choice", 1, projectsSubMenu.length);
				switch (projectsSubMenuChoice) {
				// Create Projects
				case 1:
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
					
				// Update Project
				case 2:
					String [] updateProjectsSubMenu = {
							"Modify Project Title",
							"Change Supervisor",
							"Back"
					};
					
					int updateProjectsSubMenuChoice = -1;
					
					while(updateProjectsSubMenuChoice <= updateProjectsSubMenu.length) {
						if(updateProjectsSubMenuChoice==3) break;
						
						cli.displayTitle("Update Projects Menu");
						cli.display(updateProjectsSubMenu);
						
						updateProjectsSubMenuChoice = cli.inputInteger("Choice", 1, updateProjectsSubMenu.length);
					switch (updateProjectsSubMenuChoice) {
					// Modify Project Title
					case 1:
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
						confirmation = cli.inputInteger("Confirm Choice? Enter: \n 1 to Confirm \n 2 to Cancel", 1, 2);
						
						if (confirmation == 2) {
							break; 
						}
						if (Project.getProject(projectID).getProjectTitle().equals(Project.getProject(projectID).getOriProjectTitle())) { 
							Project.getProject(projectID).setProjectTitle(newTitle);
						}
						Project.getProject(projectID).setOriProjectTitle(newTitle);
						cli.displayTitle("Project Name has been changed successfully");
						
						Database.updateAllData();
						Thread.sleep(3000);
						break; 
					
					// Change Supervisor
					case 2:
						cli.displayTitle("DO WE NEED THIS FUNCTION?");
						Thread.sleep(1000);
						break;
						
					// Back
					case 3:
						break;
					}
					
					}
					Thread.sleep(1000);
					break;
				// View All Projects
				case 3:
					cli.displayTitle("View All Projects");
					for (int i = 0; i < Project.getProjectList().size(); i++) {
						cli.display("----------------------------------------------------------------------------");
						cli.display("Project ID: " + Project.getProjectList().get(i).getProjectId());
						cli.display("Project Title: " + Project.getProjectList().get(i).getProjectTitle());
						cli.display("Supervisor ID: " + Project.getProjectList().get(i).getSupervisorId());
						cli.display("Supervisor Name: " + Project.getProjectList().get(i).getSupervisorName());
						cli.display("Supervisor Email: " + Project.getProjectList().get(i).getSupervisorEmail());
					}
					Thread.sleep(1000);
					break;
					
				// Back
				case 4:
					Thread.sleep(1000);
					break;
				}
				}
				break;
					
			// View Pending Requests
			case 3:
				/*
				 * User will be able to view all pending requests.
				 * By entering the requestID, they are able to take action on that particular request.
				 * User will be prompted to confirm their decision. 
				 * They will be redirected to either approve or reject these pending requests.
				 * User will be able to view requests according to the different filters set.
				 */
				ArrayList <Integer> pendingRequestID = new ArrayList<>();
				cli.displayTitle("View All Pending Requests");
				for (Request req : Request.getRequests()) {
					if (req.getRequestStatus() == RequestStatus_Enum.PENDING) {
						RequestView.printRequestInfo(req.getRequestID());
						cli.display("------------------------------------");
						pendingRequestID.add(req.getRequestID());
					}
				}	
				
				if (pendingRequestID.size()==0) {
					cli.displayTitle("There are no pending requests");
					Thread.sleep(1000);
					break;
				}
				
				cli.displayTitle("Approve Pending Requests");
				int requestID = cli.inputInteger("Enter Request ID (Enter 0 to exit)", 0, Request.getRequests().size());
				confirmation = RequestView.requestConfirmation();
				
				int projectID = Request.getRequest(requestID).getProjectID();
				String studentID = Request.getRequest(requestID).getSenderID();
				
				if (confirmation == 0) break;
				if (confirmation == 1) {
					Request.getRequest(requestID).approve();
					cli.displayTitle("This method is not actually implemented yet, Lol");
				}
				else {
					Request.getRequest(requestID).reject();
					cli.displayTitle("This method is not actually implemented yet, Lol");
				}
				
				// Print Request Sub Menu
				String [] requestsSubMenu = {
						"Approve/Reject by Request ID",
						"Approve/Reject with Filters",
						"View All Pending Requests",
						"Back"
				};

				int requestsSubMenuChoice = -1;
				
				while(requestsSubMenuChoice <= requestsSubMenu.length) {
					if(requestsSubMenuChoice==4) break;
					
					cli.displayTitle("Approve Requests Menu");
					cli.display(requestsSubMenu);
					
					requestsSubMenuChoice = cli.inputInteger("Choice", 1, requestsSubMenu.length);
				switch (requestsSubMenuChoice) {
				
				// Approve/Reject Requests by Request ID
				case 1:
					int approveChoice;
					String [] approveRequestsFiltersSubMenu = {
							"Approve/Reject Project Allocation",
							"Approve/Reject Title Change",
							"Approve/Reject Supervisor Change",
							"Approve/Reject Deregistration",
							"Back",
					};
					
					approveChoice = 0;
					while(approveChoice <= approveRequestsFiltersSubMenu.length) {
						if(approveChoice==5)
							break;
						cli.displayTitle("Approve Requests Menu");
						cli.display(approveRequestsFiltersSubMenu);
						
						approveChoice = cli.inputInteger("Choice", 1, approveRequestsFiltersSubMenu.length);
						
						switch(approveChoice) {
						
							// Approve/Reject Project Allocation
							case 1:
								ArrayList<Integer> allocationRequestID = new ArrayList<>();
								cli.displayTitle("Pending Project Allocation Requests");
								for (Request req : Request.getRequests()) {
									if (req.getRequestStatus() == RequestStatus_Enum.PENDING) {
										if (req.getRequestType() == RequestType_Enum.REGISTERPROJECT) {
											RequestView.printRequestInfo(req.getRequestID());
											allocationRequestID.add(req.getRequestID());
											cli.display("------------------------------------");
										}
									}
								}	
								if (allocationRequestID.size()==0) {
									cli.displayTitle("There are no pending Project Allocation requests");
									break;
								}
								requestID = -1;
								while (!allocationRequestID.contains(requestID)) {
									cli.displayTitle("Approve Project Allocation");
									requestID = cli.inputInteger("Enter Request ID (Enter 0 to exit)", 0, Request.getRequests().size());
									if (!allocationRequestID.contains(requestID)) {
										cli.display("Please enter a valid Request ID");
									}
									if (requestID == 0) {
										break; 
									}
								}
								// Exit if Coordinator chose to quit
								if (requestID == 0) {
									break; 
								}
								// Else continue with approval
								studentID = Request.getRequest(requestID).getSenderID();
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
									cli.displayTitle("Project Allocation Has Been Approved");
								}
								else {
									// Reject Request
									Request.getRequest(requestID).reject();
									// Update student projectID
									Student.getStudentFromID(studentID).setProjectID(-1);
									// Make Project available again after rejecting request
									Project.getProject(requestID).setProjectStatus(ProjectStatus_Enum.AVAILABLE);
									cli.displayTitle("Project Allocation Has Been Rejected");
								}
								Database.updateAllData();
								Thread.sleep(3000);
								break; 
							
							// Approve/Reject Title Change
							case 2:
								ArrayList<Integer> titleRequestID = new ArrayList<>();
								cli.displayTitle("Pending Title Change Requests");
								for (Request req : Request.getRequests()) {
									if (req.getRequestStatus() == RequestStatus_Enum.PENDING) {
										if (req.getRequestType() == RequestType_Enum.CHANGETITLE) {
											RequestView.printRequestInfo(req.getRequestID());
											cli.display("------------------------------------");
											titleRequestID.add(req.getRequestID());
										}
									}
								}	
								
								if (titleRequestID.size()==0) {
									cli.displayTitle("There are no pending Title Change requests");
									break;
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
									Request.getRequest(requestID).approve();
									// Update project title
									Project.changeProjectTitle(projectID, newProjectTitle);
									cli.displayTitle("Title Change Has Been Approved");
								}
								// Reject
								else {
									// Reject Request
									Request.getRequest(requestID).reject();
									cli.displayTitle("Title Change Has Been Rejected");
								}
								Database.updateAllData();
								Thread.sleep(3000);
								break; 
								
							// Approve/Reject Supervisor Change 
							case 3:
								ArrayList<Integer> supChangeRequestID = new ArrayList<>();
								cli.displayTitle("Pending Supervisor Change Requests");
								for (Request req : Request.getRequests()) {
									if (req.getRequestStatus() == RequestStatus_Enum.PENDING) {
										if (req.getRequestType() == RequestType_Enum.CHANGESUPERVISOR) {
											RequestView.printRequestInfo(req.getRequestID());
											cli.display("------------------------------------");
											supChangeRequestID.add(req.getRequestID());
										}
									}
								}	
								
								if (supChangeRequestID.size()==0) {
									cli.displayTitle("There are no pending Supervisor Change requests");
									break;
								}
								
								cli.displayTitle("Approve Supervisor Change");
								requestID = cli.inputInteger("Enter Request ID (Enter 0 to exit)", 0, Request.getRequests().size());
								if (requestID == 0) break;

								confirmation = RequestView.requestConfirmation();
								
								projectID = Request.getRequest(requestID).getProjectID();
								String newSupervisorName = Request.getRequest(requestID).getNewSupervisorName();
								
								// Approve
								if (confirmation == 1) {
									// Approve Request
									Request.getRequest(requestID).approve();
									// Update project new supervisor
									Project.changeSupervisor(projectID, newSupervisorName);
									cli.displayTitle("Supervisor Change Has Been Approved");
									
								}
								// Reject
								else {
									// Reject Request
									Request.getRequest(requestID).reject();
									cli.displayTitle("Supervisor Change Has Been Rejected");
								}
								Database.updateAllData();
								Thread.sleep(3000);
								break; 
								
							// Approve/Reject Deregistration 
							case 4:
								ArrayList<Integer> deregisterRequestID = new ArrayList<>();
								cli.displayTitle("Pending Deregistration Requests");
								for (Request req : Request.getRequests()) {
									if (req.getRequestStatus() == RequestStatus_Enum.PENDING) {
										if (req.getRequestType() == RequestType_Enum.DEREGISTERPROJECT) {
											RequestView.printRequestInfo(req.getRequestID());
											cli.display("------------------------------------");
											deregisterRequestID.add(req.getRequestID());
										}
									}
								}	
								
								if (deregisterRequestID.size()==0) {
									cli.displayTitle("There are no pending Deregistration requests");
									break;
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
									cli.displayTitle("Deregistration Has Been Approved");
								}
								else {
									Request.getRequest(requestID).reject();
									cli.displayTitle("Deregistration Has Been Rejected");
								}
								
								Database.updateAllData();
								Thread.sleep(3000);
								break; 
								
							// View all request
							case 5:
								
							// Back	
							case 6:
								break; 
						}
					}		
					
					break; 
					}
				}
			// View Project Details Report (with filters)
			case 4:
				/*
				 * User is able to view project details using filters
				 * The first filter is to view by supervisor ID
				 * The second filter is to view by student ID
				 * The last filter is to view by project status
				 */
				String [] projectDetailsSubMenu = {
						"View by Supervisor ID",
						"View by Student ID",
						"View by Project Status",
						"Back"
				};
		
				int projectDetailsSubMenuChoice = -1;
				while(projectDetailsSubMenuChoice <= projectDetailsSubMenu.length) {
					if(projectDetailsSubMenuChoice==4)
						break;
					cli.displayTitle("Generate Project Details Report(with filters)");
					cli.display(projectDetailsSubMenu);
					
					projectDetailsSubMenuChoice = cli.inputInteger("Choice", 1, projectDetailsSubMenu.length);
					switch(projectDetailsSubMenuChoice) {
					// View by Supervisor ID
					case 1:
						String tempSupervisorId = "-1";
						Supervisor tempSupervisor = null;
						while (!Supervisor.getSupervisorsList().contains(tempSupervisor)) {
							tempSupervisorId =  cli.inputString("Please Enter Supervisor ID (Enter 0 to exit)").toUpperCase();
							tempSupervisor = Supervisor.getSupervisorFromId(tempSupervisorId);
							if (!Supervisor.getSupervisorsList().contains(tempSupervisor)) {
								if (tempSupervisorId.equals("0")) break;
								cli.display("Please enter a valid Supervisor ID");
							}
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
						// View All Projects by Chosen Supervisor
						case 1: 
							cli.displayTitle("Generating All Project Details... ");
							for (Project proj : Project.getProjectList()) {
								if (proj.getSupervisorId().equals(tempSupervisorId)) {
									ProjectView.printProjectInfo(proj.getProjectId());
									cli.display("------------------------------------");
								}
							}
							Thread.sleep(3000);
							break;	
							
						// View Supervised Projects by Chosen Supervisor
						case 2:
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
						
					// View by Student ID
					case 2:
						studentID = "-1";
						Student student = null;
						while (!Student.getStudentsList().contains(student)) {
							studentID =  cli.inputString("Please Enter Student ID (Enter 0 to exit)").toUpperCase();
							student = Student.getStudentFromID(studentID);
							if (!Student.getStudentsList().contains(student)) {
								if (studentID.equals("0")) break;
								cli.display("Please enter a valid Student ID");
							}
						}
						if (studentID.equals("0")) break;
						cli.displayTitle("Generating Project Details of Student... ");
						for (Project proj : Project.getProjectList()) {
							if (proj.getSupervisorId().equals(studentID)) {
								ProjectView.printProjectInfo(proj.getProjectId());
								cli.display("------------------------------------");
							}
						}
						Thread.sleep(3000);
						break;
						
					// View by Project Status	
					case 3:
						cli.displayTitle("Choose the Project Status to View:");
						String [] projStatus = { 
								"Available",
								"Reserved",
								"Allocated",
								"Unavailable",
								"Back"
						};
						cli.display(projStatus);
						int innerChoice = cli.inputInteger("Choice", 1, projStatus.length);

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
			
			// View Request History
			case 5:
				/*
				 * Case 5 enables user to view all requests sent by them
				 */
				cli.displayTitle("View Request History");
				for (Request req : Request.getRequests()) {
					RequestView.printRequestInfo(req.getRequestID());
					cli.display("------------------------------------");
				}
				
				Database.updateAllData();
				Thread.sleep(3000);
				break; 
				
               
			// View Profile	
			case 6:
				/*
				 * Case 6 enables users to view their current profile information
				 */
				cli.displayTitle("View Profile");
				FYPCoordinatorView.printFYPCoordinatorRecordInfo(FYPCoordinatorModel.getId(), FYPCoordinatorModel.getName(), FYPCoordinatorModel.getEmailAddress(), FYPCoordinatorModel.getPassword());
				Thread.sleep(3000);
				break;
				
			// Logout	
			case 7:
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
		
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			



