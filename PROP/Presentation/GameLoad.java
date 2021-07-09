package FONTS.Presentation;

import FONTS.Domain.DomainController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.Flow;

public class GameLoad extends JPanel {
    JTable games;
    DefaultTableModel gamesModel;
    JScrollPane pane;
    JTextField toLoad;
    JLabel l;
    int maxId;
    String idSelected;

    PresentationController presentation;

    public GameLoad(PresentationController p){
        this.presentation = p;
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        JTable Ranking = new JTable();
        this.games = Ranking;
        gamesModel = (DefaultTableModel)Ranking.getModel();
        gamesModel.addColumn("Identifier");
        gamesModel.addColumn("Player 1");
        gamesModel.addColumn("Player 2");
        gamesModel.addColumn("Score 1");
        gamesModel.addColumn("Score 2");
        pane = new JScrollPane(Ranking);
        initComponents();
    }
    private void initComponents(){
        initPanels();
        initLabels();
        printTable();
        initButtons();

    }
    private void initPanels(){
        this.setBackground(Color.darkGray);
    }
    private void initLabels(){
        JLabel label = new JLabel("Load Game",SwingConstants.CENTER);// creació d'una etiqueta i centrem el text dins l'etiqueta
        label.setBounds(250,400, 200,150); // tamany etiqueta
        label.setForeground(Color.ORANGE); // color lletra
        label.setOpaque(false);// donem permis per pintar fons a l'etiqueta
        // label.setBackground(Color.WHITE); // donem color al fons de l'etiqueta, contrast blau blanc
        //label.setSize(100,50); // definim tamañ etiqueta
        label.setFont(new Font("American Typewriter",Font.BOLD,50)); // estil de la font
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setAlignmentY(CENTER_ALIGNMENT);
        this.add(label); // afegeixo l'etiqueta al panell
    }
    private void initButtons(){
        JButton loadGame = new JButton("Load Game"); // creem boto per cargar  joc
        loadGame.setBounds(400,300,100,40); // l'ubiquem i donem tamany
        loadGame.setEnabled(true); // habilita la funció del boto, si estigues a false no es podria pitjar
        loadGame.setForeground(Color.BLACK); // color lletra boto
        loadGame.setFont(new Font("American Typewriter",Font.BOLD,30)); // font lletra boto
        final JPanel p = this;
        ActionListener LoadGames = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int text = Integer.parseInt(idSelected);
                if(text > 0 && text <= maxId) presentation.loadGame(idSelected);
                else {
                    l.setVisible(true);
                }

                PresentationController.showGamePage("NewGamePage");
                revalidate();
                repaint();
            }
        };
        loadGame.addActionListener(LoadGames);
        loadGame.setAlignmentX(CENTER_ALIGNMENT);
        loadGame.setAlignmentY(CENTER_ALIGNMENT);
        this.add(loadGame,BorderLayout.PAGE_END);

        l = new JLabel("Not a valid id",SwingConstants.LEFT);
        l.setForeground(Color.WHITE);
        l.setVisible(false);

        Label namelabel1 = new Label();
        namelabel1.setForeground(Color.WHITE);
        namelabel1.setFont(new Font("American Typewriter",Font.BOLD,30));
        this.add(namelabel1,BorderLayout.PAGE_END);
        this.add(l,BorderLayout.PAGE_END);
    }


    private void clearTable(){
        int a = gamesModel.getRowCount()-1;
        for (int i = a; i >= 0; i--) {
            gamesModel.removeRow(i);
        }
    }

    private void printTable(){
        clearTable();

        ArrayList<String[]> sl = DomainController.getSavedGames();
        Vector<String[]> s = new Vector<>(sl);
        maxId = s.size();
        int i = 1;
        for (String[] line : s) {
            String [] row ={Integer.toString(i),line[2],line[3],line[4],line[5]};
            gamesModel.addRow(row);
            ++i;
        }

        games.setBackground(Color.WHITE);
        games.setOpaque(false);
        games.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = games.rowAtPoint(e.getPoint());
                int col = games.columnAtPoint(e.getPoint());
                if((row > -1) && (col > -1)) {
                    // QUAN SELECCIONEM LINIA
                    idSelected = games.getModel().getValueAt(row, 0).toString();
                    //System.out.println(idSelected);
                }
            }
        });
        games.setModel(gamesModel);
        add(pane);
    }
}

