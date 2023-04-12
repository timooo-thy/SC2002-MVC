package controllers;

import models.Student;
import models.User;
import models.Supervisor;
import models.FYPCoordinator;


public class MainController extends Controller {
    
    private User currentUser;

	private StudentController studentController = new StudentController();
	private SupervisorController supervisorController = new SupervisorController();
	private FYPCoordinatorController fypcoordinatorController = new FYPCoordinatorController();
	
	public void run(User user) throws Throwable{
		
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
                String studentid = cli.inputString("ID");
                String studentpassword = cli.inputString("Password");
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
