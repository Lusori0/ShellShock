package Weapons.MultiShot;

import Model.GameModel;
import Weapons.Weapon;
import Window.Var;


public class MultiShot extends MultiShotAbs {


    public MultiShot(GameModel gameModel) {
        super(gameModel, "Triple Shot", 3, 15, 5, Var.shotIcon);
    }

    @Override
    public int getLevel() {
        return 3;
    }
}