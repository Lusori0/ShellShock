package Weapons.Granade;

import Model.GameModel;
import Panzer.Panzer;
import Window.Var;

import java.awt.image.BufferedImage;

public class Granade extends GranadeAbs {
    public Granade(GameModel gameModel) {
        super(gameModel, "Granade", 1, 21, Var.shotIcon);
    }

    @Override
    public void create(int startX, int startY, double winkel, double strength, boolean rechts, Panzer herkunft) {
        bullets.add(new Bullet(startX,startY,winkel,gameModel,rechts,50,strength));
        this.herkunft = herkunft;
    }

    @Override
    public int getLevel() {
        return 1;
    }
}
