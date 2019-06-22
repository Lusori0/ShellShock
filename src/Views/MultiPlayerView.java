package Views;

import Model.MultiPlayerModel;
import Window.MyButton;
import Window.MyWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MultiPlayerView extends JPanel implements ActionListener {
    MultiPlayerModel multiPlayerModel;

    MyButton lokal,back;
    BufferedImage background;

    public  MultiPlayerView(MultiPlayerModel multiPlayerModel) {
        this.multiPlayerModel = multiPlayerModel;
        this.setBackground(MyWindow.backgroundColor);
        try {
            background = ImageIO.read(new File("res/gameimages/Multiplayerplayermenü.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        erzeugenOverlay();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background,0,0,MyWindow.WIDTH,MyWindow.HEIGHT,null);
    }


    private void erzeugenOverlay()
    {
        this.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();


        lokal = new MyButton("KnopfMultiplayerLokalMetallic1.png","Lokales Spiel starten");
        lokal.addActionListener(this);
        //Einstellen von der Anordnung


        g.fill = GridBagConstraints.VERTICAL; //Anordnung des Button in dem GridbagLayout--> Vertikal --> Untereinander

        g.gridx = 0;//Festlegung in welchem Grid x der Button sein soll --> 0= erster Grid

        g.insets = new Insets(MyWindow.HEIGHT/10, 0, 0, 0);// Erzeugen eines Abstandes mit dem nächsten Button

        g.gridy = 0;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

        //Einfügen der Inhalte in Abhängigkeit der GridBags
        this.add(lokal,g);


        back = new MyButton("KnopfZurückMetallic1.png","Back");
        back.addActionListener(this);
        //Einstellen von der Anordnung

        g.gridy = 1;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

        //Einfügen der Inhalte in Abhängigkeit der GridBags
        this.add(back,g);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == lokal)
        {
            multiPlayerModel.lokalAction();
        }
        if(e.getSource() == back)
        {
            multiPlayerModel.backAction();
        }
    }
}
