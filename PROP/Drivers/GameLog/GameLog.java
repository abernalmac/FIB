package FONTS.Drivers.GameLog;

import java.util.ArrayList;
import java.util.Vector;


public class GameLog {
    static ArrayList<Game> log;
    static Ranking ranking;

    public GameLog() {
        log = new ArrayList<>();
        ranking = new Ranking();
    }

    public Game getGame(int i) {
        return log.get(i);
    }

    /***
     * Add a game to the game log
     * @param game the game to add
     */
    public void addNewGame(Game game) {
        log.add(game);
        int pos1 = isInRanking(game.getPlayerIdentifier(1));
        int pos2 = isInRanking(game.getPlayerIdentifier(2));
        if(pos1 == -1) {
            ranking.rankingLine.add(game.getPlayer(1));
            pos1 = ranking.rankingLine.size()-1;
        }
        if(pos2 == -1) {
            ranking.rankingLine.add(game.getPlayer(2));
            pos2 = ranking.rankingLine.size()-1;
        }
        if(game.getScore(1) > game.getScore(2)) {
            ranking.rankingLine.get(pos1).setWins(ranking.rankingLine.get(pos1).getWins()+1);
            ranking.rankingLine.get(pos2).setLosses(ranking.rankingLine.get(pos2).getLosses()+1);
        }
        else if(game.getScore(2) > game.getScore(1)) {
            ranking.rankingLine.get(pos2).setWins(ranking.rankingLine.get(pos2).getWins()+1);
            ranking.rankingLine.get(pos1).setLosses(ranking.rankingLine.get(pos1).getLosses()+1);
        }
        else{
            ranking.rankingLine.get(pos1).setTies(ranking.rankingLine.get(pos1).getTies()+1);
            ranking.rankingLine.get(pos2).setTies(ranking.rankingLine.get(pos2).getTies()+1);
        }

    }

    public void addGame(Game game){
        log.add(game);
        if(isInRanking(game.getPlayerIdentifier(1)) == -1) ranking.rankingLine.add(game.getPlayer(1));
        if(isInRanking(game.getPlayerIdentifier(2)) == -1) ranking.rankingLine.add(game.getPlayer(2));
    }

    /***
     * Get ranking sorted by Player ID
     * @return a sorted ranking by ID
     */
    public static Vector<String[]> getSortedByID(boolean ascending){
        ranking.sortRanking(Ranking.SortCriteria.BY_ID, ascending);
        return ranking.getRankingLine();
    }
    /***
     * Get ranking sorted by number of games won
     * @return a sorted ranking by Wins
     */
    public Vector<String[]> getSortedByWins(boolean ascending){
        ranking.sortRanking(Ranking.SortCriteria.BY_WINS, ascending);
        return ranking.getRankingLine();
    }
    /***
     * Get ranking sorted by number of games lost
     * @return a sorted ranking by Loses
     */
    public Vector<String[]> getSortedByLoses(boolean ascending){
        ranking.sortRanking(Ranking.SortCriteria.BY_LOSES, ascending);
        return ranking.getRankingLine();
    }
    /***
     * Get ranking sorted by number of games tied
     * @return a sorted ranking by Ties
     */
    public Vector<String[]> getSortedByTies(boolean ascending){
        ranking.sortRanking(Ranking.SortCriteria.BY_TIES, ascending);
        return ranking.getRankingLine();
    }

    /**
     * Checks if a player is in the array
     * @param identifier    player identifier
     * @return the position, if not found -1
     */
    public int isInRanking(int identifier){
        return ranking.isInRanking(identifier);
    }
}
