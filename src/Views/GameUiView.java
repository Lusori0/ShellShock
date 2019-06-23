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
    private MyButton fire,back,toSettings,weaponChoosing,back_end_game;
    private int health;
    private int sprit;
    private int maxSprit;
    private int maxHealth;
    private GameModel gameModel;
    private JPanel options;
    private BufferedImage bottomHealth,bottomSprit;
    private BufferedImage background;

    public  GameUiView(GameModel gameModel)
    {
        this.gameModel = gameModel;

        bottomHealth = new BufferedImage(300 , 300,BufferedImage.TYPE_4BYTE_ABGR);
        bottomSprit = new BufferedImage(300 , 300,BufferedImage.TYPE_4BYTE_ABGR);

        try {
            background = ImageIO.read(new File("res/gameimages/Metall2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void drawBar() {

        repaint();

        health = gameModel.getLastLocalHuman().getPanzer().getLeben();
        maxHealth = gameModel.getLastLocalHuman().getPanzer().getMaxLeben();
        sprit = gameModel.getLastLocalHuman().getPanzer().getSprit();
        maxSprit = gameModel.getLastLocalHuman().getPanzer().getMaxSprit();
        Graphics2D g2d = bottomHealth.createGraphics();


        //HealthBar
            g2d.drawImage(Panzer.getPanzerImage(gameModel.getLastLocalHuman().getProfil().getPanzerLevel()), 0,0,300, 200,null);
            g2d.setColor(Color.BLACK);
            g2d.fillRect(10, 210,280, 90);
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
            g2dt.drawString("Full",15,60);
            g2dt.drawString("Empty",200,280);
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

            repaint();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;

        g2d.drawImage(background,0,0,MyWindow.WIDTH, (int) (MyWindow.HEIGHT * 0.3),null);

        if(!gameModel.isEnded()) {
            g2d.drawImage(bottomHealth, (int) (MyWindow.WIDTH * 0.02), 0, MyWindow.WIDTH / 5, (int) (MyWindow.HEIGHT * 0.3), null);
            g2d.drawImage(bottomSprit, (int) (MyWindow.WIDTH * 0.04) + MyWindow.WIDTH / 5, 0, MyWindow.WIDTH / 5, (int) (MyWindow.HEIGHT * 0.3), null);
        }
    }

    //Gamemodel .getLocalHuman.getPanzer.(Da sind alle Infos)
    public void erzeugenOverlay()
    {


        drawBar();

        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.RIGHT);
        fl.setVgap(0);
        fl.setHgap((int) (MyWindow.WIDTH * 0.02));
        this.setLayout(fl);
        this.setPreferredSize(new Dimension(MyWindow.WIDTH, (int) (MyWindow.HEIGHT*0.3)));


        fire = new MyButton("KnopfFeuerMetallic1.png","Fire Button",MyWindow.WIDTH/3,(int) (MyWindow.HEIGHT*0.3));
        fire.setMargin(new Insets(0,0,0,0));
        fire.addActionListener(this);
        this.add(fire);

        options = new JPanel();
        options.setOpaque(true);
        options.setBackground(new Color(0,0,0,0));
        BorderLayout bl = new BorderLayout();

        bl.setVgap((int) (MyWindow.HEIGHT * 0.017));
        options.setLayout(bl);



        toSettings = new MyButton("KnopfEinstellungenMetallic1.png","Press to enter Settings");
        toSettings.addActionListener(this);
        options.add(toSettings,BorderLayout.CENTER);

        weaponChoosing = new MyButton("WeaponKnopfMetallic1.png","Press to change Weapons");
        weaponChoosing.addActionListener(this);
        options.add(weaponChoosing,BorderLayout.NORTH);


        back = new MyButton("KnopfKampfVerlassenMetallic1.png","Leave the Match");
        back.addActionListener(this);
        options.add(back,BorderLayout.SOUTH);
        this.add(options);
    }


    public void erzeugenOverlay_End()
    {
        back_end_game = new MyButton("leaveGameKnopfMetallic1.png","Back to MainMenu");
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
