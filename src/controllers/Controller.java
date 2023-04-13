package controllers;

import models.User;
import views.ConsoleInterface;

/**
 * The class is an abstract class that provides a general interface for all controllers in the application.
 * @author Timothy Lee
 * @author Justin Wong
 * @author Jun Hao
 * @author Lee Cheng Yao
 * @author Abhishekh
 */
public abstract class Controller {
	
	/** Represents the General Interface. */
	protected ConsoleInterface cli;
	
	/**
	 * Instantiates a new Controller object and initialises the console interface.
	 */
	public Controller() {
		cli = new ConsoleInterface();
	}
	
	/**
	 * This method is to be implemented by all subclasses of Controller. It runs the controller and handles user input/output.
	 * 
	 * @param user The currently logged in user.
	 * @throws ClassNotFoundException If the specified class cannot be found.
	*/
	public abstract void run(User user) throws Throwable;

}

