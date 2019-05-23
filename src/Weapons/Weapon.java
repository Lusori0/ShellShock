package Weapons;

import Model.GameModel;
import Panzer.Panzer;
import Weapons.Bounce.BigBouncer;
import Weapons.Bounce.MiddleBouncer;
import Weapons.Bounce.SmallBouncer;
import Weapons.Shot.BigShot;
import Weapons.Shot.HugeShot;
import Weapons.Shot.NormalShot;
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

    protected BufferedImage[] icons;

    protected Panzer herkunft;

    protected AffineTransform affineTransform;

    private int id;
    private int anzahl;

    private Color color;

    public Weapon(GameModel gameModel,String name,int id,Color color,BufferedImage icon) {
        this.gameModel = gameModel;

        this.name = name;

        this.id = id;

        anzahl = 1;

        this.color = color;
        this.icon = icon;

        affineTransform = new AffineTransform();
    }

    public void createImage(){



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

    private void callculateNewCoords(){

    }

    private void weaponEnd(){


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

    public static Weapon getById(int id, GameModel model){

        switch (id){
            case 1:return new NormalShot(model);
            case 2:return new BigShot(model);
            case 3:return new HugeShot(model);
            case 4:return new Fireball(model);
            case 5:return new SmallBouncer(model);
            case 6:return new MiddleBouncer(model);
            case 7:return new BigBouncer(model);
            default:return null;
        }

    }
}
