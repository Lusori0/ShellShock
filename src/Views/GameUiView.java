package Views;

import Model.GameModel;
import Model.MainMenuModel;
import Model.PreGameModel;
import Model.SettingsModel;
import Panzer.Panzer;
import Weapons.Granade.Granade;
import Window.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Dictionary;
import java.util.Hashtable;


public class GameUiView extends JPanel implements ActionListener, ChangeListener {
    MyButton fire,back,toSettings,weaponChoosing,back_end_game;
    private int health,sprit,maxSprit,maxHealth,yStart,xUnit;
    GameModel gameModel;
    JPanel options;
    private BufferedImage bottomHealth,bottomSprit;
    BufferedImage background;

    BufferedImage heathBar;
    public  GameUiView(GameModel gameModel)
    {
        this.gameModel = gameModel;

        bottomHealth = new BufferedImage(300 , 300,BufferedImage.TYPE_4BYTE_ABGR);
        bottomSprit = new BufferedImage(300 , 300,BufferedImage.TYPE_4BYTE_ABGR);

        try {
            background = ImageIO.read(new File("res/gameimages/Multiplayerplayermenü.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void drawBar() {

        health = gameModel.getLastLocalHuman().getPanzer().getLeben();
        maxHealth = gameModel.getLastLocalHuman().getPanzer().getMaxLeben();
        sprit = gameModel.getLastLocalHuman().getPanzer().getSprit();
        maxSprit = gameModel.getLastLocalHuman().getPanzer().getMaxSprit();
        Graphics2D g2d = bottomHealth.createGraphics();


        //HealthBar
            g2d.drawImage(Panzer.getPanzerImage(gameModel.getLastLocalHuman().getProfil().getPanzerLevel()), 0,0,300, 200,null);
            g2d.setColor(Color.BLACK);
            g2d.fillRect(10, 210,280, 80);
            g2d.setColor(Color.GREEN);
            g2d.fillRect(15, 215, (int) (270.0/maxHealth * health), 70);
            g2d.setFont(new Font("Calibri",Font.BOLD,20));
            if(health > 0) {
                g2d.drawString("Health:" + health + "/" + maxHealth, 15, 300);
            }else{
                g2d.drawString("Health:" + 0 + "/" + maxHealth, 15, 300);
            }
        //´SpritBar

            Graphics2D g2dt = bottomSprit.createGraphics();

            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            g2dt.setColor(Color.BLACK);

            g2dt.fillRect(0,0,300,300);

            Point2D rotationPoint = new Point2D.Double(20,280);

            g2dt.setColor(Color.WHITE);

            AffineTransform aft = new AffineTransform();

            g2dt.setFont(new Font("Calibri",Font.BOLD,20));
            g2dt.drawString("Full",15,10);
            g2dt.drawString("Empty",250,270);
            for(int i = 0;i < 7;i++) {
                aft.setToRotation(Math.PI/12 * i,rotationPoint.getX(),rotationPoint.getY());

                g2dt.setTransform(aft);


                g2dt.fillRect(15, 10, 10, 20);
            }

            int[] xCords = new int[]{10,20,30,20};
            int[] yCords = new int[]{290,280,290,10};

            g2dt.setColor(Color.RED);

            aft.setToRotation(Math.PI/2 - Math.PI/(2 * maxSprit) * sprit,rotationPoint.getX(),rotationPoint.getY());

            g2dt.setTransform(aft);

            g2dt.fillPolygon(new Polygon(xCords,yCords,4));

            g2dt.setTransform(new AffineTransform());

            BufferedImage temp1 = new BufferedImage(MyWindow.WIDTH/6, (int) (MyWindow.HEIGHT * 0.3),BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2dtemp1 = temp1.createGraphics();
            g2dtemp1.drawImage(bottomHealth,0,0,MyWindow.WIDTH/6, (int) (MyWindow.HEIGHT * 0.3),null);
            bottomHealth = temp1;

            BufferedImage temp2 = new BufferedImage(MyWindow.WIDTH/6, (int) (MyWindow.HEIGHT * 0.3),BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2dtemp2 = temp2.createGraphics();
            g2dtemp2.drawImage(bottomSprit,0,0,MyWindow.WIDTH/6, (int) (MyWindow.HEIGHT * 0.3),null);
            bottomSprit = temp2;

            this.repaint();
    }

    //Gamemodel .getLocalHuman.getPanzer.(Da sind alle Infos)
    public void erzeugenOverlay()
    {


        drawBar();

        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.CENTER);
        fl.setVgap(0);
        fl.setHgap((int) (MyWindow.WIDTH * 0.05));
        this.setLayout(fl);
        this.setPreferredSize(new Dimension(MyWindow.WIDTH, (int) (MyWindow.HEIGHT*0.3)));



        JLabel healthImg = new JLabel(new ImageIcon(bottomHealth));
        healthImg.setPreferredSize(new Dimension(MyWindow.WIDTH/6, (int) (MyWindow.HEIGHT * 0.3)));

        this.add(healthImg);

        JLabel spritImg = new JLabel(new ImageIcon(bottomSprit));
        spritImg.setPreferredSize(new Dimension(MyWindow.WIDTH/6, (int) (MyWindow.HEIGHT * 0.3)));

        this.add(spritImg);



        fire = new MyButton("KnopfFeuerMetallic1.png","Fire Button",MyWindow.WIDTH/3,(int) (MyWindow.HEIGHT*0.3));
        fire.setMargin(new Insets(0,0,0,0));
        fire.addActionListener(this);
        this.add(fire);

        options = new JPanel();
        BorderLayout bl = new BorderLayout();
        bl.setVgap((int) (MyWindow.HEIGHT * 0.017));
        options.setLayout(bl);



        toSettings = new MyButton("KnopfEinstellungenMetallic1.png","Press to enter Settings");
        toSettings.addActionListener(this);
        options.add(toSettings,BorderLayout.CENTER);

        weaponChoosing = new MyButton("WeaponKnopfMetallic1.png","Press to change Weapons");
        weaponChoosing.addActionListener(this);
        options.add(weaponChoosing,BorderLayout.NORTH);


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
            gameModel.feuerButtonAction();
        }
        if ((e.getSource() == toSettings))
        {
            GameLoop.pauset = true;
            new SettingsModel(gameModel.getGameView());
        }
        if(e.getSource() == weaponChoosing)
        {
            MyKeys.weapon = true;
        }

        if(e.getSource() == back_end_game)
        {
            new File("data/").mkdirs();

            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("data/" + Var.activeUser.getName() + ".bin"));
                objectOutputStream.writeObject(Var.activeUser);
                objectOutputStream.close();

            } catch (IOException d) {
                System.out.println("Write Fehler while saving game: " + d);
            }

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

    @Override
    public void stateChanged(ChangeEvent e) {

    }
}
