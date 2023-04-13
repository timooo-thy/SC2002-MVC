package views;

/**
 * The StudentView class provides methods for displaying information about Students in a console-based user interface.
 * @author Timothy Lee
 * @author Justin Wong
 * @author Jun Hao
 * @author Lee Cheng Yao
 * @author Abhishekh
 */
public class StudentView {
    
	/**
     * Prints the information for a single Student.
     * 
     * @param studentId The ID of the Student
     * @param studentName The name of the Student
     * @param studentEmail The email of the Student
     * @param password The password of the Student
     */
	public static void printStudentRecordInfo(String studentId, String studentName, String studentEmail, String password){
		View.cli.display("Student ID: " + studentId);
		View.cli.display("Student Name: " + studentName.toUpperCase());
		View.cli.display("Student Email: " + studentEmail.toUpperCase());
		View.cli.display("Student Password: " + password);
		
	}
}
