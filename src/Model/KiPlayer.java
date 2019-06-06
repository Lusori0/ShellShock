package Model;

import java.awt.geom.Point2D;
import java.util.LinkedList;

public class KiPlayer extends Player {

    Player ziel;
    private boolean movementFinished = false;
    private double targety,x,myY;

    private double finalWinkel,finalSpeed;

    private int movementMode = 0;

    private int difficoulty;

    public KiPlayer(GameModel model, int team,int id,int difficoulty,Profil profil) {
        super(model, team,id,new Profil("KI",profil.getLevel(),"leer",profil.getXp(),profil.getUnlockedWeapons()));
        this.difficoulty = difficoulty;
    }

    public void prepare(GameModel model){
        if(changeTarget(model)){
            targety = getPanzer().getBulletspawn().getY()-ziel.getPanzer().getBulletspawn().getY();
            x = (ziel.getPanzer().getBulletspawn().getX() - getPanzer().getBulletspawn().getX());
            myY = getPanzer().getBulletspawn().getY();

        }else{
            if(action(model) == 2) {
                movementMode = 1;
            }
        }

    }

    public void move(GameModel model,GameMap map){




        if(isOnTurn()) {
            if(ziel == null){
                prepare(model);
            }




            targety = getPanzer().getBulletspawn().getY() - ziel.getPanzer().getBulletspawn().getY();
            x = (ziel.getPanzer().getBulletspawn().getX() - getPanzer().getBulletspawn().getX());
            myY = getPanzer().getBulletspawn().getY();
            if (!movementFinished) {
                if (getPanzer().getSprit() > 0) {

                    switch (movementMode){
                        case 0:
                            if (Math.abs(x) < model.getHeight() / 20) {
                                if (x > 0) {
                                    getPanzer().setMoveLeft(true);
                                } else {
                                    getPanzer().setMoveRight(true);
                                }
                            } else {
                                movementFinished = true;

                            }
                            break;

                        case 1:
                            if(x > 0){
                                getPanzer().setMoveRight(true);
                            }else{
                                getPanzer().setMoveLeft(true);
                            }

                            break;
                    }


                } else {
                    movementFinished = true;
                    movementMode = 0;
                }
                getPanzer().move(map);
            }else{
                action(model);
                lockIn();
            }


            //TODO movement

        }else{
            getPanzer().moveNotTurn(map);
        }
    }

