package FONTS.Domain;

public class Player {
    int identifier;
    String name;
    int wins;
    int losses;
    int ties;
    int color; //1 black, 2 white
    PlayerLevel level;

    /**
     * Defect constructor
     * */
    public Player() {
        this.identifier = getIdentifier();
        this.wins = 0;
        this.losses = 0;
        this.color = 1;
        this.level = PlayerLevel.HUMAN;
    }

    /**
     * Custom constructor
     * @param id    player identifier
     * @param color player color
     * @param level player level
     */
    public Player(int id, int color, PlayerLevel level) {
        this.identifier = id;
        this.wins = 0;
        this.losses = 0;
        this.ties = 0;
        this.color = color;
        this.level = level;

    }

    /**
     * Custom constructor
     * @param id        player identifier
     * @param wins      player's wins
     * @param losses    player's losses
     * @param ties      player's ties
     * @param color     player color
     * @param level     player level
     */
    public Player(int id, String name, int wins, int losses, int ties, int color, PlayerLevel level) {
        this.identifier = id;
        this.name = name;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
        this.color = color;
        this.level = level;
    }
    /**
     * Return Player identifier
     * @return identifier
     * */
    public int getIdentifier() {
        return identifier;
    }
    /**
     * Set Player identifier
     * @param identifier = the new identifier that player use
     * */
    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }
    /**
     * Return Player Color
     * @return color
     * */
    public int getColor(){
        return color;
    }
    /**
     * Set Player Color
     * @param color = the new color that player use
     * */
    public void setColor(int color) {
        this.color = color;
    }
    /**
     * Return Player wins
     * @return wins
     * */
    public int getWins() {
        return wins;
    }

    /**
     * Return player name
     * */
    public String getName() {
        return name;
    }

    /**
     * Set Player wins
     * @param wins = the new wins that player had
     * */
    public void setWins(int wins) {
        this.wins = wins;
    }
    /**
     * Return Player loses
     * @return loses
     * */
    public int getLosses() {
        return losses;
    }
    /**
     * Set Player loses
     * @param losses= the new loses that player use
     * */
    public void setLosses(int losses) {
        this.losses = losses;
    }

    /**
     * Return Bot Level
     * @return level
     * */
    public PlayerLevel getLevel(){
        return level;
    }
    /**
     * Set bot level
     * @param level = contains the information the new level
     * */
    public void setLevel(PlayerLevel level) {
        this.level = level;
    }
    /**
     * Return Player ties
     * @return ties
     * */
    public int getTies() {
        return ties;
    }
    /***
     * This function set player ties
     * @param ties = new Player ties
     * */

    public void setTies(int ties) {
        this.ties = ties;
    }
}
