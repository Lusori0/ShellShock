package Views;

import Model.SinglePlayerModel;
import Window.MyButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SinglePlayerView extends JPanel implements ActionListener {

    SinglePlayerModel singlePlayerModel;

    MyButton gegenKi,sandbox,back;

    ImageIcon gegenKiImg,sandboxImg,backImg;

    public SinglePlayerView(SinglePlayerModel singlePlayerModel)
    {
        this.singlePlayerModel = singlePlayerModel;


        gegenKi = new MyButton("","GegenKI", gegenKiImg);
        gegenKi.addActionListener(this);
        //gegenKiImg = new ImageIcon("");
        //gegenKi.setIcon(gegenKiImg);
        this.add(gegenKi);


        sandbox = new MyButton("","Sandbox",sandboxImg);
        sandbox.addActionListener(this);
        //sandboxImg = new ImageIcon("");
        //sandbox.setIcon(sandboxImg);
        this.add(sandbox);


        back = new MyButton("KnopfZurückMetallic1.png","Back",backImg);
        back.addActionListener(this);
        // backImg = new ImageIcon("");
        //back.setIcon(backImg);
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
