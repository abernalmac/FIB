package FONTS.Domain;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.Vector;

public class GameManager {
    static int globalID = 0;
    static int turn; //1 if black, 2 if white
    static int gamemode;
    static Board board;
    public static GameLog log;
    public static Game game;
    public static Algorithm algorithm;
    public static Algorithm algorithm2;
    Cell[][] map = new Cell[][]{
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.BLACK, Cell.EMPTY, Cell.WHITE, Cell.EMPTY, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.BLACK, Cell.WHITE, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.WHITE, Cell.BLACK, Cell.WHITE, Cell.EMPTY, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.WHITE, Cell.WHITE, Cell.BLACK, Cell.WHITE, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
            {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY}
    };

    public GameManager(){
        turn = 1;
        log = new GameLog();
        board = new Board();
        game = new Game();
        algorithm = new Algorithm();
        algorithm2= new Algorithm();
    }

    public GameManager(String[] info, String[] boardarray) {
        turn = 1;
        log = new GameLog();
        //board setup
        int height = Integer.parseInt(info[21]);
        int width = height;
        int score1 = Integer.parseInt(info[0]);
        int score2 = Integer.parseInt(info[1]);
        Cell[][] init = getCellArray(boardarray);
        boolean diagonal = Boolean.parseBoolean(info[2]);
        boolean horizontal = Boolean.parseBoolean(info[3]);
        boolean vertical = Boolean.parseBoolean(info[4]);
        //board = new Board(height,width, diagonal,horizontal,vertical);
        board = new Board(height,width, init, diagonal,horizontal,vertical);

        //board = new Board();
        //game setup
        game = new Game(false,score1,score2);
        int id1 = Integer.parseInt(info[5]) ;
        String name1 = info[6];
        int wins1 = Integer.parseInt(info[7]);
        int losses1 = Integer.parseInt(info[8]);
        int ties1 = Integer.parseInt(info[9]);
        int color1 = Integer.parseInt(info[10]);
        PlayerLevel level1 = null;
        if(info[11].equals("Random")) level1 = PlayerLevel.RANDOM;
        else if(info[11].equals("Greedy")) level1 = PlayerLevel.GREEDY;
        else if(info[11].equals("Human")) level1 = PlayerLevel.HUMAN;
        else if(info[11].equals("Minimax")) level1 = PlayerLevel.MINIMAX;
        else level1 = PlayerLevel.WEIGHTED_MINIMAX;
        game.setPlayer(id1,name1,wins1,losses1,ties1,color1,level1);
        int id2 = Integer.parseInt(info[12]);
        String name2 = info[13];
        int wins2 = Integer.parseInt(info[14]);
        int losses2 = Integer.parseInt(info[15]);
        int ties2 = Integer.parseInt(info[16]);
        int color2 = Integer.parseInt(info[17]);
        PlayerLevel level2;
        if(info[18].equals("Random")) level2 = PlayerLevel.RANDOM;
        else if(info[18].equals("Greedy")) level2 = PlayerLevel.GREEDY;
        else if(info[18].equals("Human")) level2 = PlayerLevel.HUMAN;
        else if(info[18].equals("Minimax")) level2 = PlayerLevel.MINIMAX;
        else level2 = PlayerLevel.WEIGHTED_MINIMAX;
        game.setPlayer(id2,name2,wins2,losses2,ties2,color2,level2);
        //algorithm setup
        int depth = Integer.parseInt(info[19]);
        algorithm = new Algorithm(height,width,depth);
        int depth2 = Integer.parseInt(info[20]);
        algorithm2 = new Algorithm(height,width,depth2);
        setGamemode();
    }

    public void setGamemode(){
        gamemode = 0;
        if (game.getPlayerLevel(1) != PlayerLevel.HUMAN || game.getPlayerLevel(2) != PlayerLevel.HUMAN) {
            if (game.getPlayerLevel(1) != PlayerLevel.HUMAN && game.getPlayerLevel(2) != PlayerLevel.HUMAN)
                gamemode = 2;
            else gamemode = 1;
        }
    }

