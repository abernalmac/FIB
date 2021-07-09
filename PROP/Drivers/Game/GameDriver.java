package FONTS.Drivers.Game;

public class GameDriver {
    private static Game game;

    private static void testConstructor() {
        game = new Game();
        if (!game.finished && game.score1 == 0 && game.score2 == 0)
            System.out.println("Constructor: DONE");
        else System.out.println("Constructor: FAILED");
    }

    private static void testCustomConstructor() {
        game = new Game(false,32,23);
        if (!game.finished && game.score1 == 32 && game.score2 == 23)
            System.out.println("customConstructor: DONE");
        else System.out.println("customConstructor: FAILED");
    }

    /**
     * Test player setter. This test uses Player functions that have been tested
     * in the Player driver. Therefore, any error is Game's fault or Game-Player
     * integration.
     */
    private static void testSetPlayer(){
        boolean error = false;
        game = new Game(false, 0,0);
        game.setPlayer(1,"juan", 2,3,4,1,PlayerLevel.HUMAN);
        if (game.getPlayerIdentifier(1) != 1) error = true;
        if (!game.getPlayerName(1).equals("juan")) error = true;
        if (game.getPlayerWins(1) != 2) error = true;
        if (game.getPlayerLosses(1) != 3) error = true;
        if (game.getPlayerTies(1) != 4) error = true;
        if (!game.getPlayerLevel(1).equals(PlayerLevel.HUMAN)) error = true;
        if (!error) System.out.println("Player setter: DONE");
        else System.out.println("Player setter: FAILED");
    }

    public static void main(String[] args) {
        System.out.println("Game driver: ");
        testConstructor();
        testCustomConstructor();
        testSetPlayer();
    }
}
