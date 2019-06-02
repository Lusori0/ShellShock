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
    private int xp,level;
    private Weapon unlockedWeapon;

    public Player(GameModel model,int team,int id,Profil profil){
        panzer = new StandartPanzer(model,profil.getName());

        xp = 10;

        level = 1;

        this.id = id;

        name = profil.getName();

        onTurn = false;

        panzer.changeRohr((int)panzer.getCenterX(), (int) panzer.getCenter().getY(),model);

        this.team = team;

        weapons = new CopyOnWriteArrayList<>();


        addWeapon(new MG(model));
        addWeapon(new ShotGun(model));


        addWeapon(new TripleShot(model));

        addWeapon(new Nuke(model));
        addWeapon(new MegaNuke(model));
        addWeapon(new AirStrike(model));
        addWeapon(new BigAirStrike(model));
        addWeapon(new Bombs(model));
        addWeapon(new Sniper(model));
        addWeapon(new HeavySniper(model));
        addWeapon(new OneShot(model));

        for(int i = 0; i < 10;i++){
            addWeapon(new NormalShot(model));
        }

        addWeapon(new MiddleBouncer(model));
        addWeapon(new SmallBouncer(model));
        addWeapon(new Fireball(model));
        addWeapon(new HugeShot(model));
        addWeapon(new BigShot(model));



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
}
