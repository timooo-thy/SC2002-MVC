package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import models.Student;
import models.User;
import models.Supervisor;
import models.FYPCoordinator;
import models.Project;
import models.Request;
import models.RequestStatus_Enum;
import models.RequestType_Enum;
import models.ProjectStatus_Enum;

/**
 * This class represents the methods to initialise and update the database.
 */
public class Database {
    
    /**
     * This method initialises all data in the database.
     * 
     * @throws Throwable if an error occurs
     */
    public static void initializeAllData() throws Throwable {
        Student.initializeFile();
        Supervisor.initializeFile();
        FYPCoordinator.initializeFile();
        Project.initializeProjectFile();
        Request.initializeRequestFile();
    }
    
    /**
     * This method updates all data in the database.
     * 
     * @throws Throwable if an error occurs
     */
    public static void updateAllData() throws Throwable {
        Student.updateFile();
        Supervisor.updateFile();
        FYPCoordinator.updateFile();
        Project.updateProjectFile();
        Request.updateRequestFile();
    }
    
    /**
     * This method initialises the Student file in the database.
     * 
     * @param FILENAME the name of the Student file
     * @param FILEPATH the path of the Student file
     * @return a HashMap representing the Student file
     */
    public HashMap<String, String[]> initializeStudentFile(String FILENAME, String FILEPATH) {
        File model = new File(FILEPATH + FILENAME);
        HashMap<String, String[]> map = new HashMap<>();
        try {
            FileReader fileReader = new FileReader(model);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.readLine(); //skip the first line
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] splitLine = line.trim().split(";");
                String email = splitLine[1];
                String id = splitLine[1].substring(0, splitLine[1].indexOf("@"));
                String name = splitLine[0].trim(); 
                String password = splitLine[2].trim(); 
                String deregistered = splitLine[3];
                map.put(name, new String[] {id, email, password, deregistered});
            }
            bufferedReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
    
