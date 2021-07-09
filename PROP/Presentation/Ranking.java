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

public class Ranking extends JPanel{
    JTable Ranking;
    DefaultTableModel RankingModel;
    JScrollPane pane;

    public Ranking(){
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        JTable Ranking = new JTable();
        this.Ranking = Ranking;
        RankingModel = (DefaultTableModel)Ranking.getModel();
        RankingModel.addColumn("Id");
        RankingModel.addColumn("Name");
        RankingModel.addColumn("Wins");
        RankingModel.addColumn("Loses");
        RankingModel.addColumn("Ties");
        pane = new JScrollPane(Ranking);
        initComponents();
    }

    private void initComponents(){
        initPanels();
        initLabels();
        initButtons();
        //initDefaultRanking();
        printTable("id");
    }
    private void initPanels(){
        this.setBackground(Color.DARK_GRAY); // color de fons del panell
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Desactivem diseny predeterminat o PETA !!!
        ;// afegim el panell a sobre la finestra gràfica
    } // el panel de ref encima de la ventana
    private void initLabels(){

        JLabel label = new JLabel("RANKING",SwingConstants.CENTER);// creació d'una etiqueta i centrem el text dins l'etiqueta
        label.setBounds(250,400, 200,150); // tamany etiqueta
        label.setForeground(Color.ORANGE); // color lletra
        label.setOpaque(false);// donem permis per pintar fons a l'etiqueta
        // label.setBackground(Color.WHITE); // donem color al fons de l'etiqueta, contrast blau blanc
        //label.setSize(100,50); // definim tamañ etiqueta
        label.setFont(new Font("American Typewriter",Font.BOLD,50)); // estil de la font
        label.setAlignmentX(CENTER_ALIGNMENT);
        this.add(label); // afegeixo l'etiqueta al panell
    } // gestor etiquetas
    private void initButtons(){
        JButton sort = new JButton("Sort By"); // creem boto per ordenar
        sort.setBounds(400,400,100,40); // l'ubiquem i donem tamany
        sort.setEnabled(true); // habilita la funció del boto, si estigues a false no es podria pitjar
        sort.setForeground(Color.BLACK); // color lletra boto
        sort.setFont(new Font("arial",Font.ITALIC,10)); // font lletra boto

        JButton id = new JButton("Sort by id"); // declarem + nom de boto
        JButton wins = new JButton("Sort by wins");// declarem + nom de boto
        JButton loses = new JButton("Sort by loses"); // declarem + nom boto
        JButton ties = new JButton("Sort by ties");  // declarem + nom boto
        final Boolean[] menuShown = {false};
        final Ranking r = this;
        ActionListener sortBy = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id.setBounds(400,360,100,40); // tamany i ubi de boto
                id.setEnabled(true); // activem que pugui utilitzarse
                id.setForeground(Color.BLACK); // color lletra boto
                id.setFont(new Font("arial",Font.ITALIC,10));// tipus font
                ActionListener sortID = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        printTable("id");
                        revalidate();
                        repaint();
                        // IMPRIMIM RANKING ORDENAT PER ID
                        //GameLog gameLog = null;
                        //gameLog.getSortedByID();
                    }
                }; //ordenem per id
                id.addActionListener(sortID);
                id.setAlignmentX(RIGHT_ALIGNMENT);
                id.setAlignmentY(TOP_ALIGNMENT);


                wins.setBounds(400,320,100,40);// tamany i ubi de boto
                wins.setEnabled(true);// activem que pugui utilitzarse
                wins.setForeground(Color.BLACK);// color lletra boto
                wins.setFont(new Font("arial",Font.ITALIC,10));// tipus font
                ActionListener sortWins = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        printTable("wins");
                        revalidate();
                        repaint();
                        // sort by WINS
                        //  GameLog gameLog = null;
                        //    gameLog.getSortedByWins();
                    }
                };    // ordenem per wins
                wins.addActionListener(sortWins);
                wins.setAlignmentX(RIGHT_ALIGNMENT);
                wins.setAlignmentY(TOP_ALIGNMENT);



                loses.setBounds(400,280,100,40); // tamany i ubi de boto
                loses.setEnabled(true); // activem l'us
                loses.setForeground(Color.BLACK); // color lletra boto
                loses.setFont(new Font("arial",Font.ITALIC,10)); // tipus font
                ActionListener sortLoses = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        printTable("loses");
                        revalidate();
                        repaint();
                        // ordenem per loses
                        //   GameLog gameLog = null;
                        //    gameLog.getSortedByLoses();
                    }
                }; // ordenem per loses
                loses.addActionListener(sortLoses);
                loses.setAlignmentX(RIGHT_ALIGNMENT);
                loses.setAlignmentY(TOP_ALIGNMENT);


                ties.setBounds(400,240,100,40); // tamany i ubi de boto
                ties.setEnabled(true); // activem l'us
                ties.setForeground(Color.BLACK);// color lletra boto
                ties.setFont(new Font("arial",Font.ITALIC,10));// tipus font
                ActionListener sortTies = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        printTable("ties");
                        revalidate();
                        repaint();
                        // ordenem per empats
                        //      GameLog gameLog = null;
                        //    gameLog.getSortedByTies();
                    }
                };
                ties.addActionListener(sortTies);
                ties.setAlignmentX(RIGHT_ALIGNMENT);
                ties.setAlignmentY(TOP_ALIGNMENT);

                if (menuShown[0]){
                    r.remove(id);
                    r.remove(wins);
                    r.remove(loses);
                    r.remove(ties);
                    menuShown[0] = false;
                }
                else {
                    r.add(id);
                    r.add(wins);
                    r.add(loses);
                    r.add(ties); // afegim boto al panell
                    menuShown[0] = true;
                }
                r.revalidate();
                r.repaint();
            }
        }; // despleguem 4 botons amb 4 accions associades

        sort.addActionListener(sortBy); // afegim accio
        sort.setAlignmentX(RIGHT_ALIGNMENT);
        sort.setAlignmentY(TOP_ALIGNMENT);
        this.add(sort); // l'afegim al panell
        this.revalidate();
        this.repaint();
    } // gestor de botones y acciones


    private void initDefaultRanking(){
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        JTable Ranking = new JTable();
        DefaultTableModel RankingModel= (DefaultTableModel)Ranking.getModel();
        RankingModel.addColumn("Id");
        RankingModel.addColumn("Name");
        RankingModel.addColumn("Wins");
        RankingModel.addColumn("Loses");
        RankingModel.addColumn("Ties");
        Vector<Integer> p = new Vector<Integer>();
        Integer id = 12; Integer wins = 13; Integer loses = 3; Integer ties = 4;
        p.add(id); p.add(wins); p.add(loses); p.add(ties);
        Vector<String[]> s = DomainController.getRanking("id");
        for (String[] line : s) {
            RankingModel.addRow(line);
        }

        Ranking.setBackground(Color.WHITE);
        Ranking.setOpaque(false);
        Ranking.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = Ranking.rowAtPoint(e.getPoint());
                int col = Ranking.columnAtPoint(e.getPoint());
                if((row > -1) && (col > -1)) {
                    // QUAN SELECCIONEM LINIA


                }
            }
        });
        Ranking.setModel(RankingModel);
        add(new JScrollPane(Ranking));
    }

    private void clearTable(){
        int a = RankingModel.getRowCount()-1;
        for (int i = a; i >= 0; i--) {
            RankingModel.removeRow(i);
        }
    }

    private void printTable(String type){
        clearTable();

        Vector<Integer> p = new Vector<Integer>();
        Integer id = 12; Integer wins = 13; Integer loses = 3; Integer ties = 4;
        p.add(id); p.add(wins); p.add(loses); p.add(ties);
        DomainController.initGameLog();
        Vector<String[]> s = DomainController.getRanking(type);
        for (String[] line : s) {
            RankingModel.addRow(line);
        }

        Ranking.setBackground(Color.WHITE);
        Ranking.setOpaque(false);
        Ranking.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = Ranking.rowAtPoint(e.getPoint());
                int col = Ranking.columnAtPoint(e.getPoint());
                if((row > -1) && (col > -1)) {
                    // QUAN SELECCIONEM LINIA


                }
            }
        });
        Ranking.setModel(RankingModel);
        add(pane);
    }
} // ranking por defecto al abrir la pagina

