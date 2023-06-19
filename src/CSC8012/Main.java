package CSC8012;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
public class Main
{
    private static String newTxtFile;
    private static Scanner options;
    private static int results;
    private static int activities;
    private static int numberOftickets;
    private static boolean incorrectDetails = true;
    private static boolean backToMainMenu = false;
    private static boolean wrongActivity = false;
    private static boolean fileLoaded = false;
    private static boolean shutDownRequest = false;
    public static void setWrongActivity(boolean wrongActivity) {
        Main.wrongActivity = wrongActivity;
    }
    private static SortedArrayList<Customers> customersSortedArrayList = new SortedArrayList<>();
    private static SortedArrayList<Activities> activitySortedArrayList = new SortedArrayList<>();
    private static Activities activity = new Activities("", 0);

    public static void main(String[] args) throws FileNotFoundException
    {
        newTxtFile = "src/CSC8012/input.txt";
        fileReadr();
        if (fileLoaded)
        {
            System.out.println("Please load a file");
        }
        else
        {
            userInterface();
        }
    }

    /**
     * using a scanner to read the file, and naming the arrayLists being used
     */
    public static void fileReadr()
    {

        try
        {
            // the Scanner reads in the file.
            Scanner readFileIn = new Scanner(new FileReader(newTxtFile));
            //takes the information 1 by 1 and adds them to a sorted list, using a for loop.
            int loopNumber = Integer.parseInt(readFileIn.nextLine());
            for (int i = 0; i < loopNumber; i++)
            {
                String name = readFileIn.nextLine();
                System.out.println(name);
                int ticket = parseInt(readFileIn.nextLine());
                System.out.println(ticket);
                Activities activity = new Activities(name.toLowerCase(), ticket);
                activitySortedArrayList.add(activity);
            }
            loopNumber = parseInt(readFileIn.nextLine());
            //reads the information in to a Customer Object and then stores that Object in a Customer Sorted Array List.
            for (int i = 0; i < loopNumber; i++)
            {
                String[] name = readFileIn.nextLine().split(" ");
                Customers person = new Customers(name[0].toLowerCase(), name[1].toLowerCase());
                customersSortedArrayList.add(person);
            }
            // catch a file not found Exception
        } catch (FileNotFoundException e)
        {
            System.out.println("Please make sure you have a file loaded.");
            fileLoaded = true;
            throw new RuntimeException(e);
            // catch if the number at the start of the file isn't a int
        } catch (NumberFormatException e)
        {
            System.out.println("Please make sure you have a correct file loaded");
            fileLoaded = true;
        }
    }
    /**
     * The user interface is contains switches that allow the user to operate the program.
     */
    private static void userInterface()
    {

        // while loop for the userInterface to run as a text based UI.
        // once shut down request is equal to true, the system will shut down.
        while (!shutDownRequest)
        {
            // once back to main menu is equal to true, the system will shut down.
            while (!backToMainMenu)
            {
                //text based UI
                System.out.println("\nPlease choose from the following options\n");
                System.out.println(" f - to finish running the program " +
                        "\n a - to display on the screen information about all the activities " +
                        "\n c - to display on the screen information about all the customers " +
                        "\n t - to update the stored data when tickets are bought by one of the registered customers " +
                        "\n r - to update the stored data when a registered customer cancels tickets for a booking" +
                        "\n \n \n \n ");
                System.out.println();
                System.out.print("Please choose from the following list: ");
                options = new Scanner(System.in);
                String userOption = null;
                //ensuring the user is putting in a valid value.
                while (true)
                {
                    try {
                        userOption = options.nextLine();
                        break;
                    }
                    //asking the user for a valid input
                    catch (NumberFormatException e)
                    {
                        System.out.println("Please Input a correct value");
                    }
                }
                // Switches that allows the user to use the system.

                    switch (userOption.toLowerCase())
                    {

                        // case f returns the shut down of the system.
                        case "f" ->
                        {
                            backToMainMenu =true;
                            shutDownRequest = true;
                            System.out.println("Good Bye!");
                            break;
                        }
                        // case a will load the list of activities.
                        case "a" ->
                        {
                            listOfActivities();

                            break;
                        }
                        // case c will load the list of customers.
                        case "c" ->
                        {
                            listOfCustomers();
                            break;
                        }
                        //case t will allow the user to sell activities to the customers.
                        case "t" ->
                        {
                            while (incorrectDetails)
                            {
                                while (!wrongActivity)
                                {
                                    listOfCustomers();
                                    System.out.print("Please choose the Customer: ");
                                    customerSearch();
                                    if (!wrongActivity)
                                    {
                                        System.out.println("please make sure you have typed in the customers name correctly");
                                    }
                                }
                                wrongActivity = false;
                            }
                            incorrectDetails = true;

                            while (incorrectDetails)
                            {
                                while (!wrongActivity)
                                {
                                    listOfActivities();
                                    activitySearch();
                                    if (!wrongActivity)
                                    {
                                        System.out.println("Please make sure you have typed the correct activity!");
                                    }
                                }
                                wrongActivity = false;
                                customerArrayListManagement();
                            }
                            sellingActivityToCustomer();
                            customerArrayListManagement();
                            incorrectDetails = true;
                            break;
                        }
                        // case r will refund an activity.
                        case "r" ->
                        {
                            System.out.println(customersSortedArrayList.get(0).getTickets());
                            System.out.println(customersSortedArrayList.get(1).getTickets());
                            System.out.println(customersSortedArrayList.get(2).getTickets());

                            if(customersSortedArrayList.get(0).getTickets() > 0 || customersSortedArrayList.get(1).getTickets() > 0 || customersSortedArrayList.get(2).getTickets() > 0)
                            {
                                while (incorrectDetails)
                                {
                                    while (!wrongActivity)
                                    {
                                        listOfCustomers();
                                        System.out.print("Please choose the Customer: ");
                                        customerSearch();
                                        if (!wrongActivity)
                                        {
                                            System.out.println("please make sure you have typed in the customers name correctly");
                                        }
                                    }
                                }

                                wrongActivity = false;
                                incorrectDetails = true;
                                while (incorrectDetails)
                                {
                                    while (!wrongActivity)
                                    {
                                        listOfActivities();
                                        activitySearch();
                                        if (!wrongActivity)
                                        {
                                            System.out.println("Please make sure you have typed the correct activity!");
                                        }
                                    }
                                    returningTickets();
                                }
                                wrongActivity = false;
                                incorrectDetails = true;
                                break;
                            }
                            else
                            {
                                System.out.println("No customer has chosen an activity.");
                            }
                        }
                        // asking the user to only input the vaules being asked for, unless they will get an "Invalided input" message.
                        default ->
                        {
                            System.out.println("Invalided input");
                        }
                    }

            }
        }
    }

