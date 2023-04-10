package views;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class ConsoleInterface {

	/** Represents the scanner. */
	private Scanner sc;
	
	/**
	 * Instantiates a new General Interface.
	 */
	public ConsoleInterface() {
		
		sc = new Scanner(System.in);
	}
	
	/**
	 * Display a String.
	 *
	 * @param toDisplay A String containing the things to be displayed.
	 */
	public void display(String toDisplay) {
		System.out.println(toDisplay);
	}
	
	/**
	 * Display an Array of Strings.
	 *
	 * @param toDisplay A String Array containing the things to be displayed.
	 */
	public void display(String[] toDisplay) {
		
		int count = 1;
		
		for (String str : toDisplay) {
			System.out.println("(" + (count++) + ") " + str);
		}
	}
	
	/**
	 * Display an ArrayList of Strings .
	 *
	 * @param toDisplay An ArrayList containing the things to be displayed.
	 */
	public void display(ArrayList<String> toDisplay) {
		
		int count = 1;
		
		for (String str : toDisplay) {
			System.out.println("(" + (count++) + ") " + str);
		}
	}
	
	/**
	 * Display the Title.
	 *
	 * @param toDisplay A String containing the Title to be displayed.
	 */
	public void displayTitle(String toDisplay){
		
		System.out.println("----------------------------------------------------------------------------");
		System.out.println(toDisplay);
		System.out.println("----------------------------------------------------------------------------");
	}
	
	/**
	 * Input an Integer.
	 *
	 * @param title A String containing the Title to be displayed.
	 * @param lower An Integer containing the lower limit of the input.
	 * @param upper An Integer containing the upper limit of the input.
	 * @return An Integer representing the input.
	 */
	public int inputInteger(String title, int lower, int upper) {
		
		while (true) {
			try {
				System.out.print(">> " + title + " (" + lower + "~" + upper + "): ");
				int input =sc.nextInt();
				sc.nextLine();
				if (input >= lower & input <= upper) {
					return input;
				}
			} catch (InputMismatchException e) {
				sc.nextLine();
			}
			System.out.println("   ERROR: Please enter a valid input.");
		}
	}
	
	public int inputInteger(String title) {
		
		while (true) {
			try {
				System.out.print(">> " + title + ": ");
				int input =sc.nextInt();
				sc.nextLine();
				return input;
			} catch (InputMismatchException e) {
				sc.nextLine();
			}
			System.out.println("   ERROR: Please enter a valid input.");
		}
	}
	/**
	 * Input a String with length restrictions.
	 *
	 * @param title  A String containing the Title to be displayed.
	 * @param upper An Integer containing the maximum length of the input.
	 * @param desc A String containing the description to be displayed.
	 * @return A String representing the input.
	 */
	public String inputString(String title, int upper,String desc) {
		
		while(true) {
			try {
				System.out.println(">> Input " + title + "(" + desc + ")" );
				String input = sc.next();
				sc.nextLine();
				if(input.length() == upper ) {
					return input;
				}
			} catch(InputMismatchException e) {
				sc.nextLine();
			}
			System.out.println(" ERROR: Please enter a valid input");
		}
	}
	
	/**
	 * Input a String without length restrictions.
	 *
	 * @param title A String containing the Title to be displayed.
	 * @param desc An Integer containing the description to be displayed.
	 * @return  A String representing the input.
	 */
	public String inputString(String title, String desc){
		while(true) {
			try {
				System.out.println(">> Input " + title + "(" + desc + ")" );
				String input = sc.nextLine();
				
				return input;

			} catch(InputMismatchException e) {
				sc.nextLine();
			}
			System.out.println(" ERROR: Please enter a valid input");
		}
	}

    public String inputString(String title) {
		while(true) {
			try {
				System.out.println(">> Input " + title);
				String input = sc.nextLine();
				
				return input;

			} catch(InputMismatchException e) {
				sc.nextLine();
			}
			System.out.println(" ERROR: Please enter a valid input");
		}
	}
}
