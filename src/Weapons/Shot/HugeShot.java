package Weapons.Shot;

import Model.GameModel;
import Weapons.Shot.Shot;
import Window.Var;

import java.awt.image.BufferedImage;

public class HugeShot extends Shot {

    public HugeShot(GameModel gameModel) {
        super(gameModel,"Huge Shot", 3);

        effecttime = 2;

        weaponsize = gameModel.getHeight()/60;

        explosionRadius = 100;

        damage = 50;

    }

    @Override
    public int getLevel() {
        return 3;
    }

}
