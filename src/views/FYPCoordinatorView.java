package views;

/**
 * The FYPCoordinatorView class provides methods for displaying information about FYP Coordinators in a console-based user interface.
 * @author Timothy Lee
 * @author Justin Wong
 * @author Jun Hao
 * @author Lee Cheng Yao
 * @author Abhishekh
 * @version 1.0
 * @since 2023-04-16
 */
public class FYPCoordinatorView {
    
    /**
     * Prints the information for a single FYP Coordinator.
     * 
     * @param FYPCoordinatorId The ID of the FYP Coordinator
     * @param FYPCoordinatorName The name of the FYP Coordinator
     * @param FYPCoordinatorEmail The email of the FYP Coordinator
     * @param password The password of the FYP Coordinator
     */
    public static void printFYPCoordinatorRecordInfo(String FYPCoordinatorId, String FYPCoordinatorName, String FYPCoordinatorEmail, String password){
        View.cli.display("FYPCoordinator ID: " + FYPCoordinatorId);
        View.cli.display("FYPCoordinator Name: " + FYPCoordinatorName.toUpperCase());
        View.cli.display("FYPCoordinator Email: " + FYPCoordinatorEmail.toUpperCase());
        View.cli.display("FYPCoordinator Password: " + password);    
    }
}
