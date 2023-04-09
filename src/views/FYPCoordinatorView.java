package views;

public class FYPCoordinatorView {
    public void printFYPCoordinatorRecordInfo(String FYPCoordinatorId,String FYPCoordinatorName, String FYPCoordinatorEmail, String password){
		View.cli.display("FYPCoordinator ID: " + FYPCoordinatorId );
		View.cli.display("FYPCoordinator Name: " + FYPCoordinatorName.toUpperCase() );
		View.cli.display("FYPCoordinator Email: " + FYPCoordinatorEmail.toUpperCase() );
		View.cli.display("FYPCoordinator Password: " + password);	
	}
}
