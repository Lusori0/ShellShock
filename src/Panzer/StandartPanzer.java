package Panzer;

import Model.GameModel;
import Window.Var;

public class StandartPanzer extends Panzer{


    public StandartPanzer(GameModel model,String name) {
        super(Var.panzer,Var.panzerRohr, 100, 300,name);
    }
}
