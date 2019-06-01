package Model;

import Views.PreGameView;
import Window.MyWindow;

import java.util.LinkedList;
import java.awt.*;

public class PreGameModel {

    private GameType gametype;
    private final boolean sandbox;

    public PreGameModel(GameType gametype, boolean sandbox) {
        this.gametype = gametype;
        this.sandbox = sandbox;
        MyWindow.setContent(new PreGameView(this));
    }

    public void gameSettingsAction(){

    }

    public void startAction(){
        GameModel model = new GameModel();
        LinkedList<Player> players = new LinkedList<>();
        players.add(new HumanPlayer(model,1,1,"name"));
        players.add(new KiPlayer(model,2,2, 10));
        new Thread(() -> model.start(players,null,null, true,Color.CYAN,Color.BLACK)).start();

    }

    public void backAction(){
        new MainMenuModel();
    }

    public void startAction(LinkedList<Player> players, Color background, Color foreground){
        GameModel model = new GameModel();
        new Thread(() -> model.start(players,null,null, sandbox, background, foreground)).start();

    }
}

/*
////////////////Reminder/////////////////////////////
GameModel model = new GameModel();
        LinkedList<Player> players = new LinkedList<>();
        players.add(new HumanPlayer(model,1,1,"name"));
        players.add(new KiPlayer(model,2,2, 10));
        new Thread(new Runnable() {
            @Override
            public void run() {
                model.start(players,null,null, true,Color.WHITE,Color.BLACK);
            }
        }).start();
////////////////////////////////////////////////////
 */