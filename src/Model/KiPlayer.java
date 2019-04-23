package Model;

import Window.MyKeys;

import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class KiPlayer extends Player {

    Player ziel;
    private boolean movementFinished = false;
    private double targety,x,myY;

    public KiPlayer(GameModel model, int team) {
        super(model, team);
    }

    public void prepare(GameModel model){
        ziel = model.getGegener();

        targety = getPanzer().getCenter().getY()-ziel.getPanzer().getCenter().getY();
        x = (ziel.getPanzer().getCenterX() - getPanzer().getCenterX());
        myY = getPanzer().getCenter().getY();
    }

    public void move(GameModel model){



        targety = getPanzer().getCenter().getY()-ziel.getPanzer().getCenter().getY();
        x = (ziel.getPanzer().getCenterX() - getPanzer().getCenterX());
        myY = getPanzer().getCenter().getY();
        if(!movementFinished){
            if(getPanzer().getSprit() > 0){
                if(Math.abs(x) < model.getHeight()/20){
                    if(x > 0){
                        getPanzer().setMoveLeft(true);
                    }else{
                        getPanzer().setMoveRight(true);
                    }
                }else{
                    movementFinished = true;
                    action(model);
                }
            }else{
                movementFinished = true;
                action(model);
            }
        }
    }

    public void action(GameModel model){


        //winkel = atan((targety + down * (targetx-myx) - myy)/(targetx - myx)




        double down = getSelectedWeapon().getDownspeed();
        double speed = getSelectedWeapon().getSpeed();

        double finalWinkel = 999;
        double finalSpeed = 1;

        Point2D highest;

        if(x > 0) {
            highest = model.getMap().getHeighestPoint((int)getPanzer().getCenterX(), (int) ziel.getPanzer().getCenterX());
        }else{
            highest = model.getMap().getHeighestPoint((int)ziel.getPanzer().getCenterX(), (int) getPanzer().getCenterX());
        }

        System.out.println(getPanzer().getCenter().getY() + " : " + highest.getY());

        /*for(int i = 0;i < 10;i++){
            double wink = -Math.atan((Math.pow(speed,2) - Math.sqrt(Math.pow(speed,4) - down * (down * (x * x) + 2 * targety * (speed * speed))))/(down * x));
            double wink2 = -Math.atan((Math.pow(speed,2) + Math.sqrt(Math.pow(speed,4) - down * (down * (x * x) + 2 * targety * (speed * speed))))/(down * x));
            System.out.println(finalWinkel);
            if(Math.atan((Math.pow(speed,2) - Math.sqrt(Math.pow(speed,4) - down * (down * (highest.getX() * highest.getX()) + 2 * (myY-highest.getY()) * (speed * speed))))/(down * highest.getX())) > wink){
                System.out.println("test");
                if(wink < finalWinkel){
                    finalWinkel = wink;
                    finalSpeed = speed/getSelectedWeapon().getSpeed();
                }
            }else if(Math.atan((Math.pow(speed,2) + Math.sqrt(Math.pow(speed,4) - down * (down * (highest.getX() * highest.getX()) + 2 * (myY-highest.getY()) * (speed * speed))))/(down * highest.getX())) > wink2){
                if(wink < finalWinkel){
                    finalWinkel = wink;
                    finalSpeed = speed/getSelectedWeapon().getSpeed();
                }
            }

            speed -= speed * (i+1)/10;

        }*/



        double wink = -Math.atan((Math.pow(speed,2) - Math.sqrt(Math.pow(speed,4) - down * (down * (x * x) + 2 * targety * (speed * speed))))/(down * x));
        double wink2 = -Math.atan((Math.pow(speed,2) + Math.sqrt(Math.pow(speed,4) - down * (down * (x * x) + 2 * targety * (speed * speed))))/(down * x));
        double wink3 = -Math.atan((Math.pow(speed/2,2) + Math.sqrt(Math.pow(speed/2,4) - down * (down * (x * x) + 2 * targety * ((speed/2) * (speed/2)))))/(down * x));

        double xtemp = highest.getX() - getPanzer().getCenterX();

        if(x>0) {

            if (Math.atan((Math.pow(speed, 2) - Math.sqrt(Math.pow(speed, 4) - down * (down * (xtemp * xtemp) + 2 * (myY - highest.getY()) * (speed * speed)))) / (down * xtemp)) < wink) {

                finalWinkel = wink;
            } else {
                for(int i = 1;i < 10;i++) {
                    if (Math.atan((Math.pow(speed / i, 2) - Math.sqrt(Math.pow(speed / i, 4) - down * (down * (xtemp * xtemp) + 2 * (myY - highest.getY()) * ((speed / i) * (speed / i))))) / (down * xtemp)) >
                            -Math.atan((Math.pow(speed/i,2) + Math.sqrt(Math.pow(speed/i,4) - down * (down * (x * x) + 2 * targety * ((speed/i) * (speed/i)))))/(down * x))) {
                        System.out.println("test");
                        finalWinkel = -Math.atan((Math.pow(speed/i,2) + Math.sqrt(Math.pow(speed/i,4) - down * (down * (x * x) + 2 * targety * ((speed/i) * (speed/i)))))/(down * x));
                        finalSpeed = (speed / i)/getSelectedWeapon().getSpeed();
                    }
                }

                if(finalWinkel==999){
                    finalWinkel = wink2;
                }
            }
        }else{

            if (Math.atan((Math.pow(speed, 2) - Math.sqrt(Math.pow(speed, 4) - down * (down * (xtemp * xtemp) + 2 * (myY - highest.getY()) * (speed * speed)))) / (down * xtemp)) < wink) {

                finalWinkel = wink;
            } else {
                for(int i = 1;i < 10;i++) {
                    if (Math.atan((Math.pow(speed / i, 2) - Math.sqrt(Math.pow(speed / i, 4) - down * (down * (xtemp * xtemp) + 2 * (myY - highest.getY()) * ((speed / i) * (speed / i))))) / (down * xtemp)) <
                            -Math.atan((Math.pow(speed/i,2) + Math.sqrt(Math.pow(speed/i,4) - down * (down * (x * x) + 2 * targety * ((speed/i) * (speed/i)))))/(down * x))) {
                        System.out.println("test");
                        finalWinkel = -Math.atan((Math.pow(speed/i,2) + Math.sqrt(Math.pow(speed/i,4) - down * (down * (x * x) + 2 * targety * ((speed/i) * (speed/i)))))/(down * x));
                        finalSpeed = (speed / i)/getSelectedWeapon().getSpeed();
                    }
                }

                if(finalWinkel==999){
                    finalWinkel = wink2;
                }
            }
        }







            if (x > 0) {
                getSelectedWeapon().create((int) (getPanzer().getBulletspawn().getX()), (int) getPanzer().getBulletspawn().getY(),
                        Math.PI + finalWinkel, finalSpeed, getPanzer().isOrientationRight());
            } else {
                getSelectedWeapon().create((int) (getPanzer().getBulletspawn().getX()), (int) getPanzer().getBulletspawn().getY(),
                        finalWinkel, finalSpeed, getPanzer().isOrientationRight());
            }


        model.addWeapon(getSelectedWeapon());

        getWeapons().remove(getSelectedWeapon());

        setSelectedWeapon(getWeapons().getFirst());

        model.setShot(true);

        movementFinished = false;
    }

    @Override
    public boolean isKi() {
        return true;
    }


}
