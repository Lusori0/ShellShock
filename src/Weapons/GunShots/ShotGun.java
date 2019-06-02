package Weapons.GunShots;

import Model.GameModel;

import java.awt.*;
import Window.*;

public class ShotGun extends GunShotAbs {
    int timer = 20;
    int bigTimer = 0;
    public ShotGun(GameModel gameModel) {
        super(gameModel, "Shot Gun", 2, 16,Var.shotIcon);
    }

    @Override
    public void draw(Graphics2D g2d) {

        if(timer == 20){
            timer = 0;
            if(bigTimer < 3){
                played = false;
                playShotSound();
                shots.add(new Bullet(startX,startY,winkel-0.05,gameModel,rechts));
                shots.add(new Bullet(startX,startY,winkel,gameModel,rechts));
                shots.add(new Bullet(startX,startY,winkel+0.05,gameModel,rechts));

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
