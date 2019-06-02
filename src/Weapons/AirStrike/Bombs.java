package Weapons.AirStrike;

import Model.GameModel;
import Weapons.Weapon;
import Window.Var;


public class Bombs extends AirStrikeAbs {
    public Bombs(GameModel model) {
        super(model,"Bombs",3,18,80,30,3,11,Var.bombs);
        
    }

    @Override
    public int getLevel() {
        return 3;
    }
}
