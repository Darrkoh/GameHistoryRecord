
//THIS PROGRAM ALLOWS USERS TO KEEP A RECORD OF THEIR VIDEO GAME COLLECTION\\

//Imported Classes\\
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//Main Program\\
public class Main {
    public static void main(String[] args) {
        Scanner inputscanner = new Scanner(System.in); //Defines Scanner
        boolean cont = true; //This is so the Program will continue running until user decides to exit
        ArrayList<GameDetails> Records = ReadingFile(); //Loads in Contents from disk
        String confirmation; //For Switch Statement

        //Loading In file index number
        int index; //Index Number. Used for Identifying each Entry of the list. Will inherit the lists size straight away so there are no conflicting entries.

        //Introductory Messages
        System.out.println("Welcome To Your Video Game Collection!");
        System.out.println("Here You Can: Record/See Your Video Games, Record/See Dates of Your Last Playthrough (Start/End) and Record/View the Cost"); //Gives Options to user

        //User Makes Choice
        do {
            System.out.println("Are you Adding Records, Removing Records, Editing Records, Sorting/Viewing your existing records, Or Performing calculations on your System?");
            System.out.println("Please Enter (Case Sensitive): Adding/Removing/Viewing/Editing/Calculations, or anything else to save your contents, and exit program");
            String Choice = inputscanner.nextLine(); //Defines Variable
            switch (Choice){
                case "Adding":
                    index = Records.size(); //Updates Index
                    confirmation = addingRecords(Records, index); //Executes Method to add Records, Saves index and then will print Done
                    System.out.println(confirmation); //As otherwise output is on same line as next output
                    break;
                case "Removing":
                    confirmation = removingRecords(Records); //Runs removingRecords Method
                    System.out.println(confirmation); //This is so the Done output is not on the same line as the next Text Line
                    break;
                case "Viewing":
                    sortingRecords(Records);//Executes Method to print Records
                    for (GameDetails game:Records)
                    {
                        if (game.getEndDate() != null) {
                            System.out.print(game.getName() + "; " + game.getPlatform() + "; £" + game.getCost() + "; Started On: " + game.getStartDate() + "; Finished On: " + game.getEndDate() + "; " + game.getRating() + " \n"); //Will Print Each Object. We need to get each attribute otherwise the hashcode will be printed
                        }
                        else {
                            System.out.println(game.getName() + "; " + game.getPlatform() + "; £" + game.getCost() + "; Started On: " + game.getStartDate() + "; " + game.getRating() + " \n"); //Will Print If User Hasn't finished game
                        }
                    }
                    break;
                case "Editing":
                    editingRecords(Records);
                    break;
                case "Calculations":
                    boolean i = false;
                    do {
                        System.out.println("What Would You Like To Calculate?");
                        System.out.println("Number of Items (Items), Total Price (Price)");
                        String calculationChoice = inputscanner.nextLine();

                        //code adapted from W3Schools, n.d
                        if (calculationChoice.equalsIgnoreCase("Items")) { //Case Insensitive.

                            System.out.println(calculateItems(Records));
                            i = true; //Exits Loop
                        } else if (calculationChoice.equalsIgnoreCase("Price")) {
                        //end of adapted code
                            System.out.println("£" + calculatePrice(Records));
                            i = true; //Exits Loop
                        }
                        else {
                            System.out.println("Please Enter a Valid Input");
                        }
                    } while (!i);
                    break;

                default:
                    cont = false; //Exits Loop
                    saveGame(Records); //Will Run Method To Save Game To Class and end program
            }
        } while(cont);
        System.out.println("Saving File and Exiting..."); //End Program Message
    }

