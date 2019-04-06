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

    public MainMenuView(MainMenuModel mainMenuModel) {
        this.mainMenuModel = mainMenuModel;

        singelplayer = new MyButton("Einzelspieler");
        singelplayer.addActionListener(this);
        this.add(singelplayer);


        einstellungen = new MyButton("Einstellungen");
        einstellungen.addActionListener(this);
        this.add(einstellungen);


        profil = new MyButton("Profil");
        profil.addActionListener(this);
        this.add(profil);


        exit = new MyButton("Exit");
        exit.addActionListener(this);
        this.add(exit);


        multiplayer = new MyButton("Mehrspieler");
        multiplayer.addActionListener(this);
        this.add(multiplayer);

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
