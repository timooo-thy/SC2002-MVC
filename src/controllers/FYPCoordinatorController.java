package controllers;

import models.FYPCoordinator;
import models.User;


public class FYPCoordinatorController extends Controller {
	
	
	private FYPCoordinator FYPCoordinatorModel;
	
	private MainController mainController;
	
	public void run(User user) throws Throwable{

        if(user instanceof FYPCoordinator) {
		    FYPCoordinatorModel = (FYPCoordinator)user;
		}
            
		mainController = new MainController();

		String[] menu = {
                "1. Change Password ",
				"2. Logout"
		};
		
		int choice = 0;
		
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

				case 2:
					cli.display("Logging out...");
					return;
									
				default:
				mainController.run(user);
			}
		}
	}
}


