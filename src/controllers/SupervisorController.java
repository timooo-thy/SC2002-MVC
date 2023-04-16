package controllers;

import java.util.ArrayList;

import models.Project;
import models.ProjectStatus_Enum;
import models.Request;
import models.RequestStatus_Enum;
import models.Supervisor;
import models.User;
import utilities.Database;
import models.RequestType_Enum;
import views.ProjectView;
import views.RequestView;
import views.SupervisorView;


/**
 * This class represents a controller for a Supervisor in the FYP Registration System.
 * It extends the abstract class Controller and overrides its run() method.
 * @author Timothy Lee
 * @author Justin Wong
 * @author Jun Hao
 * @author Lee Cheng Yao
 * @author Abhishekh
 * @version 1.0
 * @since 2023-04-16
 */
public class SupervisorController extends Controller {
	
	/**
	 * The Supervisor model associated with this controller.
	 */
	private Supervisor supervisorModel;
	
	/**
	 * The MainController associated with this Supervisor Controller.
	 */
	private MainController mainController;
	
	/**
	 * This method overrides the abstract method run() in the Controller class.
	 * It takes in a User object as a parameter and throws any exceptions that may occur during execution.
	 * 
	 * @param user The currently logged in Supervisor
	 * @throws ClassNotFoundException If the specified class cannot be found
	 */
	public void run(User user) throws Throwable{

		if(user instanceof Supervisor) {
		    supervisorModel = (Supervisor)user;
		}
            
		mainController = new MainController();
		
		
		String[] menu = {
                "Change Password ",
                "Create/Update/View projects ",
                "View Student Pending Request ",
				"View Incoming and Outgoing Request History and Status", 
				"Request to Change Supervisor",
				"View Profile",
				"Logout"
		};	
		
		int choice = 0;
		int id;
		int minichoice;
		int choice3;
		while(choice < menu.length) {
			/*
			 * Supervisor main page
			 */
			cli.displayTitle("SUPERVISOR FUNCTIONS");
			String newPending = RequestView.checkForNew(supervisorModel.getId());
			menu[2] = "View Pending Request " + newPending;
			cli.display(menu);
			
			choice = cli.inputInteger("Choice", 1, menu.length);
			
			switch (choice) {
				case 1: //Change Password
					/**
					 * Case 1 enables user to change password. If attempt to key current password is wrong 3 times, attempt is failed
					 * If successful, password is changed and user will have to log in again.
					 */
				    boolean isPasswordChanged = false;
				    int tries = 3;
	
				    while (tries > 0 && !isPasswordChanged) {
	
				        try {
				            String currentPass = cli.inputString("your current password");
	
				            String newPass = cli.inputString("your new password");
	
				            String confirmPass = cli.inputString("password to reconfirm");
	
				            if (!newPass.equals(confirmPass)) {
				                cli.displayTitle("Passwords do not match. Please try again.");
				                continue;
				            }
				             supervisorModel.setPassword(newPass);
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
					
				case 2: 
					/*
					 *	Case 2 enables user to Create,Update and View project.  
					 *	Creating a project will add a project into the project list.
					 *	User is able to change the name of their project
					 *	When selection is to view projects, user will be able to see projects they supervised or the projects they have created
					 */
					String[] create_update_view_menu = {
							"Create Projects ",
							"Update Projects ",
							"View Projects ",
							"Back"
					};
					
					choice3 = 0;
					
					while (choice3<create_update_view_menu.length) {
						cli.displayTitle("Create/Update/View Menu");
						cli.display(create_update_view_menu);
						choice3 = cli.inputInteger("Choice",1,create_update_view_menu.length);
						
						switch (choice3) {
							case 1:
								cli.displayTitle("Create New Project");						
								String projectTitle;
								projectTitle = cli.inputString("Please enter the Project Title (Enter 0 to exit)");
								if (projectTitle.equals("0")) break;
								else if (supervisorModel.getSupervisedProjectList().size()!= Project.MAX_PROJECT)
									new Project(supervisorModel.getName(),projectTitle,ProjectStatus_Enum.AVAILABLE);
								else new Project(supervisorModel.getName(),projectTitle,ProjectStatus_Enum.UNAVAILABLE);
								cli.displayTitle("Project has been added successfully");
								Database.updateAllData();
								Thread.sleep(1500);
								break;
								
							case 2:
								cli.displayTitle("Modify Own Project Title");
								ArrayList<Integer> ownProjectID = new ArrayList<>();
								id = -1;
								//loops through project list to find supervisorID
							    for (Project proj : Project.getProjectList()) {
							    	if (proj.getSupervisorId().equals(supervisorModel.getId())) {
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
									     
							    while (!ownProjectID.contains(id)) {
								    id = cli.inputInteger("Choose Project ID to modify Project Title (Enter 0 to exit)");
								    if (id == 0) break;
								    if (!ownProjectID.contains(id))
								    	 cli.display("Please enter a valid project ID");
								    }  				     
									if (id == 0) break; 
									String newtitle = cli.inputString("Enter New Project Title (Enter 0 to exit)");
									if (newtitle.equals("0")) {
										cli.displayTitle("Modification Cancelled");
									    Thread.sleep(1500);
										break;
									}
									//continues if never input 0
									if (Project.getProject(id).getProjectTitle().equals(Project.getProject(id).getOriProjectTitle())) 
										Project.getProject(id).setProjectTitle(newtitle);
									Project.getProject(id).setOriProjectTitle(newtitle);
									cli.displayTitle("Project Title has been updated");
									Database.updateAllData();
								    Thread.sleep(1500);
								    break;
								    
							case 3:
								String[] viewMenu = {
									"View Supervised Projects",
									"View Projects Created",
									"Back"
								};
								int choice2 = 0;
								
								while (choice2<viewMenu.length) {
									cli.displayTitle("View Menu");
									cli.display(viewMenu);
									choice2 = cli.inputInteger("Choice",1,viewMenu.length);
									
									switch(choice2) {
									case 1:
										cli.displayTitle("View Supervised Projects");
										if (supervisorModel.getSupervisedProjectList().size() == 0) {
											cli.displayTitle("Currectly not supervising any project");
											Thread.sleep(1500);
											break;
										}
										else {
											//loops through project list to print all supervised projects
											for (Project proj : supervisorModel.getSupervisedProjectList()) {
												ProjectView.printProjectInfo(proj.getProjectId());
												cli.display("------------------------------------");
											}
											Thread.sleep(1500);
											break;
										}
									case 2:
										cli.displayTitle("View Projects Created");
										//loops through project list to print all projects created
										int projectsCreated = 0;
										for (Project proj : Project.getProjectList()) {
											if (proj.getSupervisorId().equals(supervisorModel.getId())) {
												ProjectView.printProjectInfo(proj.getProjectId());
												projectsCreated++;
											}
										}
										if (projectsCreated == 0) {
											cli.displayTitle("You have not created any projects");
										}
										Thread.sleep(1500);
										break;
									case 3:
										break;
									default:
										break;
									}
								}
								break;
								
							case 4:
							    Thread.sleep(1500);
								break;
							default:	
								break;
						}
					}
					break;
					
				// View student pending request
				case 3:
					/*
					 * User will first see all pending requests for them.
					 * User is then able to approve or reject these requests.
					 */
					String[] ApproveReject = {
							"Approve",
							"Reject",
							"Back"
					};					
					cli.displayTitle("View Student Pending Request Menu" + newPending); 
					ArrayList<String> requestTitleChangeStudentID = new ArrayList<>();
					//loops through requests to find PENDING and CHANGETITLE requests
					for (Request req : Request.getRequests()) {
						if (req.getRecipientID().equals(supervisorModel.getId()) && req.getRequestStatus() == RequestStatus_Enum.PENDING) {
							RequestView.printRequestInfo(req.getRequestID());
							if (req.getRequestType() == RequestType_Enum.CHANGETITLE) {
								requestTitleChangeStudentID.add(req.getSenderID());
							}
							else if (req.getRequestType() != RequestType_Enum.CHANGETITLE) {
								cli.display("[Log In To Coordinator Account To Approve]");
							}
							cli.display("------------------------------------");
						}
					}
					if (requestTitleChangeStudentID.size() == 0) {
						cli.display("There are no pending requests to be approved");
						Thread.sleep(1500);
						break;
					}
					else {
						String selection = "-1";
						while (!requestTitleChangeStudentID.contains(selection)) {
							selection = cli.inputString("Student ID (0 to exit)");
							if (selection.equals("0")) {
								cli.display("Cancelled");
								break;
							}
							
							if (!requestTitleChangeStudentID.contains(selection))
								cli.display("Please enter a valid Student ID");
							}
						
							if (selection.equals("0")) break;
							
							cli.display(ApproveReject);
							choice = cli.inputInteger("Choice ", 1, ApproveReject.length);
							
							if (choice==1) {
								Project.changeProjectTitle(Request.getRequestFromStudentId(selection).getProjectID(), Request.getRequestFromStudentId(selection).getNewProjectTitle());
								Request.getRequestFromStudentId(selection).setRequestStatus(RequestStatus_Enum.APPROVED);
								cli.displayTitle("Request approved, Project Title has been updated");
								Database.updateAllData();
								Thread.sleep(1500);
								break;
							}
							
							else if (choice==2) {
								Request.getRequestFromStudentId(selection).setRequestStatus(RequestStatus_Enum.REJECTED);
								cli.displayTitle("Request rejected");
								Database.updateAllData();
								Thread.sleep(1500);
								break;
							}
							
							else if (choice==3) {
								Thread.sleep(1500);
								break;
							}
							
						}
					
				//View Incoming and Outgoing Request History and Status
				case 4: 
					/*
					 * Case 4 enables user to view all incoming requests history and their status.
					 * They are also able to view all outgoing requests history and thier status.
					 */
					String[] historyMenu = {
							"View Incoming Requests History and Status",
							"View Outgoing Requests History and Status",
							"Back"
					};
					
					choice = 0;
					
					while (choice<historyMenu.length) {
						cli.displayTitle("View Incoming and Outgoing Request History and Status");
						cli.display(historyMenu);
						choice = cli.inputInteger("Choice", 1, historyMenu.length);
					
						switch(choice) {
							case 1:
								cli.displayTitle("View Incoming Request History"); //pass in ???
								RequestView.printIncomingRequestHistory(supervisorModel.getId());	
								Thread.sleep(1500);
								break;
							case 2:
								cli.displayTitle("View Outgoing Request History");
								RequestView.printRequestHistory(supervisorModel.getId());
								Thread.sleep(1500);
								break;
							case 3:
								break;
							default: 
								break;
							}
					}
					break;
				
				//Request to change supervisor in charge of project
				case 5:
					/*
					 * Case 5 enables user to request to FYPCoordinator to change the supervisor in charge of the project.
					 * The first input will be the Project ID, followed by the replacement supervisor ID. 
					 */
					cli.displayTitle("Request to Change Supervisor in Charge");
					if (supervisorModel.getSupervisedProjectList().size() == 0) {
						cli.displayTitle("Currently not supervising any project!");
						Thread.sleep(1500);
						break;
					}
					id = -1;
					minichoice = -1;
					ArrayList<Integer> supervisedProjectID = new ArrayList<>();	
					//loops through the project list to print projects
					for (Project proj : supervisorModel.getSupervisedProjectList()) {
							ProjectView.printProjectInfo(proj.getProjectId());
							supervisedProjectID.add(proj.getProjectId());
							cli.display("------------------------------------");
				    }
					while (!supervisedProjectID.contains(id)) {
						id = cli.inputInteger("Select Project ID (Enter 0 to exit) ");
						if (id == 0) break; 							
						if (!supervisedProjectID.contains(id)) {
							cli.display("Please enter a valid Project ID");
						}
					}
					// Exit if supervisor choose to quit
					if (id == 0) break; 
							     
					minichoice = 0;
					String newSupervisorID = cli.inputString("Enter the Replacement Supervisor ID");
					while (minichoice != 1) {
						//if replacement supervisor id is found will proceed normally
						for (Supervisor sup : Supervisor.getSupervisorsList()) {
								if (sup.getId().equals(newSupervisorID)) {
							    	minichoice = 1;
							    	break;
							    }
						}
						if (minichoice == 1) break;
						cli.display("Supervisor ID does not exist!");
						newSupervisorID = cli.inputString("Enter the Replacement Supervisor ID");
					}							
					//creates new request
					new Request(supervisorModel.getId(),supervisorModel.getName(),supervisorModel.getEmailAddress(), "ASFLI", "Li Fang", "ASFLI@ntu.edu.sg",id,newSupervisorID,Supervisor.getSupervisorIdToName(newSupervisorID),Supervisor.getSupervisorIdToEmail(newSupervisorID),RequestType_Enum.CHANGESUPERVISOR,RequestStatus_Enum.PENDING,Request.getRequests().size()+1);
					cli.displayTitle("Request has been sent");
					Database.updateAllData();
					Thread.sleep(1500);
					break;
					
				case 6:
					/*
					 * Case 6 enables users to view their current profile information
					 */
					cli.displayTitle("View Profile");
					SupervisorView.printSupervisorRecordInfo(supervisorModel.getId(), supervisorModel.getName(), supervisorModel.getEmailAddress(), supervisorModel.getPassword());
					Thread.sleep(1500);
					break;
				case 7:
					cli.displayTitle("Logging out...");
					Thread.sleep(1500);
					return;
				default:
					return;
					
			}
		}
	}
}