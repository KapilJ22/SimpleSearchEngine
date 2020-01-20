package search;

import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.Integer.parseInt;
import static java.lang.String.*;
import static java.lang.String.valueOf;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        HashMap<String, List> map = new HashMap<String, List>();
        List<Integer> list = new ArrayList<Integer>();


        String pathToPeopleListFile;
        //     pathToPeopleListFile = "/Users/kapil.jainibm.com/peopleList.txt";
        pathToPeopleListFile = args[1];

        ArrayList<String> listOfPeople = new ArrayList<>();
        // split by new line
        String[] lines = readFileAsString(pathToPeopleListFile).split("\\r?\\n");
        int count = 1;
        for (String line : lines) {
            //System.out.println("line " + count++ + " : " + line);
            listOfPeople.add(line);
            for (String word : line.split("\\s+")) {
                if (map.containsKey(word.toLowerCase())) map.get(word.toLowerCase()).add(count);
                else {
                    map.put(word.toLowerCase(), (new ArrayList<Integer>()));
                    map.get(word.toLowerCase()).add(count);
                }
            }
            count++;
        }

        map.forEach((k, v) -> System.out.println("Key: " + k + ", Value: " + v));


        //  ArrayList<String> listOfPeople = addPeopleToList(scanner);
//        === Menu ===
//                1. Find a person
//        2. Print all people
//        0. Exit
//                > 3
        displayMenu();

        int option = parseInt(scanner.nextLine());
        while (option != 0) {
            doAction(option,
                    listOfPeople,
                    scanner,
                    map);
            displayMenu();
            String s = scanner.nextLine();
            if (s.isBlank() || s.isEmpty())
                option = parseInt(scanner.nextLine());
            else {
                if (isNumeric(s))
                    option = parseInt(s);
                else option = -1;

            }
        }

        scanner.close();
    }

    private static void doAction(int option, ArrayList<String> listOfPeople, Scanner scanner, Map map) {
        switch (option) {
            case 0:
                break;
            case 1:
                String searchString = scanner.next().toLowerCase().trim();
                ArrayList<String> person = findPeople(listOfPeople, searchString, map);
                printAllPeople(person);
                break;
            case 2:
                printAllPeople(listOfPeople);
                break;
            default:
                System.out.println("Incorrect option! Try again.");
        }
    }


    private static void printAllPeople(ArrayList<String> listOfPeople) {
        for (String person : listOfPeople) {
            System.out.println(person);
        }
    }

    private static void displayMenu() {
        System.out.println("\n=== Menu ===");
        System.out.println("1. Find a person");
        System.out.println("2. Print all people");
        System.out.println("0. Exit");
    }

    private static ArrayList<String> addPeopleToList(Scanner scanner) {
        System.out.println("Enter the number of people:");
        int numOfPeople = scanner.nextInt();
        scanner.nextLine();
        ArrayList<String> localListOfPeople = new ArrayList<String>();

        while (numOfPeople > 0) {
            localListOfPeople.add(scanner.nextLine());
            numOfPeople--;
        }
        return localListOfPeople;
    }

    private static ArrayList<String> findPeople(ArrayList<String> listOfPeople, String searchString, Map<Integer, List> map) {
        boolean found = false;
        ArrayList<String> resultsPeople1 = new ArrayList<String>();
//        for (int i = 0; i < listOfPeople.size(); i++) {
//            if (listOfPeople.get(i).toLowerCase().contains(searchString)) {
//                resultsPeople1.add(listOfPeople.get(i));
//
//            }
//        }
        List<Integer> list = new ArrayList<Integer>();
        if (map.containsKey(searchString.toLowerCase())) {
            list = map.get(searchString.toLowerCase());
        }

        for (int count : list) {
            resultsPeople1.add(listOfPeople.get(count - 1));

        }

        return resultsPeople1;

    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }


}
