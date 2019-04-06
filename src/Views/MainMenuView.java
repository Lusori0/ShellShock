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

        this.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();


        singelplayer = new MyButton("KnopfSingleplayerMetallic1.png","Singleplayer", singleplayerImg);
        singelplayer.addActionListener(this);
        //singleplayerImg = new ImageIcon("res\\buttons\\KnopfSingleplayerMetallic1.png");
        //singelplayer.setIcon(singleplayerImg);
        //this.add(singelplayer);
        g.weightx = 0.5;
        g.fill = GridBagConstraints.VERTICAL;
        g.gridx = 0;
        g.insets= new Insets(100,0,0,0);
        g.gridy = 0;
        this.add(singelplayer,g);



        multiplayer = new MyButton("KnopfMultiplayerMetallic1.png","Mehrspieler",multiplayerImg);
        multiplayer.addActionListener(this);
        //multiplayerImg = new ImageIcon("");
        // multiplayer.setIcon(multiplayerImg);
      //  this.add(multiplayer);
        g.fill = GridBagConstraints.VERTICAL;
        g.weightx = 0.5f;
        g.gridx = 0;
        g.insets= new Insets(100,0,0,0);
        g.gridy = 1;
        this.add(multiplayer,g);


        einstellungen = new MyButton("","Einstellungen",einstellungenImg);
        einstellungen.addActionListener(this);
        //einstellungenImg = new ImageIcon("");
         // einstellungen.setIcon(einstellungenImg);
       // this.add(einstellungen);
        g.fill = GridBagConstraints.VERTICAL;
        g.weightx = 0.5f;
        g.gridx = 0;
        g.insets= new Insets(100,0,0,0);
        g.gridy = 2;
        this.add(einstellungen,g);



        profil = new MyButton("","Profil",profilImg);
        profil.addActionListener(this);
        //profilImg = new ImageIcon("");
        // profil.setIcon(profilImg);
        //this.add(profil);
        g.fill = GridBagConstraints.VERTICAL;
        g.weightx = 0.5f;
        g.gridx = 0;
        g.insets= new Insets(100,0,0,0);
        g.gridy = 3;
        this.add(profil, g);


        exit = new MyButton("","Exit",exitImg);
        exit.addActionListener(this);
        //exitImg = new ImageIcon("");
        // exit.setIcon(exitImg);
       // this.add(exit);
        g.fill = GridBagConstraints.VERTICAL;
        g.weightx = 0.5f;   //request any extra vertical space
        g.gridx = 0;
        g.insets= new Insets(100,0,0,0);//aligned with button 2
        g.gridy = 4;       //third row
        this.add(exit, g);



/*
        this.setLayout(new GridBagLayout());
        JPanel objekt_Panel = new JPanel();
        objekt_Panel.setBackground(c);
        objekt_Panel.add(singelplayer);
        objekt_Panel.add(multiplayer);
        objekt_Panel.add(einstellungen);
        objekt_Panel.add(profil);
        objekt_Panel.add(exit);
        GridBagConstraints objekt_GridbagConstraints = new GridBagConstraints();
        objekt_GridbagConstraints.fill = GridBagConstraints.NONE;
        objekt_GridbagConstraints.gridwidth = 1;
        objekt_GridbagConstraints.gridheight = 5;
        objekt_GridbagConstraints.gridx = 0;
        objekt_GridbagConstraints.gridy = 0;
        objekt_GridbagConstraints.weightx = 0.5;
        objekt_GridbagConstraints.weighty = 0;


        this.add(objekt_Panel,objekt_GridbagConstraints);
        */

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
