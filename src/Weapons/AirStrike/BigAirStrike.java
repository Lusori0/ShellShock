package Weapons.AirStrike;

import Model.GameModel;
import Weapons.Weapon;
import Window.Var;


public class BigAirStrike extends AirStrikeAbs {
    public BigAirStrike(GameModel model) {
        super(model,"Big Air Strike",2,12,50,20,5,10,Var.bigairstrike);


    }

    @Override
    public int getLevel() {
        return 2;
    }
}
