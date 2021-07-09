package FONTS.Test.Algorithm;

public class AlgorithmTest {
    static Algorithm player1;
    static Algorithm player2;
    public static Board board = new Board();
    static int turn;

    /**
     * Test which bot is better. The order should be Random < Greedy < Minimax < Weighted Minimax
     * @param which1    first bot identifier. 1-Random, 2-Greedy, 3-Minimax, 4-Weighted Minimax
     * @param which2    second bot identifier. 1-Random, 2-Greedy, 3-Minimax, 4-Weighted Minimax
     * @return  the amount of matches won by bot1
     */
    public static int test10Matches(int which1, int which2) {
        turn = 1;
        player1 = new Algorithm();
        player2 = new Algorithm();
        int matchesWon1 = 0;
        for (int i = 0; i < 10; ++i) {
            turn = 1;
            board = new Board();
            while (!board.gameEnded()) {
                if(board.getLegals(turn).length > 0) {
                    if (turn == 1) {
                        switch (which1) {
                            case 1 -> player1.randAlgorithm(board, 1);
                            case 2 -> player1.greedyAlgorithm(board, 1);
                            case 3 -> player1.minimaxAlgorithm(board, 1, 1);
                            case 4 -> player1.minimaxAlgorithm(board, 1, 2);
                        }
                    }
                    else if (turn == 2) {
                        switch (which2) {
                            case 1 -> player2.randAlgorithm(board, 2);
                            case 2 -> player2.greedyAlgorithm(board, 2);
                            case 3 -> player2.minimaxAlgorithm(board, 2, 1);
                            case 4 -> player2.minimaxAlgorithm(board, 2, 2);
                        }
                    }
                }
                toggleTurn();
            }
            int score1 = board.score(1);
            int score2 = board.score(2);
            if (score1 > score2) {
                ++matchesWon1;
            }
        }
        return matchesWon1;
    }

    /**
     * Test which bot is better. The one with more depth should win
     * Only ran once since every game would be exactly the same
     * @param which1    bot identifier. 3-Minimax, 4-Weighted Minimax
     * @return  the Minimax bot that won the match, 1 or 2
     */
    public static int testMatchDepth(int which1, int depth1, int depth2) {
        turn = 1;
        player1 = new Algorithm(8,8,depth1);
        player2 = new Algorithm(8,8,depth2);
            board = new Board();
            while (!board.gameEnded()) {
                if(board.getLegals(turn).length > 0) {
                    if (turn == 1) {
                        switch (which1) {
                            case 3 -> player1.minimaxAlgorithm(board, 1, 1);
                            case 4 -> player1.minimaxAlgorithm(board, 1, 2);
                        }
                    }
                    else if (turn == 2) {
                        switch (which1) {
                            case 3 -> player2.minimaxAlgorithm(board, 2, 1);
                            case 4 -> player2.minimaxAlgorithm(board, 2, 2);
                        }
                    }
                }
                toggleTurn();
            }
            int score1 = board.score(1);
            int score2 = board.score(2);
            int winner;
            if (score1 > score2) {
                winner = 1;
            }
            else winner = 2;
        return winner;
    }

    public static void toggleTurn(){
        if (turn == 1) turn = 2;
        else turn = 1;
    }

    public static void main(String[] args) {
        //Random vs Greedy
        int first = test10Matches(1,2);
        int second = 10-first;
        System.out.println("Random won " + first + " matches");
        System.out.println("Greedy won " + second + " matches");
        if (second > first) System.out.println("TEST PASSED");
        else System.out.println("TEST FAILED");

        //Random vs Minimax
        first = test10Matches(1,3);
        second = 10-first;
        System.out.println("Random won " + first + " matches");
        System.out.println("Minimax won " + second + " matches");
        if (second > first) System.out.println("TEST PASSED");
        else System.out.println("TEST FAILED");

        //Random vs Weighted Minimax
        first = test10Matches(1,4);
        second = 10-first;
        System.out.println("Random won " + first + " matches");
        System.out.println("Weighted Minimax won " + second + " matches");
        if (second > first) System.out.println("TEST PASSED");
        else System.out.println("TEST FAILED");

        //Greedy vs Minimax
        first = test10Matches(2,3);
        second = 10-first;
        System.out.println("Greedy won " + first + " matches");
        System.out.println("Minimax won " + second + " matches");
        if (second > first) System.out.println("TEST PASSED");
        else System.out.println("TEST FAILED");

        //Greedy vs Weighted Minimax
        first = test10Matches(2,4);
        second = 10-first;
        System.out.println("Greedy won " + first + " matches");
        System.out.println("Weighted Minimax won " + second + " matches");
        if (second > first) System.out.println("TEST PASSED");
        else System.out.println("TEST FAILED");

        //Minimax vs Weighted Minimax
        first = test10Matches(3,3);
        second = 10-first;
        System.out.println("Minimax won " + first + " matches");
        System.out.println("Weighted Minimax won " + second + " matches");
        if (second > first) System.out.println("TEST PASSED");
        else System.out.println("TEST FAILED");

        //Weighted Minimax vs Minimax
        first = test10Matches(4,3);
        second = 10-first;
        System.out.println("Weighted Minimax won " + first + " matches");
        System.out.println("Minimax won " + second + " matches");
        if (first > second) System.out.println("TEST PASSED");
        else System.out.println("TEST FAILED");

        //Minimax 4 vs Minimax 3
        int winner = testMatchDepth(3,4, 3);
        if (winner == 1) {
            System.out.println("MinimaxDepth4 won against MinimaxDepth3");
            System.out.println("TEST PASSED");
        }
        else {
            System.out.println("MinimaxDepth3 won against MinimaxDepth4");
            System.out.println("TEST FAILED");
        }

        //Weighted Minimax 4 vs WeightedMinimax 3
        winner = testMatchDepth(4,4, 3);
        if (winner == 1) {
            System.out.println("Weighted MinimaxDepth4 won against Weighted MinimaxDepth3");
            System.out.println("TEST PASSED");
        }
        else {
            System.out.println("Weighted MinimaxDepth3 won against Weighted MinimaxDepth4");
            System.out.println("TEST FAILED");
        }
    }
}
