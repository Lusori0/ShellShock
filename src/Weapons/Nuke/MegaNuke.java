package Weapons.Nuke;

import Model.GameModel;
import Weapons.Weapon;
import Window.Var;

public class MegaNuke extends NukeAbs {
    public MegaNuke(GameModel model) {
        super(model,"Mega Nuke",1,9,Var.meganuke);

        effecttime = 1;

        weaponsize = 24;

        explosionRadius = 500;

        damage = 40;
    }

    @Override
    public int getLevel() {
        return 2;
    }
}
