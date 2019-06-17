package Weapons.AirStrike;

import Model.GameModel;
import Panzer.Panzer;
import Weapons.Shot.BigShot;
import Weapons.Shot.HugeShot;
import Weapons.Shot.NormalShot;
import Weapons.Weapon;
import Window.Var;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AirStrikeAbs extends Weapon {


    private int count;
    protected int weaponsize;
    private double speed = 3;
    private double gravity = 0.007;
    private double downspeed = 0;
    private int explosionTimer = 0;
    protected int explosionRadius;
    protected int damage;
    private int steps = 5;
    private int hit = 0;
    private double drawWinkel = 0;
    private boolean wasNoCollision = true;


    private LinkedList<double[]> coords = new LinkedList<>();
    private int starttimer = 0;
    protected int effecttime;


    private CopyOnWriteArrayList<Bullet> shots = new CopyOnWriteArrayList<>();
    private boolean called;

    public AirStrikeAbs(GameModel gameModel, String name,int level,int size,int expsize,int damage,int count,int id,BufferedImage icon) {
        super(gameModel, name,id,new Color(255,0,0));

        this.count = count;

        this.icon = icon;

        createImage();


        weaponsize = size;
        explosionRadius = expsize;
        this.damage = damage;




    }

    public void reset(){
        explosionTimer = 0;
        downspeed = 0;
        hit = 0;
        drawWinkel = 0;
        starttimer = 0;
        affineTransform = new AffineTransform();
        shots.removeAll(shots);
        played = false;
        coords = new LinkedList<>();
        called = false;
    }


    @Override
    public void create(int startX, int startY, double winkel, double strength, boolean rechts, Panzer herkunft) {
        super.create(startX, startY, winkel, strength, rechts,herkunft);
        for(int i = 0; i < 40;i++){
            coords.add(new double[]{(int) startX, (int) startY,0});
        }
    }

    @Override
    public void draw(Graphics2D g2d) {

        playShotSound();

        if(explosionTimer == 0) {

            AffineTransform t = new AffineTransform();
            int temp = 0;
            for(double[] cord : coords){

                g2d.setColor(new Color(255,0,0,255));
                double size = (weaponsize*1.2)/40 * temp;
                if(cord[0] != (int)xPosition) {


                    t.setToRotation(cord[2],cord[0],cord[1]);

                    g2d.setTransform(t);

                    g2d.fill(new Ellipse2D.Double(cord[0] - size/2, cord[1]-size/2, size, size));


                }

                temp++;
            }

            g2d.setColor(Color.WHITE);
            g2d.setTransform(affineTransform);
            g2d.fill(new Ellipse2D.Double(xPosition-weaponsize/2,yPosition - weaponsize/2,weaponsize,weaponsize));
            g2d.setTransform(new AffineTransform());
        }



        Point2D tCollisionPoint = affineTransform.transform(new Point2D.Double(xPosition,yPosition + weaponsize/2),null);

        if(gameModel.isCollision((int)tCollisionPoint.getX(),(int)tCollisionPoint.getY()) && wasNoCollision){

            xPosition = coords.get(coords.indexOf(coords.getLast())-1)[0];
            yPosition = coords.get(coords.indexOf(coords.getLast())-1)[1];

            strength /= 1.5;


            if(rechts) {
                winkel = 2 * gameModel.getMap().getWinkel(xPosition + weaponsize / 2.0) - drawWinkel;
            }else{
                winkel = (2 * gameModel.getMap().getWinkel(xPosition + weaponsize / 2.0) - drawWinkel) -  Math.PI;
            }
            drawWinkel = winkel;


            if(speed * strength < 0.1 && !called){

                strength = 0;
                called = true;

                for(int i = 0; i < count;i++){
                    int dis = (count-1) * 30;
                    shots.add(new Bullet((int) (xPosition - dis/2 + i * 30),20,gameModel,explosionRadius));
                }
            }


            downspeed = 0;
            wasNoCollision = false;
        }else{
            wasNoCollision = true;
        }

        if(called){
            boolean t = false;
            for(Bullet b : shots){
                if(!b.isDone()){
                    t = true;
                    b.draw(g2d,herkunft);
                }
            }

            if(!t){
                weaponEnd();
            }
        }

        if(!called) {

            callculateNewCoords();
        }

    }

    protected void callculateNewCoords(){

        for(int i = 0; i < steps;i++) {

            double ht = speed * strength;
            downspeed += gravity;



            if(explosionTimer == 0) {
                if (rechts) {
                    xPosition += Math.cos(winkel) * ht;
                    yPosition += Math.sin(winkel) * ht + downspeed;
                    drawWinkel = Math.atan2(Math.sin(winkel) * ht + downspeed,Math.cos(winkel) * ht);
                } else {
                    xPosition += -Math.cos(winkel) * ht;
                    yPosition += -Math.sin(winkel) * ht + downspeed;
                    drawWinkel = Math.PI - Math.atan2(Math.sin(-winkel) * ht + downspeed,Math.cos(-winkel) * ht);
                }
            }






            affineTransform.setToRotation(drawWinkel, xPosition, yPosition);

            if (starttimer == effecttime) {
                coords.add(new double[]{ xPosition,  yPosition,drawWinkel});
                starttimer = 0;
            } else {
                starttimer++;
            }

            if (coords.size() > 40) {
                coords.remove(coords.getFirst());
            }


        }

    }

    private void weaponEnd(){
        gameModel.removeWeapon(this);
        gameModel.nextTurn();
    }

    @Override
    public Weapon getLevelWeapon(int level,GameModel model){
        switch (level){
            case 1:return new AirStrike(model);
            case 2:return new BigAirStrike(model);
            case 3:return new Bombs(model);
            default:return null;
        }
    }

    @Override
    public int getLevelAnzhal() {
        return 3;
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
        return affineTransform.transform(new Point2D.Double(xPosition + weaponsize/(double)2,yPosition ),null);
    }



    public Point2D getCoord() {
        return affineTransform.transform(new Point2D.Double(xPosition + weaponsize/2,yPosition+weaponsize/2),null);
    }







}
