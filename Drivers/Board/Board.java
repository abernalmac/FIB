package FONTS.Drivers.Board;

import java.util.ArrayList;

public class Board {
    static int height;
    static int width;
    int[][] map;   //-1 if border 0 if empty 1 if black 2 if white

    public Board() {
        height = 8;
        width = 8;
        map = new int[height+2][width+2];
        //board initialization
        initBoard();
    }

    public Board(int customHeight, int customWidth) {
        height = customHeight;
        width = customWidth;
        map = new int[height+2][width+2];
        //board initialization
        initBoard();
    }

    public Board(int customHeight, int customWidth, int[][] customMap) {
        height = customHeight;
        width = customWidth;
        map = new int[height+2][width+2];
        //board initialization
        initCustomBoard(customMap);
    }

    private static class Position{
        int x;
        int y;
        public Position(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Attempt to make a move and do so if it is legal
     * @param heightCoord  move position's row
     * @param widthCoord   move position's column
     * @return             whether the move was made
     */
    public boolean makeMove(int heightCoord, int widthCoord){
        if(heightCoord < 1 || widthCoord < 1 || heightCoord > height + 1 || widthCoord > width + 1){
            return false;
        }
        return updateBoard(heightCoord, widthCoord, DomainController.getTurn());
    }

    /**
     * Attempt to make a move and do so if it is legal
     * @param heightCoord  move position's row
     * @param widthCoord   move position's column
     * @param turn         who is playing - Black (1) or White (2)
     * @return             whether the move was made
     */
    public boolean makeMoveTurn(int heightCoord, int widthCoord, int turn){
        if(heightCoord < 1 || widthCoord < 1 || heightCoord > height + 1 || widthCoord > width + 1){
            return false;
        }
        return updateBoard(heightCoord, widthCoord, turn);
    }

    /**
     * Creates a list with the legal moves a player can make
     * @param turn  who is playing - Black (1) or White (2)
     * @return      a list of legal moves
     */
    public Integer[] getLegals(int turn){
        ArrayList<Integer> legalPositions = new ArrayList<>();
        for (int i = 1; i < height + 1; i++) {
            for (int j = 1; j < width + 1; j++) {
                if(map[i][j] == 0) {
                    ArrayList<Position> where = new ArrayList<>();
                    if (checkPosition( i, j, turn, where)) {
                        legalPositions.add(i);
                        legalPositions.add(j);
                    }
                }
            }
        }
        return legalPositions.toArray(Integer[]::new);
    }

    /**
     * Check whether a move is legal or not
     * @param heightCoord  move position's row
     * @param widthCoord   move position's column
     * @param turn         who is playing - Black (1) or White (2)
     * @return             whether the move is legal or not
     */
    public boolean isLegal(int heightCoord, int widthCoord, int turn){
        ArrayList<Position> where = new ArrayList<>();
        return checkPosition(heightCoord, widthCoord, turn, where);
    }

    /**
     * Copy the board
     * @return  a copy of the board
     */
    public Board copyBoard() {
        Board newBoard = new Board();
        newBoard.map = this.copyArray(this.map);
        return newBoard;
    }

    /**
     * Return a printable board showing the legal moves the player can make
     */
    public char[][] getBoard(){
        char[][] mapToPrint = new char[height + 2][width + 2];
        for (int i = 0; i < height + 2; i++) {
            for (int j = 0; j < width + 2; j++) {
                if (map[i][j] == -1) {
                    mapToPrint[i][j] = '■';
                }
                if (map[i][j] == 0) {
                    mapToPrint[i][j] = '-';
                }
                if (map[i][j] == 1) {
                    mapToPrint[i][j] = 'X';
                }
                if (map[i][j] == 2) {
                    mapToPrint[i][j] = 'O';
                }
            }
        }
        return mapToPrint;
    }

    /**
     * Return a printable board showing the legal moves the player can make
     */
    public char[][] getBoardWithLegals(){
        char[][] mapToPrint = new char[height + 2][width + 2];
        int [][] completeMap;
        completeMap = copyArray(map);
        Integer [] legals = getLegals(DomainController.getTurn());
        for (int i = 0; i < legals.length; i+=2) {
            completeMap[legals[i]][legals[i + 1]] = 3;
        }
        for (int i = 0; i < height + 2; i++) {
            for (int j = 0; j < width + 2; j++) {
                if (completeMap[i][j] == -1) {
                    mapToPrint[i][j] = '■';
                }
                if (completeMap[i][j] == 0) {
                    mapToPrint[i][j] = '-';
                }
                if (completeMap[i][j] == 1) {
                    mapToPrint[i][j] = 'X';
                }
                if (completeMap[i][j] == 2) {
                    mapToPrint[i][j] = 'O';
                }
                if (completeMap[i][j] == 3) {
                    mapToPrint[i][j] = '~';
                }
            }
        }
        return mapToPrint;
    }

    /**
     * Determine if the game has ended
     * @return  whether there is a player with legal moves to make
     */
    public boolean gameEnded(){
        Integer[] legals = getLegals(1);
        Integer[] legals2 = getLegals(2);
        return legals.length == 0 || legals2.length == 0;
    }

    /**
     * Sets the initial default values of the positions in map
     */
    private void initBoard() {
        for (int i = 0; i < height+2; i++) {
            for (int j = 0; j < width+2; j++) {
                if ((i == 0) || (i == height+1) || (j == 0) || (j == width+1)){
                    map[i][j] = -1;
                }
                else if (((i == height/2 + 1) && (j == width/2 + 1)) || ((i == height/2) && (j == width/2))){
                    map[i][j] = 2;
                }
                else if (((i == height/2 + 1) && (j == width/2)) || ((i == height/2) && (j == width/2 +1 ))){
                    map[i][j] = 1;
                }
                else {
                    map[i][j] = 0;
                }
            }
        }
    }

    /**
     * Sets the initial custom values of the positions in map
     */
    private void initCustomBoard(int[][] customMap){
        for(int i = 0; i < map.length; ++i)
            for (int j = 0; j < map[0].length; j++) {
                if (i == 0 || j == 0 || i == height + 1 || j == width + 1) {
                    map[i][j] = -1;
                } else {
                    map[i][j] = customMap[i - 1][j - 1];
                }
            }
    }

    /**
     * Get the opposite color
     * @param color  a color, Black (1) or White (2)
     * @return       the opposite of color
     */
    private int getOppositeColor(int color){
        if(color == 1) return 2;
        else return 1;
    }

    /**
     * Copy the board's array
     * @param array  the array to copy
     * @return       the copied array
     */
    int[][] copyArray(int[][] array){
        int [][] copy = new int[height+2][width+2];
        for (int i = 0; i < array.length; i++) {
            System.arraycopy(array[i], 0, copy[i], 0, array[0].length);
        }
        return copy;
    }

    /**
     * Get the score of a given player
     * @param turn  the player of the desired score - Black (1) or White (2)
     * @return      the score of the player
     */
    public int score(int turn) {
        int points = 0;
        for (int i = 0; i < height+2; ++i) {
            for (int j = 0; j < width+2; ++j) {
                if (map[i][j] == turn) ++points;
            }
        }
        return points;
    }

    /**
     * Update the game's board with a given position if legal
     * @param heightCoord  move position's row
     * @param widthCoord   move position's row
     * @param turn         the color of the disk being placed - Black (1) or White (2)
     * @return             whether it was possible to update the board
     */
    private boolean updateBoard(int heightCoord, int widthCoord, int turn){
        ArrayList<Position> where = new ArrayList<>();
        if(checkPosition(heightCoord,widthCoord,turn,where)){
            for (Position position : where) {
                int i = heightCoord;
                int j = widthCoord;
                map[i][j] = turn;
                i += position.x;
                j += position.y;
                while (map[i][j] == getOppositeColor(turn)) {
                    map[i][j] = turn;
                    i += position.x;
                    j += position.y;
                }
            }
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Check whether a move to a position is legal or not
     * @param heightCoord  move's row
     * @param widthCoord   move's row
     * @param turn         the color of the disk being placed - Black (1) or White (2)
     * @param where        a list of the directions
     * @return             whether the move is legal
     */
    private boolean checkPosition(int heightCoord, int widthCoord, int turn, ArrayList<Position> where){
        Position [] direction = {
                new Position(1,1), new Position(1,-1), new Position(-1,1), new Position(-1,-1),
                new Position(0,-1), new Position(0,1), new Position(1,0), new Position(-1,0)
        };
        boolean[] rules = DomainController.getRules();
        boolean [] validDirection = {rules[0], rules[0], rules[0], rules[0], rules[1], rules[1], rules[2], rules[2]};
        int numIter = 8;
        for(int iter = 0; iter < numIter; ++iter){
            if(validDirection[iter]) {
                boolean stopSearching = false;
                int i = direction[iter].x;
                int j = direction[iter].y;
                if (map[heightCoord + i][widthCoord + j] == getOppositeColor(turn)) {
                    i += direction[iter].x;
                    j += direction[iter].y;
                    while (!stopSearching) {
                        if (map[heightCoord + i][widthCoord + j] == turn) {
                            stopSearching = true;
                            where.add(new Position( direction[iter].x, direction[iter].y));
                        } else if (map[heightCoord + i][widthCoord + j] == 0 || map[heightCoord + i][widthCoord + j] == -1) {
                            stopSearching = true;
                        }
                        i += direction[iter].x;
                        j += direction[iter].y;
                    }
                }
            }
        }
        return !where.isEmpty();
    }
}

