package FONTS.Drivers.GameLog;

/**
 * STUB
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;

public class Ranking {
    ArrayList<Player> rankingLine;

    /**
     * Constructor
     * */
    public Ranking(){
        rankingLine = new ArrayList<>();
    }

    public Vector<String[]> getRankingLine() {
        Vector<String[]> players = new Vector<String[]>();

        return players;
    }

    /**
     * Check if in our ranking there is a player with the same identifier as the parameter
     * @param identifier the param we are searching
     * @return the position, if not found -1
     * */
    public int isInRanking(int identifier){
        return 1;
    }

    public enum SortCriteria {
        BY_ID,
        BY_WINS,
        BY_LOSES,
        BY_TIES,
    }

    /**
     * Sort ranking by the specified criteria
     * @param sortCriteria Type of sort
     * @param ascending    Whether ascending or descending order is selected
     * */
    public void sortRanking(Ranking.SortCriteria sortCriteria, boolean ascending) {

    }
}

