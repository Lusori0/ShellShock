package Model;

import java.io.Serializable;

public class Profil implements Serializable {

    private String name;
    private int level;

    public Profil(String name, int level){
        this.name = name;
        this.level = level;
    }

    public String getName(){
        return name;
    }
    public int getLevel(){return level;}
}
