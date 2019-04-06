package Views;

import Model.MainMenuModel;
import Model.SinglePlayerModel;
import Window.MyButton;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.DefaultMenuLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Window.MyWindow;

public class MainMenuView extends JPanel implements ActionListener {

    MainMenuModel mainMenuModel;

    MyButton singelplayer,multiplayer,einstellungen,profil,exit;

    ImageIcon singleplayerImg,multiplayerImg,einstellungenImg,profilImg,exitImg;



    public MainMenuView(MainMenuModel mainMenuModel) {
        this.mainMenuModel = mainMenuModel;
        //Erstellen der Standard Backgrpundcolor
        this.setBackground(MyWindow.backgroundColor);
        // Ersellen des Loadouts
        this.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();


        singelplayer = new MyButton("KnopfSingleplayerMetallic1.png","Singleplayer", singleplayerImg);
        singelplayer.addActionListener(this);
        //Einstellen von der Anordnung
        g.weightx = 0.5;
        g.fill = GridBagConstraints.VERTICAL;
        g.gridx = 0;
        g.insets= new Insets(100,0,0,0);
        g.gridy = 0;
        //Einfügen der Buttons in Abhängigkeit der GridBag
        this.add(singelplayer,g);



        multiplayer = new MyButton("KnopfMultiplayerMetallic1.png","Mehrspieler",multiplayerImg);
        multiplayer.addActionListener(this);
        //Einstellen von der Anordnung
        g.fill = GridBagConstraints.VERTICAL;
        g.weightx = 0.5f;
        g.gridx = 0;
        g.insets= new Insets(100,0,0,0);
        g.gridy = 1;
        //Einfügen der Buttons in Abhängigkeit der GridBag
        this.add(multiplayer,g);


        einstellungen = new MyButton("","Einstellungen",einstellungenImg);
        einstellungen.addActionListener(this);
        //Einstellen von der Anordnung
        g.fill = GridBagConstraints.VERTICAL;
        g.weightx = 0.5f;
        g.gridx = 0;
        g.insets= new Insets(100,0,0,0);
        g.gridy = 2;
        //Einfügen der Buttons in Abhängigkeit der GridBag
        this.add(einstellungen,g);



        profil = new MyButton("","Profil",profilImg);
        profil.addActionListener(this);
        //Einstellen von der Anordnung
        g.fill = GridBagConstraints.VERTICAL;
        g.weightx = 0.5f;
        g.gridx = 0;
        g.insets= new Insets(100,0,0,0);
        g.gridy = 3;
        //Einfügen der Buttons in Abhängigkeit der GridBag
        this.add(profil, g);


        exit = new MyButton("","Exit",exitImg);
        exit.addActionListener(this);
        //Einstellen von der Anordnung
        g.fill = GridBagConstraints.VERTICAL;
        g.weightx = 0.5f;   //request any extra vertical space
        g.gridx = 0;
        g.insets= new Insets(100,0,0,0);//aligned with button 2
        g.gridy = 4;       //third row
        //Einfügen der Buttons in Abhängigkeit der GridBag
        this.add(exit, g);





    }




    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == singelplayer){
            mainMenuModel.singlePlayerAction();
        }
        if(e.getSource() == multiplayer){
            mainMenuModel.multiplayerAction();
        }
        if(e.getSource() == einstellungen){
            mainMenuModel.einstellungenAction();
        }
        if(e.getSource() == profil){
            mainMenuModel.profilAction();
        }
        if(e.getSource() == exit){
            mainMenuModel.exitAction();
        }
    }
}
