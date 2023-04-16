package models;

/**
 * This interface represents the methods to initialise and update the files to the database.
 * @author Timothy Lee
 * @author Justin Wong
 * @author Jun Hao
 * @author Lee Cheng Yao
 * @author Abhishekh
 * @version 1.0
 * @since 2023-04-16
 */
public interface DatabaseConnector {

	/**
	 * Initialises the list of models from the data file.
	 * 
	 * @throws Throwable If there is an error reading the data file
	 */
	public static void initializeFile() throws Throwable {}
	
	/**
	 * Updates the data file with the current list of models.
	 */
	public static void updateFile() {}
	
}
