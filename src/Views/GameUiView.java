package Views;

import Model.GameModel;
import Model.MainMenuModel;
import Model.PreGameModel;
import Window.MyButton;
import Window.Var;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameUiView extends JPanel implements ActionListener {
    ImageIcon fireImg,backImg,muteMusikImg;
    MyButton fire,back,muteMusik;

    GameModel gameModel;

    public  GameUiView(GameModel gameModel)
    {
        this.gameModel = gameModel;
        erzeugenOverlay();
    }


    private void erzeugenOverlay()
    {
        fire = new MyButton("","Fire Button",fireImg);
        fire.addActionListener(this);
        this.add(fire);

        back = new MyButton("KnopfZurückMetallic1.png","Back to Pregame",backImg);
        back.addActionListener(this);
        this.add(back);

        muteMusik = new MyButton("KnopfSoundMetallic1.png","Press to Mute Musik",muteMusikImg);
        muteMusik.addActionListener(this);
        this.add(muteMusik);

    }






    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() == fire))
        {
            gameModel.feuerButtonAction();
        }
        if ((e.getSource() == back))
        {
            new MainMenuModel();
        }
        if ((e.getSource() == muteMusik))
        {
            System.out.println("Sollte was passiert sein!");
            Var.music.setMuted(true);
        }
    }
}
