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
	 * @param user The currently logged in Supervisor.
	 * @throws IOException If there is an input/output error during the execution of the method.
	 * @throws ClassNotFoundException If the specified class cannot be found.
	 */
	public void run(User user) throws Throwable{

		if(user instanceof Supervisor) {
		    supervisorModel = (Supervisor)user;
		}
            
		mainController = new MainController();

		String[] menu = { 
				"Change Password",
				"Create Project",
				"Modify Own Project Title",
				"View Supervised Projects",
				"View Projects Created",
				"Approve/Reject Title Change Requests ",
				"Request to Change Supervisor",
				"View Incoming and Outgoing Request History and Status", //doing
				"View Profile",
				"Logout"
		};
		
		int choice = 0;
		int id;
		int minichoice;
		
		while(choice < menu.length) {
			
			cli.displayTitle("SUPERVISOR FUNCTIONS");
			String newPending = RequestView.checkForNew(supervisorModel.getId());
			menu[5] = "Approve/Reject Title Change Requests " + newPending;
			cli.display(menu);
			
			choice = cli.inputInteger("Choice", 1, menu.length);
			
			switch(choice) {
			
				case 1:
					//Change Password
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
				            System.out.println("Error: " + e.getMessage() + ". Please try again.");
				            tries--;
				        }
				    }
				    Database.updateAllData();
				    Thread.sleep(1000);    
					return;
					
				case 2:
					//Create new project
			
						cli.displayTitle("Create New Project");						
						String projectTitle;
						projectTitle = cli.inputString("Please enter the Project Title (Enter 0 to exit)");
						if (projectTitle.equals("0")) break;
						else if (supervisorModel.getSupervisedProjectList().size()!= 2)
							new Project(supervisorModel.getName(),projectTitle,ProjectStatus_Enum.AVAILABLE);
						else new Project(supervisorModel.getName(),projectTitle,ProjectStatus_Enum.UNAVAILABLE);
						cli.displayTitle("Project has been added successfully");
						Database.updateAllData();
						Thread.sleep(1000);
						break;

				
				case 3:
					//Modify own project title
						cli.displayTitle("Modify Own Project Title");
						ArrayList<Integer> ownProjectID = new ArrayList<>();
						id = -1;
					    for (Project proj : Project.getProjectList()) {
					    	if (proj.getSupervisorId().equals(supervisorModel.getId())) {
						    	ProjectView.printProjectInfo(proj.getProjectId());
						    	ownProjectID.add(proj.getProjectId());
								cli.display("------------------------------------");
						     }
					    }
							     
					    if(ownProjectID.size()==0) {
					    	cli.displayTitle("You have yet registered for a project, unable to modify title");
						    Thread.sleep(1000);
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
							    Thread.sleep(1000);
								break;
							}
							//continues if never input 0
							if (Project.getProject(id).getProjectTitle().equals(Project.getProject(id).getOriProjectTitle())) 
								Project.getProject(id).setProjectTitle(newtitle);
							Project.getProject(id).setOriProjectTitle(newtitle);
							cli.displayTitle("Project Title has been updated");
							Database.updateAllData();
						    Thread.sleep(1000);
						    break;

					
				case 4:
					// View supervised project
					cli.displayTitle("View Supervised Projects");
					if (supervisorModel.getSupervisedProjectList().size() == 0) {
						cli.displayTitle("Currectly not supervising any project");
						Thread.sleep(1000);
						break;
					}
					else {
						for (Project proj : supervisorModel.getSupervisedProjectList()) {
							ProjectView.printProjectInfo(proj.getProjectId());
							cli.display("------------------------------------");
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
					Thread.sleep(1000);
					break;
					
				case 6:
					//approve/reject title change requests
					String[] Menu_6 = {
							"Approve/Reject a request",
							"Back"
					};
					String[] Menu_6_2 = {
							"Approve",
							"Reject",
							"Back"
					};
					choice = 0;
					
					while (choice < Menu_6.length) {
						cli.displayTitle("Approve/Reject Title Change Requests Menu");
						cli.display(Menu_6);
						choice = cli.inputInteger("Choice",1,Menu_6.length);
						ArrayList<String> requestTitleChangeStudentID = new ArrayList<>();
						switch(choice) {
							case 1:
								cli.displayTitle("Approve/Reject Title Change Requests");
								for (Request req : Request.getRequests()) {
									if (req.getRequestStatus() == RequestStatus_Enum.PENDING && req.getRequestType() == RequestType_Enum.CHANGETITLE) {
										RequestView.printRequestInfo(req.getRequestID());
										requestTitleChangeStudentID.add(req.getSenderID());
										cli.display("------------------------------------");
									}
								}
								if (requestTitleChangeStudentID.size() == 0) {
									cli.display("There are no pending requests");
									Thread.sleep(1000);
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
										
										cli.display(Menu_6_2);
										int choice2 = cli.inputInteger("Choice ", 1, Menu_6_2.length);
										
										if (choice2==1) {
											Project.changeProjectTitle(Request.getRequestFromStudentId(selection).getProjectID(), Request.getRequestFromStudentId(selection).getNewProjectTitle());
											Request.getRequestFromStudentId(selection).setRequestStatus(RequestStatus_Enum.APPROVED);
											cli.displayTitle("Request approved, Project Title has been updated");
											Database.updateAllData();
											Thread.sleep(1000);
											break;
										}
										
										else if (choice2==2) {
											Request.getRequestFromStudentId(selection).setRequestStatus(RequestStatus_Enum.REJECTED);
											cli.displayTitle("Request rejected");
											Database.updateAllData();
											Thread.sleep(1000);
											break;
										}
										
										else if (choice2==3) {
											cli.displayTitle("Returning to Request Menu...");
											Thread.sleep(1000);
											break;
										}
										
									}
							case 2:
								break;
							default:
								break;
								
						}
						
					}
					break;
					
				case 7:
					//Request FYP coordinator to change supervisor in charge

					cli.displayTitle("Request to Change Supervisor in Charge");
					if (supervisorModel.getSupervisedProjectList().size() == 0) {
						cli.displayTitle("Currently not supervising any project!");
						Thread.sleep(1000);
						break;
					}
					id = -1;
					minichoice = -1;
					ArrayList<Integer> supervisedProjectID = new ArrayList<>();		    
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
					// Exit if Coordinator chose to quit
					if (id == 0) break; 
							     
					minichoice = 0;
					String newSupervisorID = cli.inputString("Enter the Replacement Supervisor ID");
					while (minichoice != 1) {
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
					new Request(supervisorModel.getId(),supervisorModel.getName(),supervisorModel.getEmailAddress(), "ASFLI", "Li Fang", "ASFLI@ntu.edu.sg",id,newSupervisorID,Supervisor.getSupervisorIdToName(newSupervisorID),Supervisor.getSupervisorIdToEmail(newSupervisorID),RequestType_Enum.CHANGESUPERVISOR,RequestStatus_Enum.PENDING,Request.getRequests().size()+1);
					cli.displayTitle("Request has been sent");
					Database.updateAllData();
					Thread.sleep(1000);
					break;
					
				case 8: 
					//View Incoming and Outgoing Request History and Status
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
							Thread.sleep(1000);
							break;
						case 2:
							cli.displayTitle("View Outgoing Request History");
							RequestView.printRequestHistory(supervisorModel.getId());
							Thread.sleep(1000);
							break;
						case 3:
							break;
						default: 
							break;
					}
				}
				break;
				
				case 9: //View Profile
					cli.displayTitle("View Profile");
					SupervisorView.printSupervisorRecordInfo(supervisorModel.getId(), supervisorModel.getName(), supervisorModel.getEmailAddress(), supervisorModel.getPassword());
					Thread.sleep(1000);
					break;
					
				case 10:
					cli.displayTitle("Logging out...");
					Thread.sleep(1000);
					return;
									
				default:
					return;
			}
		}
	}
}

