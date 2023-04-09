package Views;

public class SupervisorView {
    
    public void printSupervisorRecordInfo(String supervisorId,String supervisorName, String supervisorEmail, String password){
		System.out.println("Supervisor ID: " + supervisorId );
		System.out.println("Supervisor Name: " + supervisorName.toUpperCase() );
		System.out.println("Supervisor Email: " + supervisorEmail.toUpperCase() );
		System.out.println("Supervisor Password: " + password);
		
	}
}
