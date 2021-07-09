package FONTS.Persistence;
import FONTS.Domain.DomainController;

import java.util.*;

public class PersistenceController {
    DomainController domain;
    ReadWritePlayerData playerData;
    ReadWriteGameData gameData;
    ReadWriteBoardData boardData;

    public PersistenceController(){
        playerData = new ReadWritePlayerData();
        gameData = new ReadWriteGameData();
        boardData = new ReadWriteBoardData();
    }

    public void linkDomain(DomainController domain) {
        this.domain = domain;
    }

    public boolean logIn(String username, String password){
        return playerData.signUp(username,password);
    }

    public boolean playerExists(String id){
        return playerData.playerExists(id);
    }

    public boolean gameExists(String id){
        return gameData.gameExists(id);
    }

    public boolean addPlayer(String[] player){
        return playerData.addPlayer(player);
    }

    public boolean addGame(String[] game){
        gameData.getMaxId();
        String[] gameWithId = new String[game.length + 1];
        gameWithId[0] = gameData.getMaxId();
        for (int i = 0; i < game.length; i++) gameWithId[i+1] = game[i];
        return gameData.addGame(gameWithId);
    }

    public boolean updateGame(String[] game){
        return gameData.updateGame(game);
    }

    public boolean updatePlayer(String[] player){
        return playerData.updatePlayer(player);
    }

    public boolean updateBoard(String gameId, String[] board){
        return boardData.updateBoard(gameId,board);
    }

    public boolean addBoard(String id, String[] info) {
        return boardData.addBoard(id,info);
    }

    public ArrayList<String[]> getPlayers(){
        return playerData.getPlayers();
    }

    public ArrayList<String[]> getGames(){
        return gameData.getGames();
    }

    public String [] getPlayer(String playerId) {
        return playerData.getPlayer(playerId);
    }

    public String [] getGame(String gameId) {
        return gameData.getGame(gameId);
    }

    public String [] getBoard(String gameId) {
        Vector<String> boardV = new Vector<>(boardData.getBoard(gameId));
        String[] board = boardV.toArray(new String[boardV.size()]);
        return board;
    }


}