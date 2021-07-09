package FONTS.Persistence;

import java.io.*;
import java.util.*;

public class ReadWritePlayerData {
    String playersLoginFilePath = "DATA/playersLogin.txt";
    String playersDataFilePath = "DATA/playersData.txt";
    //private static ReadWritePlayerData readWriter;
/*
    public static ReadWritePlayerData getInstance(){
        if (readWriter == null){
            readWriter = new ReadWritePlayerData();
        }
        return readWriter;
    }*/

    public ReadWritePlayerData() {
    }

    /**
     * Tries to sign up a new player with its identifier and password if the player doesn't exists otherwise checks valid credentials
     * @param playerId = identifier of the player
     * @param password = password of the player
     * @return false if the player exists and the password is wrong
     */
    public boolean signUp(String playerId, String password) {

        FileWriter fw = null;
        PrintWriter pw;
        boolean ret = false;
        try {
            int logIn = checkPlayer(playerId,password);
            if (logIn == 0) {
                fw = new FileWriter(playersLoginFilePath,true);
                pw = new PrintWriter(fw);

                pw.println(playerId + " " + password);
                fw.close();
                ret = true;
            }
            else if (logIn == 1) ret = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fw) {
                    fw.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }

        return ret;
    }


    /**
     * Checks if player info is correct
     * @return 0 if 1 if found and -1 if found but wrong password
     * */
    //Returns
    public int checkPlayer(String playerId, String password) {
        FileReader fr = null;
        BufferedReader br = null;

        String player = "";
        String pass = "";
        try {
            fr = new FileReader(playersLoginFilePath);
            br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {
                String[] words = line.split(" ");
                player = words[0];
                pass = words[1];
                if (player.equals(playerId)) {
                    if (pass.equals(password)) return 1;
                    else return -1;
                }
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }

        }
        return 0;
    }

    /**
     * Gets every saved player from file with the playerID at the first parameter "String[0]"
     * @return a list of the saved players
     */
    public ArrayList<String[]> getPlayers(){
        ArrayList<String[]> players = new ArrayList<String[]>();
        FileReader fr = null;
        BufferedReader br;

        try{
            fr = new FileReader(playersDataFilePath);
            br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null){
                String[] data = line.split(" ");
                players.add(data);
            }
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return players;
    }

    /**
     * Gets the player with the requested id
     * @param id = identifier of the player to be obtained
     * @return a list of the player information
     */
    public String[] getPlayer(String id){
        String[] player = null;
        FileReader fr = null;
        BufferedReader br;

        try{
            fr = new FileReader(playersDataFilePath);
            br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null){
                String [] words = line.split(" ");
                if(words[0].equals(id)) player = words.clone();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return player;
    }

    /**
     * Save a new player if it doesn't exists
     * @param player = data of the player to be added, first position "player[0]" must be the player identifier
     * @return true if the new player has been added, false if not
     */
    public boolean addPlayer(String[] player){
        boolean added = false;
        if(!playerExists(player[0])){
            FileWriter fw = null;
            PrintWriter pw;

            try{
                fw = new FileWriter(playersDataFilePath,true);
                pw = new PrintWriter(fw);
                String newPlayer;
                newPlayer = String.join(" ", player);
                pw.println(newPlayer);
                fw.close();
                added = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return added;
    }

    /**
     * Tells if the player exists or not
     * @param id = identifier of the player to be searched
     * @return true if exists false if not
     */
    public boolean playerExists(String id){
        FileReader fr = null;
        BufferedReader br = null;
        boolean exists = false;
        try{
            fr = new FileReader(playersDataFilePath);
            br = new BufferedReader(fr);
            String line;
            while (((line = br.readLine()) != null) && !exists){
                String[] data = line.split(" ");
                if (data[0].equals(id)) exists = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return exists;
    }

    /**
     * Updates the player if exists
     * @param player = list of the information to be restored, the first parameter "player[0]" must be the identifier
     * @return false if the player don't exists
     */
    public boolean updatePlayer(String[] player){
        boolean updated = false;
        FileReader fr = null;
        BufferedReader br;
        FileWriter fw = null;
        PrintWriter pw;
        if(playerExists(player[0])){
            try{
                fr = new FileReader(playersDataFilePath);
                br = new BufferedReader(fr);
                String line;
                Queue<String[]> q = new LinkedList<String[]>();
                while((line = br.readLine()) != null){
                    String[] words = line.split(" ");
                    if(words[0].equals(player[0])) q.add(player);
                    else q.add(words);
                }
                fr.close();

                fw = new FileWriter(playersDataFilePath);
                pw = new PrintWriter(fw);

                fw.write("");
                while(!q.isEmpty()){
                    String toCopy = String.join(" ",q.peek());
                    pw.println(toCopy);
                    q.remove();
                }

                fw.close();
                updated = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return updated;
    }
}

