package Weapons.Shot;

import Model.GameModel;
import Window.Var;

public class HugeShot extends Shot {

    public HugeShot(GameModel gameModel) {
        super(gameModel,"Huge Shot", 3,Var.hughshot);


        effecttime = 2;

        weaponsize = 15;

        explosionRadius = 100;

        damage = 12;

    }

    @Override
    public int getLevel() {
        return 3;
    }

}
