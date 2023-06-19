package CSC8012;

/**
 * The Activities class makes activity object containing a String and int values.
 * these activities are used within arraylists.
 */
public class Activities implements Comparable<Activities>
{
    private String nameOfActivities = "";
    private int numberOfActivities = 0;

    /**
     * Constructs an activity Object using one String, and one Int value (name of activity and number of activity).
     * @param nameOfActivities
     * @param numberOfActivities
     */

    public Activities (String nameOfActivities, int numberOfActivities)
    {
        this.nameOfActivities = nameOfActivities;
        this.numberOfActivities = numberOfActivities;
    }

    public String toString()
    {
         return "Activity: " + getNameOfActivities() + " " + "Tickets: " + getNumberOfActivities();
    }

    /**
     * Comparing the two activity objects by their name.
     * @param user the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Activities user) {
        return this.getNameOfActivities().compareTo(user.getNameOfActivities());
    }

    public String getNameOfActivities() {
        return nameOfActivities;
    }

    public void setNameOfActivities(String nameOfActivities) {
        this.nameOfActivities = nameOfActivities;
    }

    public int getNumberOfActivities() {
        return numberOfActivities;
    }

    public void setNumberOfActivities(int numberOfActivities) {
        this.numberOfActivities = numberOfActivities;
    }
}
