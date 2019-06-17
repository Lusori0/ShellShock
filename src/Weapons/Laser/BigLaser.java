package Weapons.Laser;

import Model.GameModel;
import Window.Var;

import java.awt.*;

public class BigLaser extends LaserAbs {
    public BigLaser(GameModel gameModel) {
        super(gameModel, "Big Laser", 2, 25, Var.biglaser, Color.RED,20);

        damage = 25;
    }

    @Override
    public int getLevel() {
        return 2;
    }
}
