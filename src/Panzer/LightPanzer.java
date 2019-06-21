package Panzer;

import Model.GameModel;
import Window.Var;

public class LightPanzer extends Panzer {

    public LightPanzer(GameModel model, String name) {
        super(Var.lightPanzer,Var.panzerRohr, 100, 500,name);
    }
}
