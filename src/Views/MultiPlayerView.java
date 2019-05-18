package Views;

import Model.MultiPlayerModel;
import Window.MyButton;
import Window.MyWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MultiPlayerView extends JPanel implements ActionListener {
    MultiPlayerModel multiPlayerModel;

    ImageIcon lokalImg,lanImg,backImg;
    MyButton lokal,lan,back;

    public  MultiPlayerView(MultiPlayerModel multiPlayerModel) {
        this.multiPlayerModel = multiPlayerModel;
        this.setBackground(MyWindow.backgroundColor);
        erzeugenOverlay();
    }



    private void erzeugenOverlay()
    {
        this.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();


        lokal = new MyButton("KnopfMultiplayerLokalMetallic1.png","Lokales Spiel starten",lokalImg);
        lokal.addActionListener(this);
        //Einstellen von der Anordnung


        g.fill = GridBagConstraints.VERTICAL; //Anordnung des Button in dem GridbagLayout--> Vertikal --> Untereinander

        g.gridx = 0;//Festlegung in welchem Grid x der Button sein soll --> 0= erster Grid

        g.insets = new Insets(100, 0, 0, 0);// Erzeugen eines Abstandes mit dem nächsten Button

        g.gridy = 0;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

        //Einfügen der Inhalte in Abhängigkeit der GridBags
        this.add(lokal,g);

        lan = new MyButton("KnopfMultiplayerLANMetallic1.png","Lan Spiel starten",lanImg);
        lan.addActionListener(this);
        //Einstellen von der Anordnung

        g.gridy = 1;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

        //Einfügen der Inhalte in Abhängigkeit der GridBags
        this.add(lan,g);

        back = new MyButton("KnopfZurückMetallic1.png","",backImg);
        back.addActionListener(this);
        //Einstellen von der Anordnung

        g.gridy = 2;//Festlegung in welchem Grid y der Button sein soll --> 0= erster Grid

        //Einfügen der Inhalte in Abhängigkeit der GridBags
        this.add(back,g);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == lokal)
        {
            multiPlayerModel.lokalAction();
        }
        if (e.getSource() == lan)
        {
            multiPlayerModel.lanAction();
        }
        if(e.getSource() == back)
        {
            multiPlayerModel.backAction();
        }
    }
}