        //TO ADD RECORDS
        public static String addingRecords(ArrayList<GameDetails> Records, int index) {
            //Variables
            Scanner inputscanner = new Scanner(System.in); //Defines Scanner
            String Platform; //Default Value
            String Name; //Default Value
            double cost; //Default Value
            Date startDate; //Default Value
            Date endDate = new Date(); //Default Value
            boolean i; //loop condition
            Rating rating; //Defines Enum Variable

            //For Name//
            Name = creatingNames();
            if (Name.equals("___")) {
                return "Returning to menu"; //Return to menu command
            }

            //For Platform//
            Platform = creatingPlatforms();

            //For Cost//
            cost = creatingCosts();

            //For Start Date//
            startDate = creatingDates(); //Creates a start date for Constructor

            //For End Date//
            do {
                i = true;

                System.out.println("Have You Finished This Playthrough? ('Yes' if you have)");
                String stringAnswer = inputscanner.next();
                try {
                    //code adapted from W3Schools, n.d
                    if (stringAnswer.equalsIgnoreCase("Yes")) { //In case user inputs 'yes'
                    //end of adapted code
                        endDate = creatingDates(); //End date is created for Constructor
                    } else {
                        endDate = null;
                    }
                } catch (IllegalArgumentException e) { //If Wrong Option is entered
                    System.out.println("Please Enter 'Yes' Or 'No'");
                    i = false;
                }
            }
            while (!i);

            //For Rating
            rating = creatingRatings();

            //Creating Record
            GameDetails Record = creatingRecord(Name, Platform, startDate, endDate, cost, index, rating); //Executes Creating Method. In Separate Method For Unit Testing

            //Here we'll add the entry to a file
            Records.add(Record);
            index = Records.size(); //Index number will increase

            System.out.println("Are You Adding Anymore Games? (Yes to continue)");
            String choice = inputscanner.next();
            //code adapted from W3Schools, n.d
            if (choice.equalsIgnoreCase("Yes")) {
            //end of adapted code
                addingRecords(Records, index); //Recursion to Add another Game
            }
            return "Done"; //To Return To Menu
            }
            
        //TO REMOVE RECORDS
        public static String removingRecords(ArrayList<GameDetails> Records) {
            Scanner inputScanner = new Scanner(System.in);
            boolean i;
            do {
                i = true;
                if (Records.isEmpty()) {
                    return "List Is Empty, Returning to Menu";  //Returns To Menu If THere are no more entries in the list
                } else {
                    System.out.println("Please Enter the Index Number For the Entry You Wish To Delete, Or Enter any text else to go back to the menu");
                    for (GameDetails game : Records) {
                        System.out.print("#" + game.getIndex() + "; " + game.getName() + "; " + game.getPlatform() + "; £" + game.getCost() + "; Started On: " + game.getStartDate() + "; Finished On: " + game.getEndDate() + "; " + game.getRating() + " \n"); //Same as Adding Records output, Except this time we are also printing the index number
                    }
                    String chosenIndex = inputScanner.next(); //We need to get the input as a string and parse it in order to prevent scanner glitches should we need another input
                    if (Integer.parseInt(chosenIndex) <= Records.size()) { //Will check if the int value of chosenIndex is valid
                        try {
                            int intChoice = Integer.parseInt(chosenIndex); //Changes Entered text to an integer
                            if (intChoice <= Records.size()) {
                                System.out.println("Are You Sure You want to delete this entry?"); //For Confirmation
                                String confirmation = inputScanner.next();

                                //code adapted from W3Schools, n.d
                                if (confirmation.equalsIgnoreCase("Yes")) {
                                    //end of adapted code
                                    Records.remove(intChoice); //Removes Chosen Entry
                                    for (GameDetails games : Records) {
                                        if (games.getIndex() > intChoice) {
                                            updateIndex(Records); //To update List Index Placements after Change
                                        }
                                    }
                                    System.out.println("Do You want to remove another entry? ('Yes' to Continue)");
                                    String anotherChoice = inputScanner.next();
                                    //code adapted from W3Schools, n.d
                                    if (anotherChoice.equalsIgnoreCase("Yes")) {
                                        //end of adapted code
                                        i = false; //Will trigger Loop Condition
                                    } else {
                                        return "Returning to Menu";
                                    }
                                } else {
                                    System.out.println("Returning to Choice List");
                                    i = false;
                                }
                            }
                        } catch (NumberFormatException e) {
                            return "Returning to Menu"; //Will return user to Menu
                        }
                    }
                    else {
                        System.out.println("Please Enter A Valid Choice"); //Error Message
                        i = false; //Will Reset Loop For User to make Choice again
                    }
                }
            } while (!i);
            return "Removal Done";
        }
        //TO VIEW RECORDS
        public static void sortingRecords(ArrayList<GameDetails> Records) {
            Scanner inputScanner = new Scanner(System.in);

                //SORTING OPTION\\
            System.out.println("Would You Like to Sort Your List Based on Rating? (Yes/No NOTE: This is Irreversible)"); //So User Can Choose how the list will be sorted
            String Answer = inputScanner.next();

            if (Answer.equalsIgnoreCase("Yes")) {
                Collections.sort(Records); //Will Either Sort list based on rating, or return in order they were added. Java doesn't let a sort be reversed to my knowledge
                updateIndex(Records); //Will update Game's Index Number Attributes based on their list placement
            }
        }

