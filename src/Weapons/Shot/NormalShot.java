package Weapons.Shot;

import Model.GameModel;
import Window.*;

public class NormalShot extends Shot {



    public NormalShot(GameModel gameModel) {
        super(gameModel,"Normal Shot", 1,Var.shot);

        effecttime = 1;

        weaponsize = 12;

        explosionRadius = 80;

        damage = 5;

    }

    @Override
    public int getLevel() {
        return 1;
    }
}