    /**
     *List of activities uses a for loop to go through the information in the activity sorted arrayList and,
     * get the name of the activity and the number of tickets available.
     */
    public static void listOfActivities()
    {
        System.out.println(String.format("%-15s%20s", "Activities: ", "Tickets: "));
        System.out.println("-----------------------------------");
        for (int i = 0; i < activitySortedArrayList.size(); i++)
        {
            Activities list = activitySortedArrayList.get(i);
            System.out.println(String.format("%-15s%15s", (" " + list.getNameOfActivities()), list.getNumberOfActivities()));
        }
        System.out.println("\n");
    }

    /**
     *List of customers uses a for loop to go through the information in the customer sorted arrayList and,
     * get the name of the first and second name of the customer along with and the number of tickets they have bought.
     */
    public static void listOfCustomers()
    {
        // List of customers uses a for loop to go through the information in the customer sorted arrayList.
        System.out.println(String.format("%-15s%20s", "Customers: ", "Tickets: "));
        System.out.println("-----------------------------------");

        for (int i = 0; i < customersSortedArrayList.size(); i++)
        {
            Customers listOfCustomers = customersSortedArrayList.get(i);
            String consoleOutput = "";

            //For loop is being used in order to get the selected activities (if the customer has chosen one),
            // if the user has selected an activity, than it will be displayed with the number of tickets bought.
            if (listOfCustomers.getTickets() > 0)
            {
                for(int j = 0 ; j < listOfCustomers.getTickets(); j++)
                {
                    consoleOutput += listOfCustomers.activitiesOfCustomer.get(j).getNameOfActivities() +
                            " " + listOfCustomers.activitiesOfCustomer.get(j).getNumberOfActivities();

                    if( j + 1 < listOfCustomers.getTickets())
                    {
                        consoleOutput += ",";
                    }
                }

                System.out.println(String.format("%-15s%15s",("" + listOfCustomers.getFirstName()) + " " + listOfCustomers.getLastName(), consoleOutput));
            }
            //Return only their first and second name and the preset value of getTickets (0)
            else
            {
                System.out.println(String.format("%-15s%15s", ("" + listOfCustomers.getFirstName()) + " " + listOfCustomers.getLastName(), listOfCustomers.getTickets()));
            }
        }
        System.out.println("\n" + "\n" + "\n" + "\n");
    }

