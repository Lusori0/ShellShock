package Model;

import java.io.Serializable;

public class Profil implements Serializable {
    private String name;

    public Profil(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
