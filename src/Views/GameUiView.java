package Views;

import Model.GameModel;
import Model.MainMenuModel;
import Model.PreGameModel;
import Window.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class GameUiView extends JPanel implements ActionListener {
    MyButton fire,back,muteMusik,weaponChoosing,back_end_game;
    int health,sprit;
    GameModel gameModel;
    JPanel options;

    BufferedImage heathBar;
    public  GameUiView(GameModel gameModel)
    {
        this.gameModel = gameModel;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        health = gameModel.getLastLocalHuman().getPanzer().getLeben();
        sprit = gameModel.getLastLocalHuman().getPanzer().getSprit();
        Graphics2D g2d = (Graphics2D) g ;
        //HealthBar
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0,200,health * 5,50);
            g2d.setColor(Color.GREEN);
            g2d.fillRect(0,200,health * 5,50);
            g2d.setFont(new Font("Calibri",Font.BOLD,20));
            g2d.drawString("Health:" + health + "/" + 100,0,200);
        //´SpritBar
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0,250,100,100);
            g2d.setColor(Color.RED);
            for(int i = 90;i>=0;i--)
            {
                g2d.drawLine(0,350,(int) Math.cos(i)*100,(int) Math.sin(i));
            }
            //g2d.drawLine(0,350,(int) Math.cos(90),(int) Math.sin(90)+250);//(sprit/10)*3 = Sprit-Winkel-Verhältnis
        repaint();
    }

    //Gamemodel .getLocalHuman.getPanzer.(Da sind alle Infos)
    //TODO: Die Bar unten fertig machen(Größe der Knöpfe,Lebensanzeige,Spritanzeige)
    public void erzeugenOverlay()
    {

        fire = new MyButton("KnopfFeuerMetallic1.png","Fire Button",MyWindow.WIDTH/4,(int) (MyWindow.HEIGHT*0.3));
        fire.addActionListener(this);
        this.add(fire);

        options = new JPanel();
        options.setLayout(new BorderLayout());
        if(Var.music.isMuted())
        {
            muteMusik = new MyButton("Mute1.png","Press to Mute Musik");
        } else
        {
            muteMusik = new MyButton("KnopfSoundMetallic1.png","Press to Mute Musik");
        }
        muteMusik.addActionListener(this);
        options.add(muteMusik,BorderLayout.NORTH);

        weaponChoosing = new MyButton("WeaponKnopfMetallic1.png","Press to change Weapons");
        weaponChoosing.addActionListener(this);
        options.add(weaponChoosing,BorderLayout.CENTER);

        back = new MyButton("KnopfKampfVerlassenMetallic1.png","Back to Pregame");
        back.addActionListener(this);
        options.add(back,BorderLayout.SOUTH);
        this.add(options);
    }


    public void erzeugenOverlay_End()
    {
        back_end_game = new MyButton("KnopfKampfVerlassenMetallic1.png","Back to Pregame");
        back_end_game.addActionListener(this);
        this.add(back_end_game);

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
