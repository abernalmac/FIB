package FONTS.Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings extends JPanel {

    public Settings(){
        init();
    }
    public void init(){
        initPanel();
        initLabel();
        initButton();
    }
    public void initPanel(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.DARK_GRAY);
    }
    public void initLabel(){
        JLabel label = new JLabel("SETTINGS",SwingConstants.CENTER);// creació d'una etiqueta i centrem el text dins l'etiqueta
        label.setBounds(250,400, 200,150); // tamany etiqueta
        label.setForeground(Color.ORANGE); // color lletra
        label.setOpaque(false);// donem permis per pintar fons a l'etiqueta
        //label.setBackground(Color.WHITE); // donem color al fons de l'etiqueta, contrast blau blanc
        //label.setSize(100,50); // definim tamañ etiqueta
        label.setFont(new Font("American Typewriter",Font.BOLD,60)); // estil de la font
        label.setAlignmentX(CENTER_ALIGNMENT);
        this.add(label,BorderLayout.LINE_START); // afegeixo l'etiqueta al panell
    }
    public void initButton(){
        JButton volume = new JButton("Volume off    "); // creem boto per Crear nou joc
        //volume.setBounds(400,400,100,40); // l'ubiquem i donem tamany
        volume.setEnabled(true); // habilita la funció del boto, si estigues a false no es podria pitjar
        volume.setForeground(Color.BLACK); // color lletra boto
        volume.setFont(new Font("American Typewriter",Font.BOLD,30)); // font lletra boto
        ActionListener volumes = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // desactivar so i canviar el nom del boto a volume on
            }
        };
        volume.addActionListener(volumes);
        volume.setAlignmentX(CENTER_ALIGNMENT);
        volume.setAlignmentY(CENTER_ALIGNMENT);
        this.add(volume,BorderLayout.CENTER);


        JButton sound = new JButton("Sound effects off"); // creem boto per Crear nou joc
        volume.setBounds(400,400,100,40); // l'ubiquem i donem tamany
        volume.setEnabled(true); // habilita la funció del boto, si estigues a false no es podria pitjar
        volume.setForeground(Color.BLACK); // color lletra boto
        volume.setFont(new Font("American Typewriter",Font.BOLD,30)); // font lletra boto
        ActionListener sounds = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // desactivar sorolls al moure fitzes i canviar el nom del boto a sound effects off
            }
        };
        sound.addActionListener(sounds);
        sound.setAlignmentX(CENTER_ALIGNMENT);
        sound.setAlignmentY(CENTER_ALIGNMENT);
        this.add(sound,BorderLayout.CENTER);


        JButton changeDiskColor = new JButton("Change disk color"); // creem boto per Crear nou joc
        volume.setBounds(400,400,100,40); // l'ubiquem i donem tamany
        volume.setEnabled(true); // habilita la funció del boto, si estigues a false no es podria pitjar
        volume.setForeground(Color.BLACK); // color lletra boto
        volume.setFont(new Font("American Typewriter",Font.BOLD,30)); // font lletra boto
        ActionListener changesDiskColor = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // mostra paleta de colors i canvia els dos colors per defecte del disk
            }
        };
        changeDiskColor.addActionListener(changesDiskColor);
        changeDiskColor.setAlignmentX(CENTER_ALIGNMENT);
        changeDiskColor.setAlignmentY(CENTER_ALIGNMENT);
        this.add(changeDiskColor,BorderLayout.CENTER);
    }


}
