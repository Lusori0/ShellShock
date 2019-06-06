package Weapons.Bounce;

import Model.GameModel;
import Window.Var;

public class MiddleBouncer extends Bouncer {
    public MiddleBouncer(GameModel gameModel) {
        super(gameModel, "Middle Bouncer", 2, 4, true, 12, 80, 4,1.1,6,Var.middlebounce);


    }

    @Override
    public int getLevel() {
        return 2;
    }
}
