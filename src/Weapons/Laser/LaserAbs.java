package Weapons.Laser;

import Model.GameModel;
import Panzer.Panzer;
import Weapons.Weapon;
import Window.*;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public abstract class LaserAbs extends Weapon {


    protected int weaponsize;
    private double speed = 3;
    private double gravity = 0.007;
    private double downspeed = 0;
    private int explosionTimer = 0;
    protected int explosionRadius;
    protected int damage;
    private int steps = 5;
    private boolean hit = false;


    private LinkedList<int[]> coords = new LinkedList<>();
    private int starttimer = 0;
    protected int effecttime;
    private Color drawColor;

    public LaserAbs(GameModel gameModel, String name, int level,int id,BufferedImage icon,Color drawColor,int size) {
        super(gameModel,name,id,new Color(253,255,179));

        this.icon = icon;
        this.drawColor = drawColor;
        this.weaponsize = size;

        createImage();




        for(int i = 0; i < 40;i++){
            coords.add(new int[]{(int) xPosition, (int) yPosition});
        }
    }

    public void reset(){
        explosionTimer = 0;
        hit = false;
        downspeed = 0;
        starttimer = 0;
        affineTransform = new AffineTransform();
        played = false;
    }

    @Override
    public void create(int startX, int startY, double winkel, double strength, boolean rechts,Panzer herkunft) {
        super.create(startX, startY, winkel, strength, rechts,herkunft);
        for(int i = 0; i < 40;i++){
            coords.add(new int[]{(int) xPosition, (int) yPosition});
        }
    }

    @Override
    public void draw(Graphics2D g2d) {


        playShotSound();

        if(explosionTimer == 0){


            RoundRectangle2D rect2 = new RoundRectangle2D.Double(startX + 40 - weaponsize/2.0,startY - weaponsize,2800,weaponsize * 2,10,10);



            RoundRectangle2D rect = new RoundRectangle2D.Double(startX + 40,startY - weaponsize/2.0,2800,weaponsize,10,10);


            if(rechts) {
                affineTransform.setToRotation(winkel, startX, startY);
            }else{
                affineTransform.setToRotation(Math.PI+winkel, startX, startY);
            }
            Shape shape = affineTransform.createTransformedShape(rect);
            Shape shape2 = affineTransform.createTransformedShape(rect2);
            gameModel.noImpactExplosion(shape,damage,herkunft);
            g2d.setColor(drawColor);
            g2d.fill(shape2);
            g2d.setColor(Color.WHITE);
            g2d.fill(shape);
            explosionTimer++;
        }else if(explosionTimer < 100) {


            RoundRectangle2D rect2 = new RoundRectangle2D.Double(startX + 38,startY - 10,2800,20,10,10);



            RoundRectangle2D rect = new RoundRectangle2D.Double(startX + 40,startY - 5,2800,10,10,10);


            if(rechts) {
                affineTransform.setToRotation(winkel, startX, startY);
            }else{
                affineTransform.setToRotation(Math.PI+winkel, startX, startY);
            }
            Shape shape = affineTransform.createTransformedShape(rect);
            Shape shape2 = affineTransform.createTransformedShape(rect2);
            g2d.setColor(new Color(drawColor.getRed(),drawColor.getGreen(),drawColor.getBlue(),255 - 255/100 * explosionTimer));
            g2d.fill(shape2);
            g2d.setColor(new Color(255,255,255,255 - 255/100 * explosionTimer));
            g2d.fill(shape);
            explosionTimer++;


        }else{

            weaponEnd();
        }
    }

    private void weaponEnd(){
        gameModel.removeWeapon(this);
        gameModel.nextTurn();
    }

    @Override
    public Weapon getLevelWeapon(int level,GameModel model){
        switch (level){
            case 1:return new SmallLaser(model);
            case 2:return new BigLaser(model);
            default:return null;
        }
    }

    @Override
    public int getLevelAnzhal() {
        return 2;
    }

    public abstract int getLevel();

    public double getDownspeed(){
        return gravity;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public Point2D getCenter() {
        return new Point2D.Double(xPosition + weaponsize/(double)2,yPosition + weaponsize/(double)2);
    }
}

