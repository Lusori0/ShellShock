package Views;

import Model.PreGameModel;
import Model.SinglePlayerModel;
import Window.MyButton;
import Window.MyWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PreGameView extends JPanel implements ActionListener{
    PreGameModel preGameModel;

    MyButton gameSettings,start,back;

    ImageIcon gameSettingsImg,startImg,backImg;

    public PreGameView (PreGameModel preGameModel)
    {
        this.preGameModel = preGameModel;

        this.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();

        this.setBackground(MyWindow.backgroundColor);

        start = new MyButton("","START",startImg);
        start.addActionListener(this);
        //Einstellen von der Anordnung
        g.weightx = 0.5;
        g.fill = GridBagConstraints.VERTICAL;
        g.gridx = 0;
        g.insets= new Insets(100,0,0,0);
        g.gridy = 0;
        //Einfügen der Buttons in Abhängigkeit der GridBag
        this.add(start,g);


        gameSettings = new MyButton("","Spieleinstellungen",gameSettingsImg);
        gameSettings.addActionListener(this);
        //Einstellen von der Anordnung
        g.weightx = 0.5;
        g.fill = GridBagConstraints.VERTICAL;
        g.gridx = 0;
        g.insets= new Insets(100,0,0,0);
        g.gridy = 1;
        //Einfügen der Buttons in Abhängigkeit der GridBag
        this.add(gameSettings,g);


        back = new MyButton ("KnopfZurückMetallic1.png","BACK", backImg);
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
