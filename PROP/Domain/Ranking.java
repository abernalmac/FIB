package FONTS.Domain;

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

        for (Player player : rankingLine){
            String[] p = new String[5];
            p[0] = String.valueOf(player.getIdentifier());
            p[1] = player.getName();
            p[2] = String.valueOf(player.getWins());
            p[3] = String.valueOf(player.getLosses());
            p[4] = String.valueOf(player.getTies());
            players.add(p);
        }
        return players;
    }

    /**
     * Check if in our ranking there is a player with the same identifier as the parameter
     * @param identifier the param we are searching
     * @return the position, if not found -1
     * */
    public int isInRanking(int identifier){
        assert(rankingLine!=null);
        for (int i = 0; i < rankingLine.size(); i++) {
            if (rankingLine.get(i).getIdentifier() == identifier) return i;
        }
        return -1;
    }

    public enum SortCriteria {
        BY_ID,
        BY_WINS,
        BY_LOSES,
        BY_TIES,
    }

    Comparator<Player> compareById = Comparator.comparingInt(Player::getIdentifier);

    Comparator<Player> compareByWins = Comparator.comparingInt(Player::getWins);

    Comparator<Player> compareByLosses = Comparator.comparingInt(Player::getLosses);

    Comparator<Player> compareByTies = Comparator.comparingInt(Player::getTies);

    Comparator<Player> compareByIdReversed = Comparator.comparingInt(Player::getIdentifier).reversed();

    Comparator<Player> compareByWinsReversed = Comparator.comparingInt(Player::getWins).reversed();

    Comparator<Player> compareByLossesReversed = Comparator.comparingInt(Player::getLosses).reversed();

    Comparator<Player> compareByTiesReversed = Comparator.comparingInt(Player::getTies).reversed();

    /**
     * Sort ranking by the specified criteria
     * @param sortCriteria Type of sort
     * @param ascending    Whether ascending or descending order is selected
     * */
    public void sortRanking(Ranking.SortCriteria sortCriteria, boolean ascending) {
        if (ascending) switch (sortCriteria) {
            case BY_ID -> this.rankingLine.sort(compareById);
            case BY_WINS -> this.rankingLine.sort(compareByWins);
            case BY_LOSES -> this.rankingLine.sort(compareByLosses);
            case BY_TIES -> this.rankingLine.sort(compareByTies);
        }
        else switch (sortCriteria) {
            case BY_ID -> this.rankingLine.sort(compareByIdReversed);
            case BY_WINS -> this.rankingLine.sort(compareByWinsReversed);
            case BY_LOSES -> this.rankingLine.sort(compareByLossesReversed);
            case BY_TIES -> this.rankingLine.sort(compareByTiesReversed);
        }
    }
}

