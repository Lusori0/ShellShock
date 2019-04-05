package Model;

import Views.SinglePlayerView;
import Window.MyWindow;

public class SinglePlayerModel {

    SinglePlayerView singlePlayerView;

    public SinglePlayerModel() {
        this.singlePlayerView = new SinglePlayerView();

        MyWindow.setContent(singlePlayerView);
    }
}
