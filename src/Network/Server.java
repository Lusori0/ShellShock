package Network;

import Model.GameModel;
import Model.InetPlayer;
import Model.Player;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Server extends Thread {

    private DatagramSocket socket;
    private GameModel model;
    private List<InetPlayer> connections = new ArrayList<>();
    private Networktest networktest;
    private ServerHandler inputHandler;

    private ServerSocket serverTCP;

    private boolean isAccepting = true;


    public Server(GameModel model,Networktest networktest){
        this.model = model;

        this.networktest = networktest;

        this.inputHandler = new ServerHandler(this,networktest,model);


        try {
            this.socket = new DatagramSocket(1331);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        try {
            serverTCP = new ServerSocket(1331);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run(){

        while(true){

            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data,data.length);

            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }


            String message = new String(packet.getData());




            inputHandler.handleInput(message.trim(),packet);

        }
    }

    public List<InetPlayer> getConnections() {
        return connections;
    }

    public void sendData(byte[] data, InetAddress ipAdress, int port){


        DatagramPacket packet = new DatagramPacket(data,data.length,ipAdress,port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendtoAll(byte[] data) {
        for(InetPlayer p : connections){
            sendData(data,p.getIpAdress(),p.getPort());
        }
    }

    public void addConnection(InetPlayer player){
        connections.add(player);
    }

    public void sendToOneTCP(String message,InetPlayer player){
        player.sendTCP(message);
    }

    public void sendToAllTCP(String message){
        for(InetPlayer player : connections){
            player.sendTCP(message);
        }
    }

    public void setAccepting(boolean accepting) {
        isAccepting = accepting;
    }

    public void acceptingTCP(InetAddress address,InetPlayer joiningPlayer){
        isAccepting = true;
        new Thread(() -> {
            while(isAccepting) {
                Socket client = null;
                try {

                    client = serverTCP.accept();


                } catch (IOException e) {
                    e.printStackTrace();
                }



                if (client != null) {

                    for (InetPlayer player : connections) {


                        if (client.getInetAddress().equals(player.getIpAdress())) {
                            player.setupTCP(client, inputHandler);
                            for(Player data : networktest.getPlayer()){
                                if(data.equals(joiningPlayer)){
                                    sendToOneTCP(("localplayer:" + data.getName() + ":" + data.getId() + ":" + data.getTeam()), joiningPlayer);
                                }else {
                                    sendToOneTCP(("player:" + data.getName() + ":" + data.getId() + ":" + data.getTeam()), joiningPlayer);
                                }
                            }
                            isAccepting = false;
                        }
                    }
                }
            }
        }).start();
    }
}
