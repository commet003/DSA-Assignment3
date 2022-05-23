package assignment3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.crypto.Data;

public class Assignmentmain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*
		 * *****************************************************************************
		 * *****
		 ***** Task 1 Start
		 * ******************************************************************
		 */

		List<String> data = new ArrayList<String>();
		List<String> accounts = new ArrayList<String>();

		// Read friend.txt and store values in List:data
		// Tries to read file, but if unsuccessful,
		// it catches the error and throws an execption
		try {
			File myFile = new File("src/assignment3/friend.txt");
			Scanner readDoc = new Scanner(myFile);
			while (readDoc.hasNextLine()) {
				data.add(readDoc.nextLine().toLowerCase());

			}
			readDoc.close();
			// System.out.println(data);
		} catch (FileNotFoundException e) {
			System.err.println("Error: File Not Found!");
			e.printStackTrace();
		}

		// Read index.txt and store values in List:accounts
		// Tries to read file, but if unsuccessful,
		// it catches the error and throws an execption
		try {
			File myFile = new File("src/assignment3/index.txt");
			Scanner readDoc = new Scanner(myFile);
			while (readDoc.hasNextLine()) {
				accounts.add(readDoc.nextLine().toLowerCase());

			}
			readDoc.close();
			// System.out.println(accounts);
		} catch (FileNotFoundException e) {
			System.err.println("Error: File Not Found!");
			e.printStackTrace();
		}

		int n = Integer.parseInt(accounts.get(0));

		Graph socialGraph = new Graph(accounts.size() - 1);

		for (int i = 1; i <= accounts.size() - 1; i++) {

			socialGraph.setLabel(i - 1, String.valueOf(accounts.get(i).substring(2).toString()));
			System.out.println(socialGraph.getLabel(i - 1));
		}

		for (int j = 1; j <= data.size() - 1; j++) {
			socialGraph.addEdge(Character.getNumericValue(data.get(j).charAt(0)),
					Character.getNumericValue(data.get(j).charAt(2)));
		}

		/*
		 * *****************************************************************************
		 * *****
		 ***** Task 2 Start
		 * ******************************************************************
		 */
		
		Scanner response = new Scanner(System.in);

		System.out.println("Please enter a name: ");
		String name = response.next().toLowerCase();
		boolean nameFound = false;
		int k;
		int indexOfName = 0;


		if (socialGraph.size() > 0) {
			for (k = 0; k < accounts.size() - 1; k++) {
				if (name.equals(socialGraph.getLabel(k))) {
					indexOfName = k;
					System.out.println("Success!!");
					nameFound = true;
					break;
				}
			}
			if (nameFound == true) {
				for (int l = 0; l < socialGraph.size(); l++) {
					if (socialGraph.isEdge(l, indexOfName) && indexOfName != l) {
						System.out.println(socialGraph.getLabel(l));
					}
				}
			}
			if (k == accounts.size() - 1 && nameFound == false) {
				System.out.println("ERROR: User not found!");
			}
		} else {
			System.err.println("ERROR: No users found!");

		}
		response.close();

		/*
		 * *****************************************************************************
		 * *****
		 ***** Task 3 Start
		 * ******************************************************************
		 */

		 System.out.println("TASK 3: Please enter a name: ");

		 Scanner task3Response = new Scanner(System.in);

		 String task3Name = task3Response.next().toLowerCase();

		 boolean found = false;

		 int m;
		 int indexNameTask3 = 0;
		 List<Integer> friendIndex = new ArrayList<Integer>();

		 if (socialGraph.size() > 0) {
			for (m = 0; m < accounts.size() - 1; m++) {
				if (name.equals(socialGraph.getLabel(m))) {
					indexNameTask3 = m;
					System.out.println("Success!!");
					found = true;
					break;
				}
			}
			if (found == true) {
				for (int l = 0; l < socialGraph.size(); l++) {
					if (socialGraph.isEdge(l, indexOfName) && indexOfName != l) {
						friendIndex.add(l);
					}
				}
				for(int p = 0; p < socialGraph.size(); p++){
					if(socialGraph.isEdge(friendIndex.get(p), p) && friendIndex.get(p) != p){
						System.out.println(socialGraph.getLabel(p));
					}
				}
			}
			if (m == accounts.size() - 1 && found == false) {
				System.out.println("ERROR: User not found!");
			}
		} else {
			System.err.println("ERROR: No users found!");

		}
		task3Response.close();

	}

}
