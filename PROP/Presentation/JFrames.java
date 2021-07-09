package FONTS.Presentation;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class JFrames extends JFrame implements ActionListener{
    JFrame mainFrame;
    private JMenuItem back;

    GameLoad gameloadpanel;
    Game gamepanel;
    NewGame newgamepanel;
    Settings settingspanel;
    Ranking rankingpanel;
    MainPage mainpagepanel;


    PresentationController presentation;

    public JFrames (PresentationController p){
        this.presentation = p;
        mainFrame = new JFrame();

        init();

    }

    public void init(){
        mainFrame = new JFrame();
        mainFrame.setSize(1000,1000); // fixem tamany finestra
        mainFrame.setTitle("Othello"); //titol finestra
        mainFrame.setLocationRelativeTo(null); // centrem finestra
        // mainFrame.setResizable(false);// la ventana no puede cambiar de tamaño
        //setminimumsize setmaximumsize en caso contrario
        // this.getContentPane().setBackground(Color.ORANGE); // color fons panell a sobre finestra
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE); // quan tanquem la pestanya
        mainFrame.setVisible(true);
        initButton();
        // constructora ventana grafica
    }

    public void initButton(){
        //setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        JMenuBar mb = new JMenuBar();
        back = new JMenuItem("Go Back");
        back.addActionListener(this);
        mb.add(back);
        mainFrame.setJMenuBar(mb);
        //mainFrame.add(mb);
    }

    public void showRanking() {
        mainFrame.getContentPane().removeAll();
        rankingpanel = new Ranking();
        mainFrame.getContentPane().add(rankingpanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    public void showMainPage(){
        mainFrame.getContentPane().removeAll();
        mainpagepanel = new MainPage();
        mainFrame.getContentPane().add(mainpagepanel);
        mainFrame.revalidate();
        mainFrame.repaint();

    }
    public void showSettingsPage(){
        mainFrame.getContentPane().removeAll();
        settingspanel = new Settings();
        mainFrame.getContentPane().add(settingspanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public void showNewGamePage(){
        mainFrame.getContentPane().removeAll();
        newgamepanel = new NewGame(this.presentation);
        mainFrame.getContentPane().add(newgamepanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    public void showLoadGamePage(){
        mainFrame.getContentPane().removeAll();
        gameloadpanel = new GameLoad(presentation);
        mainFrame.getContentPane().add(gameloadpanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    public void showGamePage(){
        mainFrame.getContentPane().removeAll();
        gamepanel = new Game(this.presentation, this);
        mainFrame.getContentPane().add(gamepanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == back) PresentationController.goBack();
    }
}
