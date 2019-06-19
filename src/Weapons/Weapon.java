package Weapons;

import Model.GameModel;
import Panzer.Panzer;
import Weapons.AirStrike.AirStrike;
import Weapons.AirStrike.BigAirStrike;
import Weapons.AirStrike.Bombs;
import Weapons.Bounce.BigBouncer;
import Weapons.Bounce.MiddleBouncer;
import Weapons.Bounce.SmallBouncer;
import Weapons.GhostBomb.GhostBomb;
import Weapons.Granade.Granade;
import Weapons.Granade.Trinade;
import Weapons.GunShots.MG;
import Weapons.GunShots.ShotGun;
import Weapons.Laser.BigLaser;
import Weapons.Laser.SmallLaser;
import Weapons.MultiShot.MultiShot;
import Weapons.MultiShot.QuadShot;
import Weapons.MultiShot.TripleShot;
import Weapons.Nuke.MegaNuke;
import Weapons.Nuke.Nuke;
import Weapons.Shot.BigShot;
import Weapons.Shot.HugeShot;
import Weapons.Shot.NormalShot;
import Weapons.Sniper.HeavySniper;
import Weapons.Sniper.OneShot;
import Weapons.Sniper.Sniper;
import Window.*;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;


public abstract class Weapon {

    protected int startX,startY;
    protected double winkel,strength;
    protected double xPosition,yPosition;
    protected GameModel gameModel;
    protected boolean rechts;

    protected BufferedImage image;
    protected BufferedImage icon;
    protected String name;

    protected Panzer herkunft;

    protected AffineTransform affineTransform;

    private int id;
    private int anzahl;

    private Color color;
    protected boolean played;

    /*Alle Waffen erben von der Klasse Weapon
    * für die Physik der Waffen gibt es zwei verschiedene Arten:
    * zum einen die in der Klasse Shot bei der auf den ersten Aufprall die Explosion folgt
    * zum anderen die aus der Klasse Bouncer, bei der das Projektil wie ein Ball funktioniert*/

    public Weapon(GameModel gameModel,String name,int id,Color color) {
        this.gameModel = gameModel;

        this.name = name;

        this.id = id;

        anzahl = 1;

        this.color = color;

        affineTransform = new AffineTransform();
    }

