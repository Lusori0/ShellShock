package Weapons.GhostBomb;

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

public class GhostBomb extends Weapon {


    protected int weaponsize;
    private double speed = 3;
    private double gravity = 0.007;
    private double downspeed = 0;
    private int explosionTimer = 0;
    protected int explosionRadius;
    protected int damage;
    private int steps = 2;
    private boolean hit = false;


    private LinkedList<int[]> coords = new LinkedList<>();
    private int starttimer = 0;
    protected int effecttime;

    public GhostBomb(GameModel gameModel) {
        super(gameModel,"Ghost Bomb",23,new Color(200,200,200));

        weaponsize = 30;

        explosionRadius = 200;

        effecttime = 2;

        damage = 17;

        this.icon = Var.ghostbomb;

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
        coords = new LinkedList<>();
    }

    @Override
    public void create(int startX, int startY, double winkel, double strength, boolean rechts,Panzer herkunft) {
        super.create(startX, startY, winkel, strength, rechts,herkunft);
        for(int i = 0; i < 40;i++){
            coords.add(new int[]{ startX, startY});
        }
    }

    @Override
    public void draw(Graphics2D g2d) {


        playShotSound();





        int temp = 0;
        for(int[] cord : coords){

            if(cord[0] != (int)xPosition && cord[1] != (int)yPosition) {
                g2d.setColor(new Color(255, 255, 255, 150));
                double size = (weaponsize * 1.2) / 40 * temp;
                g2d.fill(new Ellipse2D.Double(cord[0] - size / 2, cord[1] - size / 2, size, size));
            }


            temp++;
        }

        g2d.setColor(new Color(255,255,255,150));

        if(explosionTimer == 0) {
            g2d.fill(new Ellipse2D.Double(xPosition - weaponsize / (double) 2, yPosition - weaponsize / (double) 2, weaponsize, weaponsize));
        }

        callculateNewCoords();

        if(gameModel.isCollisionPanzer((int)xPosition + weaponsize/2,(int)yPosition + weaponsize/2,herkunft)){
            hit = true;
        }

        if(hit) {
            if (explosionTimer == 0) {
                Var.playSound(Var.explosionClip);
                gameModel.noImpactExplosion(new Ellipse2D.Double((int) xPosition + weaponsize/2 - explosionRadius / 2, (int) yPosition + weaponsize/2 - explosionRadius / 2, (int)(explosionRadius ), (int)(explosionRadius)),damage,herkunft);

                g2d.setColor(Color.WHITE);
                g2d.setTransform(affineTransform);
                g2d.fillOval((int) xPosition + weaponsize/2 - explosionRadius / 2, (int) yPosition + weaponsize/2 - explosionRadius / 2, (int)(explosionRadius ), (int)(explosionRadius));
                g2d.setTransform(new AffineTransform());
                explosionTimer++;
            } else if (explosionTimer <= 100) {
                g2d.setColor(new Color(255, 255, 255, (int) (255 - 255 * explosionTimer / (double) 100)));
                g2d.fillOval((int) xPosition - explosionRadius / 2, (int) yPosition - explosionRadius / 2, (int) (explosionRadius), (int) (explosionRadius));
                explosionTimer++;
            } else {


                if (coords.getFirst()[0] == (int) xPosition) {
                    weaponEnd();
                }

            }
        }
    }

    protected void callculateNewCoords(){

        for(int i = 0; i < steps;i++) {

            double ht = speed * strength;
            downspeed += gravity;


            affineTransform.setToRotation(winkel, getCenter().getX(), getCenter().getY());


            if(explosionTimer == 0) {
                if (rechts) {
                    xPosition += Math.cos(winkel) * ht;
                    yPosition += Math.sin(winkel) * ht + downspeed;
                } else {
                    xPosition += -Math.cos(winkel) * ht;
                    yPosition += -Math.sin(winkel) * ht + downspeed;
                }
            }

            if (starttimer == effecttime) {
                coords.add(new int[]{(int) xPosition, (int) yPosition});
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
            case 1:return new GhostBomb(model);
            default:return null;
        }
    }

    @Override
    public int getLevelAnzhal() {
        return 1;
    }

    @Override
    public int getLevel() {
        return 1;
    }

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
