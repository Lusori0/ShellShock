package Model;

import Network.ServerHandler;
import Weapons.Weapon;


import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class InetPlayer extends Player {

    private InetAddress ipAdress;
    private int port;
    private Socket socket;
    private BufferedReader reader;
    private PrintStream writer;

    private double bulletSpawnX,bulletSpawnY,rohrwinkel,shootstrength;
    private boolean right;

    private GameModel model;

    private ServerHandler inputHandler;


    public InetPlayer(GameModel model, int team,int id,InetAddress ipAdress,int port,String name) {
        super(model, team,id,name);
        this.model = model;
        this.ipAdress = ipAdress;
        this.port = port;

    }

    public InetPlayer(GameModel model,int team,int id,String name){
        super(model,team,id,name);
        this.model = model;

    }



    @Override
    public int action(GameModel model) {
        return 0;
    }

    @Override
    public void move(GameModel model, GameMap map) {

    }

    @Override
    public void prepare(GameModel model) {

    }

    public void setWeapon(int id){
        setSelectedWeapon(Weapon.getById(id,model));
    }

    public void setShootValues(double bulletX,double bulletY,double rohrwinkel,double strength,boolean right){
        this.bulletSpawnX = bulletX;
        this.bulletSpawnY = bulletY;
        this.rohrwinkel = rohrwinkel;
        this.shootstrength = strength;
        this.right = right;
        setLockedIn(true);
    }

    @Override
    public void shoot(GameModel model) {

        getSelectedWeapon().create((int)(bulletSpawnX), (int) bulletSpawnY,rohrwinkel,shootstrength,right,getPanzer());

    }

    public InetAddress getIpAdress() {
        return ipAdress;
    }

    public int getPort() {
        return port;
    }

    public void setupTCP(Socket socket, ServerHandler inputHandler) {

        this.inputHandler = inputHandler;
        this.socket = socket;
        try {

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            if(writer == null) {
                writer = new PrintStream(socket.getOutputStream());
            }
            readTCP();

        } catch (IOException e) {
            e.printStackTrace();
        }




        sendTCP("tcptest");
    }

    public void readTCP(){

        InetPlayer player = this;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(socket.isConnected()){
                    String in = null;
                    try {
                        in = reader.readLine();
                        System.out.println("Server: " + in);
                        inputHandler.handleInput(in,player);
                    } catch (IOException e) {
                        //e.printStackTrace();
                    }
                    System.out.println(in);
                }
            }
        }).start();
    }

    public void sendTCP(String message){

        if(writer == null){
            try {
                writer = new PrintStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("output: " + message);
        writer.println(message);
        writer.flush();

    }
}
