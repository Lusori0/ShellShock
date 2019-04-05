package Model;

import Views.PreGameView;
import Window.MyWindow;

public class PreGameModel {
    PreGameView preGameView;

    public PreGameModel() {
        this.preGameView = new PreGameView(this);

        MyWindow.setContent(preGameView);
    }
}
