
package Weapons.GunShots;

import Model.GameModel;
import Panzer.Panzer;
import Weapons.MultiShot.MultiShot;
import Weapons.MultiShot.QuadShot;
import Weapons.MultiShot.TripleShot;
import Weapons.Weapon;
import Window.Var;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public abstract class GunShotAbs extends Weapon {


    protected int weaponsize;
    private double speed = 3;
    private double gravity = 0.007;
    protected int explosionRadius;
    protected int damage;


    protected int effecttime;

    protected LinkedList<Bullet> shots = new LinkedList<>();

    public GunShotAbs(GameModel gameModel, String name, int level, int id,BufferedImage icon) {
        super(gameModel,name,id, new Color(0,200,255));


        this.icon = icon;

        createImage();





    }

    public void reset(){
        affineTransform = new AffineTransform();
        shots.removeAll(shots);
    }

    @Override
    public void create(int startX, int startY, double winkel, double strength, boolean rechts, Panzer herkunft) {
        super.create(startX, startY, winkel, strength, rechts,herkunft);

    }

    @Override
    public abstract void draw(Graphics2D g2d);

    protected void weaponEnd(){
        gameModel.removeWeapon(this);
        gameModel.nextTurn();
    }

    @Override
    public Weapon getLevelWeapon(int level,GameModel model){
        switch (level){
            case 1:return new MG(model);
            case 2:return new ShotGun(model);
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
