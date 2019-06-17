package Weapons.Granade;

import Model.GameModel;
import Panzer.Panzer;
import Window.Var;

import java.awt.image.BufferedImage;

public class Trinade extends GranadeAbs {
    public Trinade(GameModel gameModel) {
        super(gameModel, "Trinade", 2, 22, Var.trinade);
    }

    @Override
    public void create(int startX, int startY, double winkel, double strength, boolean rechts, Panzer herkunft) {
        bullets.add(new Bullet(startX,startY,winkel-0.1,gameModel,rechts,10,strength));
        bullets.add(new Bullet(startX,startY,winkel,gameModel,rechts,10,strength));
        bullets.add(new Bullet(startX,startY,winkel+0.1,gameModel,rechts,10,strength));
        this.herkunft = herkunft;
    }

    @Override
    public int getLevel() {
        return 2;
    }
}
