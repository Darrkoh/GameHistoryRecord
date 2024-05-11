import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void creatingRecord() { //TESTING THE CONSTRUCTOR
        GameDetails Contents = new GameDetails("Spider-man 2", "PS5", new Date(1/2/2023), new Date(18/9/2023), 50.00, 0, Rating.Liked); //We will use this to compare the methods output
        GameDetails Record = Main.creatingRecord("Spider-man 2", "PS5", new Date(1/2/2023), new Date(18/9/2023), 50.00, 0, Rating.Liked); //We will use this as the parameter
        assertEquals(Contents.getName(), Record.getName()); //Just Testing name as otherwise we get hashcode.
    }

    @Test
    void removingRecords() { //TESTING RESPONSE IF LIST IS EMPTY
        ArrayList<GameDetails> Contents = new ArrayList<>();
        String Response = Main.removingRecords(Contents);
        assertEquals("List Is Empty, Returning to Menu", Response); //This Is The Response We Should Get
    }

    @Test
    void updateIndex() { //Tests to see if the Index is set correctly if currently set incorrectly
        ArrayList<GameDetails> Records = new ArrayList<>();
        Records.add(new GameDetails("Spider-man 2", "PS5", new Date(1/2/2023), new Date(18/9/2023), 50.00, 1, Rating.Liked)); //First Entry
        Main.updateIndex(Records); //This should change the index to 0
        assertEquals(0, Records.get(0).getIndex());
    }

    @Test
    void calculateItems() { //Tests to see if system can calculate number of items in a list
        ArrayList<GameDetails> Records = new ArrayList<>();
        Records.add(new GameDetails("Spider-man 2", "PS5", new Date(1/2/2023), new Date(18/9/2023), 50.00, 0, Rating.Liked));
        Records.add(new GameDetails("Hell Divers 2", "PS5", new Date(1/2/2023), new Date(18/9/2023), 29.99, 1, Rating.Liked));
        Records.add(new GameDetails("Minecraft", "PC", new Date(1/2/2023), new Date(18/9/2023), 25, 2, Rating.Loved));
        int size = Main.calculateItems(Records);
        assertEquals(3, size);
    }

    @Test
    void calculatePrice() { //Tests the Calculating Price Method
        ArrayList<GameDetails> Records = new ArrayList<>();
        Records.add(new GameDetails("Spider-man 2", "PS5", new Date(1/2/2023), new Date(18/9/2023), 50.00, 0, Rating.Liked));
        Records.add(new GameDetails("Hell Divers 2", "PS5", new Date(1/2/2023), new Date(18/9/2023), 29.99, 1, Rating.Liked));
        Records.add(new GameDetails("Minecraft", "PC", new Date(1/2/2023), new Date(18/9/2023), 25, 2, Rating.Loved));
        double totalPrice = Main.calculatePrice(Records);
        assertEquals(104.99, totalPrice);
    }
}