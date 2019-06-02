package Weapons;

import Model.GameModel;
import Panzer.Panzer;
import Window.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Fireball extends Weapon {


    private int weaponsize;
    private double speed = 3;
    private double gravity = 0.01;
    private double downspeed = 0;
    private int explosionTimer = 0;
    private int explosionRadius = 100;
    private int damage = 20;
    private boolean hit = false;
    private int steps = 3;



    private LinkedList<int[]> coords = new LinkedList<>();
    private int starttimer = 0;

    public Fireball(GameModel gameModel) {
        super(gameModel,"Fireball",4,new Color(255,150,0));

        icon = Var.fireball;

        createImage();

        weaponsize = gameModel.getHeight()/60;

    }

    public void reset(){
        explosionTimer = 0;
        downspeed = 0;
        hit = false;
        affineTransform = new AffineTransform();
    }

    @Override
    public void create(int startX, int startY, double winkel, double strength, boolean rechts,Panzer herkunft) {
        super.create(startX, startY, winkel, strength, rechts,herkunft);

        for(int i = 0; i < 20;i++){
            coords.add(new int[]{(int) xPosition, (int) yPosition});
        }
    }

    @Override
    public void draw(Graphics2D g2d) {


        playShotSound();

            int temp = 0;
            for(int[] cord : coords){
                int opac = 255/20 * temp;
                g2d.setColor(new Color(255,150,0,opac));
                double size = (weaponsize*1.2)/20 * temp;
                if(cord[0] != (int)xPosition) {
                    g2d.fill(new Ellipse2D.Double(cord[0] - size / 2, cord[1] - size / 2, size, size));
                }

                temp++;
            }

            g2d.setColor(Color.WHITE);


            if(explosionTimer == 0) {
                g2d.fill(new Ellipse2D.Double(xPosition - weaponsize / (double) 2, yPosition - weaponsize / (double) 2, weaponsize, weaponsize));
            }

            callculateNewCoords();

        if(gameModel.isCollisionPanzer((int)xPosition + weaponsize/2,(int)yPosition + weaponsize/2,herkunft) && explosionTimer == 0){
            hit = true;
        }

        if(hit) {
            if (explosionTimer == 0) {
                Var.playSound(Var.fireClip);
                gameModel.explosion((int) xPosition, (int) yPosition, explosionRadius, damage, herkunft);


                g2d.setColor(new Color(255, 150, 0));
                g2d.fillOval((int) xPosition - explosionRadius / 2, (int) yPosition - explosionRadius / 2, explosionRadius, explosionRadius);

                explosionTimer++;
            } else if (explosionTimer <= 100) {
                System.out.println("test");
                g2d.setColor(new Color(255, 150, 0, (int) (255 - 255 * explosionTimer / (double) 100)));
                g2d.fillOval((int) xPosition - explosionRadius / 2, (int) yPosition - explosionRadius / 2, explosionRadius, explosionRadius);
                explosionTimer++;
            } else {
                if (coords.getFirst()[0] == (int) xPosition) {
                    weaponEnd();
                }
            }
        }
    }

    private void callculateNewCoords(){

        for(int i = 0;i < steps;i++) {

            double ht = speed * strength;
            downspeed += gravity;


            if (explosionTimer == 0) {

                if (rechts) {
                    xPosition += Math.cos(winkel) * ht;
                    yPosition += Math.sin(winkel) * ht + downspeed;
                } else {
                    xPosition += -Math.cos(winkel) * ht;
                    yPosition += -Math.sin(winkel) * ht + downspeed;
                }
            }

            if (starttimer == 4) {
                coords.add(new int[]{(int) xPosition, (int) yPosition});
                starttimer = 0;
            } else {
                starttimer++;
            }

            if (coords.size() > 20) {
                coords.remove(coords.getFirst());
            }
        }

    }

    private void weaponEnd(){
        gameModel.removeWeapon(this);
        gameModel.nextTurn();
    }

    @Override
    public Weapon getLevelWeapon(int level, GameModel model) {
        switch(level){
            case 1:return new Fireball(model);
            default: return null;
        }
    }

    @Override
    public int getLevelAnzhal() {
        return 1;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public double getDownspeed() {
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

