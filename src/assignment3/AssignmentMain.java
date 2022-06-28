package assignment3;

import java.awt.event.FocusEvent;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class AssignmentMain {


    public static List<String> FriendFileName(String friendFileName){
        // Read friend.txt and store values in List:data
        // Tries to read file, but if unsuccessful,
        // it catches the error and throws an execption

        List<String> data = new ArrayList<>();

        try {
            File myFile = new File(friendFileName);
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

        return data;
    }

    public static List<String> IndexFileName(String indexFileName){
        // Read index.txt and store values in List:accounts
        // Tries to read file, but if unsuccessful,
        // it catches the error and throws an execption

        List<String> accounts = new ArrayList<>();

        try {
            File myFile = new File(indexFileName);
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
        return accounts;
    }

    public static Graph CreateGraph(List<String> accounts, List<String> data){
        Graph socialGraph = new Graph(accounts.size());

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

        return socialGraph;
    }

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

    public static List<String> SearchForFriends(String name, Graph socialGraph) {
        List<String> friendsList = new ArrayList<>();
        boolean nameFound = false;
        int k;
        int indexOfName = 0;


        if (socialGraph.size() > 0) {
            for (k = 0; k < socialGraph.size(); k++) {
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

    public static List<String> SearchForFriendsOfFriends(List<String> friendList, Graph socialGraph) {

        List<String> alreadyVisitedFriends = new ArrayList<>();


        for (int i = 0; i < friendList.size(); i++) {
            if (alreadyVisitedFriends.contains(friendList.get(i))) {
                System.out.println("Friends of this friend have already been added to the list");
            } else {
                SearchForFriends(friendList.get(i), socialGraph);
                alreadyVisitedFriends.add(friendList.get(i));
            }
        }

        System.out.print(alreadyVisitedFriends);
        return alreadyVisitedFriends;
    }


    public static List<String> SearchForCommonFriends(Graph socialGraph) {
        List<String> commonFriends = new ArrayList<>();

        Scanner response = new Scanner(System.in);

        System.out.println("Enter name 1: ");
        String name1 = response.next().toLowerCase();
        System.out.println("Enter name 2: ");
        String name2 = response.next().toLowerCase();

        List<String> name1Friends = SearchForFriends(name1, socialGraph);
        List<String> name2Friends = SearchForFriends(name2, socialGraph);

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
                    friends = SearchForFriends(name, socialGraph);
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

    public static void ListPopularity(Graph socialGraph){
        int[][] popularity = new int[socialGraph.size()][2];
        for(int i = 0; i < socialGraph.size(); i++){
            popularity[i][0] = socialGraph.neighbors(i).length;
            popularity[i][1] = i;
            //System.out.println(socialGraph.getLabel(i) + " " + popularity[i]);
        }

        int temp;
        int tempIndex;

        for (int j = 0; j < socialGraph.size(); j++){
            for (int k = 1; k < (socialGraph.size() - j); k++){
                if(popularity[k-1][0] < popularity[k][0]){
                    temp = popularity[k-1][0];
                    tempIndex = popularity[k-1][1];
                    popularity[k-1][0] = popularity[k][0];
                    popularity[k-1][1] = popularity[k][1];

                    popularity[k][0] = temp;
                    popularity[k][1] = tempIndex;
                }
            }
        }

        for (int l = 0; l < popularity.length; l++){
            System.out.println(socialGraph.getLabel(popularity[l][1]) + " " + popularity[l][0]);
        }

    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        // Task 7
        boolean running = true;
        String friendFileName = "";
        String indexFileName = "";
        Scanner response = new Scanner(System.in);
        List<String> data;
        List<String> accounts;
        Graph socialGraph = null;

        while(running){
            System.out.println("1. Enter Friend filename and Index filename: ");
            System.out.println("2. Enter the name of a user to list their friends: ");
            System.out.println("3. Enter the name of a user to list the friends of their friends: ");
            System.out.println("4. Enter the names of two users to list their common friends: ");
            System.out.println("5. Enter the name of a user you would like to delete: ");
            System.out.println("6. List all users in order of popularity: ");
            System.out.println("7. Exit: ");

            if(response.hasNext()){
                switch(response.next()){
                    case "1":

                        System.out.println("Please enter the Friends filename: ");
                        if (response.hasNext()){
                            friendFileName = response.next();
                        }else {
                            break;
                        }

                        System.out.println("Please enter the Index filename: ");
                        if (response.hasNext()){
                            indexFileName = response.next();
                        }else {
                            break;
                        }

                        data =  FriendFileName(friendFileName);
                        accounts = IndexFileName(indexFileName);
                        socialGraph = CreateGraph(accounts, data);

                        break;

                    case  "2":
                        SearchForFriends(PromptForName(), socialGraph);
                        break;

                    case "3":
                        SearchForFriendsOfFriends(SearchForFriends(PromptForName(), socialGraph), socialGraph);
                        break;

                    case "4":
                        SearchForCommonFriends(socialGraph);
                        break;
                    case  "5":
                        RemoveAccount(socialGraph);
                        break;

                    case "6":
                        ListPopularity(socialGraph);
                        break;

                    case "7":
                        running = false;
                        break;

                    default:
                        System.out.println("Error:: Invalid selection");
                        break;
                }
            }






        }

        // Friend Filename: src/assignment3/friend.txt
        // Index Filename:  src/assignment3/index.txt
    }
}
