package Weapons.Sniper;

import Model.GameModel;
import Weapons.Weapon;
import Window.Var;

public class Sniper extends SniperAbs {
    public Sniper(GameModel model) {
        super(model,"Sniper",1,18,Var.sniper);
        damage = 250;

    }

    @Override
    public int getLevel() {
        return 1;
    }
}
