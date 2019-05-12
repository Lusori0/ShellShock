package Network;

import Model.GameModel;
import Model.InetPlayer;
import Model.Player;

import java.net.DatagramPacket;

public class ServerHandler {

    Server server;
    Networktest networktest;
    GameModel model;

    public ServerHandler(Server server, Networktest networktest, GameModel model) {
        this.server = server;
        this.networktest = networktest;
        this.model = model;
    }

    public void handleInput(String msg, InetPlayer player){
        System.out.println(msg);

        String[] content = msg.split(":");

        switch (content[0]) {
            case "weapon":
                for (Player player1 : model.getPlayer()) {
                    if (player1.getId() == Integer.parseInt(content[1])) {
                        ((InetPlayer) player1).setWeapon(Integer.parseInt(content[2]));
                    }
                }
                break;
            case "weaponvalues":
                for (Player player1 : model.getPlayer()) {
                    if (player1.getId() == Integer.parseInt(content[1])) {
                        double bulletX = Double.parseDouble(content[2]);
                        double bulletY = Double.parseDouble(content[3]);
                        double winkel = Double.parseDouble(content[4]);
                        double strength = Double.parseDouble(content[5]);
                        boolean right = Boolean.parseBoolean(content[6]);

                        ((InetPlayer) player1).setShootValues(bulletX, bulletY, winkel, strength, right);


                    }
                }
                break;
        }
    }

    public void handleInput(String msg, DatagramPacket info){

        //MESSAGE: "<art>:<general info>:<info>..."

        String[] content = msg.split(":");

        switch (content[0]){

            case "search":

                System.out.println(info.getAddress().toString() + " | " + info.getPort());
                server.sendData("here:nameServer".getBytes(),info.getAddress(),info.getPort());
                break;

            case "join":

                //if(canjoin)
                server.sendData("joinreply:nameServer".getBytes(),info.getAddress(),info.getPort());
                int id = model.getNextId();
                InetPlayer joiningPlayer = new InetPlayer(model,2,id,info.getAddress(),info.getPort(),content[1]);
                server.getConnections().add(joiningPlayer);
                server.acceptingTCP(info.getAddress(),joiningPlayer);
                networktest.addPlayer(joiningPlayer);


                for(InetPlayer ad : server.getConnections()){
                    if(ad != joiningPlayer) {
                        server.sendToOneTCP(("addplayer:" + content[1] + ":" + joiningPlayer.getId() + ":" + joiningPlayer.getTeam()), ad);
                    }
                }

            case "move":

                try {
                    for (Player p : server.getConnections()) {
                        if(p.getId() == Integer.parseInt(content[1])) {
                            int xPos = Integer.parseInt((content[2]));
                            int yPos = Integer.parseInt(content[3]);
                            double drawWinkel = Double.parseDouble(content[4]);
                            double winkel = Double.parseDouble(content[5]);
                            double rohrwinkel = Double.parseDouble(content[6]);
                            boolean right = Boolean.parseBoolean(content[7]);

                            p.setValues(xPos, yPos,drawWinkel, winkel,rohrwinkel,right);
                        }
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException ignored) {

                }

                break;
        }




    }
}
