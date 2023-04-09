package views;

public class SupervisorView {
    
    public void printSupervisorRecordInfo(String supervisorId,String supervisorName, String supervisorEmail, String password){
    	View.cli.display("Supervisor ID: " + supervisorId );
    	View.cli.display("Supervisor Name: " + supervisorName.toUpperCase() );
    	View.cli.display("Supervisor Email: " + supervisorEmail.toUpperCase() );
    	View.cli.display("Supervisor Password: " + password);
	}
}
