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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class GameUiView extends JPanel implements ActionListener {
    ImageIcon fireImg,backImg,muteMusikImg,weapojChoosingImg;
    MyButton fire,back,muteMusik,weaponChoosing,back_end_game;

    GameModel gameModel;

    public  GameUiView(GameModel gameModel)
    {
        this.gameModel = gameModel;

    }


    public void erzeugenOverlay()
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

    public void erzeugenOverlay_End()
    {
        back_end_game = new MyButton("KnopfKampfVerlassenMetallic1.png","Back to Pregame",backImg);
        back_end_game.addActionListener(this);
        this.add(back_end_game);
        this.setVisible(true);

    }






    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() == fire))
        {
            gameModel.feuerButtonAction();
        }
        if ((e.getSource() == back))
        {
            gameModel.getLastLocalHuman().getPanzer().setLeben(0);
            gameModel.nextTurn();
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
        if(e.getSource() == back_end_game)
        {
            new File("data/").mkdirs();

            for(int i =0;i<Var.login_profils.length;i++)
            {
                if(Var.login_profils[i] != null) {
                    try {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("data/" + Var.login_profils[i].getName() + ".bin"));
                        objectOutputStream.writeObject(Var.login_profils[i]);
                        objectOutputStream.close();

                    } catch (IOException d) {
                        System.out.println("Write Fehler while saving connected players progress: " + d);
                    }
                }

                Var.login_profils[i] = null;
            }

            new MainMenuModel();
        }
    }
}
