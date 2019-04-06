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

public class MainMenuView extends JPanel implements ActionListener {

    MainMenuModel mainMenuModel;

    MyButton singelplayer,multiplayer,einstellungen,profil,exit;

    ImageIcon singleplayerImg,multiplayerImg,einstellungenImg,profilImg,exitImg;

    ImageIcon img;

    JLabel bImg;

    public MainMenuView(MainMenuModel mainMenuModel) {
        this.mainMenuModel = mainMenuModel;
        Color c = new Color(30,30,30);
        this.setBackground(c);

        singelplayer = new MyButton("KnopfSingleplayerMetallic1.png","Singleplayer", singleplayerImg);
        singelplayer.addActionListener(this);
        //singleplayerImg = new ImageIcon("res\\buttons\\KnopfSingleplayerMetallic1.png");
        //singelplayer.setIcon(singleplayerImg);

        //this.add(singelplayer);



        multiplayer = new MyButton("KnopfMultiplayerMetallic1.png","Mehrspieler",multiplayerImg);
        multiplayer.addActionListener(this);
        //multiplayerImg = new ImageIcon("");
        // multiplayer.setIcon(multiplayerImg);

      //  this.add(multiplayer);


        einstellungen = new MyButton("","Einstellungen",einstellungenImg);
        einstellungen.addActionListener(this);
        //einstellungenImg = new ImageIcon("");
         // einstellungen.setIcon(einstellungenImg);

       // this.add(einstellungen);


        profil = new MyButton("","Profil",profilImg);
        profil.addActionListener(this);
        //profilImg = new ImageIcon("");
        // profil.setIcon(profilImg);

        //this.add(profil);


        exit = new MyButton("","Exit",exitImg);
        exit.addActionListener(this);
        //exitImg = new ImageIcon("");
        // exit.setIcon(exitImg);

       // this.add(exit);

        this.setLayout(new GridBagLayout());
        JPanel objekt_Panel = new JPanel();
        objekt_Panel.setBackground(c);
        objekt_Panel.add(singelplayer);
        objekt_Panel.add(multiplayer);
        objekt_Panel.add(einstellungen);
        objekt_Panel.add(profil);
        objekt_Panel.add(exit);
        GridBagConstraints objekt_GridbagConstraints = new GridBagConstraints();
        objekt_GridbagConstraints.fill = GridBagConstraints.BOTH;
        objekt_GridbagConstraints.gridx = 0;
        objekt_GridbagConstraints.gridy = 0;
        objekt_GridbagConstraints.weightx = 0;
        objekt_GridbagConstraints.weighty = 1;



        this.add(objekt_Panel,objekt_GridbagConstraints);
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