    public int action(GameModel model){


        //winkel = atan((targety + down * (targetx-myx) - myy)/(targetx - myx)





        double down = getSelectedWeapon().getDownspeed();
        double speed = getSelectedWeapon().getSpeed();

        finalWinkel = 999;
        finalSpeed = 1;

        Point2D highest;







        double wink = -Math.atan((Math.pow(speed,2) - Math.sqrt(Math.pow(speed,4) - down * (down * (x * x) + 2 * targety * (speed * speed))))/(down * x));
        double wink2 = -Math.atan((Math.pow(speed,2) + Math.sqrt(Math.pow(speed,4) - down * (down * (x * x) + 2 * targety * (speed * speed))))/(down * x));
        double wink3 = -Math.atan((Math.pow(speed/2,2) + Math.sqrt(Math.pow(speed/2,4) - down * (down * (x * x) + 2 * targety * ((speed/2) * (speed/2)))))/(down * x));

        if(x>0) {

            if (isPossible((int)getPanzer().getCenterX(), (int) ziel.getPanzer().getCenterX(),wink,speed,down,model)) {


                finalWinkel = wink;
            } else {
                for(double i = 1;i < 10;i += 0.1) {
                    if (isPossible((int)getPanzer().getBulletspawn().getX(), (int) ziel.getPanzer().getBulletspawn().getX(),-Math.atan((Math.pow(speed/i,2) + Math.sqrt(Math.pow(speed/i,4) - down * (down * (x * x) + 2 * targety * ((speed/i) * (speed/i)))))/(down * x)),speed/i,down,model)) {

                        finalWinkel = -Math.atan((Math.pow(speed/i,2) + Math.sqrt(Math.pow(speed/i,4) - down * (down * (x * x) + 2 * targety * ((speed/i) * (speed/i)))))/(down * x));
                        finalSpeed = (speed / i)/getSelectedWeapon().getSpeed();
                    }
                }



                if(finalWinkel==999){
                    finalWinkel = wink2;
                }
            }
        }else if(x<0){

            if (isPossible((int)getPanzer().getBulletspawn().getX(), (int) ziel.getPanzer().getBulletspawn().getX(),wink,speed,down,model)) {

                finalWinkel = wink;
            } else {
                for(double i = 1;i < 10;i += 0.1) {
                    if (isPossible((int)getPanzer().getCenterX(), (int) ziel.getPanzer().getCenterX(),-Math.atan((Math.pow(speed/i,2) + Math.sqrt(Math.pow(speed/i,4) - down * (down * (x * x) + 2 * targety * ((speed/i) * (speed/i)))))/(down * x)),speed/i,down,model)) {

                        finalWinkel = -Math.atan((Math.pow(speed/i,2) + Math.sqrt(Math.pow(speed/i,4) - down * (down * (x * x) + 2 * targety * ((speed/i) * (speed/i)))))/(down * x));
                        finalSpeed = (speed / i)/getSelectedWeapon().getSpeed();
                    }
                }

                if(finalWinkel==999){
                    finalWinkel = wink2;
                }
            }
        }else{


            finalSpeed = 1;

            double xtempRohr;
            double ytempRohr;


            if(x<0) {
                finalWinkel = 1;
                xtempRohr = -Math.sin(Math.PI / 2 + finalWinkel) * 100 + getPanzer().getCenterX();
                ytempRohr = -Math.cos(-Math.PI / 2 + finalWinkel) * 100 + getPanzer().getCenter().getY();

            }else{

                finalWinkel = -1;
                xtempRohr = -Math.sin(-Math.PI / 2 + finalWinkel) * 100 + getPanzer().getCenterX();
                ytempRohr = -Math.cos(Math.PI / 2 + finalWinkel) * 100 + getPanzer().getCenter().getY();

            }



            getPanzer().setTemps();

            getPanzer().changeRohr((int) xtempRohr,(int)ytempRohr,model);

            return 1;
        }

        if(Double.isNaN(finalWinkel)){


            finalSpeed = 1;

            double xtempRohr;
            double ytempRohr;


            if(x<0) {
                finalWinkel = 1;
                xtempRohr = -Math.sin(Math.PI / 2 + finalWinkel) * 100 + getPanzer().getCenterX();
                ytempRohr = -Math.cos(-Math.PI / 2 + finalWinkel) * 100 + getPanzer().getCenter().getY();

            }else{

                finalWinkel = -1;
                xtempRohr = -Math.sin(-Math.PI / 2 + finalWinkel) * 100 + getPanzer().getCenterX();
                ytempRohr = -Math.cos(Math.PI / 2 + finalWinkel) * 100 + getPanzer().getCenter().getY();

            }



            getPanzer().setTemps();

            getPanzer().changeRohr((int) xtempRohr,(int)ytempRohr,model);

            return 2;
        }






        //TODO improve

        double xtempRohr;
        double ytempRohr;


        if(x<0) {
             xtempRohr = -Math.sin(Math.PI / 2 + finalWinkel) * 100 + getPanzer().getCenterX();
             ytempRohr = -Math.cos(-Math.PI / 2 + finalWinkel) * 100 + getPanzer().getCenter().getY();
        }else{

            xtempRohr = -Math.sin(-Math.PI / 2 + finalWinkel) * 100 + getPanzer().getCenterX();
            ytempRohr = -Math.cos(Math.PI / 2 + finalWinkel) * 100 + getPanzer().getCenter().getY();
        }



        getPanzer().setTemps();

        getPanzer().changeRohr((int) xtempRohr,(int)ytempRohr,model);







        return 0;
    }

    public void lockIn(){
        setLockedIn(true);


        movementFinished = false;
    }

