package Panzer;

import Model.GameModel;
import Window.Var;

import java.awt.image.BufferedImage;

public class StandartPanzer extends Panzer{


    public StandartPanzer(GameModel model,String name) {
        super(model,Var.panzer,Var.panzerRohr, 100, 300,name);
    }
}
