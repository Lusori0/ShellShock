package Views;

import Model.SinglePlayerModel;
import Window.MyButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SinglePlayerView extends JPanel implements ActionListener {

    SinglePlayerModel singlePlayerModel;

    MyButton gegenKi,sandbox,back;

    public SinglePlayerView(SinglePlayerModel singlePlayerModel)
    {
        this.singlePlayerModel = singlePlayerModel;

        gegenKi = new MyButton("GegenKI");

        sandbox = new MyButton("Sandbox");

        back = new MyButton("Back");


        gegenKi.addActionListener(this);
        sandbox.addActionListener(this);
        back.addActionListener(this);

        this.add(gegenKi);
        this.add(sandbox);
        this.add(back);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == gegenKi)
        {
            singlePlayerModel.gegenKiAction();
        }

        if (e.getSource() == sandbox)
        {
            singlePlayerModel.sandboxAction();
        }

        if (e.getSource() == back)
        {
            singlePlayerModel.backAction();
        }
    }

}
