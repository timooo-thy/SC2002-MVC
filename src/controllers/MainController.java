package controllers;

import models.Student;
import models.User;
import models.Supervisor;
import models.FYPCoordinator;


/**
 * The class is responsible for handling the main menu of the FYP Registration System Application (FYPRSA).
 * It routes users to their respective controllers based on their chosen role.
 * @author Timothy Lee
 * @author Justin Wong
 * @author Jun Hao
 * @author Lee Cheng Yao
 * @author Abhishekh
 * @version 1.0
 * @since 2023-04-16
 */
public class MainController extends Controller {
    
	/**
	 * The current logged in user.
	 */
    private User currentUser;

    /**
     * The controller for managing Students.
     */
	private StudentController studentController = new StudentController();
	
	/**
     * The controller for managing Supervisors.
     */
	private SupervisorController supervisorController = new SupervisorController();
	
	/**
     * The controller for managing FYP Coordinators.
     */
	private FYPCoordinatorController fypcoordinatorController = new FYPCoordinatorController();
	
	/**
	 * Runs the main menu and routes users to their respective controllers based on their chosen role.
	 * 
	 * @param user The currently logged in user.
	 * @throws ClassNotFoundException If the specified class cannot be found.
	*/
	public void run(User user) throws Throwable{
		
		/*
		 * Main login page before any user is able to access their functions.
		 * User will choose their User Type and will be prompted their login details: User ID and Password.
		 * A successful login will run the respective user controller.
		 */
		int choice = 0;

		String[] menu = {
				"Student",
				"Supervisor",
				"FYPCoordinator",
				"Exit"
		};

		
		while(choice < menu.length) {
			
			currentUser=null;

			cli.displayTitle("Welcome to the FYP Registration System Application (FYPRSA)");

			cli.display(menu);
			
			choice = cli.inputInteger("choice", 1, menu.length);

			
			switch(choice) {
			
			case 1:
                String studentid = cli.inputString("your ID");
                String studentpassword = cli.inputString("your password");
                for(Student student:Student.getStudentsList()) {
        	        if(student != null && student.getId().equals(studentid) && student.getPassword().equals(studentpassword)){
        	            currentUser=student;
        	            break;
        	        }
                }
                
                if(currentUser!=null) {
                	cli.display("Welcome " + currentUser.getName());
                	studentController.run(currentUser);
                }
                
                else {
                	cli.display("Wrong Username/Password. Try again.");
                	Thread.sleep(1000);
                }
                
                break;
            
			case 2:
                String supervisorid = cli.inputString("ID");
                String supervisorpassword = cli.inputString("Password");
                for(Supervisor supervisor:Supervisor.getSupervisorsList()) {
        	        if(supervisor != null && supervisor.getId().equals(supervisorid) && supervisor.getPassword().equals(supervisorpassword)){
        	            currentUser=supervisor;
        	            break;
        	        }
                }
                
                if(currentUser!=null) {
                	cli.display("Welcome " + currentUser.getName());
                	supervisorController.run(currentUser);
                }
                
                else {
                	cli.display("Wrong Username/Password. Try again.");
                	Thread.sleep(1000);
                }
                
                break;
                
			case 3:
                String fypcoordinatorid = cli.inputString("ID");
                String fypcoordinatorpassword = cli.inputString("Password");
                for(FYPCoordinator fypcoordinator:FYPCoordinator.getFYPCoordinatorsList()) {
        	        if(fypcoordinator != null && fypcoordinator.getId().equals(fypcoordinatorid) && fypcoordinator.getPassword().equals(fypcoordinatorpassword)){
        	            currentUser=fypcoordinator;
        	            break;
        	        }
                }
                
                if(currentUser!=null) {
                	cli.display("Welcome " + currentUser.getName());
                	fypcoordinatorController.run(currentUser);
                }
                
                else {
                	cli.display("Wrong Username/Password. Try again.");
                	Thread.sleep(1000);
                }
                
                break;
	
			case 4:
				System.out.println("Thank you for using the FYP System!");
				return;
				
			}
		}
				
	}
	
}
