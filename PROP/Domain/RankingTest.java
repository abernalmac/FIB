package FONTS.Domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class RankingTest {

    @Test
    void Ranking() {
        Player p1 = new Player(1,1,null);
        Player p2 = new Player(2,2,null);
        Player p3 = new Player(3,1,null);
        Player p4 = new Player(4,2,null);
        Game game1 = new Game();
        game1.setPlayerShort(1,1, PlayerLevel.RANDOM);
        game1.setScore(1,10);
        game1.setPlayerShort(2,2, PlayerLevel.RANDOM);
        game1.setScore(0,5);

        Game game2 = new Game();
        game2.setPlayerShort(3,1, PlayerLevel.RANDOM);
        game2.setScore(1, 11);
        game2.setPlayerShort(4,2, PlayerLevel.RANDOM);
        game2.setScore(0,6);
        Game game3 = new Game();
        game3.setPlayerShort(1,1, PlayerLevel.RANDOM);
        game3.setScore(1,10);
        game3.setPlayerShort(3,2, PlayerLevel.RANDOM);
        game3.setScore(0,5);
        GameLog gamelog = new GameLog();
        gamelog.addNewGame(game1);
        gamelog.addNewGame(game2);
        gamelog.addNewGame(game3);


        ArrayList<Player> desiredRanking = new ArrayList<>();
        desiredRanking.add(p1);
        desiredRanking.add(p2);
        desiredRanking.add(p3);
        desiredRanking.add(p4);

        Ranking r = new Ranking();
        for(int i = 0; i < r.rankingLine.size();i++){
            System.out.println("i:" + i + " " + desiredRanking.get(i).identifier+ " " + r.rankingLine.get(i).identifier);
            Assertions.assertEquals(desiredRanking.get(i).identifier,r.rankingLine.get(i).identifier);
        }
    }

    @Test
    void isInRanking() {
        Game game1 = new Game();
        game1.setPlayerShort(1,1, PlayerLevel.RANDOM);
        game1.setScore(1,10);
        game1.setPlayerShort(2,2, PlayerLevel.RANDOM);
        game1.setScore(2,5);
        Game game2 = new Game();
        game2.setPlayerShort(3,1, PlayerLevel.RANDOM);
        game2.setScore(1,11);
        game2.setPlayerShort(4,2, PlayerLevel.RANDOM);
        game2.setScore(2,6);
        GameLog gamelog = new GameLog();
        gamelog.addNewGame(game1);
        gamelog.addNewGame(game2);

        Ranking r = GameLog.ranking;
        Assertions.assertEquals(0,r.isInRanking(1));
        Assertions.assertEquals(1,r.isInRanking(2));
        Assertions.assertEquals(2,r.isInRanking(3));
        Assertions.assertEquals(3,r.isInRanking(4));
    }

    @Test
    void sortRanking() {
        Player p1 = new Player(1,1,null);
        Player p2 = new Player(2,2,null);
        Player p3 = new Player(3,1,null);
        Player p4 = new Player(4,2,null);
        Game game1 = new Game();
        game1.setPlayerShort(1,1, PlayerLevel.RANDOM);
        game1.setScore(1,10);
        game1.setPlayerShort(2,2, PlayerLevel.RANDOM);
        game1.setScore(0,5);
        Game game2 = new Game();
        game2.setPlayerShort(3,1, PlayerLevel.RANDOM);
        game2.setScore(1,11);
        game2.setPlayerShort(4,2, PlayerLevel.RANDOM);
        game2.setScore(0,6);
        GameLog gamelog = new GameLog();
        gamelog.addNewGame(game1);
        gamelog.addNewGame(game2);
        ArrayList<Player> desiredRankingByID = new ArrayList<>();
        desiredRankingByID.add(p1);
        desiredRankingByID.add(p2);
        desiredRankingByID.add(p3);
        desiredRankingByID.add(p4);


        Ranking IdRanking = new Ranking();
        IdRanking.sortRanking(Ranking.SortCriteria.BY_ID, true);
        for(int i = 0; i < IdRanking.rankingLine.size();i++) {
            Assertions.assertEquals(desiredRankingByID.get(i).identifier, IdRanking.rankingLine.get(i).identifier);
        }


        ArrayList<Player> desiredRankingByWins = new ArrayList<>();
        desiredRankingByWins.add(p2);
        desiredRankingByWins.add(p4);
        desiredRankingByWins.add(p1);
        desiredRankingByWins.add(p3);

        Ranking winnerRanking = new Ranking();
        winnerRanking.sortRanking(Ranking.SortCriteria.BY_WINS, true);
        for(int i = 0; i < winnerRanking.rankingLine.size();i++){
            Assertions.assertEquals(desiredRankingByWins.get(i).identifier,winnerRanking.rankingLine.get(i).identifier);
        }

        ArrayList<Player> desiredRankingByLoses = new ArrayList<>();
            desiredRankingByLoses.add(p1);
            desiredRankingByLoses.add(p3);
            desiredRankingByLoses.add(p2);
            desiredRankingByLoses.add(p4);
            Ranking loserRanking = new Ranking();
            loserRanking.sortRanking(Ranking.SortCriteria.BY_LOSES, true);
            for(int i = 0; i < loserRanking.rankingLine.size();i++){
                Assertions.assertEquals(desiredRankingByLoses.get(i).identifier,loserRanking.rankingLine.get(i).identifier);
            }

        ArrayList<Player> desiredRankingByTies = new ArrayList<>();
        desiredRankingByTies.add(p1);
        desiredRankingByTies.add(p2);
        desiredRankingByTies.add(p3);
        desiredRankingByTies.add(p4);
        Ranking tieRanking = new Ranking();
        tieRanking.sortRanking(Ranking.SortCriteria.BY_TIES, true);
        for(int i = 0; i < tieRanking.rankingLine.size();i++){
            Assertions.assertEquals(desiredRankingByTies.get(i).identifier,tieRanking.rankingLine.get(i).identifier);
        }
    }
}