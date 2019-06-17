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
    private float musicVolume;
    private float inGameVolume;

    public Profil(String name, int level, String password, int xp, List<Integer> unlockedWeapons, float musicVolume, float inGameVolume){
        this.name = name;
        this.level = level;
        this.password = password;
        this.xp = xp;
        this.unlockedWeapons = unlockedWeapons;
        this.musicVolume = musicVolume;
        this.inGameVolume = inGameVolume;
    }

    public String getName(){
        return name;
    }
    public int getLevel(){return level;}
    public String getPassword(){return password;}
    public float getMusicVolume(){return musicVolume;}
    public float getInGameVolume(){return inGameVolume;}
    public void setMusicVolume(float v){musicVolume = v;}
    public void setInGameVolume(float v){inGameVolume = v;}
    public List<Integer> getUnlockedWeapons(){
        return unlockedWeapons;
    }
    public int getXp(){
        return xp;
    }
    public void unlockWeapon(int weaponID){
        unlockedWeapons.add(weaponID);
    }
    public void addXp(){
        xp++;
    }

    public int levelUp(){
        level++;
        System.out.println(level - 1);
        switch(level - 1){
            case 1:
                for(int i = 0;i < 5;i++) {
                    unlockWeapon(2);
                }
                return 2;
            case 2:
                for(int i = 0;i < 5;i++) {
                    unlockWeapon(4);
                }
                return 4;
            case 3:
                for(int i = 0;i < 3;i++) {
                    unlockWeapon(24);
                }
                return 24;
            case 4:
                for(int i = 0;i < 5;i++) {
                    unlockWeapon(13);
                }
                return 13;
            case 5:
                for(int i = 0;i < 3;i++) {
                    unlockWeapon(16);
                }
                return 16;
            case 6:
                for(int i = 0;i < 4;i++) {
                    unlockWeapon(19);
                }
                return 19;
            case 7:
                for(int i = 0;i < 4;i++) {
                    unlockWeapon(6);
                }
                return 6;
            case 8:
                for(int i = 0;i < 5;i++) {
                    unlockWeapon(14);
                }
                return 14;
            case 9:
                for(int i = 0;i < 3;i++) {
                    unlockWeapon(22);
                }
                return 22;
            case 10:
                for(int i = 0;i < 4;i++) {
                    unlockWeapon(10);
                }
                return 10;
            case 11:
                for(int i = 0;i < 4;i++) {
                    unlockWeapon(3);
                }
                return 3;
            case 12:
                for(int i = 0;i < 4;i++) {
                    unlockWeapon(15);
                }
                return 15;
            case 13:
                for(int i = 0;i < 3;i++) {
                    unlockWeapon(23);
                }
                return 23;
            case 14:
                for(int i = 0;i < 3;i++) {
                    unlockWeapon(7);
                }
                return 7;
            case 15:
                for(int i = 0;i < 3;i++) {
                    unlockWeapon(17);
                }
                return 17;
            case 16:
                for(int i = 0;i < 3;i++) {
                    unlockWeapon(11);
                }
                return 11;
            case 17:
                for(int i = 0;i < 2;i++) {
                    unlockWeapon(20);
                }
                return 20;
            case 18:
                for(int i = 0;i < 1;i++) {
                    unlockWeapon(8);
                }
                return 8;
            case 19:
                for(int i = 0;i < 1;i++) {
                    unlockWeapon(25);
                }
                return 8;
            case 20:
                for(int i = 0;i < 1;i++) {
                    unlockWeapon(9);
                }
                return 8;
            default:return 0;
        }
    }
}
