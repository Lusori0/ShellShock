package Weapons.Granade;

import Model.GameModel;
import Panzer.Panzer;
import Weapons.Weapon;
import Window.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public abstract class GranadeAbs extends Weapon {


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
    protected LinkedList<Bullet> bullets = new LinkedList<>();
    private int starttimer = 0;
    protected int effecttime;

    public GranadeAbs(GameModel gameModel, String name, int level,int id,BufferedImage icon) {
        super(gameModel,name,id,Color.WHITE);

        this.icon = icon;

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
    }

    @Override
    public abstract void create(int startX, int startY, double winkel, double strength, boolean rechts,Panzer herkunft);


    @Override
    public void draw(Graphics2D g2d) {
        playShotSound();
        boolean temp = false;
        for(Bullet bullet : bullets) {
            if(!bullet.isDone()) {
                bullet.draw(g2d,herkunft);
                temp = true;
            }
        }
        if(!temp) {
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
            case 1:return new Granade(model);
            case 2:return new Trinade(model);
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
