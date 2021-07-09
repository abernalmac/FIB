
package FONTS.Persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ReadWriteGameData {
    String gamesDataFilePath = "DATA/games.txt";
    String gameIdFilePath = "DATA/gameId.txt";

    public void ReadWritePlayerData() {

    }

    /**
     * Saves a new game if doesn't exists
     *
     * @param data = information of the game to be saved, the first parameter "data[0]" must be the identifier
     * @return true if the new game has been saved successfully
     */
    public boolean addGame(String[] data) {
        boolean added = false;
        if (!gameExists(data[0])) {
            FileWriter fw = null;
            PrintWriter pw;

            try {
                fw = new FileWriter(gamesDataFilePath, true);
                pw = new PrintWriter(fw);
                String newPlayer = "";
                newPlayer = String.join(" ", data);
                pw.println(newPlayer);
                fw.close();
                updateMaxId();
                added = true;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != fw) {
                        fw.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }

        }
        return added;
    }

    /**
     * Checks if a game with the requested id exists
     *
     * @param id = identifier of the game
     * @return true if the game exists
     */
    public boolean gameExists(String id) {
        FileReader fr = null;
        BufferedReader br = null;
        boolean exists = false;
        try {
            fr = new FileReader(gamesDataFilePath);
            br = new BufferedReader(fr);
            String line;
            while (((line = br.readLine()) != null) && !exists) {
                String[] data = line.split(" ");
                if (data.length > 0) {
                    if (data[0].equals(id)) exists = true;
                }
            }
            fr.close();
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
     * Gets the game with the requested id
     *
     * @param id = identifier of the game to be obtained
     * @return a list of the game information
     */
    public String[] getGame(String id) {
        String[] game = null;
        FileReader fr = null;
        BufferedReader br;

        try {
            fr = new FileReader(gamesDataFilePath);
            br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(" ");
                if (words[0].equals(id)) game = words.clone();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return game;
    }

    /**
     * Updates the game if exists
     *
     * @param game = list of the information to be restored, the first parameter game[0] must be the identifier
     * @return false if the board don't exists
     */
    public boolean updateGame(String[] game) {
        boolean updated = false;
        FileReader fr = null;
        BufferedReader br;
        FileWriter fw = null;
        PrintWriter pw;
        if (gameExists(game[0])) {
            try {
                fr = new FileReader(gamesDataFilePath);
                br = new BufferedReader(fr);
                String line;
                Queue<String[]> q = new LinkedList<String[]>();
                while ((line = br.readLine()) != null) {
                    String[] words = line.split(" ");
                    if (words[0].equals(game[0])) q.add(game);
                    else q.add(words);
                }
                fr.close();

                fw = new FileWriter(gamesDataFilePath);
                pw = new PrintWriter(fw);

                fw.write("");
                while (!q.isEmpty()) {
                    String toCopy = String.join(" ", q.peek());
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

    /**
     * Gets every saved game from file with the gameID at the first parameter "String[0]"
     *
     * @return a list of the saved games
     */
    public ArrayList<String[]> getGames() {
        ArrayList<String[]> games = new ArrayList<String[]>();
        FileReader fr = null;
        BufferedReader br;

        try {
            fr = new FileReader(gamesDataFilePath);
            br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(" ");
                /*
                String[] dataWithoutId = new String[data.length-1];
                for (int i = 0; i < dataWithoutId.length; i++) {
                    dataWithoutId[i] = data[i+1];
                }*/
                games.add(data);
            }
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return games;
    }

    public String getMaxId() {
        FileReader fr = null;
        BufferedReader br;
        String id = "-1";
        try {
            fr = new FileReader(gameIdFilePath);
            br = new BufferedReader(fr);
            String line = br.readLine();
            id = line;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;
    }

    private void updateMaxId() {
        FileReader fr = null;
        BufferedReader br;
        FileWriter fw = null;
        String id = "";
        try {
            fr = new FileReader(gameIdFilePath);
            br = new BufferedReader(fr);
            String line = br.readLine();
            int newId = Integer.parseInt(line) + 1;
            id = String.valueOf(newId);

            fw = new FileWriter(gamesDataFilePath);
            fw.write(id);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

