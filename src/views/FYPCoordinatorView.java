package views;

/**
 * The FYPCoordinatorView class provides methods for displaying information about FYP Coordinators in a console-based user interface.
 */
public class FYPCoordinatorView {
    
    /**
     * Prints the information for a single FYP Coordinator.
     * 
     * @param FYPCoordinatorId the ID of the FYP Coordinator
     * @param FYPCoordinatorName the name of the FYP Coordinator
     * @param FYPCoordinatorEmail the email of the FYP Coordinator
     * @param password the password of the FYP Coordinator
     */
    public static void printFYPCoordinatorRecordInfo(String FYPCoordinatorId, String FYPCoordinatorName, String FYPCoordinatorEmail, String password){
        View.cli.display("FYPCoordinator ID: " + FYPCoordinatorId);
        View.cli.display("FYPCoordinator Name: " + FYPCoordinatorName.toUpperCase());
        View.cli.display("FYPCoordinator Email: " + FYPCoordinatorEmail.toUpperCase());
        View.cli.display("FYPCoordinator Password: " + password);    
    }
}
