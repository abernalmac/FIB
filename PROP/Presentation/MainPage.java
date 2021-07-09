package FONTS.Presentation;

import javax.print.DocFlavor;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class MainPage extends JPanel{
    //BackGround backGround = new BackGround();
    @Override
    public void paint(Graphics g){
        Image image = new ImageIcon(getClass().getResource("/DATA/othello.jpeg")).getImage();
        g.drawImage(image,0,0,getWidth(),getHeight(),this);
        setOpaque(false);
        super.paint(g);
    }
    public MainPage(){
        initComponents();
    }
    private void initComponents(){
        // this.setBackground("othello.jpeg");
        initPanels();
        initLabels();
        this.setBackground(Color.BLACK);
        // this.add(backGround);
        initButtons();

    }
    private void initPanels(){
        //this.setBackground("othello.jpeg");
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


    }
    private void initLabels(){
        JLabel label = new JLabel("OTHELLO",SwingConstants.CENTER);// creació d'una etiqueta i centrem el text dins l'etiqueta
        label.setBounds(250,400, 200,150); // tamany etiqueta
        label.setForeground(Color.BLACK); // color lletra
        label.setOpaque(false);// donem permis per pintar fons a l'etiqueta
        //label.setBackground(Color.WHITE); // donem color al fons de l'etiqueta, contrast blau blanc
        //label.setSize(100,50); // definim tamañ etiqueta
        label.setFont(new Font("American Typewriter",Font.BOLD,60)); // estil de la font
        label.setAlignmentX(CENTER_ALIGNMENT);
        this.add(label,BorderLayout.LINE_START); // afegeixo l'etiqueta al panell

        JLabel label2 = new JLabel("               ",SwingConstants.CENTER);// creació d'una etiqueta i centrem el text dins l'etiqueta
        label2.setBounds(250,400, 200,450); // tamany etiqueta
        label2.setOpaque(false);// donem permis per pintar fons a l'etiqueta
        label2.setSize(200,200);
        label2.setAlignmentX(CENTER_ALIGNMENT);
        this.add(label2,BorderLayout.LINE_START); // afegeixo l'etiqueta al panell
        JLabel label3 = new JLabel("               ",SwingConstants.CENTER);// creació d'una etiqueta i centrem el text dins l'etiqueta
        label3.setBounds(250,400, 200,450); // tamany etiqueta
        label3.setOpaque(false);// donem permis per pintar fons a l'etiqueta
        label3.setSize(200,200);
        label3.setAlignmentX(CENTER_ALIGNMENT);
        this.add(label3,BorderLayout.LINE_START); // afegeixo l'etiqueta al panell
        JLabel label4 = new JLabel("               ",SwingConstants.CENTER);// creació d'una etiqueta i centrem el text dins l'etiqueta
        label4.setBounds(250,400, 200,450); // tamany etiqueta
        label4.setOpaque(false);// donem permis per pintar fons a l'etiqueta
        label4.setSize(200,200);
        label4.setAlignmentX(CENTER_ALIGNMENT);
        this.add(label4,BorderLayout.LINE_START); // afegeixo l'etiqueta al panell
        JLabel label5 = new JLabel("               ",SwingConstants.CENTER);// creació d'una etiqueta i centrem el text dins l'etiqueta
        label5.setBounds(250,400, 200,450); // tamany etiqueta
        label5.setOpaque(false);// donem permis per pintar fons a l'etiqueta
        label5.setSize(200,200);
        label5.setAlignmentX(CENTER_ALIGNMENT);
        this.add(label5,BorderLayout.LINE_START); // afegeixo l'etiqueta al panell

    }

    private void initButtons(){
        JButton newGame = new JButton("New Game"); // creem boto per Crear nou joc
        newGame.setBounds(400,400,100,40); // l'ubiquem i donem tamany
        newGame.setEnabled(true); // habilita la funció del boto, si estigues a false no es podria pitjar
        newGame.setForeground(Color.BLACK); // color lletra boto
        newGame.setFont(new Font("American Typewriter",Font.BOLD,30)); // font lletra boto
        ActionListener newGames = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PresentationController.showNewGamePage();
            }
        };
        newGame.addActionListener(newGames);
        newGame.setAlignmentX(CENTER_ALIGNMENT);
        newGame.setAlignmentY(CENTER_ALIGNMENT);
        this.add(newGame,BorderLayout.PAGE_END);


        JButton loadGame = new JButton("Load Game"); // creem boto per cargar  joc
        loadGame.setBounds(400,300,100,40); // l'ubiquem i donem tamany
        loadGame.setEnabled(true); // habilita la funció del boto, si estigues a false no es podria pitjar
        loadGame.setForeground(Color.BLACK); // color lletra boto
        loadGame.setFont(new Font("American Typewriter",Font.BOLD,30)); // font lletra boto
        ActionListener LoadGames = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PresentationController.showLoadGamePage();
            }
        };
        loadGame.addActionListener(LoadGames);
        loadGame.setAlignmentX(CENTER_ALIGNMENT);
        loadGame.setAlignmentY(CENTER_ALIGNMENT);
        this.add(loadGame,BorderLayout.PAGE_END);

        JButton showRanking = new JButton("Show Ranking"); // creem boto per settings
        showRanking.setBounds(400, 100,100,40); // l'ubiquem i donem tamany
        showRanking.setEnabled(true); // habilita la funció del boto, si estigues a false no es podria pitjar
        showRanking.setForeground(Color.BLACK); // color lletra boto
        showRanking.setFont(new Font("American Typewriter",Font.BOLD,30)); // font lletra boto
        ActionListener showsRanking = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PresentationController.showRankingPage();
            }
        };
        showRanking.addActionListener(showsRanking);
        showRanking.setAlignmentX(CENTER_ALIGNMENT);
        showRanking.setAlignmentY(CENTER_ALIGNMENT);
        this.add(showRanking,BorderLayout.PAGE_END);

        JButton settings = new JButton("Settings"); // creem boto per settings
        settings.setBounds(400,200,100,40); // l'ubiquem i donem tamany
        settings.setEnabled(true); // habilita la funció del boto, si estigues a false no es podria pitjar
        settings.setForeground(Color.BLACK); // color lletra boto
        settings.setFont(new Font("American Typewriter",Font.BOLD,30)); // font lletra boto
        ActionListener Settings = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PresentationController.showSettingsPage();
            }
        };
        settings.addActionListener(Settings);
        settings.setAlignmentX(CENTER_ALIGNMENT);
        settings.setAlignmentY(CENTER_ALIGNMENT);
        this.add(settings,BorderLayout.PAGE_END);


    }
}
   /* class BackGround extends JPanel{
        @Override
        public void paint(Graphics g){
            Image image = new ImageIcon(getClass().getResource("/UI/Images/othello.jpeg")).getImage();
            g.drawImage(image,0,0,getWidth(),getHeight(),this);
            setOpaque(false);
            super.paint(g);
        }*/



