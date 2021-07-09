package FONTS.Drivers.Board;

import java.util.Scanner;

public class BoardDriver {
    static Board board;

    public static void main(String[] args) {
        System.out.println("Board driver: ");
        System.out.println("This is a work in progress. In the future, an input file will be given.");
        System.out.println("The tests work, but parameters have to be entered through stdin");
        System.out.println("Choose a 0-10 case:");
        System.out.println("0: Basic constructor");
        System.out.println("1: Custom constructor");
        System.out.println("2: Make move");
        System.out.println("3: Make move turn");
        System.out.println("4: Get legals");
        System.out.println("5: Game ended");
        System.out.println("6: Score");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int height;
        int width;
        int[][] map;
        while (n!=-1){
            switch (n) {
                case 0:
                    testBasicConstructor(scanner.nextInt(), scanner.nextInt());

                case 1:
                    height = scanner.nextInt();
                    width = scanner.nextInt();
                    map = readMap(height, width);
                    testCustomConstructor(height, width, map);

                case 2:
                    board = new Board(scanner.nextInt(), scanner.nextInt());
                    testMakeMove(scanner.nextInt(), scanner.nextInt());

                case 3:
                    board = new Board(scanner.nextInt(), scanner.nextInt());
                    testMakeMoveTurn(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());

                case 4:
                    height = scanner.nextInt();
                    width = scanner.nextInt();
                    map = readMap(height, width);
                    testGetLegals(height, width, map);

                case 5:
                    board = new Board();
                    testGameEnded();

                case 6:
                    board = new Board();
                    testScore();

            }
            n = scanner.nextInt();
        }
    }

    private static int[][] readMap(int height, int width){
        int[][] map = new int[height][width];
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < height; i++) for (int j = 0; j < width; j++) map[i][j] = scanner.nextInt();
        return map;
    }

    private static void testBasicConstructor(int height, int width){
        try {
            board = new Board(height, width);
            System.out.print("DONE\n");
        }
        catch(Exception e){
            System.out.print("FAILED\n");
            e.printStackTrace();
        }
    }

    private static void testCustomConstructor(int height, int width, int[][] map){
        try {
            board = new Board(height, width, map);
            System.out.print("DONE\n");
        }
        catch(Exception e){
            System.out.print("FAILED\n");
            e.printStackTrace();
        }
    }

    private static void testMakeMove(int height, int width) {
        try {
            if (board.makeMove(height, width)) {
                char[][] map = board.getBoardWithLegals();
                if(map[height][width] == '~') System.out.print("DONE\n");
                else System.out.print("FAILED\n");
            }
            else System.out.print("NOT A VALID POSITION\n");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void testMakeMoveTurn(int height, int width, int turn) {
        try {
            if (board.makeMoveTurn(height, width, turn)) {
                char[][] map = board.getBoardWithLegals();
                if(map[height][width] == '~') System.out.print("DONE\n");
                else System.out.print("FAILED\n");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void testGetLegals(int height, int width, int[][] map){
        Integer[] truelegals = new Integer[]{
                3, 4, 4, 3, 5, 6, 6, 5
        };
        Integer[] legals = board.getLegals(1);
        if(truelegals == legals) System.out.print("DONE\n");
        else System.out.print("FAILED\n");
    }

    private static void testGameEnded(){
        try {
            if(!board.gameEnded()) System.out.print("DONE\n");
            else System.out.print("FAILED\n");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void testScore(){
        try {
            if(board.score(1) == 2) System.out.print("DONE\n");
            else System.out.print("FAILED\n");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
