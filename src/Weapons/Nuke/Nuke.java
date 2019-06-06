package Weapons.Nuke;

import Model.GameModel;
import Weapons.Weapon;
import Window.Var;

public class Nuke extends NukeAbs {
    public Nuke(GameModel model) {
        super(model,"Nuke",1,8,Var.nuke);

        effecttime = 1;

        weaponsize = 24;

        explosionRadius = 300;

        damage = 30;
    }

    @Override
    public int getLevel() {
        return 1;
    }
}
