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

    public PreGameView (PreGameModel preGameModel)
    {
        this.preGameModel = preGameModel;


        gameSettings = new MyButton("Spieleinstelluungen");
        gameSettings.addActionListener(this);
        this.add(gameSettings);


        start = new MyButton("START");
        start.addActionListener(this);
        this.add(start);

        back = new MyButton ("BACK");
        back.addActionListener(this);
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
