package Views;

import Model.MainMenuModel;
import Model.SinglePlayerModel;
import Window.MyButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuView extends JPanel implements ActionListener {

    MainMenuModel mainMenuModel;

    MyButton singelplayer,multiplayer;

    public MainMenuView(MainMenuModel mainMenuModel) {
        this.mainMenuModel = mainMenuModel;

        singelplayer = new MyButton("Einzelspieler");

        singelplayer.addActionListener(this);




        multiplayer = new MyButton("Mehrspieler");

        this.add(singelplayer);
        this.add(multiplayer);
    }

    public void multiplayerAction(){

    }

    public void singelPlayerAction(){
        new SinglePlayerModel();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == singelplayer){
            singelPlayerAction();
        }
        if(e.getSource() == multiplayer){
            multiplayerAction();
        }
    }
}