    /**
     * Selling an Activity to the Customer. This method will ask the user for an input and the calulate if there is enough tickets availble,
     * if there is not enough tickets. The method will send the user a letter saying they do not have the tickets.
     * if there is a enough tickets the method will give the request tickets to the customer class.
     */
    public static void sellingActivityToCustomer()
    {
        //Selling to activity will ask the user for how many tickets they like and give the tickets to the customer and remove said tickets from activity sorted ArrayList
        Scanner tickets = new Scanner(System.in);


        while (true)
        {
            System.out.println("Enter integer value only  ");
            String input = tickets.next();
            try {
                numberOftickets = Integer.parseInt(input);
                break;
            } catch (NumberFormatException ne) {
                System.out.println("This is not a number");
            }
        }

        activity = activitySortedArrayList.get(activities);
        activity = new Activities(activity.getNameOfActivities(), numberOftickets);
        // If the customer asks for too many tickets that there will be a letter that is sent to the customer saying that we do not have any tickets left.
        if (numberOftickets > activitySortedArrayList.get(activities).getNumberOfActivities())
            {
               LettersToCustomer();
               System.out.println("There is no tickets left we have sent a letter to: " + customersSortedArrayList.get(results).getFirstName() + " " + customersSortedArrayList.get(results).getLastName());
            }
            else
            {
                //results has been set by binary activity search. (what the user has typed in)
                //gets the index and gives the activity to the customer.
            customersSortedArrayList.get(results).activitiesOfCustomer.add(activity);
            activity.setNumberOfActivities(numberOftickets);
            activitySortedArrayList.get(activities).setNumberOfActivities((activitySortedArrayList.get(activities).getNumberOfActivities() - numberOftickets));
            customersSortedArrayList.get(results).setTickets(customersSortedArrayList.get(results).activitiesOfCustomer.size());


        }
    }

