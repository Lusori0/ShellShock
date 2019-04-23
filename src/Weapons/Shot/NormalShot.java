package Weapons.Shot;

import Model.GameModel;
import Weapons.Shot.Shot;
import Window.*;

import java.awt.image.BufferedImage;

public class NormalShot extends Shot {



    public NormalShot(GameModel gameModel) {
        super(gameModel,"Normal Shot",1);

        effecttime = 1;

        weaponsize = gameModel.getHeight()/120;

        explosionRadius = 40;

        damage = 20;

    }

    @Override
    public int getLevel() {
        return 1;
    }
}
