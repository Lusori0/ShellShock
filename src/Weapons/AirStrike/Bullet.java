package Weapons.AirStrike;

import Model.GameModel;
import Panzer.Panzer;
import Views.GameLoop;
import Window.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;

public class Bullet {

    protected int weaponsize;
    private double speed = 2;
    private double gravity = 0.007;
    private double downspeed = 0;
    private int explosionTimer = 0;
    protected int explosionRadius;
    protected int damage;
    private double drawWinkel = 0;
    private double winkel = -Math.PI/2;
    private GameModel gameModel;


    private LinkedList<double[]> coords = new LinkedList<>();
    private int starttimer = 0;
    protected int effecttime;

    private double x, y, opac;

    private boolean hit,done = false;

    private AffineTransform affineTransform = new AffineTransform();

    public Bullet(int x, int damage,GameModel gameModel,int explosionRadius) {
        this.gameModel = gameModel;
        this.x = x;
        y = -10;
        opac = 255;
        this.damage = damage;
        weaponsize = 12;
        this.explosionRadius = explosionRadius;
    }

    public void draw(Graphics2D g2d,Panzer herkunft) {



        if(this.explosionTimer ==0) {

            AffineTransform t = new AffineTransform();
            int temp = 0;
            for (double[] cord : this.coords) {
                if(cord[0] != (int)x && cord[1] != (int)y) {
                    g2d.setColor(new Color(255, 0, 0, 255));
                    double size = (this.weaponsize * 1.2) / 40 * temp;


                    t.setToRotation(cord[2], cord[0], cord[1]);

                    g2d.setTransform(t);

                    g2d.fill(new Ellipse2D.Double(cord[0] - size / 2, cord[1] - size / 2, size, size));
                }




                temp++;
            }

            g2d.setColor(Color.WHITE);
            g2d.setTransform(this.affineTransform);
            g2d.fill(new Ellipse2D.Double(this.x - this.weaponsize / 2.0, this.y - this.weaponsize / 2.0, this.weaponsize, this.weaponsize));
            g2d.setTransform(new AffineTransform());
        }


        Point2D tCollisionPoint = this.affineTransform.transform(new Point2D.Double(this.x, this.y + this.weaponsize / 2.0), null);

        if(gameModel.isCollisionPanzer((int)tCollisionPoint.getX(),(int)tCollisionPoint.getY(),herkunft)) {
            hit = true;
        }

        if(hit) {
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
            }else{
                done = true;
            }
        }
        callculateNewCoords();

    }

    public boolean isDone() {
        return done;
    }

    protected void callculateNewCoords() {

        for (int i = 0; i < 3; i++) {

            double ht = speed * 1;
            downspeed += gravity;


            if (explosionTimer == 0) {

                    x += Math.cos(winkel) * ht;
                    y += Math.sin(winkel) * ht + downspeed;
                 drawWinkel = Math.atan2(Math.sin(winkel) * ht + downspeed, Math.cos(winkel) * ht);

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
