package FONTS.Drivers.Algorithm;

import java.util.concurrent.ThreadLocalRandom;

public class Algorithm {
    int[][] boardPoints;
    int boardHeight;
    int boardWidth;
    int algorithmDepth;
    //values from https://www.instructables.com/Othello-Artificial-Intelligence/
    final int corner = 1000;
    final int nextToCorner = -10;
    final int border = 10;
    final int other = 1;
    final int nullPosition = -1;


    public Algorithm() {
        boardHeight = 8;
        boardWidth = 8;
        algorithmDepth = 3;
        this.boardPoints = new int[boardHeight+2][boardWidth+2];
        fillPoints();
    }

    public Algorithm(int maxHeight, int maxWidth, int depth) {
        boardHeight = maxHeight;
        boardWidth = maxWidth;
        algorithmDepth = depth;
        this.boardPoints = new int[boardHeight+2][boardWidth+2];
        fillPoints();
    }

    /**
     *  Fill the boardPoints matrix with its values
     *  Supports different board dimensions
     */
    public void fillPoints() {
        int maxHeight = boardHeight;
        int maxWidth = boardWidth;
        //assign nulls
        for (int i = 0; i < maxWidth+2; ++i) boardPoints[0][i] = nullPosition;
        for (int i = 0; i < maxWidth+2; ++i) boardPoints[maxHeight+1][i] = nullPosition;
        for (int i = 0; i < maxHeight+2; ++i) boardPoints[i][0] = nullPosition;
        for (int i = 0; i < maxHeight+2; ++i) boardPoints[i][maxWidth+1] = nullPosition;

        //assign others by default
        for (int i = 1; i <= maxHeight; ++i) {
            for (int j = 1; j <= maxWidth; ++j) {
                boardPoints[i][j] = other;
            }
        }
        //assign corners
        boardPoints[1][1] = boardPoints[1][maxWidth] = boardPoints[maxHeight][1] = boardPoints[maxHeight][maxWidth] = corner;
        //assign next to corners
        boardPoints[1][2] = boardPoints[2][1] = boardPoints[2][2] = nextToCorner;
        boardPoints[1][maxWidth-1] = boardPoints[2][maxWidth-1] = boardPoints[2][maxWidth] = nextToCorner;
        boardPoints[maxHeight-1][1] = boardPoints[maxHeight-1][2] = boardPoints[maxHeight][2] = nextToCorner;
        boardPoints[maxHeight][maxWidth-1] = boardPoints[maxHeight-1][maxWidth-1] = boardPoints[maxHeight-1][maxWidth] = nextToCorner;
        //assign borders
        for (int i = 3; i < maxWidth-1; ++i) boardPoints[1][i] = border;
        for (int i = 3; i < maxWidth-1; ++i) boardPoints[maxHeight][i] = border;
        for (int i = 3; i < maxHeight-1; ++i) boardPoints[i][1] = border;
        for (int i = 3; i < maxHeight-1; ++i) boardPoints[i][maxWidth] = border;
    }

    /**
     * Compute the heuristic of a given move
     * @param board shows the state of the game
     * @param turn  who is playing - Black (1) or White (2)
     * @return      the score of the player minus the opponent's
     */
    public int heuristic (Board board, int turn) {
        int opponent;
        if (turn == 1) {
            opponent = 2;
        }
        else {
            opponent = 1;
        }
        int playerScore = board.score(turn);
        int opponentScore = board.score(opponent);
        return (playerScore-opponentScore);
    }

    /**
     * Compute the heuristic of a given move with weights
     * @param board shows the state of the game
     * @param turn  who is playing - Black (1) or White (2)
     * @return      the score of the player minus the opponent's
     */
    public int weightedHeuristic (Board board, int turn) {
        Cell turnCell = Cell.BLACK;
        Cell opponentCell = Cell.WHITE;
        if (turn == 2) {
            turnCell = Cell.WHITE;
            opponentCell = Cell.BLACK;
        }

        int playerScore = 0;
        int opponentScore = 0;
        for (int i = 0; i < Board.height +2; ++i) {
            for (int j = 0; j < Board.width +2; ++j) {
                if (board.getCell(i,j) == turnCell) playerScore+=boardPoints[i][j];
                else if (board.getCell(i,j) == opponentCell) opponentScore+=boardPoints[i][j];
            }
        }
        return (playerScore-opponentScore);
    }