    public void shoot(GameModel model){

        if(getSelectedWeapon().getId() == 24 || getSelectedWeapon().getId() == 25){
            targety = getPanzer().getBulletspawn().getY()-ziel.getPanzer().getBulletspawn().getY();
            x = (ziel.getPanzer().getBulletspawn().getX() - getPanzer().getBulletspawn().getX());
            myY = getPanzer().getBulletspawn().getY();
            double ytemp = ziel.getPanzer().getBulletspawn().getY() - getPanzer().getBulletspawn().getY();

            if(x < 0){
                finalWinkel = Math.atan2(ytemp, x) + Math.PI;
            }else {
                finalWinkel = Math.atan2(ytemp, x);
            }
            System.out.println(finalWinkel);

        }

        finalWinkel += (-0.1 + Math.random() * 0.2)*difficoulty;

        double xtempRohr;
        double ytempRohr;


        if(x<0) {
            xtempRohr = -Math.sin(Math.PI / 2 + finalWinkel) * 100 + getPanzer().getCenterX();
            ytempRohr = -Math.cos(-Math.PI / 2 + finalWinkel) * 100 + getPanzer().getCenter().getY();
        }else{

            xtempRohr = -Math.sin(-Math.PI / 2 + finalWinkel) * 100 + getPanzer().getCenterX();
            ytempRohr = -Math.cos(Math.PI / 2 + finalWinkel) * 100 + getPanzer().getCenter().getY();
        }



        getPanzer().setTemps();

        getPanzer().changeRohr((int) xtempRohr,(int)ytempRohr,model);

        if (x > 0) {
            getSelectedWeapon().create((int) (getPanzer().getBulletspawn().getX()), (int) getPanzer().getBulletspawn().getY(),
                    finalWinkel - Math.PI, finalSpeed, false,getPanzer());
        } else {
            getSelectedWeapon().create((int) (getPanzer().getBulletspawn().getX()), (int) getPanzer().getBulletspawn().getY(),
                    finalWinkel, finalSpeed,false,getPanzer());
        }
    }

    public boolean isPossible(int start,int end,double winkel,double speed,double down,GameModel model){

        int[] temp,xtemp;

        if(x>0) {

             xtemp = model.getMap().getX(start, end);

            temp = model.getMap().getY(start, end);

            for(int i = 0;i < temp.length;i++) {


                if (xtemp[i] > 0) {
                    double t = xtemp[i] - getPanzer().getBulletspawn().getX();

                    //double yBullet = (t/(Math.cos(winkel) * speed) * Math.sin(winkel) * speed - t/(Math.cos(winkel) * speed)*down);

                    double yBullet = (t / (Math.cos(winkel) * speed) * Math.sin(winkel) * speed + (t / (Math.cos(winkel) * speed) * t / (Math.cos(winkel) * speed)) * (down / 2));





                    if (Double.isNaN(yBullet)) {
                        return false;
                    }

                    if (myY + yBullet > temp[i] + 1) {
                        return false;
                    }

                }
            }
            return true;

        }else{
            xtemp = model.getMap().getX(end, start);

            temp = model.getMap().getY(end, start);

                for(int i = temp.length-1;i > 0;i--) {


                    if (xtemp[i] > 0) {
                        double t = xtemp[i] - getPanzer().getCenterX();

                        //double yBullet = (t/(Math.cos(winkel) * speed) * Math.sin(winkel) * speed - t/(Math.cos(winkel) * speed)*down);

                        double yBullet = (t / (Math.cos(winkel) * speed) * Math.sin(winkel) * speed + (t / (Math.cos(winkel) * speed) * t / (Math.cos(winkel) * speed)) * (down / 2));






                        if (Double.isNaN(yBullet)) {
                            return false;
                        }

                        if (myY + yBullet > temp[i] + 1) {
                            return false;
                        }

                    }
                }

                return true;
        }
    }

    public boolean changeTarget(GameModel model){
        LinkedList<Player> player = model.getPlayer();
        for(Player player1 : player){
            if(player1.getTeam() != getTeam()) {
                Player oldZiel = ziel;
                ziel = player1;

                targety = getPanzer().getBulletspawn().getY() - ziel.getPanzer().getBulletspawn().getY();
                x = (ziel.getPanzer().getBulletspawn().getX() - getPanzer().getBulletspawn().getX());
                myY = getPanzer().getBulletspawn().getY();

                System.out.println(x + ":" + targety);

                if (action(model) == 0) {
                    return true;
                }else{
                    if(oldZiel != null){
                        if(Math.abs((ziel.getPanzer().getBulletspawn().getX() - getPanzer().getBulletspawn().getX())) > Math.abs((oldZiel.getPanzer().getBulletspawn().getX() - getPanzer().getBulletspawn().getX()))){
                            ziel = oldZiel;
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean isKi() {
        return true;
    }


}