    public void createImage(){

        //Erzeugt das Bild für das Waffenauswahlmenü


        image = new BufferedImage(560,225,BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        BufferedImage bar = new BufferedImage(Var.whiteBar.getWidth(),Var.whiteBar.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);

        for(int x = 0; x < bar.getWidth();x++){
            for(int y = 0;y < bar.getHeight();y++){
                if( new Color(Var.whiteBar.getRGB(x,y),true).getAlpha() != 0) {
                    if (x > bar.getHeight() / 25 * 17) {
                        bar.setRGB(x, y, new Color(color.getRed(), color.getGreen(), color.getBlue(), new Color(Var.whiteBar.getRGB(x, y), true).getAlpha() + 40).getRGB());
                    } else {
                        bar.setRGB(x, y, color.getRGB());
                    }
                }
            }
        }

        g2d.drawImage(bar,50,50,460,125,null);


        BufferedImage unselected = new BufferedImage(Var.selected.getWidth(),Var.selected.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);

        for(int x = 0; x < unselected.getWidth();x++){
            for(int y = 0;y < unselected.getHeight();y++){

                if(new Color(Var.selected.getRGB(x,y),true).getAlpha()-40 >= 0) {

                    unselected.setRGB(x, y, new Color(color.getRed(), color.getGreen(), color.getBlue(), new Color(Var.selected.getRGB(x, y), true).getAlpha() - 40).getRGB());
                }

            }
        }



        BufferedImage selected = new BufferedImage(Var.selected.getWidth(),Var.selected.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);

        for(int x = 0; x < selected.getWidth();x++){
            for(int y = 0;y < selected.getHeight();y++){

                if(new Color(Var.selected.getRGB(x,y),true).getAlpha() != 0) {
                    selected.setRGB(x, y, new Color(color.getRed(), color.getGreen(), color.getBlue(), new Color(Var.selected.getRGB(x, y), true).getAlpha() + 40).getRGB());
                }

            }
        }

        String s = "";

        for(int i = 0; i < getLevelAnzhal();i++){

            s = s + "I";

            if(i + 1 != getLevel()){
                g2d.drawImage(unselected,53 + i * 100,3,100,50,null);

                Font font = new Font("Calibri",Font.BOLD, (30));

                FontMetrics metrics = g2d.getFontMetrics(font);

                int xt = metrics.stringWidth(s)/2;

                g2d.setColor(Color.BLACK);
                g2d.setFont(font);
                g2d.drawString(s,103-xt + i *100,35);
            }else{
                g2d.drawImage(selected,53 + i * 100,3,100,50,null);

                Font font = new Font("Calibri",Font.BOLD, (30));

                FontMetrics metrics = g2d.getFontMetrics(font);

                int xt = metrics.stringWidth(s)/2;

                g2d.setColor(Color.BLACK);
                g2d.setFont(font);
                g2d.drawString(s,103-xt + i *100,35);
            }
        }


        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Calibri",Font.BOLD, (40)));
        g2d.drawString(name,160,130);


        if(anzahl < 10){
            g2d.setFont(new Font("Calibri",Font.BOLD, (80)));
            g2d.drawString(String.valueOf(anzahl),450,145);
        }else{
            g2d.setFont(new Font("Calibri",Font.BOLD, (70)));
            g2d.drawString(String.valueOf(anzahl),435,140);
        }

        g2d.drawImage(icon,60,60, 80,100,null);



    }

    public void create(int startX, int startY, double winkel, double strength,boolean rechts,Panzer herkunft){
        reset();
        this.herkunft = herkunft;
        this.startX = startX;
        this.startY = startY;
        this.winkel = winkel;
        this.strength = strength;
        xPosition = startX;
        yPosition = startY;
        this.rechts = rechts;
    }

    public void draw(Graphics2D g2d){

    }

    public abstract void reset();

    public void drawImage(int x,int y,int widht,int height,Graphics2D g2d){
        g2d.drawImage(image,x,y,widht,height,null);
    }

    public abstract Weapon getLevelWeapon(int level, GameModel model);

    public abstract int getLevelAnzhal();

    public abstract int getLevel();

    public abstract double getDownspeed();

    public abstract double getSpeed();

    public abstract Point2D getCenter();

    public  int getId(){
        return id;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void subAnzahl(){
        anzahl--;
        createImage();
    }

    public void addAnzahl(){
        anzahl++;
        createImage();
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public static Weapon getById(int id, GameModel model){

        switch (id){
            case 1:return new NormalShot(model);//default
            case 2:return new BigShot(model);//level 2
            case 3:return new HugeShot(model);//level 12
            case 4:return new Fireball(model);//level 3
            case 5:return new SmallBouncer(model);//default
            case 6:return new MiddleBouncer(model);//level 8
            case 7:return new BigBouncer(model);//level 15
            case 8:return new Nuke(model);//level 19
            case 9:return new MegaNuke(model);//level 21
            case 10:return new BigAirStrike(model);//level 11
            case 11:return new Bombs(model);//level 17
            case 12:return new AirStrike(model);//default
            case 13:return new TripleShot(model);//level 5
            case 14:return new QuadShot(model);//level 9
            case 15:return new MultiShot(model);//level 13
            case 16:return new ShotGun(model);//level 6
            case 17:return new MG(model);//level 16
            case 18:return new Sniper(model);//default
            case 19:return new HeavySniper(model);// level 7
            case 20:return new OneShot(model);//level 18
            case 21:return new Granade(model);//default
            case 22:return new Trinade(model);//level 10
            case 23:return new GhostBomb(model);//level 14
            case 24:return new SmallLaser(model);//level 4
            case 25:return new BigLaser(model);//level 20

            default:return null;
        }

    }

    public BufferedImage getIcon() {
        return icon;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public void playShotSound(){
        if(!played){
            Var.playSound(Var.shotClip);
            played = true;
        }
    }
}
