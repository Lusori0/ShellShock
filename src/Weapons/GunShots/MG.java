

package Weapons.GunShots;

import Model.GameModel;

import java.awt.*;
import Window.*;

public class MG extends GunShotAbs {
    int timer = 10;
    int bigTimer = 0;
    public MG(GameModel gameModel) {
        super(gameModel, "Machine Gun", 2, 17, Var.mg);

    }

    @Override
    public void draw(Graphics2D g2d) {

        if(timer == 10){
            timer = 0;
            if(bigTimer < 10){
                played = false;
                playShotSound();
                shots.add(new Bullet((int)herkunft.getBulletspawn().getX(), (int) herkunft.getBulletspawn().getY(),winkel,gameModel,rechts,strength));

                bigTimer++;
            }
        }else{
            timer ++;
        }

        boolean end = true;

        for(Bullet bullet : shots){
            bullet.draw(g2d,herkunft);
            if(!bullet.isDone()){
                end = false;
            }
        }

        if(end){
            weaponEnd();
        }

    }

    @Override
    public int getLevel() {
        return 2;
    }
}

