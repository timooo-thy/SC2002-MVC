package models;

/**
 * The User class is an abstract class that serves as the parent class for all user types in the FYP Registration System.
 * It defines the common properties and methods of all users such as id, email address, name and password.
 * @author Timothy Lee
 * @author Justin Wong
 * @author Jun Hao
 * @author Lee Cheng Yao
 * @author Abhishekh
 * @version 1.0
 * @since 2023-04-16
 */
public abstract class User {

	/**
	 * This method returns the user's ID.
	 * 
	 * @return The user's ID.
	 */
	public abstract String getId();

	/**
	 * This method returns the user's email address.
	 * 
	 * @return The user's email address.
	 */
	public abstract String getEmailAddress(); 

	/**
	 * This method returns the user's name.
	 * 
	 * @return The user's name.
	 */
	public abstract String getName();
	
	/**
	 * This method returns the user's password.
	 * @return The user's password.
	 */
	public abstract String getPassword();
	
}
