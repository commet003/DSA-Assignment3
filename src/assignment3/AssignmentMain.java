package assignment3;

import java.awt.event.FocusEvent;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class AssignmentMain {

    public static String PromptForName() {
        Scanner response = new Scanner(System.in);

        System.out.println("Please enter a name: ");
        if (response.hasNext()) {
            String name = response.next().toLowerCase();
            //response.close();
            return name;
        } else {
            return "ERROR:: Response is null";
        }
    }

    public static List<String> SearchForFriends(String name, Graph socialGraph, int arrSize) {
        List<String> friendsList = new ArrayList<>();
        boolean nameFound = false;
        int k;
        int indexOfName = 0;


        if (socialGraph.size() > 0) {
            for (k = 0; k < arrSize; k++) {
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
                        friendsList.add(socialGraph.getLabel(l).toString());
                    }
                }
            } else if (nameFound == false) {
                System.out.println("ERROR: User not found!");
            }
        } else {
            System.err.println("ERROR: No users found!");
        }
        //System.out.println(friendsList);
        return friendsList;
    }

    public static List<String> SearchForFriendsOfFriends(List<String> friendList, Graph socialGraph, int arrSize) {

        List<String> alreadyVisitedFriends = new ArrayList<>();


        for (int i = 0; i < friendList.size(); i++) {
            if (alreadyVisitedFriends.contains(friendList.get(i))) {
                System.out.println("Friends of this friend have already been added to the list");
            } else {
                SearchForFriends(friendList.get(i), socialGraph, arrSize);
                alreadyVisitedFriends.add(friendList.get(i));
            }
        }

        System.out.print(alreadyVisitedFriends);
        return alreadyVisitedFriends;
    }


    public static List<String> SearchForCommonFriends(Graph socialGraph, int n) {
        List<String> commonFriends = new ArrayList<>();

        Scanner response = new Scanner(System.in);

        System.out.println("Enter name 1: ");
        String name1 = response.next().toLowerCase();
        System.out.println("Enter name 2: ");
        String name2 = response.next().toLowerCase();

        List<String> name1Friends = SearchForFriends(name1, socialGraph, n);
        List<String> name2Friends = SearchForFriends(name2, socialGraph, n);

        if (name1Friends.size() > name2Friends.size()) {
            for (int i = 0; i < name1Friends.size(); i++) {
                if (name2Friends.contains(name1Friends.get(i))) {
                    commonFriends.add(name1Friends.get(i));
                }
            }
        }

        return commonFriends;
    }

    public static void RemoveAccount(Graph socialGraph) {
        Scanner response = new Scanner(System.in);
        String name;
        List<String> friends;
        List<Integer> friendIndex = new ArrayList();
        int accountIndex = 0;


        System.out.println("Please enter the name of the account you would like to remove: ");
        if (response.hasNext()) {
            name = response.next().toLowerCase();
            System.out.println("Are you sure you wish to delete account " + name + "?");
            if (response.hasNext()) {
                if (response.next().equals("y") || response.next().equals("yes")) {
                    friends = SearchForFriends(name, socialGraph, socialGraph.size());
                    for (int k = 0; k < friends.size(); k++) {
                        for (int l = 0; l < socialGraph.size(); l++) {
                            if (friends.get(k).equals(socialGraph.getLabel(l))) {
                                friendIndex.add(l);
                            } else if (name.equals(socialGraph.getLabel(l))) {
                                accountIndex = l;
                            }
                        }
                    }
                    for (int p = 0; p < friendIndex.size(); p++) {
                        socialGraph.removeEdge(friendIndex.get(p), accountIndex);
                        socialGraph.removeEdge(accountIndex, friendIndex.get(p));
                    }

                    socialGraph.setLabel(accountIndex, null);
                }
            }
        } else {
            System.out.println("ERROR:: Name cannot be null. ");
        }
    }

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

        Graph socialGraph = new Graph(n);

        for (int i = 1; i <= accounts.size() - 1; i++) {

            socialGraph.setLabel(i - 1, accounts.get(i).substring(2));
            System.out.println(socialGraph.getLabel(i - 1) + " " + (i - 1));
        }

        for (int j = 1; j < data.size(); j++) {
            socialGraph.addEdge(Character.getNumericValue(data.get(j).charAt(0)),
                    Character.getNumericValue(data.get(j).charAt(2)));
            socialGraph.addEdge(Character.getNumericValue(data.get(j).charAt(2)),
                    Character.getNumericValue(data.get(j).charAt(0)));
        }

        /*
         * *****************************************************************************
         * *****
         ***** Task 2 Start
         * ******************************************************************
         */


        SearchForFriends(PromptForName(), socialGraph, n);



        /* *****************************************************************************
         * *****
         ***** Task 3 Start
         * *****************************************************************/

        SearchForFriendsOfFriends(SearchForFriends(PromptForName(), socialGraph, n), socialGraph, n);

        // Task 4 Start
        SearchForCommonFriends(socialGraph, n);

        // Task 5 Start
        RemoveAccount(socialGraph);

        // Task 6

    }
}
