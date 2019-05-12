package Views;

import Model.GameModel;

import Window.*;
//import sun.plugin2.util.ColorUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;


public class GameLoop extends JPanel implements MouseListener {

    GameModel gameModel;

    private boolean mouseClicked,mousePressed;
    private int mouseX,mouseY;
    private long time;
    private int temp;
    private boolean mouseEntered;
    private Graphics graphics;
    private Graphics2D g2d;
    private Color background;
    private Random rand;
    private BufferedImage bi;
    private BufferStrategy buffer;

    private int fps = 0;
    private int frames = 0;
    private long totalTime = 0;
    private long curTime = System.currentTimeMillis();
    private long lastTime = curTime;



    private Canvas canvas;

    public static boolean pauset;


    public GameLoop(GameModel gameModel){
        this.gameModel = gameModel;



        MyWindow.getFrame().setIgnoreRepaint(true);

        canvas = new Canvas();

        canvas.addMouseListener(this);

        canvas.addKeyListener(new MyKeys());

        canvas.setIgnoreRepaint(true);

        canvas.setSize(new Dimension(MyWindow.WIDTH,MyWindow.HEIGHT-MyWindow.HEIGHT/8));

        MyWindow.getFrame().setVisible(true);
        //MyWindow.getFrame().add(canvas);
        //MyWindow.getFrame().pack();



        this.add(canvas);

        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();


        bi = gc.createCompatibleImage( MyWindow.WIDTH,MyWindow.HEIGHT-MyWindow.HEIGHT/8);


        graphics = null;

        g2d = null;

        background = Color.BLACK;

        rand = new Random();


        time = System.nanoTime();




    }

    public void createStrategy(){
        canvas.createBufferStrategy(2);
        buffer = canvas.getBufferStrategy();
    }

    public void mainGameLoop(){



            while (true) {



                try {

                    // count Frames per second...
                    lastTime = curTime;
                    curTime = System.currentTimeMillis();
                    totalTime += curTime - lastTime;
                    if (totalTime > 1000) {
                        totalTime -= 1000;
                        fps = frames;
                        frames = 0;
                    }
                    ++frames;

                    long pause = 1000 / 60 - (curTime - lastTime);

                    if (pause > 0) {
                        try {
                            Thread.sleep(pause);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    g2d = bi.createGraphics();
                    g2d.setColor(background);
                    g2d.fillRect(0, 0, MyWindow.WIDTH, MyWindow.HEIGHT - MyWindow.HEIGHT / 4);


                    //drawStart

                    if (pauset) {
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        pauset = false;
                    }


                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);


                    g2d.setColor(Color.CYAN);
                    g2d.fill(gameModel.getMap().getMap());



                    gameModel.movePanzer();

                    gameModel.sendToServer();

                    gameModel.sendToAll();

                    if (MyKeys.shoot) {
                        gameModel.feuerButtonAction();

                        MyKeys.shoot = false;
                    }


                    gameModel.drawUi(g2d, mouseX, mouseY);


                    gameModel.drawPanzer(g2d);

                    gameModel.drawCurrentWeapons(g2d);


                    if (mouseEntered && !MyKeys.weapon) {



                        if (mousePressed) {
                            Point p = MouseInfo.getPointerInfo().getLocation();
                            SwingUtilities.convertPointFromScreen(p,canvas);
                            mouseX = p.x;
                            mouseY = p.y;



                            gameModel.handleMousePressed(mouseX, mouseY);
                        } else if (mouseClicked) {
                            gameModel.handleMouseClicked(mouseX, mouseY);
                            mouseClicked = false;
                        }
                    }else if(MyKeys.weapon) {
                        g2d.setColor(new Color(50, 50, 50, 100));


                        if (mouseEntered) {
                            Point p = MouseInfo.getPointerInfo().getLocation();
                            SwingUtilities.convertPointFromScreen(p,canvas);
                            mouseX = p.x;
                            mouseY = p.y;

                        }

                        if(gameModel.showWeapons(g2d, mouseX, mouseY, mouseClicked, 1)) {
                            mouseClicked = false;
                        }

                    } else if (gameModel.getWeaponsShowedTime() > 100) {
                        gameModel.setWeaponsShowTime(100);
                        g2d.setColor(new Color(50, 50, 50, 100));
                        gameModel.showWeapons(g2d, mouseX, mouseY, mouseClicked, -1);
                    } else if (gameModel.getWeaponsShowedTime() >= 0) {
                        g2d.setColor(new Color(50, 50, 50, 100));
                        gameModel.showWeapons(g2d, mouseX, mouseY, mouseClicked, -1);
                    }


                    //drawEnd

                    g2d.setFont(new Font("Courier New", Font.PLAIN, 12));
                    g2d.setColor(Color.GREEN);
                    g2d.drawString(String.format("FPS: %s", fps), 20, 20);

                    graphics = buffer.getDrawGraphics();
                    graphics.drawImage(bi, 0, 0, null);
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
}
