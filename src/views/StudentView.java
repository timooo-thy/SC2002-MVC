package Views;

public class StudentView {
    
	public void printStudentRecordInfo(String studentId,String studentName, String studentEmail, String password){
		System.out.println("Student ID: " + studentId );
		System.out.println("Student Name: " + studentName.toUpperCase() );
		System.out.println("Student Email: " + studentEmail.toUpperCase() );
		System.out.println("Student Password: " + password);
		
	}
    //JH: I dun think we have anything else to add
}
