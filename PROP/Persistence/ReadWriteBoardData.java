package FONTS.Persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ReadWriteBoardData {
    String boardsDataFilePath ="DATA/boards.txt";

    public void ReadWriteBoardData(){}

    /**
     * Saves a new board if the board doesn't exists
     * @param idBoard = identifier of the board to be saved
     * @param board = information of the board to be saved
     * @return true if the new board has been saved successfully
     */
    public boolean addBoard(String idBoard, String[] board){
        boolean added = false;
        if (!boardExists(idBoard)) {
            FileWriter fw = null;
            PrintWriter pw;

            try {
                fw = new FileWriter(boardsDataFilePath, true);
                pw = new PrintWriter(fw);
                String newBoard = "";
                pw.println("board");
                pw.println(idBoard);
                for (int i = 0; i < board.length; i++) {
                    newBoard = board[i];
                    pw.println(newBoard);
                }
                fw.close();
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
     * Checks if a board with the requested id exists
     * @param boardId = identifier of the board
     * @return true if the board exists
     */
    public boolean boardExists(String boardId){
        FileReader fr = null;
        BufferedReader br = null;
        boolean exists = false;
        try{
            fr = new FileReader(boardsDataFilePath);
            br = new BufferedReader(fr);
            String line;
            boolean nextIsId = false;
            while (((line = br.readLine()) != null) && !exists){
                String[] data = line.split(" ");
                if(nextIsId){
                    if(data[0].equals(boardId)) exists = true;
                    else nextIsId = false;
                }
                else if (data[0].equals("board")) nextIsId = true;
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
     * Gets the board with the requested id
     * @param id = identifier of the board to be obtained
     * @return a list of the board information
     */
    public ArrayList<String> getBoard(String id){
        FileReader fr = null;
        BufferedReader br = null;
        boolean exists = false;
        ArrayList<String> board = new ArrayList<>();
        try{
            fr = new FileReader(boardsDataFilePath);
            br = new BufferedReader(fr);
            String line;
            boolean nextIsId = false;
            while (!exists && ((line = br.readLine()) != null)){
                String[] data = line.split(" ");
                if(nextIsId){
                    if(data[0].equals(id)){
                        exists = true;
                    }
                    else nextIsId = false;
                }
                else if (data[0].equals("board")){
                    nextIsId = true;
                }
            }
            if(exists) {
                while (((line = br.readLine()) != null) && !line.equals("board")) {
                    board.add(line);
                }
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
        return board;
    }

    /**
     * Updates the board if exists
     * @param id = identifier of the board to be obtained
     * @param board = list of the information to be restored
     * @return false if the board don't exists
     */
    public boolean updateBoard(String id, String[] board){
        boolean updated = false;
        FileReader fr = null;
        BufferedReader br;
        FileWriter fw = null;
        PrintWriter pw;
        if(boardExists(id)){
            try{
                fr = new FileReader(boardsDataFilePath);
                br = new BufferedReader(fr);
                String line;
                Queue<String> q = new LinkedList<>();
                boolean nextIsId = false;
                boolean updating = false;
                int iterator = 0;
                while((line = br.readLine()) != null){
                    String[] data = line.split(" ");
                    if(updating){
                        for (String s : board) q.offer(s);
                        while ((line = br.readLine()) != null && !line.equals("board"));
                        if(line != null && line.equals("board")) nextIsId = true;
                        updating = false;
                    }
                    else q.offer(line);
                    if(nextIsId){
                        if(data[0].equals(id)){
                            updating = true;
                        }
                        nextIsId = false;
                    }
                    else if (data[0].equals("board")) nextIsId = true;
                }
                fr.close();

                fw = new FileWriter(boardsDataFilePath);
                pw = new PrintWriter(fw);

                fw.write("");
                while(!q.isEmpty()){
                    pw.println(q.peek());
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
