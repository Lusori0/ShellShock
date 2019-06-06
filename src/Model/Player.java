package Model;

import Panzer.Panzer;
import Weapons.AirStrike.AirStrike;
import Weapons.AirStrike.BigAirStrike;
import Weapons.AirStrike.Bombs;
import Weapons.Bounce.BigBouncer;
import Weapons.Bounce.Bouncer;
import Weapons.Bounce.MiddleBouncer;
import Weapons.Bounce.SmallBouncer;
import Weapons.Fireball;
import Weapons.*;
import Panzer.*;
import Weapons.GunShots.MG;
import Weapons.GunShots.ShotGun;
import Weapons.MultiShot.TripleShot;
import Weapons.Nuke.MegaNuke;
import Weapons.Nuke.Nuke;
import Weapons.Shot.BigShot;
import Weapons.Shot.HugeShot;
import Weapons.Shot.NormalShot;
import Weapons.Sniper.HeavySniper;
import Weapons.Sniper.OneShot;
import Weapons.Sniper.Sniper;

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

    private Profil profil;
    private String name;
    protected boolean dead;
    private Weapon unlockedWeapon;
    private boolean isLevelUp;

    private int waffenanzahl;

    public Player(GameModel model,int team,int id,Profil profil){
        panzer = new StandartPanzer(model,profil.getName());

        this.profil = profil;

        this.id = id;

        name = profil.getName();

        onTurn = false;

        panzer.changeRohr((int)panzer.getCenterX(), (int) panzer.getCenter().getY(),model);

        this.team = team;

        weapons = new CopyOnWriteArrayList<>();




    }

    public void addWeapon(Weapon weapon){
        for(Weapon w : weapons){
            if(weapon.getId() == w.getId()){
                w.addAnzahl();
                waffenanzahl++;
                return;
            }
        }
        weapons.add(weapon);
        waffenanzahl++;
    }

    public void subWaffenanzahl(){
        waffenanzahl--;
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
        return profil.getXp();
    }

    public void addXP() {
        profil.addXp();
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

    public boolean isLevelUp() {
        return isLevelUp;
    }

    public void setLevelUp(boolean levelUp) {
        isLevelUp = levelUp;
    }

    public void restoreWeapons(GameModel model) {
        while(waffenanzahl < 10){
            addRndWeapon(model);
        }
    }

    public void setSandboxWeapons(GameModel model){
        addWeapon(Weapon.getById(1,model));
        addWeapon(Weapon.getById(4,model));
        addWeapon(Weapon.getById(5,model));
        addWeapon(Weapon.getById(8,model));
        addWeapon(Weapon.getById(12,model));
        addWeapon(Weapon.getById(13,model));
        addWeapon(Weapon.getById(16,model));
        addWeapon(Weapon.getById(18,model));
        addWeapon(Weapon.getById(21,model));
        addWeapon(Weapon.getById(23,model));
        addWeapon(Weapon.getById(24,model));

    }

    private void addRndWeapon(GameModel model) {
        int t = profil.getUnlockedWeapons().size();
        int rnd = (int) (Math.random() * t);
        addWeapon(Weapon.getById(profil.getUnlockedWeapons().get(rnd),model));

    }

    public int getWeaponCount() {
        return waffenanzahl;
    }
}
