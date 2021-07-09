package FONTS.Drivers.Board;

import java.util.Scanner;

/**
 * STUB
 */
public class DomainController {
    static int globalID = 0;
    static int turn; //1 if black, 2 if white
    static Board board;
    int[][] map = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 2, 0, 0},
            {0, 0, 0, 1, 2, 0, 0, 0},
            {0, 0, 0, 2, 1, 2, 0, 0},
            {0, 0, 0, 2, 2, 1, 2, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
    };

    public DomainController(){
        turn = 1;
        board = new Board();
    }

    /**
     * Receive input from user to configure a game
     */
    public void start() {

    }

    private void printBoardWithLegals(){

    }

    /**
     * Introduce a delay
     * Used to allow the user to better identify the Bot's moves
     */
    private void delay(){

    }

    public void updateLog() {

    }

    /**
     * Change the current turn to the other player
     */
    public void toggleTurn() {

    }

    /**
     * Read standard input for a human player's move
     * @param scanner  reads from stdin
     */
    public void readHuman(Scanner scanner) {

    }

    public static Integer[] getBoardLegals(){
        Integer[] legals = {1,2,3,4,5,6,7,8};
        return legals;
    }

    public static Board getBoard() {
        return board;
    }

    public static int getTurn() {
        return turn;
    }
    public static int getBoardHeight() {
        return Board.height;
    }

    public static int getBoardWidth() {
        return Board.width;
    }

    /**
     *
     * @return A boolean array with the rules ordered by Diagonal, Vertical and Horizontal
     */
    public static boolean[] getRules() {
        return new boolean[]{true,true,true};
    }
}