    public static String[] saveGame() {
        String[] info = new String[21];
        info[0] = Integer.toString(game.getScore(1));
        info[1] = Integer.toString(game.getScore(2));
        info[2] = Boolean.toString(board.diagonal); //getter mejor
        info[3] = Boolean.toString(board.horizontal);
        info[4] = Boolean.toString(board.vertical);
        info[5] = Integer.toString(game.getPlayerIdentifier(1));
        info[6] = game.getPlayerName(1);
        info[7] = Integer.toString(game.getPlayerWins(1));
        info[8] = Integer.toString(game.getPlayerWins(1));
        info[9] = Integer.toString(game.getPlayerTies(1));
        info[10] = Integer.toString(1);
        String level;
        if (game.getPlayerLevel(1) == PlayerLevel.RANDOM) level = "Random";
        else if (game.getPlayerLevel(1) == PlayerLevel.GREEDY) level = "Greedy";
        else if (game.getPlayerLevel(1) == PlayerLevel.HUMAN) level = "Human";
        else if (game.getPlayerLevel(1) == PlayerLevel.MINIMAX) level = "Minimax";
        else level = "WeightedMinimax";
        info[11] = level;
        info[12] = Integer.toString(game.getPlayerIdentifier(2));
        info[13] = game.getPlayerName(2);
        info[14] = Integer.toString(game.getPlayerWins(2));
        info[15] = Integer.toString(game.getPlayerWins(2));
        info[16] = Integer.toString(game.getPlayerTies(2));
        info[17] = Integer.toString(2);
        String level2;
        if (game.getPlayerLevel(2) == PlayerLevel.RANDOM) level2 = "Random";
        else if (game.getPlayerLevel(2) == PlayerLevel.GREEDY) level2 = "Greedy";
        else if (game.getPlayerLevel(2) == PlayerLevel.HUMAN) level2 = "Human";
        else if (game.getPlayerLevel(2) == PlayerLevel.MINIMAX) level2 = "Minimax";
        else level2 = "WeightedMinimax";
        info[18] = level2;
        info[19] = Integer.toString(algorithm.algorithmDepth);
        info[20] = Integer.toString(algorithm2.algorithmDepth);
        return info;
    }

    private void printBoardWithLegals(){
        char[][] map = board.getBoardWithLegals(turn);
        System.out.print("  ");
        for (int j = 0; j < map.length; j++) {
            if(j != 0 && j != map[0].length - 1) {
                System.out.print(j);
            }else {
                System.out.print(" ");
            }
            System.out.print(" ");
        }
        System.out.print("\n");
        for (int i = 0; i < map.length; i++) {
            if(i != 0 && i != map[0].length - 1) {
                System.out.print(i);
            }else {
                System.out.print(" ");
            }
            System.out.print(" ");
            for (int j = 0; j < map[0].length; j++) {
                System.out.print(map[i][j]);
                System.out.print(" ");
            }
            System.out.print("\n");
        }
    }

