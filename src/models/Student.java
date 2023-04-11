package models;

import java.util.ArrayList;
import java.util.HashMap;

import utilities.Database;

public class Student extends User {
	
	private static final String FILEPATH = "src/data/";
	
	private static final String FILENAME = "studentList.txt";

	private static ArrayList<Student> studentsList = new ArrayList<Student>();

	private static Database d = new Database();
	
	private String studentId;
	
	private String studentName;
	
	private String studentEmail;
	
	private String studentPassword;

    private int projectID = -1;

    public Student(String studentId, String studentEmail, String studentPassword, String studentName) {
		this.studentId = studentId.toUpperCase();
		this.studentName = studentName;
		this.studentEmail = studentEmail;
		this.studentPassword = studentPassword;
		addStudent(this);
	}

	public void setStudentName(String studentName){
		this.studentName = studentName;
	}

	public String getName(){
		return this.studentName;
	}
	
	public String getEmailAddress(){
		return this.studentEmail;
	}
	
	public void setEmailAddress(String studentEmail){
		this.studentEmail =  studentEmail;
	}
	
	public String getPassword(){
		return this.studentPassword;
	}
	
	public void setPassword(String studentPassword){
		this.studentPassword = studentPassword;
	}
	
	public void addStudent(Student s) {
		studentsList.add(s);
		updateFile();
	}
	
	public String getId() {
		return this.studentId;
	}

    public int getProjectID() {
		return projectID;
	}

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }
	
	public static boolean duplicateStudentId(String studentId) {

		for (Student s : studentsList) {
			if (s.getId().equalsIgnoreCase(studentId)) {
				return true;
			}
		}
		return false;
	}
	
	public static Student getStudentFromID(String studentID) {
		for (Student s : studentsList) {
			if (s.getId().equals(studentID)) {
				return s;
			}
		}
		return null;
	}
	
	public static Student getStudentFromName(String studentName) {
		for (Student s : studentsList) {
			if (s.getName().equals(studentName)) {
				return s;
			}
		}
		return null;
	}
	
	public static int getTotalNumberOfStudents(){
		return studentsList.size();
	}
	
	public static ArrayList<Student> getStudentsList(){
		return studentsList;
	}
	
	public static Student getStudent(int i){
		return studentsList.get(i);
	}
	
	public static void removeStudent(Student s){
		studentsList.remove(s);
	}
	
	public static ArrayList<String> getStudentMenu(){
		
		ArrayList<String> studentMenu = new ArrayList<String>();
		
		for (Student s : studentsList) {

			studentMenu.add(s.getName() + " ("
					+ s.getId() + ")");
		}
		
		return studentMenu;
	}

	public static void updateStudentsList(ArrayList<Student> s){
		studentsList = s;
	}

	public static void initializeFile() throws Throwable {
		HashMap<String, String[]> map  = d.initializeFile(FILENAME, FILEPATH);
		for (String name : map.keySet()) {
        	String[] values = map.get(name);
        	new Student(values[0], values[1], values[2], name); 
        }
	}

	public static void updateFile() {
		ArrayList<User> usersList = new ArrayList<>(studentsList);
		
		d.updateFile(FILENAME,FILEPATH,usersList);
	}

}