    /**
     * Customer ArrayList Management, this method manages the arraylists involved in give and taking an activity to a customer.
     *
     * This method will scan the customer sorted array list, checking its size to ensure it is not greater than 4.
     *
     * If the customer sorted array list is greater than 4, than the method will check to see if the name of the activity matches one already chosen.
     *
     * if the names match, the method will then make a new activity with the sum of the tickets, and replace said activity.
     *
     * if the customer has more than 4 different activities, than the method will prompt the user to delete an activity.
     */
    public static void customerArrayListManagement() {
        Customers customers;
        customers = customersSortedArrayList.get(results);

        //checks the size of the customer activity array, if it's greater than 3 than,
        //updates the activity, by creating a new activity with the values added together.
        //removes the old activity
        //sets the new activity to the old activity space.

        if (customers.activitiesOfCustomer.size() > 3) {
            //Will get the 2 index to see if it is equal to the 3rd index
            if (customers.activitiesOfCustomer.get(2).getNameOfActivities() == customers.activitiesOfCustomer.get(3).getNameOfActivities()) {
                //updates the activity, by creating a new activity with the values added together.
                Activities activityUpdated = new Activities(activity.getNameOfActivities(), customers.activitiesOfCustomer.get(2).getNumberOfActivities() + customers.activitiesOfCustomer.get(3).getNumberOfActivities());
                //removes the second index (the old activity)
                customers.activitiesOfCustomer.remove(2);
                //sets the new activity to the 2nd index.
                customers.activitiesOfCustomer.set(2, activityUpdated);
            } else if (customers.activitiesOfCustomer.get(1).getNameOfActivities() == customers.activitiesOfCustomer.get(2).getNameOfActivities()) {
                Activities activityUpdated = new Activities(activity.getNameOfActivities(), customers.activitiesOfCustomer.get(1).getNumberOfActivities() + customers.activitiesOfCustomer.get(2).getNumberOfActivities());
                customers.activitiesOfCustomer.remove(1);
                customers.activitiesOfCustomer.set(1, activityUpdated);
            } else if (customers.activitiesOfCustomer.get(0).getNameOfActivities() == customers.activitiesOfCustomer.get(1).getNameOfActivities()) {
                Activities activityUpdated = new Activities(activity.getNameOfActivities(), customers.activitiesOfCustomer.get(0).getNumberOfActivities() + customers.activitiesOfCustomer.get(1).getNumberOfActivities());
                customers.activitiesOfCustomer.remove(activity.getNameOfActivities());
                customers.activitiesOfCustomer.remove(0);
                customers.activitiesOfCustomer.set(0, activityUpdated);
            }
            if (customers.activitiesOfCustomer.size() > 3) {


                System.out.println("The customer has reached the maximum amount of activities. Please either return one");
                listOfCustomers();
                System.out.println("Type in the one you would like to return.");

                while (!incorrectDetails) {
                    wrongActivity = false;
                    while (!wrongActivity) {
                        customerSearch();
                        if (!wrongActivity) {
                            System.out.println("Please make sure you have typed the correct Nmae in");
                        }
                        customers.customerActivitySearch();

                        if (!wrongActivity) {
                            System.out.println("Please make sure you have typed the correct activity!");
                        }
                    }

                }
                incorrectDetails = false;
                while (!incorrectDetails)
                {
                    wrongActivity = false;
                    while (!wrongActivity)
                    {
                        System.out.println("Please confirm the activity");
                        activitySearch();
                    }
                    if (customers.activitiesOfCustomer.get(customers.getCustomerActivities()).getNameOfActivities().equals(activitySortedArrayList.get(activities).getNameOfActivities())) {
                        activitySortedArrayList.get(activities).setNumberOfActivities(activitySortedArrayList.get(activities).getNumberOfActivities() + customers.activitiesOfCustomer.get(customers.getCustomerActivities()).getNumberOfActivities());
                        customersSortedArrayList.get(results).setTickets(customersSortedArrayList.get(results).activitiesOfCustomer.size());
                        customers.activitiesOfCustomer.remove(customers.getCustomerActivities());
                    } else {
                        System.out.println("Activities do not match");
                        wrongActivity = false;
                    }
                }

            }
}
            //sets customers tickets to the activities size after manipulation of the array.
            customers.setTickets(customers.activitiesOfCustomer.size());

        //checks the size of the customer activity array, if it's greater than 3 than,
        //updates the activity, by creating a new activity with the values added together.
        //removes the old activity
        //sets the new activity to the old activity space.
        if (customers.activitiesOfCustomer.size() > 2)
        {
            if (customers.activitiesOfCustomer.get(1).getNameOfActivities() == customers.activitiesOfCustomer.get(2).getNameOfActivities())
            {
                Activities activityUpdated = new Activities(activity.getNameOfActivities(), customers.activitiesOfCustomer.get(1).getNumberOfActivities() + customers.activitiesOfCustomer.get(2).getNumberOfActivities());
                customers.activitiesOfCustomer.remove(1);
                customers.activitiesOfCustomer.set(1, activityUpdated);

            }
            else if (customers.activitiesOfCustomer.get(0).getNameOfActivities() == customers.activitiesOfCustomer.get(1).getNameOfActivities())
            {
                Activities activityUpdated = new Activities(activity.getNameOfActivities(), customers.activitiesOfCustomer.get(0).getNumberOfActivities() + customers.activitiesOfCustomer.get(1).getNumberOfActivities());
                customers.activitiesOfCustomer.remove(0);
                customers.activitiesOfCustomer.set(0, activityUpdated);
            }
            //sets customers tickets to the activities size after manipulation of the array.
            customers.setTickets(customers.activitiesOfCustomer.size());
        }
        //checks the size of the customer activity array, if it's greater than 3 than,
        //updates the activity, by creating a new activity with the values added together.
        //removes the old activity
        //sets the new activity to the old activity space.
        else if (customers.activitiesOfCustomer.size() > 1)
        {

            if (customers.activitiesOfCustomer.get(0).getNameOfActivities() == customers.activitiesOfCustomer.get(1).getNameOfActivities())
            {
                Activities activityUpdated = new Activities(activity.getNameOfActivities(), customers.activitiesOfCustomer.get(0).getNumberOfActivities() + customers.activitiesOfCustomer.get(1).getNumberOfActivities());
                customers.activitiesOfCustomer.set(1, activityUpdated);
                customers.activitiesOfCustomer.remove(0);
            }

            //sets customers tickets to the activities size after manipulation of the array.
            customers.setTickets(customers.activitiesOfCustomer.size());
        }
        //customers can only hold up to 3 activities at a time, this will delete the last index

    }

