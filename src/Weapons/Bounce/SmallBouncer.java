package Weapons.Bounce;

import Model.GameModel;

public class SmallBouncer extends Bouncer {

    public SmallBouncer(GameModel gameModel) {
        super(gameModel, "Small Bouncer", 1, 2, false, 12, 80, 20,1.4,5);
    }

    @Override
    public int getLevel() {
        return 1;
    }
}
