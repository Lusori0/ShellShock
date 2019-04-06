package Views;

import Model.PreGameModel;
import Model.SinglePlayerModel;
import Window.MyButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PreGameView extends JPanel implements ActionListener{
    PreGameModel preGameModel;

    MyButton gameSettings,start,back;

    ImageIcon gameSettingsImg,startImg,backImg;

    public PreGameView (PreGameModel preGameModel)
    {
        this.preGameModel = preGameModel;

        start = new MyButton("","START",startImg);
        start.addActionListener(this);
        //startImg = new ImageIcon("");
        // start.setIcon(startImg);
        this.add(start);


        gameSettings = new MyButton("","Spieleinstellungen",gameSettingsImg);
        gameSettings.addActionListener(this);
        // gameSettingsImg = new ImageIcon("");
        //gameSettings.setIcon(gameSettingsImg);
        this.add(gameSettings);


        back = new MyButton ("","BACK", backImg);
        back.addActionListener(this);
        // backImg = new ImageIcon("");
         // back.setIcon(backImg);
        this.add(back);


    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == gameSettings) {
            preGameModel.gameSettingsAction();
        }

        if (e.getSource() == start) {
            preGameModel.startAction();
        }

        if (e.getSource() == back) {
            preGameModel.backAction();
        }
    }
}
