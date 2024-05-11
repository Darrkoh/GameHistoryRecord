import java.io.Serializable;
import java.util.Date;
public class GameDetails implements Serializable, Comparable<GameDetails> { //Implements serializable, so we can read the file and Comparable, so we can sort lists

    final private String name; //For Games Name. Can't be changed

    private String platform; //For the Platform it's owned on. Can be changed as user may buy for multiple platforms

    private Date startDate; //For Start Date of Game

    private Date endDate; //For End Date of Game

    private double cost; //For Cost of Game

    private int index; //Index number for selecting Entries. Not final as we will be updating the index when removing entries and sorting the list

    private Rating rating; //Allows User to Rate their enjoyment. Will be used for sorting

    //Constructor
    public GameDetails(String name, String platform, Date startDate, Date endDate, double cost, int index, Rating rating)
    {
        this.name = name;
        this.platform = platform;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cost = cost;
        this.index = index;
        this.rating = rating;
        //Game might not have finished yet
    }



    //Accessors/Mutators
    public String getName() {
        return name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getPlatform() {
        return platform;
    }

    public double getCost() {
        return cost;
    }

    public int getIndex() {return index;}

    public Rating getRating() {return rating;}

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;} //Allows user to set end date if they have finished

    public void setIndex(int index) {
        this.index = index;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @Override
    public int compareTo(GameDetails other) {
        //-1 Smaller, 0 Equal, 1 Bigger
        return rating.compareTo(other.rating); //Sorts Based On Enjoyment Rating
    }
}
