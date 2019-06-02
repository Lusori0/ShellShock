package Weapons.Bounce;

import Model.GameModel;
import Window.Var;

public class BigBouncer extends Bouncer {

    public BigBouncer(GameModel model){
        super(model,"Big Bouncer",3,8,true,24,100,20,1,7,Var.bigbounce);


    }

    public int getLevel(){
        return 3;
    }



}