        //TO EDIT RECORDS (GETTERS/SETTERS)
    private static void editingRecords(ArrayList<GameDetails> Records) {
        Scanner inputScanner = new Scanner(System.in);
        boolean i;
        do {
            try {
                i = true;
                System.out.println("Please Choose Which Entry to Edit Using their index. To Return To Menu Select an option and Follow instructions!"); //999 is the exit clause as it is unlikely for this program there will be 999 entries
                for (GameDetails game : Records) {
                    System.out.print("#" + game.getIndex() + "; " + game.getName() + "; " + game.getPlatform() + "; £" + game.getCost() + "; Started On: " + game.getStartDate() + "; Finished On: " + game.getEndDate() + "; " + game.getRating() + " \n"); //Same as Adding Records output, Except this time we are also printing the index number
                }
                String chosenIndex = inputScanner.next();
                int intIndex = Integer.parseInt(chosenIndex); //Parsing to avoid any scanner glitches if the loop were to reset
                if (intIndex <= Records.size()) {
                    System.out.println("Which part Would you like to edit? (Please enter the Case Sensitive attribute name, with Hyphens between words. e.g Platform, Cost, Start-Date, etc.)");
                    System.out.println("You can return to the Menu By inputting '___'");
                    System.out.println("NAMES CAN'T BE CHANGED");
                    String editChoice = inputScanner.next();
                    switch (editChoice) {
                        case "Platform":
                            String newPlatform = creatingPlatforms();
                            Records.get(intIndex).setPlatform(newPlatform);
                            break;
                        case "Cost":
                            double cost = creatingCosts();
                            Records.get(intIndex).setCost(cost);
                            break;
                        case "Start-Date":
                            Date newStartDate = creatingDates();
                            Records.get(intIndex).setStartDate(newStartDate);
                            break;
                        case "End-Date":
                            Date newEndDate = creatingDates();
                            Records.get(intIndex).setEndDate(newEndDate);
                            break;
                        case "Rating":
                            Rating newRating = creatingRatings();
                            Records.get(intIndex).setRating(newRating);
                            break;
                        case "___":
                            return; //Will Take Back To Menu
                        default:
                            i = false;
                            System.out.println("Please Enter a valid Choice");
                    }
                }
                else {
                    System.out.println("Please Enter a valid Number");
                    i = false; }

            } catch (NumberFormatException e) { //If User Doesn't Enter a Number
                System.out.println("Please Enter a Valid Number");
                i = false; }
        } while(!i);
    }


        //////////////////////////////////////////////////////Utility Methods\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    public static String creatingNames() //Creates A Name
    {
        String Name;
        Scanner inputscanner = new Scanner(System.in);

        System.out.println("First Please Enter The Games Name! (You can enter '___' To Return to the menu"); //Back Command is ___ as it is unlikely this is a games name
        Name = inputscanner.nextLine(); //Creates user input variable for Constructor
        return Name;
    }

    public static String creatingPlatforms() { //Creates A Platform
        String Platform;
        Scanner inputscanner = new Scanner(System.in);

        System.out.println("What Platform(s) Do you own the game on?");
        Platform = inputscanner.nextLine(); //Creates user input variable for Constructor
        return Platform;
    }

