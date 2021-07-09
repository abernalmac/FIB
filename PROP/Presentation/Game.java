package FONTS.Presentation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;

public class Game extends JPanel implements ComponentListener {
    private JButton[][] nBoxes = null;
    JPanel panelBoard;
    private int nRows = 8;
    private int nColumns= 8;
    JFrame mainFrame;
    PresentationController presentation;
    Boolean gameOver = true;
    int mode;

    public Game(PresentationController p, JFrame m){
        this.presentation = p;
        initComponents();
        this.mainFrame = m;
    }
    Timer timerRvsR;
    private void initComponents(){
        // this.setBackground("othello.jpeg");
        initPanels();
        initButtons();
        initBoard();

        //presentation.newGameFromPresentation();

        boolean p1human = presentation.p1human;
        boolean p2human = presentation.p2human;

        if (p1human && p2human) mode = 0;
        else if (p1human && !p2human) mode = 1;
        else if (!p1human && p2human) mode = 2;
        else mode = 3;

        System.out.println(mode);

        if (mode == 1) { // HUMAN VS ROBOT

        } else if (mode == 2) { //ROBOT VS HUMAN
            gameOver = presentation.makeMoveFromPresentation(1, 1);
            initBoard();
            revalidate();
            repaint();
        } else if (mode == 3) { //ROBOT VS ROBOT

            timerRvsR = new Timer (1000, new ActionListener ()
            {
                public void actionPerformed(ActionEvent e)
                {
                    makeMoveRvsR();
                }
            });
            timerRvsR.start();

        }

        //mostrar puntuacio

        this.setBackground(new Color(213, 181, 156));

    }

    /**
     * Introduce a delay
     * Used to allow the user to better identify the Bot's moves
     */
    private void delay(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initPanels(){
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.setBackground(Color.darkGray);

        panelBoard = new JPanel();
        panelBoard.setLayout(new GridLayout(presentation.height,presentation.width));
    }

    private void initButtons(){
        JButton save = new JButton("Save Game and return to Menu");
        //next.setBounds(400,400,100,40); // l'ubiquem i donem tamany
        save.setEnabled(true); // habilita la funció del boto, si estigues a false no es podria pitjar
        save.setForeground(new Color(213, 181, 156)); // color lletra boto
        save.setFont(new Font("arial",Font.BOLD,25)); // font lletra boto

        ActionListener savelistener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presentation.saveandquit();
                PresentationController.showMainPage();

            }
        };
        save.addActionListener(savelistener);
        save.setOpaque(true);
        save.setAlignmentX(CENTER_ALIGNMENT);
        this.add(save,BorderLayout.PAGE_START); // afegeixo l'etiqueta al panell
    }

    Timer timerHvsR;
    public void initBoard(){

        panelBoard.removeAll();

        int[][] board;
        board = presentation.getBoard();
        nBoxes = new JButton[presentation.height][presentation.height];
        for(int row = 0; row < presentation.height; row++){
            for(int column = 0; column < presentation.height; column++) {
                JButton box = new JButton();
                box.setEnabled(false);

                if (board[row][column] == 3) {
                    box.setEnabled(true);
                    if (!presentation.p1human && !presentation.p2human)
                        box.setEnabled(false);
                    box.setIcon(new ImageIcon("DATA/possible50.png"));

                    int finalRow = row;
                    int finalColumn = column;
                    ActionListener butonpressed = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            if (mode == 0) { //HUMAN VS HUMAN
                                makeMoveHvsH(finalRow +1, finalColumn+1 );
                            } else if (mode == 1) { //HUMAN VS ROBOT
                                makeMoveHvsR(finalRow +1, finalColumn +1);
                                timerHvsR = new Timer (2000, new ActionListener ()
                                {
                                    public void actionPerformed(ActionEvent e)
                                    {
                                        makeMoveRvsR();
                                        timerHvsR.stop();
                                    }
                                });
                                timerHvsR.start();
                            } else if (mode == 2) {        //ROBOT VS HUMAN
                                makeMoveHvsR(finalRow +1, finalColumn +1);
                                timerHvsR = new Timer (2000, new ActionListener ()
                                {
                                    public void actionPerformed(ActionEvent e)
                                    {
                                        makeMoveRvsR();
                                        timerHvsR.stop();
                                    }
                                });
                                timerHvsR.start();
                            } else { //ROBOT VS ROBOT
                                makeMoveRvsR();
                            }

                            //gameOver = presentation.makeMoveFromPresentation(rowB, columnB);

                        }
                    };
                    box.addActionListener(butonpressed);
                } else if (board[row][column] == 1) {
                    box.setIcon(new ImageIcon("DATA/black50.png"));
                    box.setDisabledIcon( box.getIcon() );
                    box.setEnabled(false);
                } else if (board[row][column] == 2) {
                    box.setIcon(new ImageIcon("DATA/white50.png"));
                    box.setDisabledIcon( box.getIcon() );
                    box.setEnabled(false);
                }

                panelBoard.add(box);

            }
        }

        BorderLayout b = new BorderLayout();

        this.add(panelBoard,BorderLayout.CENTER);

        this.validate();
        this.repaint();

    }

    public void makeMoveHvsH(int x, int y) {
        gameOver = presentation.makeMoveFromPresentation(x, y);
        gameOverOrNot();
        initBoard();
        revalidate();
        repaint();
    }
    public void makeMoveHvsR(int x, int y) {
        //timerHvsR.stop();
        gameOver = presentation.makeMoveFromPresentation(x, y);
        gameOverOrNot();
        initBoard();
        revalidate();
        repaint();


        //delay();
    }
    public void gameOverOrNot() {

        if (presentation.gameOver()) {
            timerRvsR.stop();
            System.out.println("S'HA ACABAT");
            int score1 = presentation.getScore1();
            int score2 = presentation.getScore2();
            String punctuation = "";
            if (score1 > score2) {
                punctuation += "Player1 WINS!\n";

            } else if (score1 < score2) {
                punctuation +="Player2 WINS!\n";
            }
            else {
                punctuation +="It's a TIE\n";
            }
            punctuation += "Scores: Player1: " + score1 + ". Player2: " + score2;

            JOptionPane.showMessageDialog(null, punctuation, "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
            PresentationController.showMainPage();

        }

    }

    public void makeMoveRvsR() {

        gameOver = presentation.makeMoveFromPresentation(1, 1);
        gameOverOrNot();
        initBoard();
        revalidate();
        repaint();
    }

    @Override
    public void componentResized(ComponentEvent e) {

    }

    public void componentMoved(ComponentEvent e){}
    public void componentShown(ComponentEvent e){ }
    public void componentHidden(ComponentEvent e){}
}
