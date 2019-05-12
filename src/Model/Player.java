package Model;

import Network.Server;
import Panzer.Panzer;
import Weapons.Bounce.Bouncer;
import Weapons.Fireball;
import Weapons.*;
import Panzer.*;
import Weapons.Shot.HugeShot;
import Weapons.Shot.NormalShot;

import java.awt.*;
import java.util.LinkedList;

public abstract class Player {

    private Panzer panzer;

    private LinkedList<Weapon> weapons;

    private Weapon selectedWeapon;

    private int team;

    private boolean onTurn,lockedIn;

    private int id;

    private String name;

    public Player(GameModel model,int team,int id,String name){
        panzer = new StandartPanzer();

        this.id = id;
        this.name = name;

        onTurn = false;

        panzer.changeRohr((int)panzer.getCenterX(), (int) panzer.getCenter().getY(),model);

        this.team = team;

        weapons = new LinkedList<>();

        for(int i = 0; i < 10;i++){
            weapons.add(new NormalShot(model));
        }

        weapons.add(new Bouncer(model,"ww",1));

        selectedWeapon = weapons.getFirst();


    }

    public Panzer getPanzer(){
        return panzer;
    }

    public boolean isLocalHuman(){
        return false;
    }

    public boolean isKi(){
        return false;
    }

    public LinkedList<Weapon> getWeapons() {
        return weapons;
    }

    public Weapon getSelectedWeapon() {
        return selectedWeapon;
    }

    public void setSelectedWeapon(Weapon selectedWeapon) {
        this.selectedWeapon = selectedWeapon;
    }

    public int getTeam() {
        return team;
    }

    public abstract int action(GameModel model);

    public abstract void move(GameModel model,GameMap map);

    public abstract void prepare(GameModel model);

    public boolean isOnTurn() {
        return onTurn;
    }

    public void setOnTurn(boolean onTurn) {
        this.onTurn = onTurn;
    }

    public boolean isLockedIn() {
        return lockedIn;
    }

    public void setLockedIn(boolean lockedIn) {
        this.lockedIn = lockedIn;
    }

    public abstract void shoot(GameModel model);

    public void send(Server server){
        String xPosition = String.valueOf((int)getPanzer().getxPosition());
        String yPosition = String.valueOf((int)getPanzer().getyPosition());
        String drawWinkel = String.valueOf(getPanzer().getDrawWinkel());
        String winkel = String.valueOf(getPanzer().getWinkel());
        String rohrwinkel = String.valueOf(getPanzer().getRohrWinkel());
        String right = String.valueOf(getPanzer().isOrientationRight());

        server.sendtoAll(("move:"+ id + ":" + xPosition + ":" + yPosition + ":" + drawWinkel + ":" + winkel + ":" + rohrwinkel + ":" + right).getBytes());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setValues(int x, int y,double drawWinkel,double winkel,double rohrwinkel,boolean right){
        getPanzer().setxPosition(x);
        getPanzer().setyPosition(y);
        getPanzer().setDrawWinkel(drawWinkel,winkel,rohrwinkel);
        getPanzer().setOrientationRight(right);

    }
}
