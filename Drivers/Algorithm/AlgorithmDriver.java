package FONTS.Drivers.Algorithm;

/**
 * This driver tests integration between Algorithm and Board.
 * It checks whether the right move is made. Using a stub i.e. not having logic
 * in Board, the only test would be crash testing.
 */
public class AlgorithmDriver {
    public static Board board = new Board();
    public static Algorithm algorithm;
    public static int[][] testBoardPoints = new int[][]{
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,1000,-10, 10, 10, 10, 10, -10,1000,-1},
            {-1,-10,-10, 1, 1, 1, 1, -10, -10,-1},
            {-1, 10, 1, 1, 1, 1, 1, 1, 10, -1},
            {-1, 10, 1, 1, 1, 1, 1, 1, 10, -1},
            {-1, 10, 1, 1, 1, 1, 1, 1, 10, -1},
            {-1, 10, 1, 1, 1, 1, 1, 1, 10, -1},
            {-1, -10, -10, 1, 1, 1, 1, -10, -10,-1},
            {-1, 1000, -10, 10, 10, 10, 10,-10,1000,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
    };

    private static void testConstructor() {
        algorithm = new Algorithm();
        System.out.println("constructor: DONE");
    }

    /**
     * Test if the output of fillPoints is the desired table.
     */
    private static void testFillPoints() {
        algorithm.fillPoints();
        boolean same = true;
        for (int i = 0; i < 8 + 2; ++i) {
            for (int j = 0; j < 8 + 2; ++j) {
                if (algorithm.boardPoints[i][j] != testBoardPoints[i][j]) {
                    same = false;
                    break;
                }
            }
        }
        if (same) System.out.println("fillPoints: DONE");
        else System.out.println("fillPoints: FAILED");
    }

    /**
     * Check if Random is making a Random move
     * @return whether Random made moves randomly or not
     */
    private static boolean checkRandom() {
        int totalTimes = 0;
        int times1 = 0;
        for (int i = 0; i < 10; ++i) {
            board = new Board();
            algorithm.randAlgorithm(board, 1);
            if (board.getCell(3, 4) == Cell.BLACK) {
                ++totalTimes;
                ++times1;
            }
            else if (board.getCell(4, 3) == Cell.BLACK) ++totalTimes;
            else if (board.getCell(5, 6) == Cell.BLACK) ++totalTimes;
            else if (board.getCell(6, 5) == Cell.BLACK) ++totalTimes;
        }
        return totalTimes == 10 && times1 < 10;
    }

    /**
     * Check if Greedy is making the move it is supposed to
     * @return  whether Greedy made the right move
     */
    private static boolean checkGreedy() {
        Cell cell1 = board.getCell(3,4);
        return cell1 == Cell.BLACK;
    }

    /**
     * Check if Minimax is making the move it is supposed to
     * @return  whether Minimax made the right move
     */
    private static boolean checkMinimax() {
        Cell cell1 = board.getCell(5,3);
        return cell1 == Cell.WHITE;
    }

    /**
     * Check if Weighted Minimax is making the move it is supposed to
     * @return  whether Weighted Minimax made the right move
     */
    private static boolean checkWeightedMinimax() {
        Cell cell1 = board.getCell(6,2);
        return cell1 == Cell.BLACK;
    }


    public static void main(String[] args) {
        System.out.println("Algorithm driver: ");
        testConstructor();
        testFillPoints();
        if (checkRandom()) System.out.println("Random algorithm: DONE");
        board = new Board();
        algorithm.greedyAlgorithm(board, 1);
        if (checkGreedy()) System.out.println("Greedy algorithm: DONE");
        algorithm.minimaxAlgorithm(board, 2, 1);
        if (checkMinimax()) System.out.println("Minimax algorithm: DONE");
        algorithm.minimaxAlgorithm(board, 1, 2);
        if (checkWeightedMinimax()) System.out.println("Weighted minimax algorithm: DONE");
    }
}
