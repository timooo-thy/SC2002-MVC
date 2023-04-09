package controllers;

import models.User;
import views.ConsoleInterface;

// Abstract class Controller.
public abstract class Controller{
	
	/** Represents the General Interface. */
	protected ConsoleInterface cli;
	
	/**
	 * Instantiates a new Controller.
	 */
	public Controller() {
		cli = new ConsoleInterface();
	}
	
	/**
	 * Runs the Controller.
	 *
	 * @throws IOEXCEPTION if Input/Output has an error
	 * @throws ClassNotFoundException if Class cannot be found.
	 */
	public abstract void run(User user) throws Throwable;

}

