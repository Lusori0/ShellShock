package Weapons;

import Model.GameModel;
import Window.*;

import com.sun.security.ntlm.Client;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public abstract class Weapon {

    protected int startX,startY;
    protected double winkel,strength;
    protected double xPosition,yPosition;
    protected GameModel gameModel;
    protected boolean rechts;

    protected BufferedImage image;
    protected BufferedImage icon;
    protected String name;

    protected BufferedImage[] icons;




    public Weapon(GameModel gameModel,String name) {
        this.gameModel = gameModel;

        this.name = name;






    }

    public void createImage(BufferedImage bar,BufferedImage iconDig,BufferedImage unlock,BufferedImage lock){
        image = new BufferedImage(600,250,BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = image.createGraphics();

        g2d.drawImage(bar,50,75,500,150,null);


        g2d.drawImage(iconDig,50,75,150,150,null);

        for(int i = 0; i < getLevelAnzhal();i++){
            g2d.drawImage(unlock,50 + i * 100,25,100,50,null);
            g2d.drawImage(icons[i],70 + i * 100,35,60,30,null);
            if(i + 1 != getLevel()){
                g2d.setColor(new Color(50,50,50,150));
                g2d.fillRect(50 + i * 100,25,100,50);
            }
        }
        g2d.setColor(Color.WHITE);

        g2d.setFont(new Font("Calibri",Font.BOLD, (int) (50)));
        g2d.drawString(name,225,175);
    }

    public void create(int startX, int startY, double winkel, double strength,boolean rechts){
        this.startX = startX;
        this.startY = startY;
        this.winkel = winkel;
        this.strength = strength;
        xPosition = startX;
        yPosition = startY;
        this.rechts = rechts;
    }

    public void draw(Graphics2D g2d){

    }

    private void callculateNewCoords(){

    }

    private void weaponEnd(){


    }

    public void drawImage(int x,int y,int widht,int height,Graphics2D g2d){
        g2d.drawImage(image,x,y,widht,height,null);
    }

    public abstract Weapon getLevelWeapon(int level, GameModel model);

    public abstract int getLevelAnzhal();

    public abstract int getLevel();

    public abstract double getDownspeed();

    public abstract double getSpeed();
}
