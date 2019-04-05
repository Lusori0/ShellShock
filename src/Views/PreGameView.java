package Views;

import Model.PreGameModel;
import Model.SinglePlayerModel;
import Window.MyButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PreGameView extends JFrame implements ActionListener{
    PreGameModel preGameModel;

    MyButton gameSettings,start,back;

    public PreGameView (SinglePlayerModel singlePlayerModel)
    {
        this.preGameModel = singlePlayerModel;

        gameSettings = new MyButton("Spieleinstelluungen");
        start = new MyButton("START");
        back = new MyButton ("BACK");

        this.add(gameSettings);
        this.add(start);
        this.add(back);



    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == gameSettings)
        {preGameModel.gameSettingsAction();}

        if (e.getSource() == start)
        {preGameModel.startAction();}

        if (e.getSource() == back)
        {preGameModel.backAchtion();}
    }
}
