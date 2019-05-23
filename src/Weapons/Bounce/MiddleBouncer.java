package Weapons.Bounce;

import Model.GameModel;

public class MiddleBouncer extends Bouncer {
    public MiddleBouncer(GameModel gameModel) {
        super(gameModel, "Middle Bouncer", 2, 4, true, 12, 80, 20,1.1,6);
    }

    @Override
    public int getLevel() {
        return 2;
    }
}