    public static double creatingCosts() { //Creates A Cost
        boolean i;
        String stringCost;
        double cost = 0.00;
        Scanner inputscanner = new Scanner(System.in);

        do {
            i = true;
            try {
                System.out.println("How much did the game cost? Please Enter as whole number or 0.00 Format");
                stringCost = inputscanner.next(); //Cost is currently a String as if we use nextDouble, Program may skip over input reading
                cost = Double.parseDouble(stringCost); //Will Change User Input into a double. Primitive so use parse
            } catch (
                    IllegalArgumentException e) { //If the string cannot be changed to a double because of invalid input
                System.out.println("You have entered an invalid input, Please enter a price");
                i = false;
            }
        } while (!i);
        return cost;
    }
    public static Rating creatingRatings() { //Creates A Rating
        boolean i;
        Rating rating = null;
        Scanner inputscanner = new Scanner(System.in);

        do {
            i = true;
            System.out.println("What is your rating? (1-5)"); //Using Enum as we will use this value for sorting
            String EnteredRating = inputscanner.next();
            switch (EnteredRating) {
                case "1":
                    rating = Rating.Hated;
                    break;
                case "2":
                    rating = Rating.Disliked;
                    break;
                case "3":
                    rating = Rating.Ok;
                    break;
                case "4":
                    rating = Rating.Liked;
                    break;
                case "5":
                    rating = Rating.Loved;
                    break;
                default:
                    System.out.println("Please Enter a Number between 1-5"); //If number isn't between 1-5
                    i = false;
            }
        } while (!i) ;
        return rating;
    }

    public static GameDetails creatingRecord(String Name, String Platform, Date startDate, Date endDate, double cost, int index, Rating rating) { //Creates A Record Object
        return new GameDetails(Name, Platform, startDate, endDate, cost, index, rating);
    }
    public static void saveGame(ArrayList<GameDetails> Records) { //This Method is used for writing the records to a file
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("record.dat"); //Here we can write our arraylist to the file to save it
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(Records); //Writes The Game Details Object To The File
        } catch(FileNotFoundException exception)
        {
            System.out.println("File Could not be found!"); //Handling errors
        } catch(IOException IO)
        {
            System.out.println("Unable to create a object output stream");
        }
    }

    public static ArrayList<GameDetails> ReadingFile() { //Gets File Contents
        try {
            FileInputStream fileInputStream = new FileInputStream("record.dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

                    //We can ignore this warning as we have checked it
            return (ArrayList<GameDetails>) objectInputStream.readObject(); //If no Errors Occur, We Will Return The Variable with the saved list, so we can append/remove from it
        } catch(FileNotFoundException exception)
        {
            System.out.println("File Could not be found!"); //Handling errors
        } catch(IOException IO)
        {
            System.out.println("Unable to create a object output stream.");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not find class");
        }
        return new ArrayList<>(); //Empty ArrayList wil be returned to return to program menu if an error occurs, Restarting the program
    }


    private static Date creatingDates() //Creates Dates
        {
            Scanner inputscanner = new Scanner(System.in); //Creates Scanner
            boolean i; //Defines Condition
            Date finalDate = new Date();
            do {
                i = true;
                System.out.println("Please Enter the Start Date (Enter the end Date if you chose to enter an end date) (dd/mm/yyyy)");

                //code adapted from JavaTPoint, n.d
                String DateString = inputscanner.next();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); //This uses the SimpleDateFormat Class to create a date format we can parse a string into
                try {
                    finalDate = sdf.parse(DateString); //Changes Date String to a Date
                } catch (ParseException e) {
                    System.out.println("Please enter a valid date in the format stated"); //IF USER INPUTS IN WRONG FORMAT
                    i = false;
                }
            } while (!i);
                //end of adapted code
            return finalDate;
        }


        public static void updateIndex(ArrayList<GameDetails> Records) { //THIS METHOD IS USED FOR UPDATING INDEXES WHEN THEY HAVE BEEN SIGNIFICANTLY ALTERED
            for (GameDetails games: Records)
            {
                games.setIndex(Records.indexOf(games)); //This will Reassign Index Values based on position of each element in the list
            }
        }

        public static int calculateItems(ArrayList<GameDetails> Records) {
            return Records.size();
        }

        public static double calculatePrice(ArrayList<GameDetails> Records) { //Will Calculate Total Price of Games Recorded
            double totalPrice = 0;
            for (GameDetails records: Records) {
                totalPrice = totalPrice + records.getCost();
            }
            return totalPrice;
        }
    }