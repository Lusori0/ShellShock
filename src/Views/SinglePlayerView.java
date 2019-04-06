package Views;

import Model.SinglePlayerModel;
import Window.MyButton;
import Window.MyWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SinglePlayerView extends JPanel implements ActionListener {

    SinglePlayerModel singlePlayerModel;

    MyButton gegenKi,sandbox,back;

    ImageIcon gegenKiImg,sandboxImg,backImg;

    public SinglePlayerView(SinglePlayerModel singlePlayerModel)
    {
        this.singlePlayerModel = singlePlayerModel;

        // Ersellen des Loadouts

        this.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();

        this.setBackground(MyWindow.backgroundColor);

        gegenKi = new MyButton("","GegenKI", gegenKiImg);
        gegenKi.addActionListener(this);
        //Einstellen von der Anordnung
        g.weightx = 0.5;
        g.fill = GridBagConstraints.VERTICAL;
        g.gridx = 0;
        g.insets= new Insets(100,0,0,0);
        g.gridy = 0;
        //Einfügen der Buttons in Abhängigkeit der GridBag
        this.add(gegenKi,g);




        sandbox = new MyButton("","Sandbox",sandboxImg);
        sandbox.addActionListener(this);

        //Einstellen von der Anordnung
        g.weightx = 0.5;
        g.fill = GridBagConstraints.VERTICAL;
        g.gridx = 0;
        g.insets= new Insets(100,0,0,0);
        g.gridy = 1;
        //Einfügen der Buttons in Abhängigkeit der GridBag
        this.add(sandbox,g);


        back = new MyButton("KnopfZurückMetallic1.png","Back",backImg);
        back.addActionListener(this);
        //Einstellen von der Anordnung
        g.weightx = 0.5;
        g.fill = GridBagConstraints.VERTICAL;
        g.gridx = 0;
        g.insets= new Insets(100,0,0,0);
        g.gridy = 2;
        //Einfügen der Buttons in Abhängigkeit der GridBag
        this.add(back,g);


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
