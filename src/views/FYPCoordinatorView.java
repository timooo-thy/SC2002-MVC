package Views;

public class FYPCoordinatorView {
    public void printFYPCoordinatorRecordInfo(String FYPCoordinatorId,String FYPCoordinatorName, String FYPCoordinatorEmail, String password){
		System.out.println("FYPCoordinator ID: " + FYPCoordinatorId );
		System.out.println("FYPCoordinator Name: " + FYPCoordinatorName.toUpperCase() );
		System.out.println("FYPCoordinator Email: " + FYPCoordinatorEmail.toUpperCase() );
		System.out.println("FYPCoordinator Password: " + password);	
	}
}
