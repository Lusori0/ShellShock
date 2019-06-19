package Views;

import Model.GameModel;
import Model.Player;
import Weapons.Weapon;
import Window.MyKeys;
import Window.MyWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.util.concurrent.CopyOnWriteArrayList;


public class GameLoop extends JPanel implements MouseListener {

    private GameModel gameModel;

    private boolean mouseClicked,mousePressed;
    private int mouseX,mouseY;
    private boolean mouseEntered;
    private Graphics2D graphics;
    private Graphics2D g2d;
    private Color background,foreground;
    private VolatileImage bi;
    private BufferStrategy buffer;


    private int fps = 0;
    private int frames = 0;
    private long totalTime = 0;
    private long curTime = System.currentTimeMillis();

    public static int imgW = 2800;
    public static int imgH = 1800;


    private Canvas canvas;

    private boolean game = true;

    private static boolean pauset;


    public GameLoop(GameModel gameModel,Color background,Color foreground){
        this.gameModel = gameModel;


        this.background = background;

        this.foreground = foreground;

        FlowLayout layout = new FlowLayout();
        layout.setVgap(0);

        this.setLayout(layout);

        //this.setPreferredSize(new Dimension(MyWindow.WIDTH, (int) (MyWindow.HEIGHT*0.7)));


        System.getProperties().setProperty("sun.java2d.opengl", "true");


        MyWindow.getFrame().setIgnoreRepaint(true);

        canvas = new Canvas();

        canvas.addMouseListener(this);

        canvas.addKeyListener(new MyKeys());

        canvas.setIgnoreRepaint(true);

        canvas.setSize(new Dimension(MyWindow.WIDTH, (MyWindow.HEIGHT)));

        MyWindow.getFrame().setVisible(true);

        this.add(canvas);

        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();

        if( gd.isDisplayChangeSupported() ) {
            gd.setDisplayMode(new DisplayMode(MyWindow.WIDTH,MyWindow.HEIGHT, 32, DisplayMode.REFRESH_RATE_UNKNOWN ));
        }


        bi = gc.createCompatibleVolatileImage(2800,1800);


        graphics = null;

        g2d = null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.fillRect(0,0,MyWindow.WIDTH,MyWindow.HEIGHT);
    }

    public void createStrategy(){

        // erzeugen des buffer-objects zum Zeichnen

        canvas.createBufferStrategy(2);
        buffer = canvas.getBufferStrategy();
    }

