package Model;

import java.io.Serializable;
import java.util.*;

public class Profil implements Serializable {
    //Klasse zum Speichern des Profils

    private String name;
    private int level;
    private int xp;
    private String password;
    private List<Integer> unlockedWeapons = new ArrayList<>();

    public Profil(String name, int level, String password, int xp, List<Integer> unlockedWeapons){
        this.name = name;
        this.level = level;
        this.password = password;
        this.xp = xp;
        this.unlockedWeapons = unlockedWeapons;
    }

    public String getName(){
        return name;
    }
    public int getLevel(){return level;}
    public String getPassword(){return password;}
    public List<Integer> getUnlockedWeapons(){
        return unlockedWeapons;
    }
    public int getXp(){
        return xp;
    }
    public void unlockWeapon(int weaponID){
        unlockedWeapons.add(weaponID);
    }

    /*TODO:
        (levelup)
    */


}
