package Model;

import Views.PreGameView;
import Window.MyWindow;

import java.util.LinkedList;

public class PreGameModel {

    public PreGameModel() {
        MyWindow.setContent(new PreGameView(this));
    }

    public void gameSettingsAction(){

    }

    public void startAction(){
        GameModel model = new GameModel(true);
        LinkedList<Player> players = new LinkedList<>();
        players.add(new HumanPlayer(model,1,1,"name"));
        players.add(new KiPlayer(model,2,2));
        new Thread(new Runnable() {
            @Override
            public void run() {
                model.start(players,null,null);
            }
        }).start();

    }

    public void backAction(){
        new MainMenuModel();
    }
}
