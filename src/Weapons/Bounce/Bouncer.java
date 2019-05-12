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

public class Bouncer extends Weapon {



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

    public Bouncer(GameModel gameModel, String name,int level) {
        super(gameModel, name,5);

        icons = new BufferedImage[]{Var.shotIcon, Var.shotIcon, Var.panzer};

        icon = icons[level-1];

        createImage(Var.greenBar,Var.shotIconDig,Var.greenUnlock,Var.greenLock);


        weaponsize = gameModel.getHeight()/60;

        for(int i = 0; i < 40;i++){
            coords.add(new double[]{(int) xPosition, (int) yPosition,0});
        }

    }


    @Override
    public void create(int startX, int startY, double winkel, double strength, boolean rechts, Panzer herkunft) {
        super.create(startX, startY, winkel, strength, rechts,herkunft);
    }

    @Override
    public void draw(Graphics2D g2d) {







        AffineTransform t = new AffineTransform();
        int temp = 0;
        for(double[] cord : coords){

            g2d.setColor(new Color(200,150,0,255));
            double size = (weaponsize*1.2)/40 * temp;
            if(cord[0] != (int)xPosition) {


                t.setToRotation(cord[2],cord[0],cord[1]);

                g2d.setTransform(t);

                g2d.fill(new Ellipse2D.Double(cord[0] - size/2, cord[1]-size/2, size, size));


            }

            temp++;
        }

        g2d.setTransform(new AffineTransform());

        g2d.setColor(Color.WHITE);

        if(explosionTimer == 0) {
            g2d.setTransform(affineTransform);
            //g2d.fill(new Ellipse2D.Double(xPosition - weaponsize / (double) 2, yPosition - weaponsize / (double) 2, weaponsize, weaponsize));
            g2d.fill(new Rectangle2D.Double(xPosition-weaponsize/2,yPosition - weaponsize/2,weaponsize,weaponsize));
            g2d.setTransform(new AffineTransform());
        }



        Point2D tCollisionPoint = affineTransform.transform(new Point2D.Double(xPosition,yPosition + weaponsize/2),null);

        if(gameModel.isCollision((int)tCollisionPoint.getX(),(int)tCollisionPoint.getY())){

            xPosition = coords.get(coords.indexOf(coords.getLast())-1)[0];
            yPosition = coords.get(coords.indexOf(coords.getLast())-1)[1];

            strength /= 1.5;

            System.out.println(drawWinkel);

            if(rechts) {
                winkel = 2 * gameModel.getMap().getWinkel(xPosition + weaponsize / 2) - drawWinkel;
            }else{
                winkel = (2 * gameModel.getMap().getWinkel(xPosition + weaponsize / 2) - drawWinkel) -  Math.PI;
            }
            drawWinkel = winkel;

            System.out.println(gameModel.getMap().getWinkel(xPosition + weaponsize/2));

            System.out.println(drawWinkel);



            downspeed = 0;



            hit ++;
        }
        if(hit > 0){
            g2d.drawLine((int)tCollisionPoint.getX(),(int)tCollisionPoint.getY(),(int)(tCollisionPoint.getX() +(Math.cos(winkel) * 100)),(int)(tCollisionPoint.getY()+(Math.sin(winkel) * 100)));

        }

        callculateNewCoords();

        /*if(hit == 2) {
            if (explosionTimer == 0) {
                gameModel.explosion((int) xPosition, (int) yPosition, explosionRadius, damage, herkunft);

                g2d.setColor(Color.WHITE);
                g2d.setTransform(affineTransform);
                g2d.fillOval((int) xPosition - explosionRadius / 2, (int) yPosition - explosionRadius / 2, explosionRadius, explosionRadius);
                g2d.setTransform(new AffineTransform());
                explosionTimer++;
            } else if (explosionTimer <= 100) {
                g2d.setColor(new Color(255, 255, 255, (int) (255 - 255 * explosionTimer / (double) 100)));
                g2d.fillOval((int) xPosition - explosionRadius / 2, (int) yPosition - explosionRadius / 2, explosionRadius, explosionRadius);
                explosionTimer++;
            } else {


                if (coords.getFirst()[0] == (int) xPosition) {
                    weaponEnd();
                }

            }
        }*/
    }

    protected void callculateNewCoords(){

        for(int i = 0; i < 1;i++) {

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
    public Weapon getLevelWeapon(int level, GameModel model) {
        return null;
    }

    /*@Override
    public Weapon getLevelWeapon(int level,GameModel model){
        switch (level){
            case 1:return new NormalShot(model);
            case 2:return new BigShot(model);
            case 3:return new HugeShot(model);
            default:return null;
        }
    }*/

    @Override
    public int getLevelAnzhal() {
        return 3;
    }

    public int getLevel() {
        return 0;
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
        return affineTransform.transform(new Point2D.Double(xPosition + weaponsize/(double)2,yPosition ),null);
    }



    public Point2D getCoord() {
        return affineTransform.transform(new Point2D.Double(xPosition + weaponsize/2,yPosition+weaponsize/2),null);
    }



}
