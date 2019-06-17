package Weapons.MultiShot;

import Model.GameModel;
import Window.Var;

public class TripleShot extends MultiShotAbs {


    public TripleShot(GameModel gameModel) {
        super(gameModel, "Triple Shot", 1, 13, 3, Var.trishot);
    }

    @Override
    public int getLevel() {
        return 1;
    }
}
