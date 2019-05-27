package Model;


import Panzer.Panzer;
import Weapons.Bounce.BigBouncer;
import Weapons.Bounce.Bouncer;
import Weapons.Bounce.MiddleBouncer;
import Weapons.Bounce.SmallBouncer;
import Weapons.Fireball;
import Weapons.*;
import Panzer.*;
import Weapons.Shot.BigShot;
import Weapons.Shot.HugeShot;
import Weapons.Shot.NormalShot;

import java.awt.*;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Player {

    private Panzer panzer;

    private CopyOnWriteArrayList<Weapon> weapons;

    private Weapon selectedWeapon;

    private int team;

    private boolean onTurn,lockedIn;

    private int id;

    private String name;
    protected boolean dead;
    private Profil profil;
    private int xp,level;
    private Weapon unlockedWeapon;

    public Player(GameModel model,int team,int id,Profil profil){
        panzer = new StandartPanzer(model,profil.getName());

        this.profil = profil;

        xp = profil.getXp();

        level = profil.getLevel();

        this.id = id;
        this.name = profil.getName();

        onTurn = false;

        panzer.changeRohr((int)panzer.getCenterX(), (int) panzer.getCenter().getY(),model);

        this.team = team;

        weapons = new CopyOnWriteArrayList<>();




        for(int i = 0;i < 10;i++){
            int t = profil.getUnlockedWeapons().size();
            int nr = (int) (t * Math.random());
            addWeapon(Weapon.getById(profil.getUnlockedWeapons().get(nr),model));
        }

        selectedWeapon = weapons.get(0);


    }

    public void addWeapon(Weapon weapon){
        for(Weapon w : weapons){
            if(weapon.getId() == w.getId()){
                w.addAnzahl();
                return;
            }
        }
        weapons.add(weapon);
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

    public CopyOnWriteArrayList<Weapon> getWeapons() {
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

    public void setDead(boolean b) {
        dead = true;
    }

    public boolean isDead() {
        return dead;
    }

    public int getXP() {
        return xp;
    }

    public void addXP() {
        profil.addXp();
        xp++;
    }

    public int getLevel(){
        return profil.getLevel();
    }

    public int levelUp() {
        return profil.levelUp();
    }

    public void setUnlockedWeapon(Weapon unlockedWeapon) {
        this.unlockedWeapon = unlockedWeapon;
    }

    public Weapon getUnlockedWeapon() {
        return unlockedWeapon;
    }
}
