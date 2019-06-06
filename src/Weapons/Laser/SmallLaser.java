package Weapons.Laser;

import Model.GameModel;
import Window.Var;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SmallLaser extends LaserAbs {
    public SmallLaser(GameModel gameModel) {
        super(gameModel, "Small Laser", 1, 24, Var.shotIcon, Color.YELLOW,10);

        damage = 10;
    }

    @Override
    public int getLevel() {
        return 1;
    }
}
