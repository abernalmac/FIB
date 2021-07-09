package FONTS.Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGame extends JPanel {
    JComboBox chosesizeBoard;
    static final String sizeBoard[] = {"Select board...","6x6","8x8","10x10","12x12"};
    JComboBox chosecustomBoard;
    static final String customBoard[] = {"Select custom board...","0","1","2"};

    Checkbox diagonal;
    Checkbox vertical;
    Checkbox horitzontal;

    JTextField nom1;
    JTextField nom2;

    JComboBox choseColor;
    static final String color[] = {"Select Disk Color","Black", "White"};
    JComboBox selectOpponent;
    static final String Opponent[] = {"Select Rol...", "Human", "Random", "Greedy", "Minimax", "Weighted Minimax"};
    JComboBox chosedepth;
    static final String depth[] = {"Select Algorithm Depth...", "3", "4", "5"};

    public JComboBox getChosesizeBoard() {
        return chosesizeBoard;
    }

    public void setChosesizeBoard(JComboBox chosesizeBoard) {
        this.chosesizeBoard = chosesizeBoard;
    }

    public JComboBox getChosecustomBoard() {
        return chosecustomBoard;
    }

    public void setChosecustomBoard(JComboBox chosecustomBoard) {
        this.chosecustomBoard = chosecustomBoard;
    }

    public Checkbox getDiagonal() {
        return diagonal;
    }

    public void setDiagonal(Checkbox diagonal) {
        this.diagonal = diagonal;
    }

    public Checkbox getVertical() {
        return vertical;
    }

    public void setVertical(Checkbox vertical) {
        this.vertical = vertical;
    }

    public Checkbox getHoritzontal() {
        return horitzontal;
    }

    public void setHoritzontal(Checkbox horitzontal) {
        this.horitzontal = horitzontal;
    }

    public JTextField getNom1() {
        return nom1;
    }

    public void setNom1(JTextField nom1) {
        this.nom1 = nom1;
    }

    public JTextField getNom2() {
        return nom2;
    }

    public void setNom2(JTextField nom2) {
        this.nom2 = nom2;
    }

    public JComboBox getChoseColor() {
        return choseColor;
    }

    public void setChoseColor(JComboBox choseColor) {
        this.choseColor = choseColor;
    }

    public JComboBox getSelectOpponent() {
        return selectOpponent;
    }

    public void setSelectOpponent(JComboBox selectOpponent) {
        this.selectOpponent = selectOpponent;
    }

    public JComboBox getChosedepth() {
        return chosedepth;
    }

    public void setChosedepth(JComboBox chosedepth) {
        this.chosedepth = chosedepth;
    }

    public JComboBox getChoseColor2() {
        return choseColor2;
    }

    public void setChoseColor2(JComboBox choseColor2) {
        this.choseColor2 = choseColor2;
    }

    public JComboBox getSelectOpponent2() {
        return selectOpponent2;
    }

    public void setSelectOpponent2(JComboBox selectOpponent2) {
        this.selectOpponent2 = selectOpponent2;
    }

    public JComboBox getChosedepth2() {
        return chosedepth2;
    }

    public void setChosedepth2(JComboBox chosedepth2) {
        this.chosedepth2 = chosedepth2;
    }

    JComboBox choseColor2;
    static final String color2[] = {"Select Disk Color","Black", "White"};
    JComboBox selectOpponent2;
    static final String Opponent2[] = {"Select Rol...", "Human", "Random", "Greedy", "Minimax", "Weighted Minimax"};
    JComboBox chosedepth2;
    static final String depth2[] = {"Select Algorithm Depth...", "3", "4", "5"};

    PresentationController presentation;

    public NewGame(PresentationController p){
        this.presentation = p;
        initComponents();
    }
    private void initComponents(){
        initPanels();
        initLabels();
        initButtons();
    }
    private void initPanels(){
        //GridLayout gridLayout = new GridLayout(4,2);
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.setBackground(Color.darkGray);
    }
    private void initLabels(){
        JLabel label = new JLabel("Game Configuration",SwingConstants.CENTER);// creació d'una etiqueta i centrem el text dins l'etiqueta
        label.setBounds(250,400, 200,150); // tamany etiqueta
        label.setForeground(Color.ORANGE); // color lletra
        label.setOpaque(false);// donem permis per pintar fons a l'etiqueta
        //label.setBackground(Color.WHITE); // donem color al fons de l'etiqueta, contrast blau blanc
        //label.setSize(100,50); // definim tamañ etiqueta
        label.setFont(new Font("arial",Font.BOLD,40)); // estil de la font
        label.setAlignmentX(CENTER_ALIGNMENT);
        this.add(label,BorderLayout.PAGE_START); // afegeixo l'etiqueta al panell
    }
    private void initButtons(){
        initBoardAndCustom();
        initRules();
        initGaps();
        JButton next = new JButton("Next");
        //next.setBounds(400,400,100,40); // l'ubiquem i donem tamany
        next.setEnabled(true); // habilita la funció del boto, si estigues a false no es podria pitjar
        next.setForeground(Color.BLACK); // color lletra boto
        next.setBackground(Color.ORANGE);

       // Color c = new Color(213, 181, 156);
       // next.setBackground(c);

        next.setFont(new Font("arial",Font.BOLD,30)); // font lletra boto
        ActionListener neext = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Boolean valid[] = {false, false, false, false, false, false, false, false, false, false, false, false, true, true, true};
                String nameError[] = {"Select board", "Select custom board", "Select any rule (or not only diagonal)", "Select disk color of Player 1", "Select disk color of Player 2",
                        "Select rol of Player 1", "Select rol of Player 2", "Write Player's 1 name", "Write Player's 2 name", "Change Player's 1 name",
                        "Change Player's 2 name", "Player's names can't be equal", "Select Player's 1 algorithm depth", "Select Player's 2 algorithm depth",
                        "A bot cannot play against itself. Select different bots."};
                if (chosesizeBoard.getSelectedIndex() != 0) valid[0] = true;
                if (chosecustomBoard.getSelectedIndex() != 0) valid[1] = true;
                if ((!(vertical.getState() == false && horitzontal.getState() == false && diagonal.getState() == false)) &&
                        !(vertical.getState() == false && horitzontal.getState() == false && diagonal.getState() == true)) valid[2] = true;
                if (choseColor.getSelectedIndex() != 0) valid[3] = true;
                if (choseColor2.getSelectedIndex() != 0) valid[4] = true;
                if (selectOpponent.getSelectedIndex() != 0) valid[5] = true;
                if (selectOpponent2.getSelectedIndex() != 0) valid[6] = true;
                if (!nom1.getText().equals("")) valid[7] = true;
                if (!nom2.getText().equals("")) valid[8] = true;
                if (!(selectOpponent.getSelectedIndex() == 1 && (nom1.getText().equals("Bot1")) || nom1.getText().equals("bot1"))) valid[9] = true;
                if (!(selectOpponent2.getSelectedIndex() == 1 && (nom2.getText().equals("Bot2")) || nom2.getText().equals("bot2"))) valid[10] = true;
                if (!nom1.getText().equals(nom2.getText())) valid[11] = true;

                if (selectOpponent.getSelectedIndex() == 4 || selectOpponent.getSelectedIndex() == 5) {
                    if (chosedepth.getSelectedIndex() == 0) valid[12] = false;
                }

                if (selectOpponent2.getSelectedIndex() == 4 || selectOpponent2.getSelectedIndex() == 5) {
                    if (chosedepth2.getSelectedIndex() == 0) valid[13] = false;
                }

                if (selectOpponent.getSelectedIndex() == 2 && selectOpponent2.getSelectedIndex() == 2) valid[14] = false;
                if (selectOpponent.getSelectedIndex() == 3 && selectOpponent2.getSelectedIndex() == 3) valid[14] = false;

                Boolean validGeneral = true;
                for (Boolean aBoolean : valid) {
                    if (!aBoolean) validGeneral = false;
                }

                if (!validGeneral) {
                    String error = "";
                    for (int i = 0; i < valid.length; i++) {
                        if (!valid[i]) {
                            error += "" + nameError[i] + "\n";
                        }
                    }
                    JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);

                } else { // tot be, fem comprovacions extres

                    Boolean valid2 = true;
                    if (selectOpponent.getSelectedIndex() == 4 && selectOpponent2.getSelectedIndex() == 4) {
                        if (chosedepth.getSelectedIndex() == chosedepth2.getSelectedIndex()) {
                            valid2 = false;
                        }
                    }
                    if (selectOpponent.getSelectedIndex() == 5 && selectOpponent2.getSelectedIndex() == 5) {
                        if (chosedepth.getSelectedIndex() == chosedepth2.getSelectedIndex()) {
                            valid2 = false;
                        }
                    }

                    if (!valid2) {
                        JOptionPane.showMessageDialog(null, "If both players have the same role, they cannot have the same level of depth.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {

                        String[] info = new String[13];

                        if (chosesizeBoard.getSelectedIndex() == 1) info[0] = "6";
                        else if (chosesizeBoard.getSelectedIndex() == 2) info[0] = "8";
                        else if (chosesizeBoard.getSelectedIndex() == 3) info[0] = "10";
                        else info[0] = "12";


                        if (chosecustomBoard.getSelectedIndex() == 1) info[1] = "1";
                        else if (chosecustomBoard.getSelectedIndex() == 2) info[1] = "2";
                        else info[1] = "3";

                        if (diagonal.getState()) info[2] = "true";
                        if (vertical.getState()) info[3] = "true";
                        if (horitzontal.getState()) info[4] = "true";

                        info[5] = nom1.getText();

                        if (choseColor.getSelectedIndex() == 1) info[6] = "1";
                        else info[6] = "2";

                        if      (selectOpponent.getSelectedIndex() == 1) info[7] = "Human";
                        else if (selectOpponent.getSelectedIndex() == 2) info[7] = "Random";
                        else if (selectOpponent.getSelectedIndex() == 3) info[7] = "Greedy";
                        else if (selectOpponent.getSelectedIndex() == 4) info[7] = "Minimax";
                        else info[7] = "Weighted Minimax";

                        info[8] = nom2.getText();

                        if (choseColor2.getSelectedIndex() == 1) info[9] = "1";
                        else info[9] = "2";

                        if      (selectOpponent2.getSelectedIndex() == 1) info[10] = "Human";
                        else if (selectOpponent2.getSelectedIndex() == 2) info[10] = "Random";
                        else if (selectOpponent2.getSelectedIndex() == 3) info[10] = "Greedy";
                        else if (selectOpponent2.getSelectedIndex() == 4) info[10] = "Minimax";
                        else info[10] = "Weighted Minimax";

                        if      (chosedepth.getSelectedIndex() == 1) info[11] = "3";
                        else if (chosedepth.getSelectedIndex() == 2) info[11] = "4";
                        else info[11] = "5";

                        if      (chosedepth.getSelectedIndex() == 1) info[12] = "3";
                        else if (chosedepth.getSelectedIndex() == 2) info[12] = "4";
                        else info[12] = "5";

                        presentation.setInfogame(info);

                        PresentationController.showGamePage("NewGamePage");
                        revalidate();
                        repaint();
                    }
                }

            }
        };
        next.addActionListener(neext);
        next.setOpaque(true);
        next.setAlignmentX(CENTER_ALIGNMENT);
        this.add(next,BorderLayout.PAGE_END);
    }

    private void initBoardAndCustom () {
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(1,2));


        chosesizeBoard = new JComboBox(sizeBoard);
        chosecustomBoard = new JComboBox(customBoard);

        panel2.add(chosesizeBoard);
        panel2.add(chosecustomBoard);


        this.add(panel2,BorderLayout.CENTER);

    }

    private void initRules() {
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(1,4));

        Label ruleslabel = new Label();
        ruleslabel.setText("Rules:");


        diagonal = new Checkbox("Diagonal");
        vertical = new Checkbox("Vertical");
        horitzontal = new Checkbox("Horitzontal");


        panel2.add(ruleslabel);
        panel2.add(diagonal);
        panel2.add(vertical);
        panel2.add(horitzontal);


        this.add(panel2,BorderLayout.CENTER);

    }
    private void initGaps(){
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(6,2));

        JLabel label = new JLabel("Player 1",SwingConstants.CENTER);// creació d'una etiqueta i centrem el text dins l'etiqueta
        label.setBounds(250,400, 200,150); // tamany etiqueta
        label.setForeground(Color.ORANGE); // color lletra
        label.setOpaque(false);// donem permis per pintar fons a l'etiqueta
        //label.setBackground(Color.WHITE); // donem color al fons de l'etiqueta, contrast blau blanc
        //label.setSize(100,50); // definim tamañ etiqueta
        label.setFont(new Font("arial",Font.BOLD,40)); // estil de la font
        label.setAlignmentX(CENTER_ALIGNMENT);

        JLabel label2 = new JLabel("Player 2",SwingConstants.CENTER);// creació d'una etiqueta i centrem el text dins l'etiqueta
        label2.setBounds(250,400, 200,150); // tamany etiqueta
        label2.setForeground(Color.ORANGE); // color lletra
        label2.setOpaque(false);// donem permis per pintar fons a l'etiqueta
        //label.setBackground(Color.WHITE); // donem color al fons de l'etiqueta, contrast blau blanc
        //label.setSize(100,50); // definim tamañ etiqueta
        label2.setFont(new Font("arial",Font.BOLD,40)); // estil de la font
        label2.setAlignmentX(CENTER_ALIGNMENT);

        choseColor = new JComboBox(color);
        selectOpponent = new JComboBox(Opponent);

        choseColor2 = new JComboBox(color2);
        selectOpponent2 = new JComboBox(Opponent2);


        JPanel panelName1 = new JPanel();
        panelName1.setLayout(new GridLayout(1,2));
        Label namelabel1 = new Label();
        namelabel1.setText("Nom:");
        nom1 = new JTextField();
        panelName1.add(namelabel1);
        panelName1.add(nom1);

        JPanel panelName2 = new JPanel();
        panelName2.setLayout(new GridLayout(1,2));
        Label namelabel2 = new Label();
        namelabel2.setText("Nom:");
        nom2 = new JTextField();
        panelName2.add(namelabel2);
        panelName2.add(nom2);


        chosedepth = new JComboBox(depth);
        chosedepth.setVisible(false);
        chosedepth2 = new JComboBox(depth2);
        chosedepth2.setVisible(false);


        panel2.add(label); // afegeixo l'etiqueta al panell
        panel2.add(label2); // afegeixo l'etiqueta al panell
        panel2.add(choseColor);
        panel2.add(choseColor2);
        panel2.add(selectOpponent);
        panel2.add(selectOpponent2);

        panel2.add(panelName1);
        panel2.add(panelName2);
        panel2.add(chosedepth);
        panel2.add(chosedepth2);

        ActionListener comboActionColor = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcioEscollida = choseColor.getSelectedIndex();

                if (opcioEscollida == 1) {
                    choseColor2.setSelectedIndex(2);
                } else if (opcioEscollida == 2) {
                    choseColor2.setSelectedIndex(1);
                }
            }
        };
        choseColor.addActionListener(comboActionColor);

        ActionListener comboActionColor2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcioEscollida = choseColor2.getSelectedIndex();

                if (opcioEscollida == 1) {
                    choseColor.setSelectedIndex(2);
                } else if (opcioEscollida == 2) {
                    choseColor.setSelectedIndex(1);
                }
            }
        };
        choseColor2.addActionListener(comboActionColor2);

        ActionListener comboActionOponent = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcioEscollida = selectOpponent.getSelectedIndex();

                if (opcioEscollida == 1) { //Human
                    nom1.setEnabled(true);
                    nom1.setText("");
                    chosedepth.setVisible(false);
                    chosedepth.setSelectedIndex(0);
                } else {
                    nom1.setEnabled(false);
                    nom1.setText("Bot1");

                    if (opcioEscollida == 4 || opcioEscollida == 5) { // minimax or weighted nosek
                        chosedepth.setVisible(true);
                    } else {
                        chosedepth.setVisible(false);
                        chosedepth.setSelectedIndex(0);
                    }
                }
            }
        };

        selectOpponent.addActionListener(comboActionOponent);

        ActionListener comboActionOponent2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcioEscollida = selectOpponent2.getSelectedIndex();

                if (opcioEscollida == 1) { //Human
                    nom2.setEnabled(true);
                    nom2.setText("");
                    chosedepth2.setVisible(false);
                    chosedepth2.setSelectedIndex(0);
                } else {
                    nom2.setEnabled(false);
                    nom2.setText("Bot2");

                    if (opcioEscollida == 4 || opcioEscollida == 5) { // minimax or weighted nosek
                        chosedepth2.setVisible(true);
                    } else {
                        chosedepth2.setSelectedIndex(0);
                        chosedepth2.setVisible(false);
                    }
                }
            }
        };

        selectOpponent2.addActionListener(comboActionOponent2);


        this.add(panel2,BorderLayout.CENTER);
    }
    private void initNames() {
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(1,4));

        Label ruleslabel = new Label();
        ruleslabel.setText("Rules:");


        Checkbox diagonal = new Checkbox("Diagonal");
        Checkbox vertical = new Checkbox("Vertical");
        Checkbox horitzontal = new Checkbox("Horitzontal");


        panel2.add(ruleslabel);
        panel2.add(diagonal);
        panel2.add(vertical);
        panel2.add(horitzontal);


        this.add(panel2,BorderLayout.CENTER);

    }

}
