package Weapons.MultiShot;

import Model.GameModel;
import Weapons.Weapon;
import Window.Var;


public class QuadShot extends MultiShotAbs {


        public QuadShot(GameModel gameModel) {
            super(gameModel, "Triple Shot", 2, 14, 4, Var.quadshot);
        }

        @Override
        public int getLevel() {
            return 2;
        }
    }

