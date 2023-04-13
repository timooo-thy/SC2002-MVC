package models;

import java.util.ArrayList;
import java.util.HashMap;
import utilities.Database;

/**
 * The Student class represents a student in the system.
 * It extends the User class and provides methods to manage the list of students.
 * It also has methods to retrieve and update information about the students from the database.
 * @author Timothy Lee
 * @author Justin Wong
 * @author Jun Hao
 * @author Lee Cheng Yao
 * @author Abhishekh
 */
public class Student extends User {
	
	/**
	 * File path of the file to store the list of students.
	 */
	private static final String FILEPATH = "src/data/";
	
	/**
	 * File name of the file to store the list of students.
	 */
	private static final String FILENAME = "studentList.txt";

	/**
	 * List of all the students in the system.
	 */
	private static ArrayList<Student> studentsList = new ArrayList<Student>();

	/**
	 * Database object to perform database operations.
	 */
	private static Database d = new Database();
	
	/**
	 * Student's ID.
	 */
	private String studentId;
	
	/**
	 * Student's Name
	 */
	private String studentName;
	
	/**
	 * Student's Email
	 */
	private String studentEmail;
	
	/**
	 * Student's Password
	 */
	private String studentPassword;

	/**
	 * Student's Project ID
	 */
    private int projectID;
    
    /**
     * Creates a new Student object with the given attributes, and adds it to the list of students.
     * 
     * @param studentId The ID of the student.
     * @param studentEmail The email address of the student.
     * @param studentPassword The password of the student.
     * @param studentName The name of the student.
     * @param projectID The ID of the project that the student is assigned to.
     */
    public Student(String studentId, String studentEmail, String studentPassword, String studentName, int projectID) {
		this.studentId = studentId.toUpperCase();
		this.studentName = studentName;
		this.studentEmail = studentEmail;
		this.studentPassword = studentPassword;
		this.projectID = projectID;
		addStudent(this);
	}
    
    /**
     * Sets the name of the student.
     * 
     * @param studentName The new name of the student.
     */
	public void setStudentName(String studentName){
		this.studentName = studentName;
	}

	/**
	 * Retrieves the name of the student.
	 * 
	 * @return The name of the student.
	 */
	public String getName(){
		return this.studentName;
	}
	
	/**
	 * Retrieves the email address of the student.
	 * @return The email address of the student.
	 */
	public String getEmailAddress(){
		return this.studentEmail;
	}
	
	/**
	 * Sets the email address of the student.
	 * 
	 * @param studentEmail The new email address of the student.
	 */
	public void setEmailAddress(String studentEmail){
		this.studentEmail =  studentEmail;
	}
	
	/**
	 * Retrieves the password of the student.
	 * 
	 * @return The password of the student.
	 */
	public String getPassword(){
		return this.studentPassword;
	}
	
	/**
	 * Sets the password of the student.
	 * 
	 * @param studentPassword The new password of the student.
	 */
	public void setPassword(String studentPassword){
		this.studentPassword = studentPassword;
	}
	
	/**
	 * Adds a new student to the list of students, and updates the file that stores the list of students.
	 * 
	 * @param s The student object to add.
	 */
	public void addStudent(Student s) {
		studentsList.add(s);
		updateFile();
	}
	
	/**
	 * Retrieves the ID of the student.
	 * 
	 * @return The ID of the student.
	 */
	public String getId() {
		return this.studentId;
	}

	/**
	 * Retrieves the ID of the project that the student is assigned to.
	 * 
	 * @return The ID of the project that the student is assigned to.
	 */
    public int getProjectID() {
		return projectID;
	}

