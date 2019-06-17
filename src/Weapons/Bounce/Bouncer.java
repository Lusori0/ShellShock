package Weapons.Bounce;

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

public abstract class Bouncer extends Weapon {



    protected int weaponsize;
    private double speed = 2;
    private double gravity = 0.007;
    private double downspeed = 0;
    private int explosionTimer = 0;
    protected int explosionRadius;
    protected int damage;
    private int steps = 5;
    private int hit = 0;
    private double drawWinkel = 0;


    private LinkedList<double[]> coords = new LinkedList<>();
    private int starttimer = 0;
    protected int effecttime;

    private int bounces;
    private boolean always;
    private double reduce;
    private boolean wasNoCollision = true;
    private CopyOnWriteArrayList<Explosion> explosions = new CopyOnWriteArrayList<>();

    public Bouncer(GameModel gameModel, String name,int level,int bounces,boolean always,int size,int expsize,int damage,double reduce,int id,BufferedImage icon) {
        super(gameModel, name,id,new Color(200,0,255));

        this.reduce = reduce;

       this.icon = icon;

        createImage();


        weaponsize = size;
        explosionRadius = expsize;
        this.always = always;
        this.bounces = bounces;
        this.damage = damage;




    }

    public void reset(){
        explosionTimer = 0;
        downspeed = 0;
        hit = 0;
        drawWinkel = 0;
        starttimer = 0;
        affineTransform = new AffineTransform();
        played = false;
        coords = new LinkedList<>();
        speed = 2;
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

                g2d.setColor(new Color(200,0,250,255));
                double size = (weaponsize*1.2)/40 * temp;
                if(cord[0] != (int)xPosition && cord[1] != (int)yPosition) {


                    t.setToRotation(cord[2],cord[0],cord[1]);

                    g2d.setTransform(t);

                    g2d.fill(new Ellipse2D.Double(cord[0] - size/2, cord[1]-size/2, size, size));


                }

                temp++;
            }

            g2d.setTransform(new AffineTransform());

            g2d.setColor(Color.WHITE);
            g2d.setTransform(affineTransform);
            //g2d.fill(new Ellipse2D.Double(xPosition - weaponsize / (double) 2, yPosition - weaponsize / (double) 2, weaponsize, weaponsize));
            g2d.fill(new Ellipse2D.Double(xPosition-weaponsize/2,yPosition - weaponsize/2,weaponsize,weaponsize));
            g2d.setTransform(new AffineTransform());
        }



        Point2D tCollisionPoint = affineTransform.transform(new Point2D.Double(xPosition,yPosition + weaponsize/2),null);

        if(gameModel.isCollision((int)tCollisionPoint.getX(),(int)tCollisionPoint.getY()) && wasNoCollision){

            xPosition = coords.get(coords.indexOf(coords.getLast())-1)[0];
            yPosition = coords.get(coords.indexOf(coords.getLast())-1)[1];

            strength /= reduce;



            if(rechts) {
                winkel = 2 * gameModel.getMap().getWinkel(xPosition + weaponsize / 2) - drawWinkel;
            }else{
                winkel = (2 * gameModel.getMap().getWinkel(xPosition + weaponsize / 2) - drawWinkel) -  Math.PI;
            }
            drawWinkel = winkel;




            downspeed = 0;

            if(always && hit<=bounces){

                    gameModel.explosion((int) xPosition, (int) yPosition, explosionRadius, damage, herkunft);
                    explosions.add(new Explosion((int)xPosition,(int)yPosition,explosionRadius,Color.WHITE));
                    hit ++;
                    if(hit > bounces){
                        explosionTimer++;
                    }
            }


            if(hit == bounces && !always){
                System.out.println("test");
                gameModel.explosion((int) xPosition, (int) yPosition, explosionRadius, damage, herkunft);
                explosions.add(new Explosion((int)xPosition,(int)yPosition,explosionRadius,Color.WHITE));
                explosionTimer++;
                hit ++;
                if(hit > bounces){
                    explosionTimer++;
                }
            }else if(!always){
                hit++;
            }


            wasNoCollision = false;
        }else{
            wasNoCollision = true;
        }

        for(Explosion exp : explosions){
            exp.draw(g2d);
            if(exp.getOpac() == 0){
                explosions.remove(exp);
                if(hit > bounces && always){
                    explosionTimer++;
                    if(explosions.size() == 0){
                        weaponEnd();
                    }
                }else if(hit > bounces && explosionTimer > 0){

                    if(explosions.size() == 0){
                        System.out.println("test");
                        weaponEnd();
                    }
                }
            }
        }

        callculateNewCoords();

    }

    protected void callculateNewCoords(){

        for(int i = 0; i < 3;i++) {

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
            case 1:return new SmallBouncer(model);
            case 2:return new MiddleBouncer(model);
            case 3:return new BigBouncer(model);
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


    private class Explosion{

        private int x,y,size,opac;

        private Color expColor;

        public Explosion(int x, int y, int size,Color color) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.expColor = color;
            opac = 255;
            Var.playSound(Var.explosionClip);
        }

        public void draw(Graphics2D g2d){

            g2d.setColor(new Color(expColor.getRed(),expColor.getGreen(),expColor.getBlue(),opac));
            g2d.fillOval((int) x - size / 2, (int) y - size / 2, size, size);

            opac -=4;

            if(opac<0){
                opac = 0;
            }
        }

        public int getOpac() {
            return opac;
        }
    }




}
