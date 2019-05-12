package Network;

import Model.GameModel;
import Model.HumanPlayer;
import Model.InetPlayer;
import Model.Player;

import java.net.DatagramPacket;

public class ClientHandler {

    Networktest networktest;
    Client client;
    GameModel model;

    public ClientHandler(Networktest networktest, Client client, GameModel model) {
        this.networktest = networktest;
        this.client = client;
        this.model = model;
    }

    public void handle(String msg) {

        String[] content = msg.split(":");

        switch (content[0]) {
            case "localplayer":
                networktest.addPlayer(new HumanPlayer(model,Integer.parseInt(content[3]),Integer.parseInt(content[2]),content[1]));
                break;
            case "player":
                networktest.addPlayer(new InetPlayer(model,Integer.parseInt(content[3]),Integer.parseInt(content[2]),content[1]));
                break;

            case "start":
                System.out.println("start");
                networktest.startGame();
                break;

            case "addplayer":
                networktest.addPlayer(new InetPlayer(model,Integer.parseInt(content[3]),Integer.parseInt(content[2]),content[1]));
                break;

            case "weapon":
                for(Player player : model.getPlayer()){
                    if(player.getId() == Integer.parseInt(content[1])){
                        if(!player.isLocalHuman()) {
                            ((InetPlayer) player).setWeapon(Integer.parseInt(content[2]));
                        }
                    }
                }
                break;
            case "weaponvalues":
                for(Player player : model.getPlayer()){
                    if(player.getId() == Integer.parseInt(content[1]) && !player.isLocalHuman()){
                        double bulletX = Double.parseDouble(content[2]);
                        double bulletY = Double.parseDouble(content[3]);
                        double winkel = Double.parseDouble(content[4]);
                        double strength = Double.parseDouble(content[5]);
                        boolean right = Boolean.parseBoolean(content[6]);

                        ((InetPlayer)player).setShootValues(bulletX,bulletY,winkel,strength,right);


                    }
                }
                break;

        }
    }

    public void handle(String msg, DatagramPacket info){

        //MESSAGE: "<art>:<general info>:<info>..."

        String[] content = msg.split(":");


        switch (content[0]){

            case "here":
                System.out.println(content[1] + " | " + info.getAddress());
                networktest.addServer(content[1],info.getAddress());
                break;

            case "joinreply":
                client.connectTCP(info.getAddress());
                networktest.setHostView();
                break;

            case "move":
                try {
                    for (Player p : model.getPlayer()) {
                        if (p.getId() == Integer.parseInt(content[1])) {

                            int xPos = Integer.parseInt(content[2]);
                            int yPos = Integer.parseInt(content[3]);
                            double drawWinkel = Double.parseDouble(content[4]);
                            double winkel = Double.parseDouble(content[5]);
                            double rohrwinkel = Double.parseDouble(content[6]);
                            boolean right = Boolean.parseBoolean(content[7]);

                            p.setValues(xPos,yPos,drawWinkel,winkel,rohrwinkel,right);
                        }
                    }
                }catch (NumberFormatException | ArrayIndexOutOfBoundsException ignored){}

                break;

        }


    }
}
