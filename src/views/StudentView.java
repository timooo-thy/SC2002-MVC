package views;

public class StudentView {
    
	public void printStudentRecordInfo(String studentId,String studentName, String studentEmail, String password){
		View.cli.display("Student ID: " + studentId );
		View.cli.display("Student Name: " + studentName.toUpperCase() );
		View.cli.display("Student Email: " + studentEmail.toUpperCase() );
		View.cli.display("Student Password: " + password);
		
	}
}
