package Views;

import Model.SinglePlayerModel;
import Window.MyButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SinglePlayerView extends JPanel implements ActionListener {
    SinglePlayerModel singlePlayerModel;

    MyButton gameSettings,start,back;

    public SinglePlayerView (SinglePlayerModel singlePlayerModel)
    {
        this.singlePlayerModel = singlePlayerModel;

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
      {}

      if (e.getSource() == start)
      {}

      if (e.getSource() == back)
      {}
    }
}