    public void mainGameLoop(){





            while (game) {




                try {

                    // count Frames per second...
                    long lastTime = curTime;
                    curTime = System.currentTimeMillis();
                    totalTime += curTime - lastTime;
                    if (totalTime > 1000) {
                        totalTime -= 1000;
                        fps = frames;
                        frames = 0;
                    }
                    ++frames;

                    long pause = 1000/80 - (curTime - lastTime);

                    if (pause > 0) {
                        try {
                            Thread.sleep(pause);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    g2d = bi.createGraphics();




                    g2d.setColor(background);
                    g2d.fillRect(0, 0, imgW, (imgH));




                    //drawStart

                    if (pauset) {
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        pauset = false;
                    }



                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);





                    g2d.setColor(foreground);
                    g2d.fill(gameModel.getMap().getMap());

                    g2d.setClip(null);



                    gameModel.movePanzer();

                    if (MyKeys.shoot) {
                        gameModel.feuerButtonAction();

                        MyKeys.shoot = false;
                    }


                    gameModel.drawUi(g2d, mouseX, mouseY);


                    gameModel.drawPanzer(g2d);

                    gameModel.drawDrops(g2d);

                    gameModel.drawCurrentWeapons(g2d);





                    if (mouseEntered && !MyKeys.weapon) {



                        if (mousePressed) {
                            Point p = MouseInfo.getPointerInfo().getLocation();
                            SwingUtilities.convertPointFromScreen(p,canvas);
                            mouseX = p.x * imgW/MyWindow.WIDTH;
                            mouseY = (int) (p.y * imgH/(MyWindow.HEIGHT * 0.7));




                            gameModel.handleMousePressed(mouseX, mouseY);
                        } else if (mouseClicked) {
                            Point p = MouseInfo.getPointerInfo().getLocation();
                            SwingUtilities.convertPointFromScreen(p,canvas);
                            mouseX = p.x * imgW/MyWindow.WIDTH;
                            mouseY = (int) (p.y * imgH/(MyWindow.HEIGHT * 0.7));
                            gameModel.handleMouseClicked(mouseX, mouseY);
                            mouseClicked = false;
                        }
                    }

                    if(MyKeys.weapon) {
                        g2d.setColor(new Color(50, 50, 50, 100));


                        if (mouseEntered) {
                            Point p = MouseInfo.getPointerInfo().getLocation();
                            SwingUtilities.convertPointFromScreen(p, canvas);
                            mouseX = p.x * imgW / MyWindow.WIDTH;
                            mouseY = (int) (p.y * imgH / (MyWindow.HEIGHT * 0.7));

                        }

                        if (gameModel.showWeapons(g2d, mouseX, mouseY, mouseClicked, 1)) {
                            mouseClicked = false;
                        }
                    }else if(gameModel.getWeaponsShowedTime() > 0 && !MyKeys.weapon){
                        g2d.setColor(new Color(50, 50, 50, 100));
                        gameModel.showWeapons(g2d,mouseX,mouseY,mouseClicked,-1);
                    }




                    //drawEnd

                    g2d.setFont(new Font("Courier New", Font.PLAIN, 80));
                    g2d.setColor(Color.GREEN);
                    //g2d.drawString(String.format("FPS: %s", fps), 20, 80);


                    graphics = (Graphics2D) buffer.getDrawGraphics();


                    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


                    graphics.drawImage(bi, 0, 0,MyWindow.WIDTH, (int) (MyWindow.HEIGHT * 0.7),null);




                    if (!buffer.contentsLost())

                        buffer.show();

                    Thread.yield();
                } finally {
                    // release resources
                    if (graphics != null)
                        graphics.dispose();
                    if (g2d != null)
                        g2d.dispose();
                }
            }

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        mouseClicked = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseEntered = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseEntered = false;
    }

    public void drawEndScreen(String text,int team) {

        //zeichnen des Abschlussbildschirms am Ende eines Spiels

        game = false;

        g2d = bi.createGraphics();

        CopyOnWriteArrayList<BufferedImage> imges = new CopyOnWriteArrayList<>();
        BufferedImage temp;
        Graphics2D g2dt;
        int timer = 0;
        int startTimer = 0;

        int time = 250;




        boolean ended =  false;

        boolean isCounting = true;

        boolean finished = false;

        while(!finished){

            g2dt = bi.createGraphics();

            if(text.equals("DRAW")) {
                g2dt.setColor(Color.WHITE);
                Font font9 = new Font("Calibri", Font.BOLD, 300);
                FontMetrics metrics9 = g2d.getFontMetrics(font9);
                // Determine the X coordinate for the text
                int xt9 = (metrics9.stringWidth(text)) / 2;
                g2dt.setFont(font9);
                g2dt.drawString(text, 1400 - xt9, 250);
            }else{
                g2dt.setColor(new Color(100,200,0));
                Font font9 = new Font("Calibri", Font.BOLD, 300);
                FontMetrics metrics9 = g2d.getFontMetrics(font9);
                // Determine the X coordinate for the text
                int xt9 = (metrics9.stringWidth(text)) / 2;
                g2dt.setFont(font9);
                g2dt.drawString(text, 1400 - xt9, 250);
            }


            if(timer <= time/4){
                g2dt.setColor(new Color(200,200,200,250/time * timer));
                g2dt.fillRect(0,0,2800,1800);
                timer ++;
            }else{
                finished = true;
                timer = 0;
            }

            graphics = (Graphics2D) buffer.getDrawGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.drawImage(bi,0,0,MyWindow.WIDTH, (int) (MyWindow.HEIGHT * 0.7),null);
            if (!buffer.contentsLost()) {
                buffer.show();
            }

        }

        int anzahl = 0;

        for(Player player : gameModel.getDeadPlayer()){
            if(player.isLocalHuman()){
                anzahl++;
            }
        }


        while(!ended) {

            for (Player player : gameModel.getDeadPlayer()) {
                if (player.isLocalHuman()) {
                    System.out.println("testLoop");
                    temp = new BufferedImage(500, 1300, BufferedImage.TYPE_4BYTE_ABGR);

                    int ws = 500/20;

                    int hs = 900/20;

                    g2dt = temp.createGraphics();

                    g2dt.setColor(new Color(150, 150, 150));
                    g2dt.fillRect(0, 0, ws * 20, hs * 28);

                    g2dt.drawImage(player.getPanzer().getImage(), ws * 2, hs * 3, ws * 16, hs * 5, null);

                    int barY = hs * 11;

                    g2dt.setColor(new Color(100, 100, 100));
                    g2dt.fillRect(ws * 4, hs/2 + barY, ws * 12, hs);

                    g2dt.setColor(Color.GREEN);

                    g2dt.fillRect(ws * 4, (hs/2+barY), (int) (ws * 12 * ((player.getXP() % 400) / 400.0)), hs);

                    g2dt.setColor(new Color(100, 100, 100));

                    g2dt.fillOval(ws * 2, barY,  ws * 4,hs * 2);
                    g2dt.fillOval(ws * 14, barY,  ws * 4,hs * 2);

                    g2dt.setColor(Color.WHITE);
                    Font font2 = new Font("Calibri",Font.BOLD, hs);
                    FontMetrics metrics2 = g2d.getFontMetrics(font2);
                    // Determine the X coordinate for the text
                    int xt2 = (metrics2.stringWidth(String.valueOf(player.getLevel())) / 2);
                    g2dt.setFont(font2);
                    g2dt.drawString(String.valueOf(player.getLevel()),ws * 4 - xt2, (int) (barY + hs * 1.2));

                    g2dt.setColor(Color.WHITE);
                    Font font3 = new Font("Calibri",Font.BOLD, hs);
                    FontMetrics metrics3 = g2d.getFontMetrics(font3);
                    // Determine the X coordinate for the text
                    int xt3 = (metrics3.stringWidth(String.valueOf(1 + player.getLevel())) / 2);
                    g2dt.setFont(font3);
                    g2dt.drawString(String.valueOf(player.getLevel()+ 1),ws * 16 - xt3, (int) (barY + hs * 1.2));



                    if(player.getTeam() == team) {
                        g2dt.setColor(new Color(100,200,0));
                        Font font = new Font("Calibri", Font.BOLD, hs * 2);
                        FontMetrics metrics = g2d.getFontMetrics(font);
                        // Determine the X coordinate for the text
                        int xt = (metrics.stringWidth(player.getPanzer().getName())) / 2;
                        g2dt.setFont(font);
                        g2dt.drawString(player.getPanzer().getName(), ws * 10 - xt, hs * 2);
                    }else{
                        g2dt.setColor(Color.WHITE);
                        Font font = new Font("Calibri", Font.BOLD, hs * 2);
                        FontMetrics metrics = g2d.getFontMetrics(font);
                        // Determine the X coordinate for the text
                        int xt = (metrics.stringWidth(player.getPanzer().getName())) / 2;
                        g2dt.setFont(font);
                        g2dt.drawString(player.getPanzer().getName(), ws * 10 - xt, hs * 2);
                    }

                    g2dt.setColor(Color.WHITE);
                    Font font1 = new Font("Calibri",Font.BOLD, hs);
                    FontMetrics metrics1 = g2d.getFontMetrics(font1);
                    // Determine the X coordinate for the text
                    int xt1 = (metrics1.stringWidth("+ " + player.getPanzer().getXp() + " XP")) / 2;
                    g2dt.setFont(font1);
                    g2dt.drawString("+ " + player.getPanzer().getXp() + " XP",ws * 10 - xt1,hs * 10);

                    if(player.getXP()/400 == player.getLevel() && !player.isLevelUp()){

                        System.out.println(player.getLevel());

                        if(player.getLevel() < 21) {

                            player.setUnlockedWeapon(Weapon.getById(player.levelUp(), gameModel));
                            player.getUnlockedWeapon().createImage();
                        }
                        player.setLevelUp(true);
                    }

                    if(player.isLevelUp()){

                        g2dt.setColor(Color.WHITE);
                        Font font6 = new Font("Calibri",Font.BOLD, hs);
                        FontMetrics metrics6 = g2d.getFontMetrics(font6);
                        // Determine the X coordinate for the text
                        g2dt.setFont(font6);


                        if(timer <= time) {
                            g2dt.setColor(new Color(255,255,255,(250/time) * timer));
                            int xt6 = (metrics6.stringWidth("Level Up")) / 2;
                            g2dt.drawString("Level Up", ws * 10 - xt6, hs * 15);
                            isCounting = true;
                        }else{
                            g2dt.setColor(new Color(255,255,255,255));
                            int xt6 = (metrics6.stringWidth("Level Up")) / 2;
                            g2dt.drawString("Level Up", ws * 10 - xt6, hs * 15);
                            isCounting = true;
                        }

                        if(timer > time && timer <= time * 2 && player.getLevel() < 21) {


                            g2dt.setColor(new Color(100,100,100,(250/time) * (timer -time)));
                            g2dt.fillRect(0,hs*16,ws * 20,hs * 12);


                            g2dt.setColor(new Color(255,255,255,(250/time) * (timer -time)));
                            int xt7 = (metrics6.stringWidth("Weapon unlocked")) / 2;
                            g2dt.drawString("Weapon unlocked", ws * 10 - xt7, hs * 18);

                            int xt8 = (metrics6.stringWidth(player.getUnlockedWeapon().getName())) / 2;
                            g2dt.drawString(player.getUnlockedWeapon().getName(), ws * 10 - xt8, hs * 26);

                            g2dt.setColor(new Color(player.getUnlockedWeapon().getColor().getRed(),player.getUnlockedWeapon().getColor().getGreen(),player.getUnlockedWeapon().getColor().getBlue(),(250/time) * (timer-time)));
                            g2dt.fillRoundRect(ws * 6, hs * 19, ws * 8, hs * 5,ws*2,hs*2);

                            float alpha = (float) ((timer - time)/(double)time);
                            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
                            g2dt.setComposite(ac);
                            g2dt.drawImage(player.getUnlockedWeapon().getIcon(), ws * 6, hs * 19, ws * 8, hs * 5,null);

                            float reset = 1f;
                            AlphaComposite reset1 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,reset);
                            g2dt.setComposite(reset1);

                            isCounting = true;

                        }else if(timer > time*2){

                            g2dt.setColor(new Color(100,100,100,255));
                            g2dt.fillRect(0,hs*16,ws * 20,hs * 12);

                            g2dt.setColor(new Color(255,255,255,255));
                            int xt7 = (metrics6.stringWidth("Weapon unlocked")) / 2;
                            g2dt.drawString("Weapon unlocked", ws * 10 - xt7, hs * 18);

                            int xt8 = (metrics6.stringWidth(player.getUnlockedWeapon().getName())) / 2;
                            g2dt.drawString(player.getUnlockedWeapon().getName(), ws * 10 - xt8, hs * 26);

                            g2dt.setColor(player.getUnlockedWeapon().getColor());
                            g2dt.fillRoundRect(ws * 6, hs * 19, ws * 8, hs * 5,ws*2,hs*2);
                            g2dt.drawImage(player.getUnlockedWeapon().getIcon(), ws * 6, hs * 19, ws * 8, hs * 5,null);
                            isCounting = false;
                        }

                        timer ++;
                    }






                    imges.add(temp);

                    if (startTimer < player.getPanzer().getXp()) {
                        player.addXP();
                        isCounting = true;
                    }





                }
            }

            startTimer++;

            g2d = bi.createGraphics();

            int tempNr = 1;
            for(BufferedImage img : imges) {
                g2d.drawImage(img, 2800/(anzahl+1) * tempNr - 250, 400, null);
                imges.remove(img);
                tempNr++;
            }


            graphics = (Graphics2D) buffer.getDrawGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.drawImage(bi,0,0,MyWindow.WIDTH, (int) (MyWindow.HEIGHT * 0.7),null);
            if (!buffer.contentsLost()) {

                buffer.show();
            }

            if(isCounting){
                isCounting = false;
            }else{
                gameModel.setUiView();
                MyWindow.getFrame().setVisible(true);
                ended = true;
            }
        }
    }
}
