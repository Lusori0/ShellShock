package Model;

import Panzer.Panzer;
import Weapons.Fireball;
import Weapons.*;
import Panzer.*;
import Weapons.Shot.HugeShot;
import Weapons.Shot.NormalShot;

import java.util.LinkedList;

public abstract class Player {

    private Panzer panzer;

    private LinkedList<Weapon> weapons;

    private Weapon selectedWeapon;

    private int team;

    public Player(GameModel model,int team){
        panzer = new StandartPanzer();

        this.team = team;

        weapons = new LinkedList<>();

        for(int i = 0; i < 10;i++){
            weapons.add(new NormalShot(model));
        }

        weapons.add(new Fireball(model));

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

    public abstract void action(GameModel model);

    public abstract void move(GameModel model);

    public abstract void prepare(GameModel model);
}
