package FONTS.Drivers.GameLog;

/**
 * STUB
 */
public class Game {
    boolean finished;
    int score1;
    int score2;
    public Player player1;
    public Player player2;

    /**
     * Defect Constructor
     * */
    public Game(){
        finished = false;
        score1 = 0;
        score2 = 0;
        player1 = new Player();
        player2 = new Player();
    }

    public Game(boolean finished, int score1, int score2){
        this.finished = finished;
        this.score1 = score1;
        this.score2 = score2;
    }

    /**
     * @return Player1
     * */
    public Player getPlayer(int color) {
        return player1;
    }
    /**
     * Player setter
     * @param identifier = identifier of the player
     * @param wins = wins of the player
     * @param losses = losses of the player
     * @param ties = ties of the player
     * @param color = color of the player
     * @param level = level of the bot dificulty
     * */
    public void setPlayer(int identifier, String name, int wins, int losses, int ties, int color, PlayerLevel level) {

    }

    /**
     * Player setter
     * @param identifier    player identifier
     * @param color         player color
     * @param level         player level
     */
    public void setPlayerShort(int identifier, int color, PlayerLevel level) {

    }

    public int getPlayerIdentifier(int color) {
        return 1;
    }

    public PlayerLevel getPlayerLevel(int color) {
        return PlayerLevel.HUMAN;
    }

    public String getPlayerName(int color) {
        return "juan";
    }

    public int getPlayerWins(int color) {
        return 1;
    }

    public int getPlayerLosses(int color) {
        return 1;
    }

    public int getPlayerTies(int color) {
        return 1;
    }

    /**
     * This function returns the score of Player1 in a game
     * @param color player color
     * @return the player's score
     * */
    public int getScore(int color) {
        return 1;
    }
    /**
     * Set Score of player 1
     * @param score player score
     * @param color player color
     * */
    public void setScore(int color, int score) {

    }
}
