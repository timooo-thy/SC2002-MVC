package app;

import controllers.MainController;
import models.User;
import utilities.Database;

public class FYPRSA {
	
	static User user;
	
	public static void main(String args[]) throws Throwable  {
		
		final MainController mainController = new MainController();

		Database.initializeAllData();
		
	    mainController.run(user);
	    
	    Database.updateAllData();
		
		System.exit(0);
	}
}