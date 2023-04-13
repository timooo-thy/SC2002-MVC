package views;

/**
 * The SupervisorView class provides methods for displaying information about Supervisors in a console-based user interface.
 * @author Timothy Lee
 * @author Justin Wong
 * @author Jun Hao
 * @author Lee Cheng Yao
 * @author Abhishekh
 */
public class SupervisorView {
    
	/**
     * Prints the information for a single Supervisor.
     * 
     * @param supervisorId The ID of the Supervisor
     * @param supervisorName The name of the Supervisor
     * @param supervisorEmail The email of the Supervisor
     * @param password The password of the Supervisor
     */
    public static void printSupervisorRecordInfo(String supervisorId,String supervisorName, String supervisorEmail, String password){
    	View.cli.display("supervisor ID: " + supervisorId);
    	View.cli.display("supervisor Name: " + supervisorName.toUpperCase());
    	View.cli.display("supervisor Email: " + supervisorEmail.toUpperCase());
    	View.cli.display("supervisor Password: " + password);
	}
}