    /**
     * Introduce a delay
     * Used to allow the user to better identify the Bot's moves
     */
    private void delay(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Change the current turn to the other player
     */
    public void toggleTurn() {
        if (turn == 1) turn = 2;
        else if (turn == 2) turn = 1;
    }

    public static int score1() {
        return board.score(1);
    }
    public static int score2() {
        return board.score(2);
    }

    public void gameOver() {
        int score1 = board.score(1);
        int score2 = board.score(2);
        if (score1 > score2) {
            System.out.println("Player1 WINS!\n" +
                    "Scores:\nPlayer1: " + score1 + " - Player2: " + score2);

        }
        else if (score1 < score2) {
            System.out.println("Player2 WINS!\n" +
                    "Scores:\nPlayer1: " + score1 + " - Player2: " + score2);
        }
        else
        {
            System.out.println("It's a TIE!");
        }
        game.setScore(1,score1);
        game.setScore(2,score2);
        log.addNewGame(game);
    }

    public void initGameLog(ArrayList<String[]> games, ArrayList<String[]> players){
        Game game;
        for (String[] g : games) {
            boolean finished = false;
            if(g[1] == "true") finished = true;
            game = new Game(finished, Integer.parseInt(g[4]), Integer.parseInt(g[5]));
            String id1 = g[2];
            String id2 = g[3];
            for(String[] p : players){
                if(p[0].equals(id1) || p[0].equals(id2)){
                    PlayerLevel level;
                    if(p[6] == "Random") level = PlayerLevel.RANDOM;
                    else if(p[6] == "Greedy") level = PlayerLevel.GREEDY;
                    else if(p[6] == "Human") level = PlayerLevel.HUMAN;
                    else if(p[6] == "Minimax") level = PlayerLevel.MINIMAX;
                    else level = PlayerLevel.WEIGHTED_MINIMAX;
                    game.setPlayer(Integer.parseInt(p[0]), p[1], Integer.parseInt(p[2]), Integer.parseInt(p[3]), Integer.parseInt(p[4]), Integer.parseInt(p[5]), level);
                }
            }
            log.addGame(game);
        }
    }

    public static Integer[] getBoardLegals(){
        return board.getLegals(turn);
    }

    public static int[][] getBoard() {
        int height = getBoardHeight();
        int width = getBoardWidth();
        char[][] legalBoard = board.getBoardWithLegals(turn);
        int[][] map = new int[height][width];
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                if (legalBoard[i+1][j+1] == '-') map[i][j] = 0;
                else if (legalBoard[i+1][j+1] == 'X') map[i][j] = 1;
                else if (legalBoard[i+1][j+1] == 'O') map[i][j] = 2;
                else if (legalBoard[i+1][j+1] == '~') map[i][j] = 3;
            }
        }
        return map;
    }
    public String[] getStringBoard(){
        char[][] cellBoard = board.getBoard();
        String [] stringBoard = new String [cellBoard.length];
        for (int i = 0; i < cellBoard.length; i++) {
            stringBoard[i] = String.valueOf(cellBoard[i]);
        }
        return stringBoard;
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

    public static boolean getIsHuman(int turn) {return game.getPlayerLevel(turn) == PlayerLevel.HUMAN;}

    public static boolean gameEnded() {
        return board.gameEnded();
    }

    public boolean makeMove(int x,int y){
        /*ArrayList<String> map = null;
        if(board.makeMoveTurn(x,y,turn)){
            toggleTurn();
            char[][] m = board.getBoardWithLegals(turn);

        }
        return map;*/
        //System.out.println("MAKE MOVE REP: (" +x+ "," +y+")");
        boolean moveMade = true;
        //printBoardWithLegals();
        if(board.getLegals(turn).length > 0) {
            if (turn == 1) {
                if (game.getPlayerLevel(1) == PlayerLevel.HUMAN) {
                    if (!board.makeMoveTurn(x,y,turn)) moveMade = false;
                } else if (game.getPlayerLevel(1) == PlayerLevel.RANDOM) {
                    //if (gamemode != 2) delay();
                    algorithm.randAlgorithm(board, 1);
                } else if (game.getPlayerLevel(1) == PlayerLevel.MINIMAX) {
                    //if (gamemode != 2) delay();
                    algorithm.minimaxAlgorithm(board, 1, 1);
                }
                else if (game.getPlayerLevel(1) == PlayerLevel.WEIGHTED_MINIMAX) {
                    //if (gamemode != 2) delay();
                    algorithm.minimaxAlgorithm(board, 1, 2);
                }
                else if (game.getPlayerLevel(1) == PlayerLevel.GREEDY) {
                    //if (gamemode != 2) delay();
                    algorithm.greedyAlgorithm(board, 1);
                }
            } else if (turn == 2) {
                if (game.getPlayerLevel(2) == PlayerLevel.HUMAN) {
                    if (!board.makeMoveTurn(x,y,turn)) moveMade = false;
                } else if (game.getPlayerLevel(2) == PlayerLevel.RANDOM) {
                    //if (gamemode != 2) delay();
                    algorithm2.randAlgorithm(board, 2);
                } else if (game.getPlayerLevel(2) == PlayerLevel.MINIMAX) {
                    //if (gamemode != 2) delay();
                    algorithm2.minimaxAlgorithm(board, 2, 1);
                }
                else if (game.getPlayerLevel(2) == PlayerLevel.WEIGHTED_MINIMAX) {
                    //if (gamemode != 2) delay();
                    algorithm2.minimaxAlgorithm(board, 2, 2);
                }
                else if (game.getPlayerLevel(2) == PlayerLevel.GREEDY) {
                    //if (gamemode != 2) delay();
                    algorithm2.greedyAlgorithm(board, 2);
                }
            }
        }
        toggleTurn();
        return moveMade;
    }

    public static Vector<String[]> getRanking(String type) {
        return switch (type) {
            case "id" -> log.getSortedByID(true);
            case "wins" -> log.getSortedByWins(true);
            case "loses" -> log.getSortedByLoses(true);
            case "ties" -> log.getSortedByTies(true);
            case "idR" -> log.getSortedByID(false);
            case "winsR" -> log.getSortedByWins(false);
            case "losesR" -> log.getSortedByLoses(false);
            case "tiesR" -> log.getSortedByTies(false);
            default -> null;
        };
    }

    public Cell[][] getCellArray(String [] board){
        Cell[][] cellBoard = new Cell[board[0].length()][board.length];
        for (int i = 0; i < board.length; i++) {
            String row = board[i];
            for (int j = 0; j < row.length(); j++) {
                char cell = row.charAt(j);
                if(cell == '0'){
                    cellBoard[i][j] = Cell.EMPTY;
                }
                else if(cell == '1'){
                    cellBoard[i][j] = Cell.BLACK;
                }
                else if(cell == '2'){
                    cellBoard[i][j] = Cell.WHITE;
                }
            }
        }
        return cellBoard;
    }

}
