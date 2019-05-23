package Model;

import Views.SinglePlayerView;
import Window.MyWindow;

import java.util.LinkedList;
import java.util.List;

public class SinglePlayerModel {



    public SinglePlayerModel() {

        MyWindow.setContent(new SinglePlayerView(this));
    }

    public void gegenKiAction(){
        new PreGameModel(GameType.AGAINST_AI,false);
    }

    public void sandboxAction(){
        new PreGameModel(GameType.SANDBOX,true);
    }

    public void backAction(){
        new MainMenuModel();
    }
}
