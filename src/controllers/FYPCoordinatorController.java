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
 * @version 1.0
 * @since 2023-04-16
 */
public class FYPCoordinatorController extends Controller {
	
	/**
	 * The FYP Coordinator model associated with this controller.
	 */
	private FYPCoordinator fypCoordinatorModel;
	
	/**
	 * The MainController associated with this FYP Coordinator Controller.
	 */
	private MainController mainController;
	
	/**
	 * This method overrides the abstract method run() in the Controller class.
	 * It takes in a User object as a parameter and throws any exceptions that may occur during execution.
	 * 
	 * @param user The currently logged in FYP Coordinator
	 * @throws ClassNotFoundException If the specified class cannot be found
	 */
	public void run(User user) throws Throwable{

        if(user instanceof FYPCoordinator) {
		    fypCoordinatorModel = (FYPCoordinator)user;
		}
            
		mainController = new MainController();
			
		String newPending = RequestView.checkForNew(fypCoordinatorModel.getId());
		String[] menu = {
                "Change Password",
                "Create/Update/View Projects",
                "View Pending Requests " + newPending,
                "View Requests History and Status",
                "Request to Change Supervisor",
                "View Project Details Report (with filters)",
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
//			newPending = RequestView.checkForNew(fypCoordinatorModel.getId());
//			cli.display(menu);
			newPending = RequestView.checkForNew(fypCoordinatorModel.getId());
			menu[2] = "View Pending Request " + newPending;
			cli.display(menu);
			
			choice = cli.inputInteger("Choice", 1, menu.length);
			
			switch(choice) {
			
			// Change password
			/*
			 * Case 1 enables user to change password. If attempt to key current password is wrong 3 times, attempt is failed.
			 * If successful, password is changed and user will have to log in again.
			 */
			case 1:
			    boolean isPasswordChanged = false;
			    int tries = 3;

			    while (tries > 0 && !isPasswordChanged) {

			        try {
			            String currentPass = cli.inputString("Your current password");

			            if (!currentPass.equals(fypCoordinatorModel.getPassword())) {
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
			             fypCoordinatorModel.setPassword(newPass);
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
			
			// Create/Update/View Projects
			/*
			 *	Enables user to Create,Update and View project.  
			 *	Creating a project will add a project into the project list.
			 *	Updating a project brings user to another menu where they are able to modify project title or change supervisor.
			 */
			case 2:
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
					new Project(fypCoordinatorModel.getName(),projectTitle,ProjectStatus_Enum.AVAILABLE);

				
					cli.displayTitle("Project has been created successfully");
					cli.display("Current Project List");
					cli.display("------------------------------------");
			
					for (Project proj : Project.getProjectList()) {
						ProjectView.printProjectInfo(proj.getProjectId());
					cli.display("------------------------------------");
					}
					
					Database.updateAllData();
					Thread.sleep(1500);
					break;  
					
				// Update Project (Modify Title)
				case 2:
					cli.displayTitle("Modify Own Project Title");
					ArrayList<Integer> ownProjectID = new ArrayList<>();
					int projectID = -1;
					//loops through project list to find supervisorID
				    for (Project proj : Project.getProjectList()) {
				    	if (proj.getSupervisorId().equals(fypCoordinatorModel.getId())) {
					    	ProjectView.printProjectInfo(proj.getProjectId());
					    	ownProjectID.add(proj.getProjectId());
							cli.display("------------------------------------");
					     }
				    }
						     
				    if(ownProjectID.size()==0) {
				    	cli.displayTitle("You have yet registered for a project, unable to modify title");
					    Thread.sleep(1500);
				    	break;
				    }
						     
				    while (!ownProjectID.contains(projectID)) {
					    projectID = cli.inputInteger("Choose Project ID to modify Project Title (Enter 0 to exit)");
					    if (projectID == 0) break;
					    if (!ownProjectID.contains(projectID))
					    	 cli.display("Please enter a valid project ID");
					    }  				     
						if (projectID == 0) break; 
						String newtitle = cli.inputString("Enter New Project Title (Enter 0 to exit)");
						if (newtitle.equals("0")) {
							cli.displayTitle("Modification Cancelled");
						    Thread.sleep(1500);
							break;
						}
						//continues if never input 0
						if (Project.getProject(projectID).getProjectTitle().equals(Project.getProject(projectID).getOriProjectTitle())) 
							Project.getProject(projectID).setProjectTitle(newtitle);
						Project.getProject(projectID).setOriProjectTitle(newtitle);
						cli.displayTitle("Project Title has been updated");
						Database.updateAllData();
						Thread.sleep(1500);
						break;
						
				// View All Projects
				case 3:
					String [] viewProjectsSubMenu = {
							"View All Projects",
							"View Projects By Status",
							"Back"
					};
					
					int viewProjectsSubMenuChoice = -1;
					
					while(viewProjectsSubMenuChoice <= viewProjectsSubMenu.length) {
						if(viewProjectsSubMenuChoice==3) break;
						
						cli.displayTitle("View Projects");
						cli.display(viewProjectsSubMenu);
						
						viewProjectsSubMenuChoice = cli.inputInteger("Choice", 1, viewProjectsSubMenu.length);
					switch (viewProjectsSubMenuChoice) {
					// View All Projects
					case 1:
						ProjectView.projectAllInfo(true);
						Thread.sleep(1500);
						break;
					
					// View Projects By Status
					case 2:
						ProjectView.printProjectsByStatus();
						Thread.sleep(1500);
						break;
					// Back
					case 3:
						break;
					}
					
					}
					break;
				// Back
				case 4:
					Thread.sleep(1500);
					break;
				}
				}
				break;
			// View Pending Requests
			/*
			 * User will be able to view all pending requests addressed to them.
			 * By entering the requestID, they are able to take action on that particular request.
			 * User will be prompted to confirm their decision.
			 * They will be redirected to either approve or reject these pending requests.
			 * User will also be able to view and approve requests according to the different filters set.
			 */
			case 3:
				ArrayList <Integer> pendingRequestID = new ArrayList<>();
				cli.displayTitle("View All Pending Requests");
				for (Request req : Request.getRequests()) {
					if (req.getRequestStatus() == RequestStatus_Enum.PENDING) {
						if (req.getRecipientID().equals(fypCoordinatorModel.getId())) {
						RequestView.printRequestInfo(req.getRequestID());
						cli.display("------------------------------------");
						pendingRequestID.add(req.getRequestID());
						}
					}
				}	
				
				if (pendingRequestID.size()==0) {
					cli.displayTitle("There are no pending requests");
					Thread.sleep(1500);
					break;
				}
				
				// Print Request Sub Menu
				String [] requestsSubMenu = {
						"Approve/Reject Requests",
						"View All Pending Requests",
						"Back"
				};

				int requestsSubMenuChoice = -1;
				
				while(requestsSubMenuChoice <= requestsSubMenu.length) {
					if(requestsSubMenuChoice==3) break;
					
					cli.displayTitle("Approve/Reject Requests Menu");
					cli.display(requestsSubMenu);
					
					requestsSubMenuChoice = cli.inputInteger("Choice", 1, requestsSubMenu.length);
				switch (requestsSubMenuChoice) {
					
				// Approve/Reject Requests 
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
						if(approveChoice==5) break;
						cli.displayTitle("Approve/Reject Requests");
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
									Thread.sleep(1500);
									break;
								}
								int requestID = -1;
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
								String studentID = Request.getRequest(requestID).getSenderID();
								int projectID = Request.getRequest(requestID).getProjectID();
								
								// Confirmation for approval
								confirmation = RequestView.requestConfirmation();
								
								if (confirmation == 0) break;
								
								else if (confirmation == 1) {
									// Approve Request
									Request.getRequest(requestID).approve();
									// Update student projectID
									Student.getStudentFromID(studentID).setProjectID(projectID);
									
									// Update student's details on project and change status to ALLOCATED
									fypCoordinatorModel.allocateProject(projectID, studentID);
									cli.displayTitle("Project Allocation Has Been Approved");
								}
								else {
									// Reject Request
									Request.getRequest(requestID).reject();
									// Update student projectID
									Student.getStudentFromID(studentID).setProjectID(-1);
									// Make Project available again after rejecting request
									Project.getProject(projectID).setProjectStatus(ProjectStatus_Enum.AVAILABLE);
									cli.displayTitle("Project Allocation Has Been Rejected");
								}
								Database.updateAllData();
								Thread.sleep(1500);
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
									Thread.sleep(1500);
									break;
								}
								
								cli.displayTitle("Approve/Reject Title Change");
								requestID = RequestView.requestRequestID();
								if (requestID == 0) break;

								confirmation = RequestView.requestConfirmation();
								
								projectID = Request.getRequest(requestID).getProjectID();
								String newProjectTitle = Request.getRequest(requestID).getNewProjectTitle();
								
								// Approve
								if (confirmation == 1) {
									Request.getRequest(requestID).approve();
									Project.changeProjectTitle(projectID, newProjectTitle);
									cli.displayTitle("Title Change Has Been Approved");
								}
								// Reject
								else {
									Request.getRequest(requestID).reject();
									cli.displayTitle("Title Change Has Been Rejected");
								}
								Database.updateAllData();
								Thread.sleep(1500);
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
									Thread.sleep(1500);
									break;
								}
								
								cli.displayTitle("Approve/Reject Supervisor Change");
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
									fypCoordinatorModel.changeSupervisor(projectID, newSupervisorName);
									cli.displayTitle("Supervisor Change Has Been Approved");
								}
								// Reject
								else {
									// Reject Request
									Request.getRequest(requestID).reject();
									cli.displayTitle("Supervisor Change Has Been Rejected");
								}
								Database.updateAllData();
								Thread.sleep(1500);
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
									Thread.sleep(1500);
									break;
								}
								
								cli.displayTitle("Approve/Reject Deregistration");
								
								requestID = -1;
								requestID = cli.inputInteger("Enter Request ID (Enter 0 to exit)", 0, Request.getRequests().size());
								while (!deregisterRequestID.contains(requestID)){
									cli.display("Please enter a valid Request ID");
									requestID = cli.inputInteger("Enter Request ID (Enter 0 to exit)", 0, Request.getRequests().size());
									if (requestID == 0) break;
								}
								if (requestID == 0) break;
								
								confirmation = RequestView.requestConfirmation();
								projectID = Request.getRequest(requestID).getProjectID();
								studentID = Request.getRequest(requestID).getSenderID();
								
								if (confirmation == 0) break;
								if (confirmation == 1) {
									Request.getRequest(requestID).approve();
									// Deregister student from project
									fypCoordinatorModel.deregisterStudent(projectID);
									//set as -2, means can never register again
									Student.getStudentFromID(studentID).setProjectID(-2);
									cli.displayTitle("Deregistration Has Been Approved");
								}
								else {
									Request.getRequest(requestID).reject();
									cli.displayTitle("Deregistration Has Been Rejected");
								}
								
								Database.updateAllData();
								Thread.sleep(1500);
								break; 

							// Back	
							case 5:
								Thread.sleep(1500);
								break; 
						}
					}		
					break; 
				
				// View all Pending Requests
				case 2:
					pendingRequestID = new ArrayList<>();
					cli.displayTitle("View All Pending Requests");
					for (Request req : Request.getRequests()) {
						if (req.getRequestStatus() == RequestStatus_Enum.PENDING) {
							RequestView.printRequestInfo(req.getRequestID());
							cli.display("------------------------------------");
							pendingRequestID.add(req.getRequestID());
						}
					}	
					
					if (pendingRequestID.size()==0) {
						cli.displayTitle("There are no pending Title Change requests");
						Thread.sleep(1500);
						break;
					}
		
					Thread.sleep(1500);
					break;
					
				// Back	
				case 3:
					Thread.sleep(1500);
					break;
				}
				}
				break;
			// View Requests History and Status	
			/*
			 * FYP Coordinator is able to view their incoming and outgoing requests.
			 * FYP Coordinator can also view all requests sent by all users.
			 */
			case 4:
				String [] viewRequestsHistorySubMenu = {
						"View Incoming Requests",
						"View Outgoing Requests",
						"View All Requests Sent By All Users",
						"Back"
				};
				int viewRequestsHistorySubMenuChoice = -1;
				while(viewRequestsHistorySubMenuChoice <= viewRequestsHistorySubMenu.length) {
					if(viewRequestsHistorySubMenuChoice==4) break;
					
					cli.displayTitle("View Request History Menu");
					cli.display(viewRequestsHistorySubMenu);
					
					viewRequestsHistorySubMenuChoice = cli.inputInteger("Choice", 1, viewRequestsHistorySubMenu.length);
					switch(viewRequestsHistorySubMenuChoice) {
					// View Incoming and Outgoing Requests
					case 1:
						cli.displayTitle("View Incoming Request History"); 
						RequestView.printIncomingRequestHistory(fypCoordinatorModel.getId());	
						Thread.sleep(1500);
						break;
					case 2:
						cli.displayTitle("View Outgoing Request History");
						RequestView.printRequestHistory(fypCoordinatorModel.getId());
						Thread.sleep(1500);
						break;
					// View All Requests History
					case 3:
						cli.displayTitle("View Request History");
						for (Request req : Request.getRequests()) {
							RequestView.printRequestInfo(req.getRequestID());
							cli.display("------------------------------------");
						}
						
						Database.updateAllData();
						Thread.sleep(1500);
						break;
					case 4:
						Thread.sleep(1500);
						break;
					default: 
						Thread.sleep(1500);
						break;
					}
				}

				break;
			
			// Request to change supervisor
			/*
			 *  User can submit a request to transfer student of their project to another supervisor.
			 */
			case 5:
				cli.displayTitle("Request to Change Supervisor in Charge");
				// List of projects supervised/pending supervision
				ArrayList<Project> supervisedProjects = new ArrayList<>();
				for (Project proj : Project.getProjectList()) {
					if (proj.getSupervisorId().equals(fypCoordinatorModel.getId()) && (proj.getProjectStatus() == ProjectStatus_Enum.ALLOCATED || proj.getProjectStatus() == ProjectStatus_Enum.RESERVED)) {
						supervisedProjects.add(proj);
					}
				}
				if (supervisedProjects.size() == 0) {
					cli.displayTitle("Currently not supervising any project!");
					Thread.sleep(1500);
					break;
				}
				int projectID = -1;
				int supervisorFound;
				// List of IDs of projects supervised/pending supervision
				ArrayList<Integer> supervisedProjectID = new ArrayList<>();	
				for (Project proj : supervisedProjects) {
						ProjectView.printProjectInfo(proj.getProjectId());
						supervisedProjectID.add(proj.getProjectId());
						cli.display("------------------------------------");
			    }
				// While project ID entered is not in supervised list, ask for a valid project ID
				while (!supervisedProjectID.contains(projectID)) {
					projectID = cli.inputInteger("Select Project ID (Enter 0 to exit) ");
					if (projectID == 0) break; 							
					if (!supervisedProjectID.contains(projectID)) {
						cli.display("Please enter a valid Project ID");
					}
				}
				// Exit if coordinator choose to quit
				if (projectID == 0) break; 
				
				
				supervisorFound = 0;
				String newSupervisorID = cli.inputString("Enter the Replacement Supervisor ID");
				// Checking if supervisor entered exists
				while (supervisorFound != 1) {
					//if replacement supervisor id is found will proceed normally
					for (Supervisor sup : Supervisor.getSupervisorsList()) {
							if (sup.getId().equals(newSupervisorID)) {
						    	supervisorFound = 1;
						    	break;
						    }
					}
					if (supervisorFound == 1) break;
					cli.display("Supervisor ID does not exist!");
					newSupervisorID = cli.inputString("Enter the Replacement Supervisor ID");
				}							
				//creates new request
				new Request(fypCoordinatorModel.getId(),fypCoordinatorModel.getName(),fypCoordinatorModel.getEmailAddress(), fypCoordinatorModel.getId(),fypCoordinatorModel.getName(), fypCoordinatorModel.getEmailAddress(),projectID,newSupervisorID,Supervisor.getSupervisorIdToName(newSupervisorID),Supervisor.getSupervisorIdToEmail(newSupervisorID),RequestType_Enum.CHANGESUPERVISOR,RequestStatus_Enum.PENDING,Request.getRequests().size()+1);
				cli.displayTitle("Request has been sent");
				Database.updateAllData();
				Thread.sleep(1500);
				break;
				
			// View Project Details Report (with filters)
			/*
			 * User is able to view project details using filters.
			 * The first filter is to view by supervisor ID.
			 * The second filter is to view by student ID.
			 * The last filter is to view by project status.
			 */
			case 6:
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
							tempSupervisorId =  cli.inputString("Supervisor ID (Enter 0 to exit)").toUpperCase();
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
							Thread.sleep(1500);
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
							Thread.sleep(1500);
							break;
						}

						Database.updateAllData();
						break;
						
					// View by Student ID
					case 2:
						String studentID = "-1";
						Student student = null;
						while (!Student.getStudentsList().contains(student)) {
							studentID =  cli.inputString("Student ID (Enter 0 to exit)").toUpperCase();
							student = Student.getStudentFromID(studentID);
							if (!Student.getStudentsList().contains(student)) {
								if (studentID.equals("0")) break;
								cli.display("Please enter a valid Student ID");
							}
						}
						if (studentID.equals("0")) break;
						cli.displayTitle("Generating Project Details of Student... ");
						String studentName = Student.getStudentIdToName(studentID);
						for (Project proj : Project.getProjectList()) {
							if (proj.getStudentName().equals(studentName)) {
								ProjectView.printProjectInfo(proj.getProjectId());
								cli.display("------------------------------------");
							}
						}
						Thread.sleep(1500);
						break;
						
					// View by Project Status	
					case 3:
						ProjectView.printProjectsByStatus();
						Thread.sleep(1500);
						break;
					}
				}
				break;
			
			
				
               
			// View Profile	
			/*
			 * Enables users to view their current profile information.
			 */
			case 7:
				cli.displayTitle("View Profile");
				FYPCoordinatorView.printFYPCoordinatorRecordInfo(fypCoordinatorModel.getId(), fypCoordinatorModel.getName(), fypCoordinatorModel.getEmailAddress(), fypCoordinatorModel.getPassword());
				Thread.sleep(1500);
				break;
				
			// Logout	
			case 8:
				cli.displayTitle("Logging out...");
				Database.updateAllData();
				Thread.sleep(1500);
				return;
				
			default:
				return;
			}
			
		}
		
		
	}
}
		
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			



