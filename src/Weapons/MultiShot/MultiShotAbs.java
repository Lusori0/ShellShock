
package Weapons.MultiShot;

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

public abstract class MultiShotAbs extends Weapon {


    protected int weaponsize;
    private double speed = 3;
    private double gravity = 0.007;
    protected int explosionRadius;
    protected int damage;
    private int count;


    protected int effecttime;

    private LinkedList<Bullet> shots = new LinkedList<>();

    public MultiShotAbs(GameModel gameModel, String name, int level, int id,int count,BufferedImage icon) {
        super(gameModel,name,id, Color.WHITE);

        this.count = count;

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
        for(int i = 0;i < count ;i++){
            double diff = 0.4/count;
            shots.add(new Bullet(startX,startY,winkel -0.2 + i*diff,gameModel,rechts));
        }
    }

    @Override
    public void draw(Graphics2D g2d) {


        playShotSound();

        boolean end = true;

        for(Bullet bullet : shots){
            bullet.draw(g2d,herkunft);
            if(!bullet.isDone()){
                end = false;
            }
        }

        if(end){
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
            case 1:return new TripleShot(model);
            case 2:return new QuadShot(model);
            case 3:return new MultiShot(model);
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
