package Panzer;

import Model.GameModel;
import Window.Var;

public class HeavyPanzer extends Panzer{

    public HeavyPanzer(GameModel model, String name) {
        super(Var.panzer,Var.panzerRohr, 150, 300,name);
    }
}
