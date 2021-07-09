package FONTS.Drivers.Player;

public class PlayerDriver {
        private static Player player;

    private static void testConstructor() {
        player = new Player();
        System.out.println("constructor: DONE");
    }

    private static void testCustomConstructor1() {
        player = new Player(1,1,PlayerLevel.RANDOM);
        if (player.getIdentifier() == 1 && player.getColor() == 1 && player.getLevel() ==
                PlayerLevel.RANDOM)
            System.out.println("customConstructor: DONE");
        else System.out.println("customConstructor: FAILED");
    }

    private static void testCustomConstructor2() {
        player = new Player(1,"juan", 1,2,3,1,PlayerLevel.RANDOM);
        if (player.getIdentifier() == 1 && player.getWins() == 1 && player.getLosses() == 2 &&
            player.getTies() == 3 && player.getColor() == 1 && player.level == PlayerLevel.RANDOM)
            System.out.println("customConstructor: DONE");
        else System.out.println("customConstructor: FAILED");
    }

    public static void main(String[] args) {
        System.out.println("Player driver: ");
        testConstructor();
        testCustomConstructor1();
        testCustomConstructor2();
    }
}
