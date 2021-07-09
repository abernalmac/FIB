package FONTS.Drivers.Game;

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
        if(color == 1) return player1;
        else return player2;
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
        Player p = new Player(identifier, name, wins,losses,ties,color,level);
        if(color == 1) player1 = p;
        else player2 = p;
    }

    /**
     * Player setter
     * @param identifier    player identifier
     * @param color         player color
     * @param level         player level
     */
    public void setPlayerShort(int identifier, int color, PlayerLevel level) {
        Player p = new Player(identifier,color,level);
        if(color == 1) player1 = p;
        else player2 = p;
    }

    public int getPlayerIdentifier(int color) {
        if (color == 1) return player1.getIdentifier();
        else return player2.getIdentifier();
    }

    public PlayerLevel getPlayerLevel(int color) {
        if (color == 1) return player1.getLevel();
        else return player2.getLevel();
    }

    public String getPlayerName(int color) {
        if (color == 1) return player1.getName();
        else return player2.getName();
    }

    public int getPlayerWins(int color) {
        if (color == 1) return player1.getWins();
        else return player2.getWins();
    }

    public int getPlayerLosses(int color) {
        if (color == 1) return player1.getLosses();
        else return player2.getLosses();
    }

    public int getPlayerTies(int color) {
        if (color == 1) return player1.getTies();
        else return player2.getTies();
    }

    /**
     * This function returns the score of Player1 in a game
     * @param color player color
     * @return the player's score
     * */
    public int getScore(int color) {
        if(color == 1) return score1;
        else return score2;
    }
    /**
     * Set Score of player 1
     * @param score player score
     * @param color player color
     * */
    public void setScore(int color, int score) {
        if(color == 1) score1 = score;
        else score2 = score;
    }
}
