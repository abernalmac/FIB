package FONTS.Drivers.GameLog;

/**
 * This driver tests game addition. Its other functionality is delivering the
 * ranking to GameManager.
 */
import java.util.ArrayList;
import java.util.Vector;

public class GameLogDriver {
    private static GameLog log;

    private static void testConstructor() {
        log = new GameLog();
        System.out.println("constructor: DONE");
    }

    private static void testAddGetGame(){
        Game game = new Game();
        log.addGame(game);
        Game test = log.getGame(0);
        if (test.equals(game))
            System.out.println("addGetGame: DONE");
        else System.out.println("addGetGame: FAILED");
    }

    private static void testGetSortedByID(){
        Vector<String[]> test = log.getSortedByID(true);
        System.out.println("getSortedByID: DONE");
    }

    private static void testGetSortedByWins(){
        Vector<String[]> test = log.getSortedByWins(true);
        System.out.println("getSortedByWins: DONE");
    }

    private static void testGetSortedByLoses(){
        Vector<String[]> test = log.getSortedByLoses(true);
        System.out.println("getSortedByLoses: DONE");
    }

    private static void testGetSortedByTies(){
        Vector<String[]> test = log.getSortedByTies(true);
        System.out.println("getSortedByTies: DONE");
    }

    public static void main(String[] args) {
        System.out.println("GameLog driver: ");
        testConstructor();
        testAddGetGame();
        testGetSortedByID();
        testGetSortedByWins();
        testGetSortedByLoses();
        testGetSortedByTies();
    }
}
