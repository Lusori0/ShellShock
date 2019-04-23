package Model;

import Views.SinglePlayerView;
import Window.MyWindow;

public class SinglePlayerModel {



    public SinglePlayerModel() {

        MyWindow.setContent(new SinglePlayerView(this));
    }

    public void gegenKiAction(){
        new PreGameModel();
    }

    public void sandboxAction(){
        new PreGameModel();
    }

    public void backAction(){
        new MainMenuModel();
    }
}
