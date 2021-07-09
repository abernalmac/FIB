package FONTS.Test.Board;


public class BoardTest {
    static Board board = new Board();
    static Cell [][][] maps = {
            {   {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.WHITE, Cell.BLACK, Cell.BLACK, Cell.BLACK, Cell.EMPTY},
                    {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.BLACK, Cell.BLACK, Cell.BLACK, Cell.EMPTY, Cell.EMPTY},
                    {Cell.EMPTY, Cell.WHITE, Cell.BLACK, Cell.BLACK, Cell.BLACK, Cell.WHITE, Cell.EMPTY, Cell.WHITE},
                    {Cell.WHITE, Cell.BLACK, Cell.BLACK, Cell.BLACK, Cell.WHITE, Cell.EMPTY, Cell.WHITE, Cell.EMPTY},
                    {Cell.WHITE, Cell.BLACK, Cell.BLACK, Cell.WHITE, Cell.WHITE, Cell.WHITE, Cell.EMPTY, Cell.EMPTY},
                    {Cell.WHITE, Cell.BLACK, Cell.WHITE, Cell.WHITE, Cell.WHITE, Cell.WHITE, Cell.BLACK, Cell.BLACK},
                    {Cell.BLACK, Cell.EMPTY, Cell.BLACK, Cell.WHITE, Cell.WHITE, Cell.WHITE, Cell.BLACK, Cell.EMPTY},
                    {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.BLACK, Cell.BLACK, Cell.BLACK, Cell.EMPTY, Cell.EMPTY}  },

            {   {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
                    {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
                    {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
                    {Cell.BLACK, Cell.BLACK, Cell.BLACK, Cell.BLACK, Cell.BLACK, Cell.BLACK, Cell.BLACK, Cell.BLACK},
                    {Cell.WHITE, Cell.WHITE, Cell.WHITE, Cell.WHITE, Cell.WHITE, Cell.WHITE, Cell.WHITE, Cell.WHITE},
                    {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
                    {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY},
                    {Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY}

            }
    };

    static Integer [][] correctLegals = {
            {1, 3, 2, 1, 2, 2, 3, 1, 3, 7, 4, 6, 5, 7, 5, 8, 8, 7},
            {6, 1, 6, 2, 6, 3, 6, 4, 6, 5, 6, 6, 6, 7, 6, 8}
    };

    static Cell [][][] correctMap = {
            {
                    {Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER},
                    {Cell.BORDER,Cell.EMPTY, Cell.EMPTY, Cell.VALID, Cell.WHITE, Cell.BLACK, Cell.BLACK, Cell.BLACK, Cell.EMPTY,Cell.BORDER},
                    {Cell.BORDER,Cell.VALID, Cell.VALID, Cell.EMPTY, Cell.BLACK, Cell.BLACK, Cell.BLACK, Cell.EMPTY, Cell.EMPTY,Cell.BORDER},
                    {Cell.BORDER,Cell.VALID, Cell.WHITE, Cell.BLACK, Cell.BLACK, Cell.BLACK, Cell.BLACK, Cell.EMPTY, Cell.WHITE,Cell.BORDER},
                    {Cell.BORDER,Cell.WHITE, Cell.BLACK, Cell.BLACK, Cell.BLACK, Cell.BLACK, Cell.BLACK, Cell.WHITE, Cell.VALID,Cell.BORDER},
                    {Cell.BORDER,Cell.WHITE, Cell.BLACK, Cell.BLACK, Cell.WHITE, Cell.BLACK, Cell.BLACK, Cell.EMPTY, Cell.VALID,Cell.BORDER},
                    {Cell.BORDER,Cell.WHITE, Cell.BLACK, Cell.WHITE, Cell.BLACK, Cell.WHITE, Cell.BLACK, Cell.BLACK, Cell.BLACK,Cell.BORDER},
                    {Cell.BORDER,Cell.BLACK, Cell.VALID, Cell.BLACK, Cell.WHITE, Cell.WHITE, Cell.BLACK, Cell.BLACK, Cell.EMPTY,Cell.BORDER},
                    {Cell.BORDER,Cell.EMPTY, Cell.EMPTY, Cell.VALID, Cell.BLACK, Cell.BLACK, Cell.BLACK, Cell.EMPTY, Cell.EMPTY,Cell.BORDER},
                    {Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER}},

            {   {Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER},
                    {Cell.BORDER,Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY,Cell.BORDER},
                    {Cell.BORDER,Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY,Cell.BORDER},
                    {Cell.BORDER,Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY,Cell.BORDER},
                    {Cell.BORDER,Cell.BLACK, Cell.BLACK, Cell.BLACK, Cell.BLACK, Cell.BLACK, Cell.BLACK, Cell.BLACK, Cell.BLACK,Cell.BORDER},
                    {Cell.BORDER,Cell.WHITE, Cell.BLACK, Cell.BLACK, Cell.WHITE, Cell.WHITE, Cell.WHITE, Cell.WHITE, Cell.WHITE,Cell.BORDER},
                    {Cell.BORDER,Cell.VALID, Cell.BLACK, Cell.VALID, Cell.VALID, Cell.VALID, Cell.VALID, Cell.VALID, Cell.VALID,Cell.BORDER},
                    {Cell.BORDER,Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY,Cell.BORDER},
                    {Cell.BORDER,Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY,Cell.BORDER},
                    {Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER,Cell.BORDER}
            }
    };

    static int[] moves = {
            4, 6, 6, 2
    };



    public static void main(String[] args){
        testLegals();
        testMove();


    }

    private static void testLegals(){
        for (int i = 0; i < 2; ++i) {
            board = new Board(8, 8, maps[i]);
            Integer[] legals = board.getLegals(1);
            if (equalsInt(legals, correctLegals[i])){
                System.out.print("Test Legals "+ i + " DONE\n");
            }
            else System.out.print("Test Legals "+ i + " FAILED\n");
        }

    }

    private static void testMove(){
        for (int i = 0; i < 2; ++i) {
            board = new Board(8, 8, maps[i]);
            if(!board.makeMoveTurn(moves[i*2],moves[i*2+1],1)) System.out.print("INVALID MOVE");
            char [][] legals = board.getBoardWithLegals(1);
            if (equalsMatChar(legals, correctMap[i])){
                System.out.print("Test Move "+ i + " DONE\n");
            }
            else System.out.print("Test Move "+ i + " FAILED\n");
        }
    }

    private static boolean equalsMatChar(char[][] e1, Cell[][] e2){
        if(e1.length != e2.length) return false;
        for(int i = 0; i < e1.length; ++i){
            for(int j = 0; j < e1.length; ++j) {
                if(e1[i][j] == '-' && e2[i][j] != Cell.EMPTY) return false;
                if(e1[i][j] == 'X' && e2[i][j] != Cell.BLACK) return false;
                if(e1[i][j] == 'O' && e2[i][j] != Cell.WHITE) return false;
                if(e1[i][j] == '~' && e2[i][j] != Cell.VALID) return false;
                if(e1[i][j] == '■' && e2[i][j] != Cell.BORDER) return false;
            }
        }
        return true;
    }

    private static boolean equalsInt(Integer[] e1, Integer[] e2){
        if(e1.length != e2.length) return false;
        for(int i = 0; i < e1.length; ++i){
            if(!e1[i].equals(e2[i])) return false;
        }
        return true;
    }
}
