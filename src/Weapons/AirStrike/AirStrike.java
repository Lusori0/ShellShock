package Weapons.AirStrike;

import Model.GameModel;
import Weapons.Weapon;
import Window.Var;

public class AirStrike extends AirStrikeAbs {
    public AirStrike(GameModel model) {
        super(model,"Air Strike",1,12,50,5,3,12,Var.airstrike);


    }

    @Override
    public int getLevel() {
        return 1;
    }
}
