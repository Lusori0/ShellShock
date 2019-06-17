package Weapons.Shot;

import Model.GameModel;
import Window.Var;

public class BigShot extends Shot {

    public BigShot(GameModel gameModel) {
        super(gameModel,"Big Shot", 2,Var.bigshot);

        effecttime = 2;

        weaponsize = 10;

        explosionRadius = 60;

        damage = 8;

    }

    @Override
    public int getLevel() {
        return 2;
    }
}