    /**
     * Sets the ID of the project that the student is assigned to.
     * 
     * @param projectID The new project ID.
     */
    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }
	
    /**
     * Checks if there is already a student with the given ID in the list of students.
     * 
     * @param studentId The ID of the student to check.
     * @return True if there is already a student with the given ID, false otherwise.
     */
	public static boolean duplicateStudentId(String studentId) {
		// loops through the list to find duplicate students
		for (Student s : studentsList) {
			if (s.getId().equalsIgnoreCase(studentId)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Retrieves the total number of students in the list of students.
	 * 
	 * @return The total number of students in the list of students.
	 */
	public static int getTotalNumberOfStudents(){
		return studentsList.size();
	}
	
	/**
	 * Retrieves the list of students.
	 * 
	 * @return The list of students.
	 */
	public static ArrayList<Student> getStudentsList(){
		return studentsList;
	}
	
	/**
	 * Retrieves a specific student from the list of students.
	 * 
	 * @param i The index of the student to retrieve.
	 * @return The student at the specified index.
	 */
	public static Student getStudent(int i){
		return studentsList.get(i-1);
	}
	
	/**
	 * Removes a student from the list of students.
	 * 
	 * @param s The student to remove.
	 */
	public static void removeStudent(Student s){
		studentsList.remove(s);
	}
	
	/**
	 * Generates a menu of students with their names and IDs.
	 * 
	 * @return An ArrayList of strings that represent the student menu.
	 */
	public static ArrayList<String> getStudentMenu(){
		ArrayList<String> studentMenu = new ArrayList<String>();
		// loops through the list and add it into the menu
		for (Student s : studentsList) {
			studentMenu.add(s.getName() + " ("
					+ s.getId() + ")");
		}
		return studentMenu;
	}

	/**
	 * Returns the student object from a given student name.
	 * 
	 * @param studentName The name of the student to retrieve
	 * @return The student object associated with the given name, or null if not found
	 */
	public static Student getStudentFromName(String studentName) {
		// loops through the list to find student object with name
		for (Student s : studentsList) {
			if (s.getName().equals(studentName)) {
				return s;
			}
		}
		return null;
	}
		
	/**
	 * Returns the student object from a given student ID.
	 * 
	 * @param studentID The ID of the student to retrieve
	 * @return The student object associated with the given ID, or null if not found
	 */
	public static Student getStudentFromID(String studentID) {
		// loops through the list to find student object with id
		for (Student s : studentsList) {
			if (s.getId().equals(studentID)) {
				return s;
			}
		}
		return null;
	}	

	/**
	 * Returns the email address of a student with a given name.
	 * 
	 * @param studentName The name of the student whose email address to retrieve
	 * @return The email address of the student with the given name, or null if not found
	 */
	public static String getStudentNameToEmail(String studentName){
		// loops through the list to find student email with name
		for (Student stud : Student.getStudentsList()) {
			if ((stud.getName()).equals(studentName)) {
				return stud.getEmailAddress();
			}
		}
		return null;
	}
	
	/**
	 * Returns the ID of a student with a given name.
	 * 
	 * @param studentName The name of the student whose ID to retrieve
	 * @return The ID of the student with the given name, or null if not found
	 */
	public static String getStudentNameToId(String studentName) {
		// loops through the list to find student id with name
		for (Student stud : Student.getStudentsList()) {
			if ((stud.getName()).equals(studentName)) {
				return stud.getId();
			}
		}
		return null;
	}
	
	/**
	 * Returns the email address of a student with a given ID.
	 * 
	 * @param studentId The ID of the student whose email address to retrieve
	 * @return The email address of the student with the given ID, or null if not found
	 */
	public static String getStudentIdToEmail(String studentId){
		// loops through the list to find student email with id
		for (Student stud : Student.getStudentsList()) {
			if ((stud.getId()).equals(studentId)) {
				return stud.getEmailAddress();
			}
		}
		return null;
	}
	
	/**
	 * Returns the name of a student with a given ID.
	 * 
	 * @param studentId the ID of the student whose name to retrieve
	 * @return the name of the student with the given ID, or null if not found
	 */
	public static String getStudentIdToName(String studentId) {
		// loops through the list to find student name with id
		for (Student stud : Student.getStudentsList()) {
			if ((stud.getId()).equals(studentId)) {
				return stud.getName();
			}
		}
		return null;
	}
	
	/**
	 * Sets the list of students to the specified list.
	 * 
	 * @param s The new list of students
	 */
	public static void updateStudentsList(ArrayList<Student> s){
		studentsList = s;
	}
	
	/**
	 * Initializes the list of students from the data in the student data file.
	 * 
	 * @throws Throwable If there is an error reading the student data file
	 */
	public static void initializeFile() throws Throwable {
		// uses hashmap to create student objects
		HashMap<String, String[]> map  = d.initializeStudentFile(FILENAME, FILEPATH);
		for (String name : map.keySet()) {
        	String[] values = map.get(name);
        	new Student(values[0], values[1], values[2], name, Integer.parseInt(values[3])); 

        }
	}

	/**
	 * Updates the student data file with the current list of students.
	 */
	public static void updateFile() {
		d.updateStudentFile(FILENAME,FILEPATH,studentsList);
	}

}
