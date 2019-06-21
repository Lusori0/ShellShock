package Model;

import Views.PreGameView;
import Window.*;


import java.awt.image.BufferedImage;
import java.io.PrintStream;
import java.util.LinkedList;
import java.awt.*;

public class PreGameModel {

    private GameType gametype;
    private final boolean sandbox;
    PreGameView preGameView;
    private GameModel model = new GameModel();

    public PreGameModel(GameType gametype, boolean sandbox,boolean multiplayer,Profil profil_Login) {
         preGameView = new PreGameView(this);
        this.gametype = gametype;
        this.sandbox = sandbox;
        if(profil_Login != null)
        {
            preGameView.setProfils(profil_Login);
        }
        if(multiplayer)
        {

            preGameView.erzeugenOverlayMultiplayer();
            MyWindow.setContent(preGameView);
        }else {

            preGameView.erzeugenOverlaySinglePlayer();
            MyWindow.setContent(preGameView);
        }

    }

    public BufferedImage mapSelectingAction(int art,Color mapC,Color skyC){
       return model.getMap().getMapSmall(art,model,mapC,skyC);
    }

    public void startAction(int amount,int[] difficutly,Profil[] profils,int mapId,int gamemode,Color f,Color b,int[] team_human,int[] team_ai,int team_currenUser){
        LinkedList<Player> players = new LinkedList<>();
        //TODO: einfach den Team sachen einbumsen
        LinkedList<Integer> weaponsTest = new LinkedList<>();

        weaponsTest.add(1);
        weaponsTest.add(4);
        weaponsTest.add(5);

        Profil profil = new Profil("test",1,"test",1,weaponsTest,0.5f,0.5f,1);

        players.add(new HumanPlayer(model,team_currenUser,model.getNextId(), Var.activeUser));
       for(int p = 0;p<profils.length;p++)
       {

           if(profils[p] != null){
               players.add(new HumanPlayer(model,team_human[p],model.getNextId(),profils[p]));
           }else{
               p++;
           }

       }

        //Einfügen der Ai abhängig von ausgewähltem Scheiß
        if(amount >0)
        {
            for(int i = 0;i<amount;i++)
            {
                System.out.println(Var.activeUser);
                players.add(new KiPlayer(model,team_ai[i],model.getNextId(), difficutly[i],Var.activeUser));
            }
        } else{}

        new Thread(() -> model.start(players, sandbox,b,f,mapId,gamemode)).start();


    }

    public void backAction(){
        new MainMenuModel();
    }

    public void startAction(LinkedList<Player> players, Color background, Color foreground){
        GameModel model = new GameModel();
        new Thread(() -> model.start(players,sandbox, background, foreground,6,1)).start();

    }
}

