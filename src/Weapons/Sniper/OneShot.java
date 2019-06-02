package Weapons.Sniper;

import Model.GameModel;
import Weapons.Weapon;
import Window.Var;

public class OneShot extends SniperAbs {
    public OneShot(GameModel model) {
        super(model,"One Shot",3,20,Var.oneshot);
        damage = 100;

    }

    @Override
    public int getLevel() {
        return 3;
    }
}