    /**
     * The returning ticket method allows the user to return a ticket, if they do not want to go to that activity again.
     *
     * the method works by checking to make sure the name of the activities match, so they are returning the correct ticket.
     * if the activities do not match the method will ask user to make sure the activity is correct.
     *
     * the method will then check to make sure the customer has the correct amount of tickets
     * if the customer does not have enough tickets, the method will ask the user for the correct amount of tickets.
     *
     * the method will then delete the tickets from the activitiesOfCustomer array and return them to the activitySortedArrayList
     *
     *
     */
    public static void returningTickets()
    {
        //Makes a new activity
        activity = new Activities("", 0);
        //makes a new customer
        Customers customers = new Customers("", "");
        //results = user input
        customers = customersSortedArrayList.get(results);
        //activities = user input
        activity = activitySortedArrayList.get(activities);
        //asking how mnay tickets would the user like to give back
        System.out.print(activities + " How many tickets would you like to give back? ");
        int numberOftickets = parseInt(options.nextLine());
        //asking if the activity name is equal to the chosen activity of the customer
        if (activity.getNameOfActivities() == customers.activitiesOfCustomer.get(0).getNameOfActivities())
        {
            //asking if the number of tickets are less than or equal to the customers number of tickets.
            if (numberOftickets <= customers.activitiesOfCustomer.get(0).getNumberOfActivities())
            {
                activity.setNumberOfActivities((activity.getNumberOfActivities() + numberOftickets));
                int tickets = customers.activitiesOfCustomer.get(0).getNumberOfActivities() - numberOftickets;
                customers.activitiesOfCustomer.get(0).setNumberOfActivities(tickets);
                //asking for the index value, if the value is equal to 0, than remove it.
                if (customers.activitiesOfCustomer.get(0).getNumberOfActivities() == 0)
                {
                    customers.activitiesOfCustomer.remove(0);
                }
                System.out.println(customers);
                System.out.println(activity);
            }
            //if the tickets requested are greater than the customers amount of tickets
            else if (numberOftickets > customers.activitiesOfCustomer.get(results).getNumberOfActivities())
            {
                System.out.println("Customer doesn't have that many tickets. ");
            }
        }
        //doing the same for the next index
        else if (activity.getNameOfActivities() == customers.activitiesOfCustomer.get(1).getNameOfActivities())
        {
            if (numberOftickets <= customers.activitiesOfCustomer.get(1).getNumberOfActivities())
            {
                activity.setNumberOfActivities((activity.getNumberOfActivities() + numberOftickets));
                int tickets = customers.activitiesOfCustomer.get(1).getNumberOfActivities() - numberOftickets;
                customers.activitiesOfCustomer.get(1).setNumberOfActivities(tickets);
                if (customers.activitiesOfCustomer.get(1).getNumberOfActivities() == 0) {
                    customers.activitiesOfCustomer.remove(1);
                }
                System.out.println(customers);
                System.out.println(activity);
            }
            else if (numberOftickets > customers.activitiesOfCustomer.get(results).getNumberOfActivities())
            {
                System.out.println("Customer doesn't have that many tickets. ");
            }
        }
        //doing the same for the next index
        else if (activity.getNameOfActivities() == customers.activitiesOfCustomer.get(2).getNameOfActivities())
        {
            if (numberOftickets <= customers.activitiesOfCustomer.get(2).getNumberOfActivities())
            {
                activity.setNumberOfActivities((activity.getNumberOfActivities() + numberOftickets));
                int tickets = customers.activitiesOfCustomer.get(2).getNumberOfActivities() - numberOftickets;
                customers.activitiesOfCustomer.get(2).setNumberOfActivities(tickets);
                if (customers.activitiesOfCustomer.get(2).getNumberOfActivities() == 0)
                {
                    customers.activitiesOfCustomer.remove(2);
                }

                System.out.println(customers);
                System.out.println(activity);
            }
            else if (numberOftickets > customers.activitiesOfCustomer.get(results).getNumberOfActivities())
            {
                System.out.println("Customer doesn't have that many tickets. ");
            }
            else
            {
                System.out.println("please choose the correct activity");
            }
        }
        //setting the customer tickets to the size of the customer array.
        customers.setTickets(customers.activitiesOfCustomer.size());
    }

