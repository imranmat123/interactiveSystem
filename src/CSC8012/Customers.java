package CSC8012;

import java.util.Scanner;

/**
 * The Customer class makes activity object containing a Strings, ints values.
 * the Customer class contains its own arraylist for activities.
 *
 */
class Customers implements Comparable<Customers>
{

    private String firstName;
    private String lastName;
    private int tickets = 0;
    private int customerActivities;

    public int getCustomerActivities() {
        return customerActivities;
    }

    public void setCustomerActivities(int customerActivities) {
        this.customerActivities = customerActivities;
    }

    public SortedArrayList<Activities> getActivitiesOfCustomer() {
        return activitiesOfCustomer;
    }

    public void setActivitiesOfCustomer(SortedArrayList<Activities> activitiesOfCustomer) {
        this.activitiesOfCustomer = activitiesOfCustomer;
    }


    public SortedArrayList<Activities> activitiesOfCustomer = new SortedArrayList<>();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public int getTickets() {
        return tickets;
    }
    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    /**
     * Constructs a customer Object using two Strings, first name and last name.
     * @param name
     * @param lastName
     */
    public Customers(String name, String lastName)
    {
        this.firstName = name;
        this.lastName = lastName;
    }
    public String toString()
    {
        return "Name: " + getFirstName() + " " + getLastName() + "," + "tickets: ";
    }

    public void customerActivitySearch()
    {
        //activity search method
        Scanner activityName = new Scanner(System.in);
        System.out.println("Please choose the customer Activity: ");
        String aName = activityName.nextLine().toLowerCase().trim();
        customerActivities = binarySearchCustomerActivity(activitiesOfCustomer, aName);
    }

    /**
     * Comparing the two Strings values (first and last name) within the customer Object.
     * @param firstNameLastname the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Customers firstNameLastname)
    {
        if (this.getLastName().equalsIgnoreCase(this.getLastName()))
        {
            return this.getLastName().compareTo(getFirstName());
        }
        return this.getLastName().compareTo(getLastName());
    }
    /**
     * Binary Search Customer Activity method, takes the users input and searches through the activitiesOfCustomer to find equal value to the users input.
     * if the user inputs a registered activity, the method will find the matching index and location.
     * @param activitiesOfCustomer
     * @param nameOfactivity
     * @return
     * @param <E>
     */
    public static <E extends Comparable<? super E>>
    int binarySearchCustomerActivity(SortedArrayList<Activities> activitiesOfCustomer, String nameOfactivity)
    {
        int first = 0, Last = activitiesOfCustomer.size() - 1;
        while (first <= Last)
        {
            int results = first + (Last - first) / 2;
            int activityName = nameOfactivity.compareTo(activitiesOfCustomer.get(results).getNameOfActivities());
            // Check if i is present at mid
            if (activityName == 0)
            {
                Main.setWrongActivity(true);
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

}



