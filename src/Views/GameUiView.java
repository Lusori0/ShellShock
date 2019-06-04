package Views;

import Model.GameModel;
import Model.MainMenuModel;
import Model.PreGameModel;
import Window.MyButton;
import Window.Var;
import Window.MyKeys;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameUiView extends JPanel implements ActionListener {
    ImageIcon fireImg,backImg,muteMusikImg,weapojChoosingImg;
    MyButton fire,back,muteMusik,weaponChoosing;

    GameModel gameModel;

    public  GameUiView(GameModel gameModel)
    {
        this.gameModel = gameModel;
        erzeugenOverlay();
    }


    private void erzeugenOverlay()
    {
        fire = new MyButton("KnopfFeuerMetallic1.png","Fire Button",fireImg);
        fire.addActionListener(this);
        this.add(fire);

        if(Var.music.isMuted())
        {
            muteMusik = new MyButton("Mute1.png","Press to Mute Musik",muteMusikImg);
        } else
        {
            muteMusik = new MyButton("KnopfSoundMetallic1.png","Press to Mute Musik",muteMusikImg);
        }
        muteMusik.addActionListener(this);
        this.add(muteMusik);

        weaponChoosing = new MyButton("","Press to change Weapons",weapojChoosingImg);
        weaponChoosing.addActionListener(this);
        this.add(weaponChoosing);

        back = new MyButton("KnopfKampfVerlassenMetallic1.png","Back to Pregame",backImg);
        back.addActionListener(this);
        this.add(back);

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
            Var.music.muteMusic();
            if(Var.music.isMuted())
            {
                ImageIcon mute = new ImageIcon("res/buttons/Mute1.png");
                mute.setImage(mute.getImage().getScaledInstance(muteMusik.getWidth() + 2, muteMusik.getHeight() + 2, Image.SCALE_SMOOTH));
                muteMusik.setIcon(mute);
            } else
            {
                ImageIcon mute = new ImageIcon("res/buttons/KnopfSoundMetallic1.png");
                mute.setImage(mute.getImage().getScaledInstance(muteMusik.getWidth() + 2, muteMusik.getHeight() + 2, Image.SCALE_SMOOTH));
                muteMusik.setIcon(mute);
            }

        }
        if(e.getSource() == weaponChoosing)
        {
            MyKeys.weapon = true;
        }
    }
}