    /**
     * This method updates the Student file in the database.
     * 
     * @param FILENAME the name of the Student file
     * @param FILEPATH the path of the Student file
     * @param list an ArrayList of Students to update
     */
    public void updateStudentFile(String FILENAME, String FILEPATH, ArrayList<Student> list) {
        try {
            PrintWriter writer = new PrintWriter(FILEPATH+FILENAME, "UTF-8");
            writer.println("name;email;password;projectID");
            for (Student user : list) {
                writer.println(user.getName() + ";" + user.getEmailAddress()+ ";" + user.getPassword() + ";" + user.getProjectID());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * This method initialises Supervisor and FYP Coordinator files in the database.
     * 
     * @param FILENAME the name of the Supervisor and FYP Coordinator files
     * @param FILEPATH the path of the Supervisor and FYP Coordinator files
     * @return a HashMap representing the Supervisor and FYP Coordinator files
     */
	public HashMap<String, String[]> initializeFile(String FILENAME, String FILEPATH) {
		File model = new File(FILEPATH + FILENAME);
		HashMap<String, String[]> map = new HashMap<>();
		try {
            FileReader fileReader = new FileReader(model);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
            	String[] splitLine = line.trim().split(";");
            	String email = splitLine[1];
            	String id = splitLine[1].substring(0, splitLine[1].indexOf("@"));
            	String name = splitLine[0].trim(); 
            	String password = splitLine[2].trim(); 
                map.put(name, new String[] {id, email, password});
            }
            bufferedReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
		return map;
        
	}
	
	/**
	 * This method updates the Supervisor and FYP Coordinator files in the database.
	 * 
	 * @param FILENAME the name of the Supervisor and FYP Coordinator files
     * @param FILEPATH the path of the Supervisor and FYP Coordinator files
	 * @param list an ArrayList of User to update
	*/
	public void updateFile(String FILENAME, String FILEPATH, ArrayList<User> list) {
		try {
	        PrintWriter writer = new PrintWriter(FILEPATH+FILENAME, "UTF-8");
	        writer.println("name;email;password");
	        for (User user : list) {
	            writer.println(user.getName() + ";" + user.getEmailAddress()+ ";" + user.getPassword());
	        }
	        writer.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
     * This method initialises Project file in the database.
     * 
     * @param FILENAME the name of the Project file
     * @param FILEPATH the path of the Project file
     * @return a HashMap representing the Project file
     */
	public HashMap<Integer, Object[]> initializeProjectFile(String FILENAME, String FILEPATH) {
		File model = new File(FILEPATH + FILENAME);
		HashMap<Integer, Object[]> map = new HashMap<>();
		try {
            FileReader fileReader = new FileReader(model);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
            	String[] splitLine = line.trim().split(";");
            	int id = Integer.parseInt(splitLine[0]); 
            	String supName = splitLine[1];
            	String title = splitLine[2];
            	String studName = splitLine[3];
            	ProjectStatus_Enum projectStatus = ProjectStatus_Enum.valueOf(splitLine[4]);
                map.put(id, new Object[] {
                		supName, title, studName, projectStatus});
            }
            bufferedReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
		return map;
        
	}
	
	/**
     * This method updates the Project file in the database.
     * 
     * @param FILENAME the name of the Project file
     * @param FILEPATH the path of the Project file
     * @param list an ArrayList of Projects to update
     */
	public void updateProjectFile(String FILENAME, String FILEPATH, ArrayList<Project> list) {
		try {
	        PrintWriter writer = new PrintWriter(FILEPATH+FILENAME, "UTF-8");
	        writer.println("ProjectId;supervisorName;Title;studentName;projectStatus");
	        for (Project proj : list) {
	            writer.println(proj.getProjectId()+ ";" + proj.getSupervisorName() + ";" + proj.getProjectTitle() + ";" + proj.getStudentName() + ";" + proj.getProjectStatus());
	        }
	        writer.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
     * This method initialises Request file in the database.
     * 
     * @param FILENAME the name of the Request files
     * @param FILEPATH the path of the Request files
     * @return a HashMap representing the Request files
     */
	public HashMap<Integer, Object[]> initializeRequestFile(String FILENAME, String FILEPATH) {
		File model = new File(FILEPATH + FILENAME);
		HashMap<Integer, Object[]> map = new HashMap<>();
		try {
            FileReader fileReader = new FileReader(model);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
            	String[] splitLine = line.trim().split(";");
            	String senderID = splitLine[0];
            	String senderName = splitLine[1];
            	String senderEmail = splitLine[2];
            	String recipientID = splitLine[3];
            	String recipientName = splitLine[4];
            	String recipientEmail = splitLine[5];
            	int projectID = Integer.parseInt(splitLine[6]);
            	int newProjectID = Integer.parseInt(splitLine[7]);
            	String newProjectTitle = splitLine[8];
            	String newSupervisorID = splitLine[9];
            	String newSupervisorName = splitLine[10];
            	String newSupervisorEmail = splitLine[11];
            	RequestType_Enum requestType = RequestType_Enum.valueOf(splitLine[12]);
            	RequestStatus_Enum requestStatus = RequestStatus_Enum.valueOf(splitLine[13]);
            	int requestID = Integer.parseInt(splitLine[14]);
                map.put(requestID, new Object[] {senderID, senderName, senderEmail, recipientID, 
                		recipientName, recipientEmail, projectID, newProjectID, newProjectTitle, 
                		newSupervisorID, newSupervisorName, newSupervisorEmail, requestType, requestStatus});
            }
            bufferedReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
		return map;
	}
	
	/**
     * This method updates the Request file in the database.
     * 
     * @param FILENAME the name of the Request file
     * @param FILEPATH the path of the Request file
     * @param list an ArrayList of Requests to update
     */
	public void updateRequestFile(String FILENAME, String FILEPATH, ArrayList<Request> list) {
		try {
	        PrintWriter writer = new PrintWriter(FILEPATH+FILENAME, "UTF-8");
	        writer.println("senderID;senderName;senderEmail;recipientID;recipientName;recipientEmail;projectID;newProjectID;newProjectTitle;newSupervisorID;newSupervisorName;newSupervisorEmail;requestType;requestStatus;requestID");
	        for (Request request : list) {
	            writer.println(request.getSenderID() + ";" + request.getSenderName() + ";" + request.getSenderEmail() + ";" 
	            		+ request.getRecipientID() + ";" + request.getRecipientName() + ";" + request.getRecipientEmail() + ";" 
	            		+ request.getProjectID() + ";" + request.getNewProjectID() + ";" + request.getNewProjectTitle() + ";" 
	            		+ request.getNewSupervisorID() + ";" + request.getNewSupervisorName() + ";" + request.getNewSupervisorEmail() + ";" 
	            		+ request.getRequestType() + ";" + request.getRequestStatus() + ";" + request.getRequestID());
	        }
	        writer.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}

