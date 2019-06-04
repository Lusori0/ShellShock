package Weapons.Granade;

import Model.GameModel;
import Panzer.Panzer;
import Weapons.Bounce.Bouncer;
import Window.Var;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;

public class Bullet {

    private double strength;
    protected int weaponsize;
    private double speed = 3;
    private double gravity = 0.007;
    private double downspeed = 0;
    private int explosionTimer = 0;
    protected int explosionRadius;
    protected int damage;
    private double drawWinkel = 0;
    private double winkel;
    private GameModel gameModel;
    private boolean right;


    private LinkedList<double[]> coords = new LinkedList<>();
    private int starttimer = 0;
    protected int effecttime;

    private double x, y, opac;

    private boolean hit,done = false;

    private AffineTransform affineTransform = new AffineTransform();
    private boolean wasNoCollision = true;

    private int timer;

    public Bullet(int x,int y,double winkel,GameModel gameModel,boolean right,int damage,double strength) {
        this.gameModel = gameModel;
        this.strength = strength;
        this.x = x;
        this.y = y;
        this.winkel = winkel;
        this.right = right;
        opac = 255;
        this.damage = damage;
        weaponsize = 20;
        this.explosionRadius = 100;

        for(int i = 0; i < 40;i++){
            coords.add(new double[]{(int) x, (int) y});
        }

    }

    public void draw(Graphics2D g2d,Panzer herkunft) {



        if(this.explosionTimer ==0) {

            g2d.setColor(new Color(0,120,0));
            g2d.setTransform(this.affineTransform);
            g2d.fill(new Ellipse2D.Double(this.x - this.weaponsize / 2.0, this.y - this.weaponsize / 2.0, this.weaponsize, this.weaponsize));
            g2d.setTransform(new AffineTransform());
        }


        Point2D tCollisionPoint = this.affineTransform.transform(new Point2D.Double(this.x, this.y + this.weaponsize / 2.0), null);

        if(gameModel.isCollision((int)tCollisionPoint.getX(),(int)tCollisionPoint.getY()) && wasNoCollision){

            x = coords.get(coords.indexOf(coords.getLast())-1)[0];
            y = coords.get(coords.indexOf(coords.getLast())-1)[1];

            strength /= 1.4;



            if(right) {
                winkel = 2 * gameModel.getMap().getWinkel(x + weaponsize / 2) - drawWinkel;
            }else{
                winkel = (2 * gameModel.getMap().getWinkel(y + weaponsize / 2) - drawWinkel) -  Math.PI;
            }
            drawWinkel = winkel;




            downspeed = 0;


            wasNoCollision = false;
        }else{
            wasNoCollision = true;
        }

        timer++;

        if(timer > 700){
            if (explosionTimer == 0) {
                Var.playSound(Var.explosionClip);
                gameModel.explosion((int) x, (int) y, explosionRadius, damage, herkunft);



                g2d.setColor(Color.WHITE);
                g2d.setTransform(affineTransform);
                g2d.fillOval((int) x + weaponsize/2 - explosionRadius / 2, (int) y + weaponsize/2 - explosionRadius / 2, (int)(explosionRadius ), (int)(explosionRadius));
                g2d.setTransform(new AffineTransform());
                explosionTimer++;
            } else if (explosionTimer <= 100) {
                g2d.setColor(new Color(255, 255, 255, (int) (255 - 255 * explosionTimer / (double) 100)));
                g2d.fillOval((int) x - explosionRadius / 2, (int) y - explosionRadius / 2, (int) (explosionRadius), (int) (explosionRadius));
                explosionTimer++;
            } else {


               done = true;

            }

        }

        if(strength > 0.01) {
            callculateNewCoords();
        }

    }

    public boolean isDone() {
        return done;
    }

    protected void callculateNewCoords() {

        for (int i = 0; i < 3; i++) {

            double ht = speed * strength;
            downspeed += gravity;


            if (explosionTimer == 0) {

                if(right) {
                    x += Math.cos(winkel) * ht;
                    y += Math.sin(winkel) * ht + downspeed;
                    drawWinkel = Math.atan2(Math.sin(winkel) * ht + downspeed, Math.cos(winkel) * ht);
                }else{
                    x += -Math.cos(winkel) * ht;
                    y += -Math.sin(winkel) * ht + downspeed;
                    drawWinkel = Math.PI - Math.atan2(Math.sin(-winkel) * ht + downspeed,Math.cos(-winkel) * ht);

                }

            }


            affineTransform.setToRotation(drawWinkel, x, y);

            if (starttimer == effecttime) {
                coords.add(new double[]{x, y, drawWinkel});
                starttimer = 0;
            } else {
                starttimer++;
            }

            if (coords.size() > 40) {
                coords.remove(coords.getFirst());
            }


        }

    }

    public double getOpac() {
        return opac;
    }

}