    /**
     * Customer search uses a Binary Search method to find the correct index of the customerSortedArrayList.
     */
    public static void customerSearch()
    {
        //customer search method
        Scanner fullName = new Scanner(System.in);
        System.out.println("first name: ");
        String firstName = fullName.nextLine().toLowerCase().trim();
        System.out.println("Second name: ");
        String secondName = fullName.nextLine().toLowerCase().trim();
        results = binarySearch(customersSortedArrayList, secondName, firstName);
    }
    /**
     * Activity search uses a Binary Search method to find the correct index of the activitySortedArrayList.
     */
    public static void activitySearch()
    {
        //activity search method
        Scanner activityName = new Scanner(System.in);
        System.out.println("Please choose the Activity: ");
        String aName = activityName.nextLine().toLowerCase().trim();
        activities = binarySearchActivity(activitySortedArrayList, aName);
    }
    public static void customeractivitySearch()
    {
        //activity search method
        Scanner activityName = new Scanner(System.in);
        System.out.println("Please choose the Activity: ");
        String aName = activityName.nextLine().toLowerCase().trim();
        //activities = binarySearchActivity(, aName);
    }




    /**
     * Binary Search method, takes the users input and searches through the customerSortedArrayList to find equal value to the users input.
     * if the user inputs a registered customers name, the method will find the matching index and location.
     *
     * @param customersSortedArrayList
     * @param lastN
     * @param firstN
     * @return
     * @param <E>
     */
    //binary Search for the customer
    public static <E extends Comparable<? super E>>
    int binarySearch(SortedArrayList<Customers> customersSortedArrayList, String lastN, String firstN)
    {
        int first = 0, Last = customersSortedArrayList.size() - 1;
        while (first <= Last) {
            int results = first + (Last - first) / 2;
            int lastName = lastN.compareTo(customersSortedArrayList.get(results).getLastName());
            int firstName = firstN.compareTo(customersSortedArrayList.get(results).getFirstName());

            // Check if i is present at mid
            if (lastName == 0 && firstName == 0)
            {
                wrongActivity = true;
                incorrectDetails = !incorrectDetails;
                return results;
            }
            // If i greater, ignore left half
            if (lastName > 0)
            {
                first = results + 1;
            }
                // If i is smaller, ignore right half
            else if (lastName < 0)
            {
                Last = results - 1;
            }
            else if (firstName > 0)
            {
                first = results + 1;
            }
            else
            {
                Last = results - 1;

            }
        }
         return -1;
    }

    /**
     * Binary Search Activity method, takes the users input and searches through the arrayOfActivities to find equal value to the users input.
     * if the user inputs a registered activity, the method will find the matching index and location.
     * @param arrayOfActivities
     * @param nameOfactivity
     * @return
     * @param <E>
     */
    //binary Search for the activities
        public static <E extends Comparable<? super E>>
        int binarySearchActivity(SortedArrayList<Activities> arrayOfActivities, String nameOfactivity)
        {
            int first = 0, Last = arrayOfActivities.size() - 1;
            while (first <= Last)
            {
                int results = first + (Last - first) / 2;
                int activityName = nameOfactivity.compareTo(arrayOfActivities.get(results).getNameOfActivities());
                // Check if i is present at mid
                if (activityName == 0)
                {
                    wrongActivity = true;
                    incorrectDetails = !incorrectDetails;
                    return results;
                }
                // If i greater, ignore left half
                if (activityName > 0)
                {
                    first = results + 1;
                }
                    // If i is smaller, ignore right half
                else
                {
                    Last = results - 1;
                }
            }
            return -1;
        }


    /**
     * Gives a letter to the customer if there are no available activities.
     */
    public static void LettersToCustomer()
        {
            //makes the methord to the customer.
            String letter = "To" + " " + customersSortedArrayList.get(results).getFirstName() + " "
                    + customersSortedArrayList.get(results).getLastName() + "\n" + "\n \n" + "We are sorry to inform you that all the tickets for " +
                    activitySortedArrayList.get(activities).getNameOfActivities() + " are no longer available.";
            try
            {
                File letterLocation = new File ("src/CSC8012/Letter.txt");
                PrintWriter output = new PrintWriter(letterLocation);
                output.print(letter);
                output.close();
            }
            catch(Exception e)
            {
                e.getStackTrace();
            }
        }
    }