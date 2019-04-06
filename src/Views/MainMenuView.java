package Views;

import Model.MainMenuModel;
import Model.SinglePlayerModel;
import Window.MyButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuView extends JPanel implements ActionListener {

    MainMenuModel mainMenuModel;

    MyButton singelplayer,multiplayer,einstellungen,profil,exit;

    ImageIcon singleplayerImg,multiplayerImg,einstellungenImg,profilImg,exitImg;

    public MainMenuView(MainMenuModel mainMenuModel) {
        this.mainMenuModel = mainMenuModel;

        singelplayer = new MyButton("KnopfSingleplayerMetallic1.png","Singleplayer", singleplayerImg);
        singelplayer.addActionListener(this);
        //singleplayerImg = new ImageIcon("res\\buttons\\KnopfSingleplayerMetallic1.png");
        //singelplayer.setIcon(singleplayerImg);
        this.add(singelplayer);


        multiplayer = new MyButton("KnopfMultiplayerMetallic1.png","Mehrspieler",multiplayerImg);
        multiplayer.addActionListener(this);
        //multiplayerImg = new ImageIcon("");
        // multiplayer.setIcon(multiplayerImg);
        this.add(multiplayer);


        einstellungen = new MyButton("","Einstellungen",einstellungenImg);
        einstellungen.addActionListener(this);
        //einstellungenImg = new ImageIcon("");
         // einstellungen.setIcon(einstellungenImg);
        this.add(einstellungen);


        profil = new MyButton("","Profil",profilImg);
        profil.addActionListener(this);
        //profilImg = new ImageIcon("");
        // profil.setIcon(profilImg);
        this.add(profil);


        exit = new MyButton("","Exit",exitImg);
        exit.addActionListener(this);
        //exitImg = new ImageIcon("");
        // exit.setIcon(exitImg);
        this.add(exit);


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
