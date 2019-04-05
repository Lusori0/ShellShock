package Views;

import Model.MainMenuModel;
import Model.SinglePlayerModel;
import Window.MyButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuView extends JPanel implements ActionListener {

    MainMenuModel mainMenuModel;

    MyButton singelplayer,multiplayer,einstellungen,profil,exit;

    public MainMenuView(MainMenuModel mainMenuModel) {
        this.mainMenuModel = mainMenuModel;

        singelplayer = new MyButton("Einzelspieler");

        einstellungen = new MyButton("Einstellungen");

        profil = new MyButton("Profil");

        exit = new MyButton("Exit");

        multiplayer = new MyButton("Mehrspieler");

        singelplayer.addActionListener(this);
        einstellungen.addActionListener(this);
        profil.addActionListener(this);
        exit.addActionListener(this);
        multiplayer.addActionListener(this);




        this.add(singelplayer);
        this.add(multiplayer);
        this.add(einstellungen);
        this.add(profil);
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
