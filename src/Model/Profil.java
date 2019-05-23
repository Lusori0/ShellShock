package Model;

import java.io.Serializable;

public class Profil implements Serializable {
    //Klasse zum Speichern des Profils

    private String name;
    private int level;
    private String password;

    public Profil(String name, int level, String password){
        this.name = name;
        this.level = level;
        this.password = password;

    }

    public String getName(){
        return name;
    }
    public int getLevel(){return level;}
    public String getPassword(){return password;}

    /*TODO:
        waffen liste int
        get unlocked weap return weapon list
        get xp
        get lvl
        unlock new weapon
        (levelup)
    */


}
