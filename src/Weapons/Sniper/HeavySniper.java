package Weapons.Sniper;

import Model.GameModel;
import Weapons.Weapon;
import Window.Var;

public class HeavySniper extends SniperAbs {

    public HeavySniper(GameModel model) {
        super(model,"Heavy Sniper",2,19,Var.heavysniper);
        damage = 30;
    }

    @Override
    public int getLevel() {
        return 2;
    }
}
