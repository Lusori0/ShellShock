package Weapons.Shot;

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

public abstract class Shot extends Weapon {


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

    public Shot(GameModel gameModel, String name, int level,int id) {
        super(gameModel,name,id,Color.WHITE,Var.shotIcon);

        icons = new BufferedImage[]{Var.shotIcon, Var.shotIcon, Var.panzer};

        icon = icons[level-1];

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
    public void create(int startX, int startY, double winkel, double strength, boolean rechts,Panzer herkunft) {
        super.create(startX, startY, winkel, strength, rechts,herkunft);
        for(int i = 0; i < 40;i++){
            coords.add(new int[]{(int) xPosition, (int) yPosition});
        }
    }

    @Override
    public void draw(Graphics2D g2d) {







            int temp = 0;
            for(int[] cord : coords){
                int[] xPos = new int []{};
                g2d.setColor(new Color(200,150,0,255));
                double size = (weaponsize*1.2)/40 * temp;
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

        if(gameModel.isCollisionPanzer((int)xPosition + weaponsize/2,(int)yPosition + weaponsize/2,herkunft)){
            hit = true;
        }

        if(hit) {
            if (explosionTimer == 0) {
                gameModel.explosion((int) xPosition, (int) yPosition, explosionRadius, damage, herkunft);

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
            case 1:return new NormalShot(model);
            case 2:return new BigShot(model);
            case 3:return new HugeShot(model);
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
        return new Point2D.Double(xPosition + weaponsize/(double)2,yPosition + weaponsize/(double)2);
    }
}