    /**
     * Perform a turn using a minimax approach
     * @param current the current state of the board
     * @param turn    who is playing - Black (1) or White (2)
     * @param mode    heuristic to use
     */
    public void minimaxAlgorithm(Board current, int turn, int mode) {
        Integer[] legals = current.getLegals(turn);
        if (legals.length == 0) {
            return;
        }
        int opponent = 1;
        if (turn == 1) {
            opponent = 2;
        }
        int bestX = legals[0];
        int bestY = legals[1];
        for (int i = 0; i < legals.length; i+=2) { //Quick check for corners before computing
            if (boardPoints[legals[i]][legals[i+1]] == corner) {
                bestX = legals[i];
                bestY = legals[i+1];
                if (!current.makeMoveTurn(bestX, bestY, turn))
                    System.out.println("Error updating currentBoard");
                return;
            }
        }
        int bestValue = Integer.MIN_VALUE;
        for (int i = 0; i < legals.length; i+=2) {
            Board tempBoard = current.copyBoard();
            if (!tempBoard.makeMoveTurn(legals[i], legals[i + 1], turn))
                System.out.println("Error updating tempBoard in minimaxAlgorithm");
            int value = minimaxValue(tempBoard, turn, opponent, 1, mode);
            if (value > bestValue) {
                bestValue = value;
                bestX = legals[i];
                bestY = legals[i + 1];
            }
        }
        if (!current.makeMoveTurn(bestX, bestY, turn))
            System.out.println("Error updating currentBoard");
    }

    /**
     * Computes the value of a move following a minimax strategy
     * @param board         the current state of the board
     * @param originalTurn  who started this turn - Black (1) or White (2)
     * @param currentTurn   who is currently being used for the calculation - Black (1) or White (2)
     * @param depth         search tree's depth, i.e. max recursive steps to make
     * @param mode          heuristic to use
     * @return              the heuristic value of a move
     */
    public int minimaxValue(Board board, int originalTurn, int currentTurn, int depth, int mode) {
        if (depth == algorithmDepth || board.gameEnded()) {
            if (mode == 1) return heuristic(board, originalTurn);
            else if (mode == 2) return weightedHeuristic(board, originalTurn);
        }
        int opponent = 1;
        if (currentTurn == 1) {
            opponent = 2;
        }
        Integer[] legals = board.getLegals(currentTurn);
        if (legals.length == 0) {
            return minimaxValue(board, originalTurn, opponent, depth +1, mode);
        }
        else {
            int bestValue = Integer.MIN_VALUE; //finding max
            if (originalTurn != currentTurn)
                bestValue = Integer.MAX_VALUE; //finding min
            for (int i = 0; i < legals.length; i+=2) {
                Board tempBoard = board.copyBoard();
                if (!tempBoard.makeMoveTurn(legals[i], legals[i + 1], currentTurn))
                    System.out.println("Error updating tempBoard in minimaxAlgorithm");
                int value = minimaxValue(tempBoard, originalTurn, opponent, depth +1, mode);
                if (originalTurn == currentTurn) {
                    if (value > bestValue)
                        bestValue = value;
                }
                else {
                    if (value < bestValue)
                        bestValue = value;
                }
            }
            return bestValue;
        }
    }

    /**
     * Perform a turn choosing at random
     * @param current the current state of the board
     * @param turn    who is playing - Black (1) or White (2)
     */
    public void randAlgorithm(Board current, int turn) {
        Integer[] legals = current.getLegals(turn);
        int size = legals.length;
        if (size == 0) return;
        int rand = ThreadLocalRandom.current().nextInt(0,size-1);
        if (rand%2 != 0) --rand;
        int row = legals[rand];
        int column = legals[rand + 1];
        if (!current.makeMoveTurn(row, column, turn))
            System.out.println("Error in random, row: " + row + " column: " + column);
    }

    /**
     * Perform a turn being greedy
     * @param current the current state of the board
     * @param turn    who is playing - Black (1) or White (2)
     */
    public void greedyAlgorithm(Board current, int turn) {
        Integer[] legals = current.getLegals(turn);
        int size = legals.length;
        if (size == 0) return;
        int startPoints = 0;
        int highestX = legals[0];
        int highestY = legals[1];
        int highestPoints = startPoints;
        for (int i = 0; i < legals.length; i+=2) {
            Board tempBoard = current.copyBoard();
            if (!tempBoard.makeMoveTurn(legals[i], legals[i + 1], turn))
                System.out.println("Error updating tempBoard in greedyAlgorithm");
            int tempPoints = tempBoard.score(turn);
            if (tempPoints > highestPoints) {
                highestX = legals[i];
                highestY = legals[i+1];
                highestPoints = tempPoints;
            }
        }
        if (!current.makeMoveTurn(highestX, highestY, turn))
            System.out.println("Error in greedy, row: " + highestX + " column: " + highestY);
    }
}
