package Model;

import Panzer.Panzer;
import Panzer.StandartPanzer;
import Weapons.Weapon;

import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Player {

    private Panzer panzer;
    private Profil profil;
    private CopyOnWriteArrayList<Weapon> weapons;
    private Weapon selectedWeapon,unlockedWeapon;
    private int team,id,waffenanzahl;
    private boolean onTurn;
    private boolean lockedIn;
    private boolean isLevelUp;
    private String name;

    protected GameModel model;


    public Player(GameModel model,int team,int id,Profil profil){
        this.panzer = profil.getPanzer(model);

        this.model = model;

        this.profil = profil;
        this.id = id;
        this.team = team;

        name = profil.getName();

        onTurn = false;

        panzer.changeRohr((int)panzer.getCenterX(), (int) panzer.getCenter().getY(),model);

        weapons = new CopyOnWriteArrayList<>();
    }

    //WAFFEN

    private void addWeapon(Weapon weapon){
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

    //ABSTRACT

    public abstract int action(GameModel model);

    public abstract void move(GameModel model,GameMap map);

    public abstract void prepare(GameModel model);

    public abstract void shoot(GameModel model);

    //LEVEL

    public void addXP() {
        profil.addXp();
    }

    public int levelUp() {
        return profil.levelUp();
    }

    //GETTER

    public boolean isLocalHuman(){
        return false;
    }

    public boolean isKi(){
        return false;
    }

    public boolean isOnTurn() {
        return onTurn;
    }

    public boolean isLockedIn() {
        return lockedIn;
    }

    public boolean isLevelUp() {
        return isLevelUp;
    }

    public Weapon getUnlockedWeapon() {
        return unlockedWeapon;
    }

    public int getLevel(){
        return profil.getLevel();
    }

    public int getXP() {
        return profil.getXp();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getTeam() {
        return team;
    }

    public CopyOnWriteArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public Weapon getSelectedWeapon() {
        return selectedWeapon;
    }

    public Panzer getPanzer(){
        return panzer;
    }

    public int getWeaponCount() {
        return waffenanzahl;
    }

    public Profil getProfil() {
        return profil;
    }

    //SETTER

    public void setLevelUp(boolean levelUp) {
        isLevelUp = levelUp;
    }

    public void setUnlockedWeapon(Weapon unlockedWeapon) {
        this.unlockedWeapon = unlockedWeapon;
    }

    public void setDead(boolean b) {
    }

    public void setOnTurn(boolean onTurn) {
        this.onTurn = onTurn;
    }

    public void setLockedIn(boolean lockedIn) {
        this.lockedIn = lockedIn;
    }

    public void setSelectedWeapon(Weapon selectedWeapon) {
        this.selectedWeapon = selectedWeapon;
    }

}
