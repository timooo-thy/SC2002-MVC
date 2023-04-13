package views;

/**
 * The SupervisorView class provides methods for displaying information about Supervisors in a console-based user interface.
 */
public class SupervisorView {
    
	/**
     * Prints the information for a single Supervisor.
     * 
     * @param supervisorId the ID of the Supervisor
     * @param supervisorName the name of the Supervisor
     * @param supervisorEmail the email of the Supervisor
     * @param password the password of the Supervisor
     */
    public static void printSupervisorRecordInfo(String supervisorId,String supervisorName, String supervisorEmail, String password){
    	View.cli.display("supervisor ID: " + supervisorId);
    	View.cli.display("supervisor Name: " + supervisorName.toUpperCase());
    	View.cli.display("supervisor Email: " + supervisorEmail.toUpperCase());
    	View.cli.display("supervisor Password: " + password);
	}
}
