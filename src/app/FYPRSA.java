package app;

import controllers.MainController;
import models.User;
import utilities.Database;

/**
 * The FYPRSA class is the main class that initialises the FYP Registration System Application and runs the main controller.
 * It also initialises and updates the application's database.
 * @author Timothy Lee
 * @author Justin Wong
 * @author Jun Hao
 * @author Lee Cheng Yao
 * @author Abhishekh
 */
public class FYPRSA {
	
	/** The currently logged in user. */
	static User user;
	
	/**
	 * The main method is the entry point for the FYP Registration System Application.
	 * It initialises the MainController and calls its run method with the currently logged in user. 
	 * It also initialises the application's database and updates it after the run method completes. 
	 *
	 * @param args The command line arguments passed to the application.
	 * @throws Throwable Any exception that may occur during the execution of the program.
	 */
	public static void main(String args[]) throws Throwable  {
		
		final MainController mainController = new MainController();

		Database.initializeAllData();
		
	    mainController.run(user);
	    
	    Database.updateAllData();
		
		System.exit(0);
	}
}