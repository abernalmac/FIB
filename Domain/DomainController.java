package FONTS.Domain;

import FONTS.Persistence.PersistenceController;
import FONTS.Presentation.PresentationController;
import java.util.ArrayList;
import java.util.Vector;

public class DomainController {
    static PersistenceController persistence;
    static PresentationController presentation;
    static GameManager gameManager;

    public String info[];
    public String currentID;

    public DomainController(PersistenceController persistence, PresentationController presentation) {
        DomainController.persistence = persistence;
        DomainController.presentation = presentation;
    }

    public void setInfo(String[] info) {
        this.info = info;
        newGame(this.info);
    }

    public void newGameFromPresentation() {
        //this.info = info;
        play();
        //newGame(this.info);
    }

    /**
     * Starts the game
     */
    public void start(){
        PresentationController.showMainPage();

        //newGame(this.info);
        //loadGame("1");
    }

    /**
     * Set up a new game with the following parameter order:
     *         info[0] = score1;
     *         info[1] = score2;
     *         info[2] = diagonal;
     *         info[3] = horizontal;
     *         info[4] = vertical;
     *         info[5] = id1;
     *         info[6] = name1;
     *         info[7] = wins1;
     *         info[8] = losses1;
     *         info[9] = ties1;
     *         info[10] = color1;
     *         info[11] = PlayerLevel1;
     *         info[12] = id2;
     *         info[13] = name2;
     *         info[14] = wins2;
     *         info[15] = losses2;
     *         info[16] = ties2;
     *         info[17] = color2;
     *         info[18] = PlayerLevel2;
     *         info[19] = depth1;
     *         info[20] = depth2;
     */
    public void newGame(String[] info) {
        String[] neededInfo = new String[22];
        if(!info[0].equals("6")){
            int id = Integer.parseInt(info[1]);
            if(info[0].equals("8")) id+=3;
            if(info[0].equals("10")) id+=6;
            if(info[0].equals("12")) id+=9;
            info[1] = String.valueOf(id);
            currentID = Integer.toString(id);
        }
        neededInfo[0] = "0";
        neededInfo[1] = "0";
        neededInfo[2] = info[2];
        neededInfo[3] = info[3];
        neededInfo[4] = info[4];
        neededInfo[5] = "4";
        neededInfo[6] = info[5];
        neededInfo[7] = "0";
        neededInfo[8] = "0";
        neededInfo[9] = "0";
        neededInfo[10] = info[6];
        neededInfo[11] = info[7];
        neededInfo[12] = "6";
        neededInfo[13] = info[8];
        neededInfo[14] = "0";
        neededInfo[15] = "0";
        neededInfo[16] = "0";
        neededInfo[17] = info[9];
        neededInfo[18] = info[10];
        neededInfo[19] = info[11];
        neededInfo[20] = info[12];
        neededInfo[21] = info[0];

        String[] boardarray;
        boardarray = persistence.getBoard(this.info[1]);

        gameManager = new GameManager(neededInfo, boardarray);
        initGameLog();




        //play();
    }

    public static void initGameLog(){
        if (gameManager == null)
            gameManager = new GameManager();
        ArrayList<String[]> games = persistence.getGames();
        ArrayList<String[]> players = persistence.getPlayers();
        gameManager.initGameLog(games,players);
    }

    /**
     * Set up a game from the stored in files
     */
    public void loadGame(String id) {
        currentID = id;
        String[] infoGame = persistence.getGame(id);
        String[] infoPlayer1 = persistence.getPlayer(infoGame[2]);
        String[] infoPlayer2 = persistence.getPlayer(infoGame[3]);
        int size1 = infoGame.length;
        int size2 = infoPlayer1.length;
        int size3 = infoPlayer2.length;
        String[] info = new String[size1+size2+size3+3];
        System.arraycopy(infoGame, 0, info, 0, infoGame.length);
        System.arraycopy(infoPlayer1, 0, info, 0 + size1, infoPlayer1.length);
        for (int i = 0; i < infoPlayer2.length; ++i) info[i+size1+size2] = infoPlayer2[i];
        String[] usefulInfo = new String[22];
        System.arraycopy(info, 4, usefulInfo, 0, usefulInfo.length);
        usefulInfo[19] = Integer.toString(3);
        usefulInfo[20] = Integer.toString(3);

        //CANVIAR BOARDARRAY
        String[] boardarray;
        boardarray = persistence.getBoard(id);
        usefulInfo[21] = Integer.toString(boardarray.length);
        gameManager = new GameManager(usefulInfo, boardarray);
        initGameLog();

        //play();
    }

    /**
     * Save a game to file
     */
    public void saveGame() {
        String[] infoGame = gameManager.saveGame();
    }

    /**
     * Get the updated ranking
     */
    public static Vector<String[]> getRanking(String type) {
        return GameManager.getRanking(type);
    }

    public Boolean makeMoveFromPresentation(int x, int y) {
        return gameManager.makeMove(x,y);
    }

    public static void play() {
        while (!GameManager.gameEnded()) {
            //presentation.getMove();
            if (gameManager.makeMove(1,2)) {
                int[][] current = getBoard();
                //presentation.showBoard(current)
            }
            else{
                //presentation.notValidMove(); o no fer res
            }
        }
        //gameManager.gameOver();
    }

    public boolean gameOver() {
        if (GameManager.gameEnded()) {
            gameManager.gameOver();
            return true;
        } else {
            return false;
        }
    }

    public static int[][] getBoard() {
        return GameManager.getBoard();
    }

    /**
     * Check the games file and retrieve the saved games
     * @return every saved game
     */
    public static ArrayList<String[]> getSavedGames() {
        return persistence.getGames();
    }

    /**
     * Use the input from the user to log in
     */
    public void logIn(String username, String password) {
        //if (!persistence.logIn(username, password) presentation.OK();
    }

    public void saveAndQuit() {
        String info[] = GameManager.saveGame();
        String infoGame[] = new String [8];
        infoGame[0] = "false";
        infoGame[1] = info[5];
        infoGame[2] = info[12];
        for (int i = 0; i < 5; ++i) infoGame[i] = info[i+2];
        persistence.addGame(infoGame);
        String infoPlayer1[] = new String[6];
        System.arraycopy(info, 5, infoPlayer1, 0, 6);
        persistence.addPlayer(infoPlayer1);
        String infoPlayer2[] = new String[6];
        System.arraycopy(info, 12, infoPlayer2, 0, 6);
        persistence.addPlayer(infoPlayer2);

        persistence.addBoard(currentID ,gameManager.getStringBoard());
    }

    public int getScore1() {
        return GameManager.score1();
    }
    public int getScore2() {
        return GameManager.score2();
    }

    public static int getHeight() {
        return GameManager.getBoardHeight();
    }
    public static int getWidth() {
        return GameManager.getBoardWidth();
    }
    public static boolean getIsHuman(int turn) {return GameManager.getIsHuman(turn);}
}
