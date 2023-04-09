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

public class Database{
	
	public static void initializeAllData() throws Throwable {
		Student.initializeFile();
		Supervisor.initializeFile();
		FYPCoordinator.initializeFile();
		Project.initializeProjectFile();
	}
	
	public static void updateAllData() throws Throwable {
		Student.updateFile();
		Supervisor.updateFile();
		FYPCoordinator.updateFile();
		Project.updateProjectFile();
	}
	
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
            	String password = splitLine[splitLine.length - 1].trim(); 
                map.put(name, new String[] {id, email, password});
            }
            bufferedReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
		return map;
        
	}
	
	public void updateFile(String FILENAME, String FILEPATH, ArrayList<User> list) {
		try {
	        PrintWriter writer = new PrintWriter(FILEPATH+FILENAME, "UTF-8");
	        writer.println("Name Email Password");
	        for (User user : list) {
	            writer.println(user.getName() + ";" + user.getEmailAddress()+ ";" + user.getPassword());
	        }
	        writer.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public HashMap<String, String> initializeProjectFile(String FILENAME, String FILEPATH) {
		File model = new File(FILEPATH + FILENAME);
		HashMap<String, String> map = new HashMap<>();
		try {
            FileReader fileReader = new FileReader(model);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
            	String[] splitLine = line.trim().split(";");
            	String title = splitLine[1];
            	String name = splitLine[0].trim(); 
                map.put(name, title);
            }
            bufferedReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
		return map;
        
	}
	
	public void updateProjectFile(String FILENAME, String FILEPATH, ArrayList<Project> list) {
		try {
	        PrintWriter writer = new PrintWriter(FILEPATH+FILENAME/*, "UTF-8"*/);
	        writer.println("Supervisor Title");
	        for (Project project : list) {
	            writer.println(project.getSupervisorName() + ";" + project.getProjectTitle());
	        }
	        writer.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}

