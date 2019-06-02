package Weapons.Shot;

import Model.GameModel;
import Weapons.Shot.Shot;
import Window.Var;

import java.awt.image.BufferedImage;

public class BigShot extends Shot {

    public BigShot(GameModel gameModel) {
        super(gameModel,"Big Shot",2,2,Var.bigshot);

        effecttime = 2;

        weaponsize = 10;

        explosionRadius = 60;

        damage = 30;

    }

    @Override
    public int getLevel() {
        return 2;
    }
}
