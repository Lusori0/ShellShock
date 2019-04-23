package Views;

import Model.GameModel;

import Window.*;
import sun.plugin2.util.ColorUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;


public class GameLoop extends JPanel implements MouseListener {

    GameModel gameModel;

    private boolean mouseClicked,mousePressed;
    private int mouseX,mouseY;
    private long time;
    private int temp;
    private boolean mouseEntered;


    public GameLoop(GameModel gameModel){
        this.gameModel = gameModel;

        this.addMouseListener(this);

        time = System.nanoTime();
    }


    @Override
    protected void paintComponent(Graphics g) {




        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;


        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);


        AffineTransform old = g2d.getTransform();

        g2d.fillRect(0,0, MyWindow.WIDTH,gameModel.getHeight());

        //  g2d.drawImage(gameModel.getMap(),-MyWindow.WIDTH/10,0, (int) (MyWindow.WIDTH * 1.2),gameModel.getHeight(),null);

        g2d.setColor(Color.CYAN);
        g2d.fill(gameModel.getMap().getMap());

        g2d.setColor(Color.RED);
        gameModel.test(g2d);


        if(mouseEntered && !MyKeys.weapon) {

            if (mousePressed) {
                mouseX = getMousePosition().x;
                mouseY = getMousePosition().y;
                gameModel.handleMousePressed(mouseX, mouseY);
            } else if (mouseClicked) {
                gameModel.handleMouseClicked(mouseX, mouseY);
                mouseClicked = false;
            }
        }


        gameModel.drawUi(g2d,mouseX,mouseY);


        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        gameModel.movePanzer();
        gameModel.drawPanzer(g2d);

        gameModel.drawCurrentWeapons(g2d);


        if(MyKeys.shoot){
            gameModel.feuerButtonAction();

            MyKeys.shoot = false;
        }

        if(MyKeys.weapon){
            g2d.setColor(new Color(50,50,50,100));



            if(mouseEntered) {
                mouseX = getMousePosition().x;
                mouseY = getMousePosition().y;

            }

            gameModel.showWeapons(g2d, mouseX, mouseY, mouseClicked,1);
            mouseClicked = false;

        }else if(gameModel.getWeaponsShowedTime() > 100){
            gameModel.setWeaponsShowTime(100);
            g2d.setColor(new Color(50,50,50,100));
            gameModel.showWeapons(g2d,mouseX,mouseY,mouseClicked,-1);
        }else if(gameModel.getWeaponsShowedTime() >= 0){
            g2d.setColor(new Color(50,50,50,100));
            gameModel.showWeapons(g2d,mouseX,mouseY,mouseClicked,-1);
        }




        g2d.setColor(Color.WHITE);



            //System.out.println(System.nanoTime() - time);
            //g2d.drawString(String.valueOf(System.nanoTime() - time), 10, 10);
            //time = System.nanoTime();


        repaint();
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
